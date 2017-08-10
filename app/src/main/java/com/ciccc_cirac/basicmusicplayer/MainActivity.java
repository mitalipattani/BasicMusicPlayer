package com.ciccc_cirac.basicmusicplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer player;
    ImageButton playerButton, stopButton, resetButton;
    boolean play_reset = true;
    private SeekBar seekbar;
    private TextView tx1,tx2,tx3;
    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx1 = (TextView)findViewById(R.id.textView2);
        tx2 = (TextView)findViewById(R.id.textView3);
        tx3 = (TextView)findViewById(R.id.textView4);
        tx3.setText("Song.mp3");
        seekbar = (SeekBar)findViewById(R.id.seekBar);
        seekbar.setClickable(false);

        // Get the button from the view
        playerButton = (ImageButton) this.findViewById(R.id.play);
        playerButton.setOnClickListener(this);
        stopButton = (ImageButton) this.findViewById(R.id.stop);
        stopButton.setOnClickListener(this);
        resetButton = (ImageButton) this.findViewById(R.id.reset);
        resetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
// If the user presses the buttonplay/pause button
// if no player instance is available, then create a media player first
                if (play_reset) {
                    play_reset = false;
                    player = MediaPlayer.create(this, R.raw.shapeofyou);
                    player.setLooping(false); // Set looping
                }
                playPause();
                break;
            case R.id.stop:
                if (!play_reset) {
// If the user presses the stop button
                    player.stop();
// change the image of the buttonplay button to buttonplay
                    playerButton.setImageResource(R.drawable.buttonplay);
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
                    playerButton.setImageResource(R.drawable.buttonplay);
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
// change the image of the buttonplay button to buttonplay
        playerButton.setImageResource(R.drawable.buttonplay);
        Toast.makeText(this, R.string.reset, Toast.LENGTH_SHORT).show();
// Release media instance to system
        player.release();
        play_reset = true;
    }
    // Toggle between the buttonplay and pause
    private void playPause() {
// if the music is playing then pause the music playback
        if(player.isPlaying()) {
            player.pause();
// change the image of the buttonplay button to buttonplay
            playerButton.setImageResource(R.drawable.buttonplay);
            Toast.makeText(this, R.string.paused, Toast.LENGTH_SHORT).show();
        }
// Music is paused, start, or resume playback
        else {
// change the image of the buttonplay button to pause
            playerButton.setImageResource(R.drawable.pause);
            player.start();
            finalTime = player.getDuration();
            startTime = player.getCurrentPosition();

            if (oneTimeOnly == 0) {
                seekbar.setMax((int) finalTime);
                oneTimeOnly = 1;
            }
            tx2.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    finalTime)))
            );

            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    startTime)))
            );
            startTime = player.getCurrentPosition();
            seekbar.setProgress((int)startTime);
            startTime = player.getCurrentPosition();
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            Toast.makeText(this, R.string.isPlaying, Toast.LENGTH_SHORT).show();
        }
    }
}

