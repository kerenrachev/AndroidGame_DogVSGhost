package com.example.androidgame_dogvsghost.controllers;

import com.example.androidgame_dogvsghost.utils.MyLoaction;

import java.io.Serializable;

public class Player implements Serializable {
    private MyLoaction myLoaction = new MyLoaction();
    private String name;
    private int score;

    public Player(){}

    public MyLoaction getLocation() {
        return myLoaction;
    }

    public Player setLocation(MyLoaction myLoaction) {
        this.myLoaction = myLoaction;
        return this;
    }

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Player setScore(int score) {
        this.score = score;
        return this;
    }

    @Override
    public String toString() {
        return name + " " +
                "Lat=" + myLoaction.getLat() +
                "Lng=" + myLoaction.getLng() +
                "score=" + score;
    }
}
