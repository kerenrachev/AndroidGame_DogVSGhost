package com.example.androidgame_dogvsghost.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.androidgame_dogvsghost.R;
import com.example.androidgame_dogvsghost.callbacks.CallBack_StartNewGame;
import com.example.androidgame_dogvsghost.utils.MyAccelerometer;
import com.example.androidgame_dogvsghost.utils.MyAlertDialog;
import com.example.androidgame_dogvsghost.utils.MySoundPlayer;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;

public class Activity_Main extends AppCompatActivity {
    private ImageButton main_BTN_regularGame;
    private ImageButton main_BTN_sensorGame;
    private ImageButton main_BTN_topTen;
    private static final String LINK = "https://cdn.dribbble.com/users/4870/screenshots/7268538/media/1d1745660cbe38e9528a61fd59c437d8.jpg";
    private ImageView main_IMG_back;
    private String click = "click";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBackground();
        findViews();
        setClickListeners();

    }


    private void setBackground() {
        main_IMG_back = findViewById(R.id.main_IMG_back);

        Glide
                .with(this)
                .load(LINK)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error)
                .into(main_IMG_back);
    }

    private void setClickListeners() {
        main_BTN_regularGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.getMe().playSound(click);
                MyAlertDialog.getMe().setCallBack_startNewGame(callBack_startNewGame);
                MyAlertDialog.getMe().startAlertDialog("buttons",Activity_Main.this);
            }
        });

        main_BTN_sensorGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.getMe().playSound(click);
                MyAlertDialog.getMe().setCallBack_startNewGame(callBack_startNewGame);
                MyAlertDialog.getMe().startAlertDialog("sensors",Activity_Main.this);
            }
        });

        main_BTN_topTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toptenIntent = new Intent(Activity_Main.this, Activity_topten.class);
                Activity_Main.this.startActivity(toptenIntent);
                MySoundPlayer.getMe().playSound(click);

            }
        });
    }

    /**
     * Callback that is called from MyAlertDialog, that is responsible for getting username.
     * Called only if user inserted his name and pressed "OK"
     */
    CallBack_StartNewGame callBack_startNewGame = new CallBack_StartNewGame() {
        @Override
        public void startGame(String input, String type) {
            Intent gameIntent = new Intent(Activity_Main.this, Activity_Game.class);
            gameIntent.putExtra("Type", type);
            gameIntent.putExtra("Name", input);
            Activity_Main.this.startActivity(gameIntent);
        }
    };

    private void findViews() {
        main_BTN_regularGame  = findViewById(R.id.main_BTN_regularGame);
        main_BTN_sensorGame = findViewById(R.id.main_BTN_sensorGame);
        main_BTN_topTen = findViewById(R.id.main_BTN_topTen);
    }
}