package com.example.androidgame_dogvsghost.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.androidgame_dogvsghost.callbacks.CallBack_SensorDirectionChanged;
import com.example.androidgame_dogvsghost.controllers.GameManager;

public class MyAccelerometer implements SensorEventListener{

    private SensorManager sensorManager;
    private CallBack_SensorDirectionChanged callBack_sensorDirectionChanged;
    private Sensor accelerometer;
    private String direction;
    private double x;
    private double z;
    private Context context;

    public MyAccelerometer(Context context){
        this.context = context;

    }
    private static MyAccelerometer me;

    public static MyAccelerometer getMe() {
        return me;
    }

    public static MyAccelerometer initHelper(Context context) {
        if (me == null) {
            me = new MyAccelerometer(context);
        }
        return me;
    }

    public void initServices(){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MyAccelerometer.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public MyAccelerometer setCallBack_sensorDirectionChanged(CallBack_SensorDirectionChanged callBack_sensorDirectionChanged) {
        this.callBack_sensorDirectionChanged = callBack_sensorDirectionChanged;
        return this;
    }

    /**
     * Use of Z and X axis to determine direction.
     * Choose the biggest.
     * If values aren't big enough, return "DEFAULT"
     * This method called by the onSensorChanged Listener, and calls the directionChanged callback.
     */
    public void getCurrentDirection(){

        if(z < -2 && (z*-1 > x || z*-1 > x*-1) ){
            this.direction = "DOWN";
        }
        else if(z> 2 && (z> x || z > x*-1) ){
            this.direction = "UP";
        }
        else if(x<-2 && (x*-1> z || x*-1 > z*-1)){
            this.direction = "RIGHT";
        }
        else if( x>2 && (x> z || x > z*-1)){
            this.direction = "LEFT";
        }
        else{
            this.direction = "DEFAULT";
        }
        callBack_sensorDirectionChanged.directionChanged(this.direction);
    }

    public void stop(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Log.d("sensor", "X:"+ sensorEvent.values[0]+ " Y:" + sensorEvent.values[1]+ " Z:" + sensorEvent.values[2]);
        this.x = sensorEvent.values[0];
        this.z = sensorEvent.values[2];
        getCurrentDirection();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
