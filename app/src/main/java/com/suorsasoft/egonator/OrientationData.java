package com.suorsasoft.egonator;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationData implements SensorEventListener {

    private SensorManager manageri;
    private Sensor acceleroMeter;
    private Sensor magnoMeter;

    private float[] accelOutput;
    private float[] magnoOutput;

    private float[] orientation = new float[3];
    public float[] getOrientation() {return orientation;}

    private float[] startOrientation = null;
    public float[] getStartOrientation() {return startOrientation;}

    public void newGame() {startOrientation = null;}

    public OrientationData() {
        manageri = (SensorManager)Vakiot.NYKYINEN_KONTEKSTI.getSystemService(Context.SENSOR_SERVICE);
        acceleroMeter = manageri.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnoMeter = manageri.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void register() {
        manageri.registerListener(this, acceleroMeter, SensorManager.SENSOR_DELAY_GAME);
        manageri.registerListener(this, magnoMeter, SensorManager.SENSOR_DELAY_GAME);
    }

    public void pause() {manageri.unregisterListener(this);}

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelOutput = event.values;
        } else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magnoOutput = event.values;
        }

        if(accelOutput != null && magnoOutput != null) {
            float[] R = new float[9];
            float[] I = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, accelOutput, magnoOutput);
            if(success) {
                SensorManager.getOrientation(R, orientation);
                if(startOrientation == null) {
                    startOrientation = new float[orientation.length];
                    System.arraycopy(orientation, 0, startOrientation, 0, orientation.length);
                }
            }
        }
    }
}
