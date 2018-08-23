package com.btandjaja.www.bakingrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class VideoActivity extends AppCompatActivity {
    private String mVideoUrl;
    private PlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;

    private boolean mAutoPlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getUrl();

    }

    private void getUrl() {
        Intent intent = getIntent();
        String extraString = getResources().getString(R.string.video_url);
        mVideoUrl = intent.hasExtra(extraString) ? intent.getExtras().getString(extraString) : null;
    }
}
