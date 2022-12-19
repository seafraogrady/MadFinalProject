package com.example.neswtilt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GamePlay extends AppCompatActivity implements SensorEventListener {
    int sequenceCount = 4, n = 0;

    private final int RED = 2;
    private final int BLUE = 1;
    private final int YELLOW = 3;
    private final int GREEN = 4;
    int score;

    boolean match = true;
    int[] gameSequence = new int[100];
    public int clickCount = 0, highScore = 0, k = 0;
    TextView tvDirection, tvScore;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    Button red, blue, green, yellow;
    private boolean isFlat = false;
    int arrayIndex = 0;
    Random r = new Random();
    View view;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        tvScore = findViewById(R.id.tvScore);

        red = findViewById(R.id.red);
        blue = findViewById(R.id.blue);
        green = findViewById(R.id.green);
        yellow = findViewById(R.id.yellow);
        Intent i = getIntent();
        score = getIntent().getIntExtra("score", -1);

        sequenceCount = getIntent().getIntExtra("sequenceCount", -1);
        gameSequence = getIntent().getIntArrayExtra("seqArray");


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
view = new View(this);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);

    }



    //ACCELEROMETER
    @SuppressLint("SetTextI18n")
    public void onSensorChanged(SensorEvent event) {

        float x, y, z;
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];
        if (x < 1 && x > -1 && y < 1 && y > -1) if (!isFlat) {
            isFlat = true;

        }

        // left tilt
        int maxTilt = 3;
        if (y < -maxTilt) {
            if (isFlat) {
                isFlat = false;
                blue.performClick();
                blue.setPressed(true);
                blue.invalidate();
                blue.setPressed(false);
                blue.invalidate();
            //    checkAnswer(1);
                clickCount++;
            }

        }
        if (y > maxTilt) {

            if (isFlat) {
                isFlat = false;
                red.performClick();
                red.setPressed(true);
                red.invalidate();
                red.setPressed(false);
               red.invalidate();
                clickCount++;
               // checkAnswer(2);
            }

        }
        if (x < -maxTilt) {
            if (isFlat) {
                isFlat = false;
                green.performClick();
                green.setPressed(true);
               green.invalidate();
               green.setPressed(false);
               green.invalidate();
                clickCount++;
          //      checkAnswer(4);
            }
        }

        if (x > maxTilt) {

            if (isFlat) {
                isFlat = false;
                yellow.performClick();
                yellow.setPressed(true);
                yellow.invalidate();
              yellow.setPressed(false);
              yellow.invalidate();

           //     checkAnswer(3);
clickCount++;

            }
            if (clickCount == gameSequence.length) {
                Intent intent = new Intent(view.getContext(),GameOver.class);
                intent.putExtra("score", score);
                startActivity(intent);
                Toast.makeText(this, "yay", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "ohno", Toast.LENGTH_SHORT).show();
            }
        }


    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void checkAnswer(int colorIndex) {




            /** if (sequenceCount+1 < sequenceCount) {
             if (gameSequence[sequenceCount] == colorIndex) {
             score++;
             tvScore.setText(String.valueOf(score));
             sequenceCount++;
             } else {
             Intent intent = new Intent(view.getContext(),GameOver.class);
             intent.putExtra("score", score);
             startActivity(intent);
             }
             } else if (sequenceCount+1 >= sequenceCount)
             {
             if (gameSequence[sequenceCount] == colorIndex)
             {
             score++;
             tvScore.setText(String.valueOf(score));
             Intent Next = new Intent(view.getContext(), MainActivity.class);
             MainActivity.score = score;
             MainActivity.sequenceCount = sequenceCount+2;
             startActivity(Next);
             finish();

             }
             else
             {
             Intent go = new Intent(GamePlay.this, GameOver.class);
             startActivity(go);
             }
             }**/
        }
        public void highscore(View view) {
        Intent i = new Intent(GamePlay.this, GameOver.class);
        i.putExtra("score", String.valueOf(clickCount));
        GamePlay.this.startActivity(i);
        }
    }





