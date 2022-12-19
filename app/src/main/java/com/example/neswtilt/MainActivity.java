package com.example.neswtilt;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {


    static int sequenceCount = 4;
    int n = 0;

    private final int RED = 2;
    private final int BLUE = 1;
    private final int YELLOW = 3;
    private final int GREEN = 4;
    boolean match = true;
    int[] gameSequence = new int[100];
    public int clickCount = 0;
    public int highScore = 0;
    public int k = 0;
    public static int score = 0;
    TextView tvDirection, tvScore;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    Button red, blue, green, yellow;
    private boolean isFlat = false;
    int arrayIndex = 0;
    Random r = new Random();
    final Handler handler = new Handler();
    public boolean pressed;

    CountDownTimer ct = new CountDownTimer(6000, 1500) {

        public void onTick(long millisUntilFinished) {
            oneButton();
        }

        public void onFinish() {
pressed = false;


            for (int i = 0; i < arrayIndex; i++) ;
            Log.d("game sequence", String.valueOf(gameSequence));

            Intent i = new Intent(MainActivity.this,GamePlay.class);
            i.putExtra("sequenceCount",sequenceCount);
            i.putExtra("Array",gameSequence);

            i.putExtra("score",String.valueOf(score));
            startActivity(i);



        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        tvScore = findViewById(R.id.tvScore);
        tvDirection = findViewById(R.id.tvDirection);
        red = findViewById(R.id.red);
        blue = findViewById(R.id.blue);
        green = findViewById(R.id.green);
        yellow = findViewById(R.id.yellow);


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }




    public void play(View view) {
        {
            if(!pressed) {
                ct.start();
                pressed = true;
            }
        }
    }

    //RANDOM SEQUENCE
    private void oneButton() {
        n = getRandom(sequenceCount);

        switch (n) {
            case 1:
                flashButton(blue);
                gameSequence[arrayIndex++] = BLUE;
                break;
            case 2:
                flashButton(red);
                gameSequence[arrayIndex++] = RED;
                break;
            case 3:
                flashButton(yellow);
                gameSequence[arrayIndex++] = GREEN;
                break;
            case 4:
                flashButton(green);
                gameSequence[arrayIndex++] = YELLOW;
                break;
            default:
                break;
        }


    }


    private void flashButton(Button fb) {

        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {

                fb.setPressed(true);
                fb.invalidate();
                fb.performClick();
                Handler handler1 = new Handler();
                Runnable r1 = new Runnable() {
                    public void run() {
                        fb.setPressed(false);
                        fb.invalidate();
                    }
                };
                handler1.postDelayed(r1, 600);

            } // end runnable
        };
        handler.postDelayed(r, 600);
    }




    private int getRandom(int maxValue) {
        return ((int) ((Math.random() * maxValue) + 1));
    }



    }

















