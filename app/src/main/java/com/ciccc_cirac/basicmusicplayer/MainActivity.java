package com.ciccc_cirac.basicmusicplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    // Create an object for Media Player
    MediaPlayer player;
    ImageButton playButton, stopButton, resetButton;
    TextView txt_totalDuration, songCurrentDurationLabel;  //added code for seek bar
    boolean play_reset = true;

    private SeekBar songProgressBar;  //added code for seek bar
    // Handler to update UI timer, progress bar etc,.
    private Handler mHandler = new Handler();//added code for seek bar
    private Utilities utils;//added code for seek bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //Instantiate an object of media player
        player = MediaPlayer.create(this,  R.raw.shapeofyou);
        utils = new Utilities();                //added code for seek bar
        playButton = (ImageButton) this.findViewById(R.id.play);
        stopButton = (ImageButton) this.findViewById(R.id.stop);
        resetButton = (ImageButton) this.findViewById(R.id.reset);
        songProgressBar = (SeekBar)findViewById(R.id.seekBar);          //added code for seek bar
        txt_totalDuration = (TextView)findViewById(R.id.totalDuration); //added code for seek bar
        songCurrentDurationLabel = (TextView)findViewById(R.id.songCurrentDurationLabel); //added code for seek bar
     
        playButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        songProgressBar.setOnSeekBarChangeListener(this);  //added code for seek bar
    }



        @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
// If the user presses the buttonplay/pause button
// if no player instance is available, then create a media player first
                if (play_reset) {
                    play_reset = false;
                    player.setLooping(false); // Set looping
                }
                playPause();
                break;
            case R.id.stop:
                if (!play_reset) {
// If the user presses the stop button
                    player.stop();
// change the image of the buttonplay button to buttonplay
                    playButton.setImageResource(R.drawable.buttonplay);
                    Toast.makeText(this, R.string.stopped, Toast.LENGTH_SHORT).show();
                    try {
                        player.prepare();
                    } catch (IllegalStateException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.reset:
                if (!play_reset) {
// If the user presses the reset button
                    player.reset();
// change the image of the buttonplay button to buttonplay
                    playButton.setImageResource(R.drawable.buttonplay);
                    Toast.makeText(this, R.string.reset, Toast.LENGTH_SHORT).show();
// Release media instance to system
                    player.release();
                    play_reset = true;
                    break;
                }
        }
    }
    // when the activity is paused, then pause the music playback
    @Override
    public void onPause() {
        super.onPause();
        player.reset();
// change the image of the play button to play
        playButton.setImageResource(R.drawable.play);
        Toast.makeText(this, R.string.reset, Toast.LENGTH_SHORT).show();
// Release media instance to system
        player.release();
        play_reset = true;
    }
    // Toggle between the buttonplay and pause
    private void playPause() {
        //todo added code for seek bar
        // set Progress bar values
        songProgressBar.setProgress(0);
        songProgressBar.setMax(100);
        updateProgressBar();
// if the music is playing then pause the music playback
        if(player.isPlaying()) {
            player.pause();
// change the image of the play button to play
            playButton.setImageResource(R.drawable.buttonplay);
            Toast.makeText(this, R.string.paused, Toast.LENGTH_SHORT).show();
        }
// Music is paused, start, or resume playback
        else {
// change the image of the play button to pause
            playButton.setImageResource(R.drawable.pause);
            player.start();
            Toast.makeText(this, R.string.isPlaying, Toast.LENGTH_SHORT).show();
        }
    }
    //todo added code for seek bar
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }
    //todo added code for seek bar
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }
    //todo added code for seek bar
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = player.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        player.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }
    //todo added code for seek bar
    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = player.getDuration();
            long currentDuration = player.getCurrentPosition();

            // Displaying Total Duration time
            txt_totalDuration.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };
}

