package com.example.androidgame_dogvsghost;

public class GameManager {
    private Integer currentPlayerIndex;
    private Integer currenGhostIndex;
    private Integer numOfHearts=3;
    private final Integer NUMOFCOLS = 3;
    private final Integer NUMOFROWS = 5;
    private final Integer STARTPOSITIONDOG = 13;
    private final Integer STARTPOSITIONGHOST = 1;

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
    }

    public Integer getCurrenGhostIndex() {
        return currenGhostIndex;
    }

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

    public boolean isCrashed(){
        if(currenGhostIndex == currentPlayerIndex) {
            removeHeart();
            return true;
        }
        else return false;
    }

    public Integer getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

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
}
