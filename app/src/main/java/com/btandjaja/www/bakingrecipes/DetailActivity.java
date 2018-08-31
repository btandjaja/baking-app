package com.btandjaja.www.bakingrecipes;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.btandjaja.www.bakingrecipes.data.InstructionAdapter;
import com.btandjaja.www.bakingrecipes.data.Recipe;
import com.btandjaja.www.bakingrecipes.ui.StepsFragment;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

public class DetailActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener,
        InstructionAdapter.InstructionAdapterOnClickHandler {
    public static final String KEY_PLAY_WHEN_READY = "play_when_ready";
    public static final String KEY_WINDOW = "window";
    public static final String KEY_POSITION = "position";
    public static Recipe mRecipe;

    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;
    private BandwidthMeter mBandwidthMeter;
    private DataSource.Factory mMediaDataSourceFactory;
    private TrackSelector mTrackSelector;

    private boolean mAutoPlay, mPlayWhenReady;
    private long mPlayBackPosition;
    private int mCurrentWindow;

    private Bundle mSavedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
        getRecipe();
        setContentView(R.layout.activity_detail);
    }

    private void getRecipe() {
        Bundle data = getIntent().getExtras();
        if (data == null) {
            Toast.makeText(this, getResources().getString(R.string.invalid_recipe), Toast.LENGTH_LONG).show();
            finish();
        }
        mRecipe = data.getParcelable(Recipe.RECIPE);
    }

    /**
     * Never called.
     */
    @Override
    public void onStepSelected(int position) {
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_LONG).show();
    }

    /**
     * Video logic for tablet and phone
     */
    @Override
    public void OnClick(int position) {
        String videoUrl = mRecipe.getVideoUrlList().get(position);
        if (ListOfRecipesActivity.mTabletMode) {
            mPlayerView = findViewById(R.id.exoplayer_view);
            fillInDescription(position);
            if (TextUtils.isEmpty(videoUrl)) {
                if (mPlayer != null) mPlayer.stop();
                invalidVideoToast();
            } else {
                displayVideo(position);
            }
        } else {
            if (TextUtils.isEmpty(videoUrl)) {
                invalidVideoToast();
            } else {
                if (mPlayer != null) releasePlayer();
                startIntent(videoUrl, position);
            }
        }
    }

    private void fillInDescription(int position) {
        TextView shortDesription = findViewById(R.id.tv_short_description_tablet);
        TextView description = findViewById(R.id.tv_description_tablet);
        if (position == 0) {
            String servingSize = getResources().getString(R.string.number_of_servings) + mRecipe.getServings();
            String ingredients = getResources().getString(R.string.ingredients) + " and ";
            shortDesription.setText(ingredients + servingSize);
            description.setText(mRecipe.getIngredientsString());
        } else {
            shortDesription.setText(mRecipe.getShortDescriptionList().get(position));
            description.setText(mRecipe.getDescriptionList().get(position));
        }
    }

    private void startIntent(String videoUrl, int position) {
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra(getResources().getString(R.string.video_url), videoUrl);
        intent.putExtra(getResources().getString(R.string.step), position);
        intent.putExtra(getResources().getString(R.string.name), mRecipe.getRecipeName());
        startActivity(intent);
    }

    private void invalidVideoToast() {
        Toast.makeText(this, getResources().getString(R.string.video_not_available), Toast.LENGTH_SHORT).show();
    }

    private void displayVideo(int position) {
        checkSavedInstance(mSavedInstanceState);
        mAutoPlay = true;
        mBandwidthMeter = new DefaultBandwidthMeter();
        mMediaDataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getResources().getString(R.string.application_name)),
                (TransferListener<? super DataSource>) mBandwidthMeter);
        initializePlayer(Uri.parse(mRecipe.getVideoUrlList().get(position)));
    }

    private void initializePlayer(Uri mediaUri) {
        mPlayerView.requestFocus();
        TrackSelection.Factory videoTrackSelection = new AdaptiveTrackSelection.Factory(mBandwidthMeter);
        mTrackSelector = new DefaultTrackSelector(videoTrackSelection);

        mPlayer = ExoPlayerFactory.newSimpleInstance(this, mTrackSelector);

        mPlayerView.setPlayer(mPlayer);

        mPlayer.setPlayWhenReady(mAutoPlay);

        MediaSource mediaSource = new ExtractorMediaSource.Factory(mMediaDataSourceFactory)
                .createMediaSource(mediaUri);

        boolean haveStartPosition = mCurrentWindow != C.INDEX_UNSET;

        if (haveStartPosition) {
            mPlayer.seekTo(mCurrentWindow, mPlayBackPosition);
        }

        mPlayer.prepare(mediaSource, !haveStartPosition, false);
    }

    private void checkSavedInstance(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mPlayWhenReady = true;
            mCurrentWindow = 0;
            mPlayBackPosition = 0;
        } else {
            mPlayWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
            mCurrentWindow = savedInstanceState.getInt(KEY_WINDOW);
            mPlayBackPosition = savedInstanceState.getLong(KEY_POSITION);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mPlayer != null) {
            updateStartPosition();
            outState.putBoolean(DetailActivity.KEY_PLAY_WHEN_READY, mPlayWhenReady);
            outState.putInt(DetailActivity.KEY_WINDOW, mCurrentWindow);
            outState.putLong(DetailActivity.KEY_POSITION, mPlayBackPosition);
        }
        super.onSaveInstanceState(outState);
    }

    private void updateStartPosition() {
        mPlayBackPosition = mPlayer.getCurrentPosition();
        mCurrentWindow = mPlayer.getCurrentWindowIndex();
        mPlayWhenReady = mPlayer.getPlayWhenReady();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) releasePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPlayer != null) mPlayer.stop(true);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            updateStartPosition();
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
}
