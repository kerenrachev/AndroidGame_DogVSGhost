package com.example.androidgame_dogvsghost.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.androidgame_dogvsghost.R;
import com.example.androidgame_dogvsghost.callbacks.CallBack_GPSLocationReturned;

public class MySoundPlayer {

    Context context;
    public MySoundPlayer(Context context){
        this.context = context;
    }
    private static MySoundPlayer me;

    public static MySoundPlayer getMe() {
        return me;
    }


    public static MySoundPlayer initHelper(Context context) {
        if (me == null) {
            me = new MySoundPlayer(context);
        }
        return me;
    }

    public void playSound(String name){
        int sound_id = context.getResources().getIdentifier(name, "raw", context.getPackageName());
        MediaPlayer sound = MediaPlayer.create(context, sound_id);
        sound.start();
    }
}
