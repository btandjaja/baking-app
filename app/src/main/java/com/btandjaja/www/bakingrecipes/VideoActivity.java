package com.btandjaja.www.bakingrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class VideoActivity extends AppCompatActivity {
    private final String KEY_PLAY_WHEN_READY = "play_when_ready";
    private final String KEY_WINDOW = "window";
    private final String KEY_POSITION = "position";

    private String mVideoUrl;
    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;

    private boolean mAutoPlay, mPlayWhenReady;
    private long mPlayBackPosition;
    private int mCurrentWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setTitle(getString(R.string.activity_title_video));
        checkSavedInstance(savedInstanceState);
        getUrl();
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

    private void getUrl() {
        Intent intent = getIntent();
        String extraString = getResources().getString(R.string.video_url);
        mVideoUrl = intent.hasExtra(extraString) ? intent.getExtras().getString(extraString) : null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        updateStartPosition();
        outState.putBoolean(KEY_PLAY_WHEN_READY, mPlayWhenReady);
        outState.putInt(KEY_WINDOW, mCurrentWindow);
        outState.putLong(KEY_POSITION, mPlayBackPosition);
        super.onSaveInstanceState(outState);
    }

    private void updateStartPosition() {
        mPlayBackPosition = mPlayer.getCurrentPosition();
        mCurrentWindow = mPlayer.getCurrentWindowIndex();
        mPlayWhenReady = mPlayer.getPlayWhenReady();
    }
}
