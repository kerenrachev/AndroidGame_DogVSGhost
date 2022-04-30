package com.example.androidgame_dogvsghost.activites;

import android.app.Application;
import android.util.Log;

import com.example.androidgame_dogvsghost.utils.MSP;
import com.example.androidgame_dogvsghost.utils.MyAccelerometer;
import com.example.androidgame_dogvsghost.utils.MyAlertDialog;
import com.example.androidgame_dogvsghost.utils.MyLocationProvider;
import com.example.androidgame_dogvsghost.utils.MySoundPlayer;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MyLocationProvider.initHelper(this);
        MSP.initHelper(this);
        MySoundPlayer.initHelper(this);
        MyAccelerometer.initHelper(this);
        MyAlertDialog.initHelper(this);
    }
}