package com.example.androidgame_dogvsghost.utils;

import java.io.Serializable;

public class MyLoaction implements Serializable {
    private double lat;
    private double lng;

    public MyLoaction(){}

    public double getLat() {
        return lat;
    }

    public MyLoaction setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public MyLoaction setLng(double lng) {
        this.lng = lng;
        return this;
    }
}
