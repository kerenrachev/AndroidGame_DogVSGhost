package com.example.androidgame_dogvsghost.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.androidgame_dogvsghost.R;
import com.example.androidgame_dogvsghost.callbacks.CallBack_StartNewGame;

public class MyAlertDialog {
    private Context context;
    private CallBack_StartNewGame callBack_startNewGame;
    public MyAlertDialog(Context context){
        this.context = context;

    }
    private static MyAlertDialog me;

    public static MyAlertDialog getMe() {
        return me;
    }

    public static MyAlertDialog initHelper(Context context) {
        if (me == null) {
            me = new MyAlertDialog(context);
        }
        return me;
    }

    public MyAlertDialog setCallBack_startNewGame(CallBack_StartNewGame callBack_startNewGame) {
        this.callBack_startNewGame = callBack_startNewGame;
        return this;
    }

    public void startAlertDialog(String type, Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("ENTER NAME");
        alertDialog.setMessage("Please enter your name");
        alertDialog.setIcon(R.drawable.user);
        final EditText input = new EditText(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input); // uncomment this line
        // Setting Positive "OK" Button
        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        if(input.getText().length()!=0){
                            Toast.makeText(context,"Good luck "+input.getText()+ "!", Toast.LENGTH_SHORT).show();
                            callBack_startNewGame.startGame(input.getText()+"",type );
                        }
                        else{
                            Toast.makeText(context,"Please enter a name.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        // Setting Negative "CANCEL" Button
        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
}
