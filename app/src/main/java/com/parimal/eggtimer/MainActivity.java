package com.parimal.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    TextView timer;
    SeekBar timeSeekBar;
    Boolean timerIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;
    MediaPlayer mediaPlayer;
    Button musicButton;
    public void resetTime() {
        timeSeekBar.setEnabled(true);
        timeSeekBar.setProgress(30);
        goButton.setText("GO!");
        timer.setText("00:30");
        countDownTimer.cancel();
        timerIsActive = false;
    }

    public void StopMusic(View view) {
            mediaPlayer.stop();
    }

    public void timeControl(View view) {
        if (timerIsActive) {

            resetTime();

        } else {
            timerIsActive = true;
            timeSeekBar.setEnabled(false);
            goButton.setText("STOP!");

            countDownTimer = new CountDownTimer(timeSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.jack);
                    mediaPlayer.start();
                    musicButton.setVisibility(View.VISIBLE);
                    resetTime();
                }
            }.start();
        }
    }

    @SuppressLint("SetTextI18n")
    public void updateTimer(int secondLeft) {
        int minutes = secondLeft / 60;
        int second = secondLeft - (minutes * 60);
        String secondString = Integer.toString(second);
        if (second < 10) // this is just for looks, Os if there is single so just put 0, like instead of 5:7 it shows 5:07
            secondString = "0" + secondString;
        timer.setText(minutes + " : " + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        timeSeekBar = (SeekBar) findViewById(R.id.timeSeekBar);
        timer = (TextView) findViewById(R.id.timerTextView);
        goButton = (Button) findViewById(R.id.startStop);
        musicButton = (Button) findViewById(R.id.musicButon) ;
        timeSeekBar.setMax(600);
        timeSeekBar.setProgress(30);

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}