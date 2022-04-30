package com.example.androidgame_dogvsghost.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.androidgame_dogvsghost.R;
import com.example.androidgame_dogvsghost.callbacks.Callback_PlayerClicked;
import com.example.androidgame_dogvsghost.controllers.Player;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Fragment_Players extends Fragment {

    private AppCompatActivity appCompatActivity;
    private Callback_PlayerClicked callback_playerClicked;
    private MaterialButton players_BTN_player1;
    private  ArrayList<Player> players= new ArrayList<>();;


    public Fragment_Players(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }
    public Fragment_Players(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_players, container, false);
        if(getArguments() != null ){
            String json = getArguments().getString("Players");
            players = new Gson().fromJson(json, new TypeToken<ArrayList<Player>>(){}.getType());
        }


        for(Player player: players){
            final Button myButton = new Button(view.getContext());
            myButton.setBackgroundColor(Color.argb(125, 0, 0, 255));
            myButton.setBackgroundTintList(ContextCompat.getColorStateList(this.appCompatActivity, R.color.btn));
            myButton.setText(player.getName() +" Score:"+ player.getScore());
            myButton.setTextColor(getResources().getColor(R.color.white));
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback_playerClicked.playerClicked(player);
                }
            });

            myButton.setBackgroundColor(getResources().getColor(R.color.purple_200));
            myButton.setTextSize(18);
            myButton.setPadding(20, 0, 20, 0);

            LinearLayout linearlayout = (LinearLayout) view.findViewById(R.id.players_RLY_topten);
            linearlayout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonParams.setMargins(0, 0, 0, 10);

            linearlayout.addView(myButton, buttonParams);
        }
        return view;
    }

    public void setCallback_playerClicked(Callback_PlayerClicked callback_playerClicked) {
        this.callback_playerClicked = callback_playerClicked;
    }
}