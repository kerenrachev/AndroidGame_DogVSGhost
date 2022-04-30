package com.example.androidgame_dogvsghost.controllers;

import com.example.androidgame_dogvsghost.Helpers.PlayerComperator;
import com.example.androidgame_dogvsghost.utils.MSP;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class TopTenController {

    private ArrayList<Player> players= new ArrayList<Player>();
    private PlayerComperator PlayerComperator;
    private final int maxPlayers = 10;
    private final String TOPTEN_KEY = "TOP10";

    public TopTenController(){
        PlayerComperator = new PlayerComperator();
    };

    public String getTOPTEN_KEY() {
        return TOPTEN_KEY;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public TopTenController setPlayers(Player player) {

        String json = MSP.getMe().getString(TOPTEN_KEY, null);
        if (json != null) {
            players = new Gson().fromJson(json, new TypeToken<ArrayList<Player>>(){}.getType());
        }
        if(player!= null)
            players.add(player);

        players.sort(PlayerComperator);
        ArrayList <Player> newArr = new ArrayList<>();
        if(players.size() > maxPlayers){
            for(int i=0; i< maxPlayers ; i++){
                newArr.add(players.get(i));
            }
        }
        else newArr = players;
        String JSONPlayers= new Gson().toJson(newArr,new TypeToken<ArrayList<Player>>(){}.getType());
        MSP.getMe().putString(TOPTEN_KEY, JSONPlayers );
        return this;
    }
}
