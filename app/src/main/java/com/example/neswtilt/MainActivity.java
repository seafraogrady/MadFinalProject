package com.example.neswtilt;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView tvx, tvy, tvz, tvDirection;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    Button right, left,up,down;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        tvDirection = findViewById(R.id.tvDirection);
        right = findViewById(R.id.right);
        left = findViewById(R.id.left);
        up = findViewById(R.id.down);


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @SuppressLint("SetTextI18n")
    public void onSensorChanged(SensorEvent event) {

        float x, y, z;
        x = Math.abs(event.values[0]);
        y = event.values[1];
        z = Math.abs(event.values[2]);

        if(y < -2)
        {
            left.performClick();
            left.setPressed(true);
            left.invalidate();
            left.setPressed(false);
            left.invalidate();
        }
        else if(y > 2)
        {
            right.performClick();
            right.setPressed(true);
            right.invalidate();
            right.setPressed(false);
            right.invalidate();
        }
        else if(x>9)
        {
            tvDirection.setText("Bottom");
        }
        else if(x<3)
        {
            tvDirection.setText("Top");
        }



    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not using
    }
}
