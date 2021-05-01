package com.example.videoplayer;

import android.app.ActionBar;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

// This activity will play a video based on one that was selected from
// the main activity. On my local machine and on a test project, I was storing a
// video within the RAW directory within the res directory.
// This class will need to take in the full path of the video that was selected
// within the main activity
public class VideoPlayerActivity extends AppCompatActivity
{
    // For testing purposes, this string represents the name of a video stored within
    // the RES folder. We will need to pass in the absolute path from the recyclerView
    // to load in the video selected.
    private static final String VIDEO_SAMPLE = "rick";
    private VideoView mVideoView;
    private int mCurrentPosition = 0;
    private static final String PLAYBACK_TIME = "play_time";
    private String videoUri;
    // This handles parsing the directory location for video playback
    private Uri getMedia(String mediaName)
    {
        //return Uri.parse("android.resource://" + getPackageName() +
          //      "/raw/" + mediaName);
        return Uri.parse(videoUri);
    }

    //  Initializes the player within the activity
    private void initializePlayer()
    {
        Uri videoUri = getMedia(VIDEO_SAMPLE);
        mVideoView.setVideoURI(videoUri);

        if(mCurrentPosition > 0)
        {
            mVideoView.seekTo(mCurrentPosition);
        }
        else
        {
            mVideoView.seekTo(1);
        }

        // Automatically start video playback
        mVideoView.start();

        // Lets the user know when video playback has ended
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(VideoPlayerActivity.this, "Playback completed",
                        Toast.LENGTH_SHORT).show();
                mVideoView.seekTo(1);
            }
        });

    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }

    private void releasePlayer()
    {
        mVideoView.stopPlayback();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        initializePlayer();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        releasePlayer();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
        {
            mVideoView.pause();
        }
    }

    // This function makes the video play in full screen
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_player);
        // Add the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        if(savedInstanceState != null)
        {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        // Bind the controls to the video player
        mVideoView = findViewById(R.id.videoview);
        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);

        // Get the intent from the main activity
        // We will need to pass in the absolute path from the selected item within
        // the main activity.
        Intent intent = getIntent();
        String value = intent.getStringExtra("movie_directory");

        videoUri = value;

        //Log.d("TEST", value);
    }


}