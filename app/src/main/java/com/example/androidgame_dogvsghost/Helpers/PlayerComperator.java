package com.example.androidgame_dogvsghost.Helpers;

import com.example.androidgame_dogvsghost.controllers.Player;

import java.util.Comparator;

public class PlayerComperator implements Comparator {

    @Override
    public int compare(Object one, Object two) {
        Player p1=(Player)one;
        Player p2=(Player)two;
        if(p1.getScore()< p2.getScore()){
            return 1;
        }
        else if(p1.getScore()> p2.getScore()){
            return -1;
        }
        return 0;
    }
}
