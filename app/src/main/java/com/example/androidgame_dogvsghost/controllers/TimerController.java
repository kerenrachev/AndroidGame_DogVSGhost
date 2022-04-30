package com.example.androidgame_dogvsghost.controllers;

import com.example.androidgame_dogvsghost.callbacks.CallBack_checkIfGameContinue;
import com.example.androidgame_dogvsghost.callbacks.CallBack_move;
import com.example.androidgame_dogvsghost.callbacks.CallBack_tick;

import java.util.Timer;
import java.util.TimerTask;

public class TimerController {

    private final int DELAY = 1000;



    private enum TIMER_STATUS {
        OFF,
        RUNNING,
        PAUSE
    }
    private int coinCouner;
    private TIMER_STATUS timerStatus = TIMER_STATUS.OFF;
    private int counter = 0;
    private Timer timer;
    private CallBack_move callBack_move;
    private CallBack_tick callBack_tick;
    private CallBack_checkIfGameContinue callBack_checkIfGameContinue;

    public TimerController(){}

    public TimerController setCallBack_move(CallBack_move callBack_move) {
        this.callBack_move = callBack_move;
        return this;
    }

    public TimerController setCallBack_tick(CallBack_tick callBack_tick) {
        this.callBack_tick = callBack_tick;
        return this;
    }

    public TimerController setCallBack_checkIfGameContinue(CallBack_checkIfGameContinue callBack_checkIfGameContinue) {
        this.callBack_checkIfGameContinue = callBack_checkIfGameContinue;
        return this;
    }


    public void startTimer() {
        timerStatus = TIMER_STATUS.RUNNING;

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                callBack_checkIfGameContinue.checkIfGameContinue();
                callBack_move.move();
                callBack_tick.tick();

            }
        }, 0, DELAY);

    }

    private void stopTimer() {
        timer.cancel();
    }

    public void onStopTimer(){
        if (timerStatus == TIMER_STATUS.RUNNING) {
            stopTimer();
            timerStatus = TIMER_STATUS.PAUSE;
        }
    }

    public void onStartTimer(){
        if (timerStatus == TIMER_STATUS.PAUSE) {
            startTimer();
        }
    }

    public int getDELAY() {
        return DELAY;
    }

    public TIMER_STATUS getTimerStatus() {
        return timerStatus;
    }

    public TimerController setTimerStatus(TIMER_STATUS timerStatus) {
        this.timerStatus = timerStatus;
        return this;
    }

    public int getCounter() {
        return counter;
    }

    public TimerController setCounter(int counter) {
        this.counter = counter;
        return this;
    }
}
