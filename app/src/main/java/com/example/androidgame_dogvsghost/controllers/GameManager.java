package com.example.androidgame_dogvsghost.controllers;

import com.example.androidgame_dogvsghost.utils.MySoundPlayer;

public class GameManager {
    private Integer currentPlayerIndex;
    private Integer currenGhostIndex;
    private Integer currentCoinPosition;
    private Integer numOfHearts=3;
    private final Integer NUMOFCOLS = 5;
    private final Integer NUMOFROWS = 6;
    private final Integer STARTPOSITIONDOG = 27;
    private final Integer STARTPOSITIONGHOST = 2;
    private Player player = new Player();
    private Integer coinCouner;

    public enum DIRECTION {
        RIGHT,
        LEFT,
        DOWN,
        UP
    };

    private DIRECTION currentDogDirection = DIRECTION.DOWN;
    private DIRECTION currentGhostDirection = DIRECTION.DOWN;

    public GameManager() { }

    public void setInitialIndex() {
        setCurrenGhostIndex(STARTPOSITIONGHOST);
        setCurrentPlayerIndex(STARTPOSITIONDOG);
        setCurrentCoinPosition(17);
    }

    public Integer getCurrentCoinPosition() {
        return currentCoinPosition;
    }

    public int getCoinCounter() {
        return coinCouner;
    }

    public void setCoinCounter(int coinCouner){
        this.coinCouner = coinCouner;
    }

    public Integer getCurrenGhostIndex() {
        return currenGhostIndex;
    }

    public int nextCoinIndex(){
        int min =0;
        int max =29;
        int randomIndex = (int)((Math.random() * (max - min)) + min);
        currentCoinPosition = randomIndex;
        return randomIndex;
    }

    /**
     * Function that calculates the next random index of ghost.
     * @return GameManager instance
     */
    public GameManager nextGhostIndex(){
        int min =0;
        int max =4;
        int randomDirection = (int)((Math.random() * (max - min)) + min);
        switch (randomDirection){
            case 0: // Right
                if((currenGhostIndex-(NUMOFCOLS-1)) % NUMOFCOLS !=0)
                    currenGhostIndex++;
                setCurrentGhostDirection(DIRECTION.RIGHT);
                break;
            case 1: // Left
                if((currenGhostIndex)%NUMOFCOLS !=0)
                    currenGhostIndex--;
                setCurrentGhostDirection(DIRECTION.LEFT);
                break;
            case 2: // Up
                if(currenGhostIndex-NUMOFCOLS >= 0)
                    currenGhostIndex-= NUMOFCOLS;
                setCurrentGhostDirection(DIRECTION.UP);
                break;
            case 3: // Down
                if(currenGhostIndex+NUMOFCOLS < NUMOFCOLS*NUMOFROWS)
                    currenGhostIndex+= NUMOFCOLS;
                else currenGhostIndex = STARTPOSITIONGHOST;
                setCurrentGhostDirection(DIRECTION.DOWN);
                break;
        }
        return this;
    }

    public boolean isGameContinue() {
        if(numOfHearts==0) return false;
        return true;
    }

    public boolean isColectedCoin(){
        boolean met = twoObjectsMet(currentCoinPosition , currentPlayerIndex);
        if(met) {
            MySoundPlayer.getMe().playSound("coincollected");
            player.setScore(player.getScore()+10);
        }
        return met;
    }
    public boolean isCrashed(){
        boolean met = twoObjectsMet(currenGhostIndex , currentPlayerIndex);
        if(met) {
            MySoundPlayer.getMe().playSound("crash");
            removeHeart();
        }
        return met;
    }

    public boolean twoObjectsMet (int one, int two){
        if(one == two) return true;
        return false;
    }

    public Integer getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     *
     * @param direction Current direction of player
     * @return next index of player
     */
    public GameManager setNextPlayerIndex(String direction) {
        switch (direction){
            case "right":
                if((currentPlayerIndex-(NUMOFCOLS-1)) % NUMOFCOLS !=0)
                    currentPlayerIndex++;
                break;
            case "left":
                if((currentPlayerIndex)%NUMOFCOLS !=0)
                    currentPlayerIndex--;
                break;
            case "up":
                if(currentPlayerIndex-NUMOFCOLS >= 0)
                    currentPlayerIndex-= NUMOFCOLS;
                break;
            case "down":
                if(currentPlayerIndex+NUMOFCOLS < NUMOFCOLS*NUMOFROWS)
                    currentPlayerIndex+= NUMOFCOLS;
                break;
        }

        return this;
    }

    public DIRECTION getCurrentGhostDirection() {
        return currentGhostDirection;
    }

    public GameManager setCurrentGhostDirection(DIRECTION currentGhostDirection) {
        this.currentGhostDirection = currentGhostDirection;
        return this;
    }

    public GameManager setCurrentCoinPosition(Integer currentCoinPosition) {
        this.currentCoinPosition = currentCoinPosition;
        return this;
    }

    public Integer getSTARTPOSITIONDOG() {
        return STARTPOSITIONDOG;
    }

    public Integer getSTARTPOSITIONGHOST() {
        return STARTPOSITIONGHOST;
    }

    public Integer getNumOfHearts() {
        return numOfHearts;
    }

    public void removeHeart() {
        setNumOfHearts(this.numOfHearts-1);
    }

    public GameManager setNumOfHearts(Integer numOfHearts) {
        this.numOfHearts = numOfHearts;
        return this;
    }

    public GameManager setCurrentPlayerIndex(Integer currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
        return this;
    }

    public GameManager setCurrenGhostIndex(Integer currenGhostIndex) {
        this.currenGhostIndex = currenGhostIndex;
        return this;
    }

    public Integer getNUMOFCOLS() {
        return NUMOFCOLS;
    }

    public Integer getNUMOFROWS() {
        return NUMOFROWS;
    }

    public DIRECTION getCurrentDogDirection() {
        return currentDogDirection;
    }

    public GameManager setCurrentDogDirection(DIRECTION currentDogDirection) {
        this.currentDogDirection = currentDogDirection;
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public GameManager setPlayer(Player player) {
        this.player = player;
        return this;
    }
}
