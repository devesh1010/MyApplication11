package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "MainActivity";
    private SensorManager sensorManager;
    private Sensor mLight;
    private Sensor accel;

    private TextView light, x,y,z;
    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        light = (TextView) findViewById(R.id.textView);
        x = (TextView) findViewById(R.id.textView2);
        y = (TextView) findViewById(R.id.textView3);
        z = (TextView) findViewById(R.id.textView4);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(mLight == null)
        {
                Log.d(TAG, "Light sensor is not present");
        }
        accel= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(final SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        float lux = event.values[0];
        // Do something with this sensor value.
        Log.d(TAG, "onSensorChanged: Lux: " + lux);
        Log.d(TAG, "onSensorChanged: " + event.sensor.getType());

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.d(TAG, "onSensorChanged: X: " + event.values[0]);
            Log.d(TAG, "onSensorChanged: Y: " + event.values[1]);
            Log.d(TAG, "onSensorChanged: Z: " + event.values[2]);

        }


    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                light.setText(Float.toString(event.values[0]));
            }
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                x.setText(Float.toString(event.values[0]));
                y.setText(Float.toString(event.values[1]));
                z.setText(Float.toString(event.values[2]));
            }
        }
    });
}
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}

