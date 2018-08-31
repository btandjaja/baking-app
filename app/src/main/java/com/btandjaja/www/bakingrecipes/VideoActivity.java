package com.btandjaja.www.bakingrecipes;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity {
    private String mVideoUrl, mRecipeName;
    private int mStep;
    private SimpleExoPlayer mPlayer;
    private BandwidthMeter mBandwidthMeter;
    private DataSource.Factory mMediaDataSourceFactory;
    private TrackSelector mTrackSelector;

    private boolean mAutoPlay, mPlayWhenReady;
    private long mPlayBackPosition;
    private int mCurrentWindow;

    @BindView(R.id.exoplayer_view) PlayerView mPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        setTitle(getString(R.string.activity_title_video));
        checkSavedInstance(savedInstanceState);
        getUrlPosition();
        setTitle(getVideoTitle());
        mAutoPlay = true;
        mBandwidthMeter = new DefaultBandwidthMeter();
        mMediaDataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getResources().getString(R.string.application_name)),
                (TransferListener<? super DataSource>) mBandwidthMeter);

        //TODO window = new TimeLine.Window();
//        initializePlayer(Uri.parse(mVideoUrl));
    }

    private void checkSavedInstance(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mPlayWhenReady = true;
            mCurrentWindow = 0;
            mPlayBackPosition = 0;
        } else {
            mPlayWhenReady = savedInstanceState.getBoolean(DetailActivity.KEY_PLAY_WHEN_READY);
            mCurrentWindow = savedInstanceState.getInt(DetailActivity.KEY_WINDOW);
            mPlayBackPosition = savedInstanceState.getLong(DetailActivity.KEY_POSITION);
        }
    }

    private void getUrlPosition() {
        Intent intent = getIntent();
        String stringUrl = getResources().getString(R.string.video_url);
        mVideoUrl = intent.hasExtra(stringUrl) ? intent.getExtras().getString(stringUrl) : null;
        if (mVideoUrl == null) {
            Toast.makeText(this, "Video may have been removed!", Toast.LENGTH_LONG).show();
            finish();
        }
        // if it has video url, it has step number
        String stepString = getResources().getString(R.string.step);
        mStep = intent.getExtras().getInt(stepString);

        String nameString = getResources().getString(R.string.name);
        mRecipeName = intent.getExtras().getString(nameString);
    }

    private String getVideoTitle() {
        return mRecipeName + " " + getResources().getString(R.string.step) + ": " + mStep;
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        updateStartPosition();
        outState.putBoolean(DetailActivity.KEY_PLAY_WHEN_READY, mPlayWhenReady);
        outState.putInt(DetailActivity.KEY_WINDOW, mCurrentWindow);
        outState.putLong(DetailActivity.KEY_POSITION, mPlayBackPosition);
        super.onSaveInstanceState(outState);
    }

    private void updateStartPosition() {
        mPlayBackPosition = mPlayer.getCurrentPosition();
        mCurrentWindow = mPlayer.getCurrentWindowIndex();
        mPlayWhenReady = mPlayer.getPlayWhenReady();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= 23) {
            initializePlayer(Uri.parse(mVideoUrl));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < 23 || mPlayer == null) {
            initializePlayer(Uri.parse(mVideoUrl));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlayer.stop(true);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
}
