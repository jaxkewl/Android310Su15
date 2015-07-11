package com.uw.android310.lesson3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG_SENSORS = "Sensors";
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.d(TAG_SENSORS, deviceSensors.toString());

        Sensor magneticFieldSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticFieldSensor != null){
            // Success! There's a magnetometer.
            logSensorData(magneticFieldSensor);
        }

        Sensor gravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        if (gravitySensor != null){
            List<Sensor> gravSensors = mSensorManager.getSensorList(Sensor.TYPE_GRAVITY);
            for (int i = 0; i < gravSensors.size(); i++) {
                if ((gravSensors.get(i).getVendor().contains("Google Inc.")) &&
                        (gravSensors.get(i).getVersion() == 3)){
                    // Use the version 3 gravity sensor.
                    gravitySensor = gravSensors.get(i);
                }
            }

        } else{
            // Use the accelerometer.
            if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
                gravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            } else {
                Log.e(TAG_SENSORS, "Sorry, there are no accelerometers on your device. You can't play this game.");
            }
        }

        if (gravitySensor != null) {
            logSensorData(gravitySensor);
        }
    }

    private void logSensorData(Sensor sensor) {
        float resolution = sensor.getResolution();
        float maxRange = sensor.getMaximumRange();
        float power = sensor.getPower();
        float minDelay = sensor.getMinDelay();

        Log.d(TAG_SENSORS, String.format("Resolution: %s", resolution));
        Log.d(TAG_SENSORS, String.format("Max Range: %s", maxRange));
        Log.d(TAG_SENSORS, String.format("Power: %s", power));
        Log.d(TAG_SENSORS, String.format("Min Delay: %s", minDelay));
    }
}
