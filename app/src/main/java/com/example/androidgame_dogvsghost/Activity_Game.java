package com.example.androidgame_dogvsghost;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Activity_Game extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private ExtendedFloatingActionButton main_FAB_action;
    private MaterialTextView main_LBL_info;
    private ArrayList<ImageView> blocks = new ArrayList<>();
    private ArrayList<ImageView> hearts = new ArrayList<>();
    private ExtendedFloatingActionButton main_FAB_moveDown;
    private ExtendedFloatingActionButton main_FAB_moveUp;
    private ExtendedFloatingActionButton main_FAB_moveLeft;
    private ExtendedFloatingActionButton main_FAB_moveRight;
    private int currentPlayer= R.drawable.ic_dog_down;
    private int currentEnemy = R.drawable.ic_ghost_down;
    private String currentPlayerDirection = "down";
    private String currentEnemyDirection = "down";
    private GameManager gameManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initiate the array of Image views
        initiateBlocksArray();
        initiateFloatingButtons();
        initiateHearts();
        initiateGameManager();
        // Score label
        main_LBL_info = findViewById(R.id.main_LBL_info);
        Log.d("ptttThread", "onCreate Thread = " + Thread.currentThread().getName());

        startTimer();

    }
    private void initiateBlocksArray() {
        blocks.add(findViewById(R.id.main_IMG_row0col1));
        blocks.add(findViewById(R.id.main_IMG_row0col2));
        blocks.add(findViewById(R.id.main_IMG_row0col3));
        blocks.add(findViewById(R.id.main_IMG_row1col1));
        blocks.add(findViewById(R.id.main_IMG_row1col2));
        blocks.add(findViewById(R.id.main_IMG_row1col3));
        blocks.add(findViewById(R.id.main_IMG_row2col1));
        blocks.add(findViewById(R.id.main_IMG_row2col2));
        blocks.add(findViewById(R.id.main_IMG_row2col3));
        blocks.add(findViewById(R.id.main_IMG_row3col1));
        blocks.add(findViewById(R.id.main_IMG_row3col2));
        blocks.add(findViewById(R.id.main_IMG_row3col3));
        blocks.add(findViewById(R.id.main_IMG_row4col1));
        blocks.add(findViewById(R.id.main_IMG_row4col2));
        blocks.add(findViewById(R.id.main_IMG_row4col3));
    }

    private void initiateHearts() {
        hearts.add(findViewById(R.id.main_IMG_heart1));
        hearts.add(findViewById(R.id.main_IMG_heart2));
        hearts.add(findViewById(R.id.main_IMG_heart3));
    }

    private void initiateGameManager() {
        gameManager = new GameManager();
        startNewGame();
    }

    private void startNewGame() {
        // Clear all images src from screen
        for(ImageView imgView: blocks){
            imgView.setImageDrawable(null);
        }
        gameManager.setInitialIndex();
        rotatePlayerDown();
        initiateEnemy();
        initiatePlayer();
    }

    private void initiatePlayer() {
        blocks.get(gameManager.getSTARTPOSITIONDOG()).setImageResource(R.drawable.ic_dog_down);
    }

    private void initiateEnemy() {
        blocks.get(gameManager.getSTARTPOSITIONGHOST()).setImageResource(R.drawable.ic_ghost_down);
    }

    private void initiateFloatingButtons() {
        main_FAB_moveDown = findViewById(R.id.main_FAB_moveDown);
        main_FAB_moveUp = findViewById(R.id.main_FAB_moveUp);
        main_FAB_moveLeft = findViewById(R.id.main_FAB_moveLeft);
        main_FAB_moveRight =findViewById(R.id.main_FAB_moveRight);
        setListenersForFloatingButtons();
    }

    private void setListenersForFloatingButtons() {
        main_FAB_moveDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotatePlayerDown();

            }
        });
        main_FAB_moveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotatePlayerUp();

            }
        });
        main_FAB_moveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotatePlayerLeft();

            }
        });
        main_FAB_moveRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotatePlayerRight();

            }
        });

    }

    private void rotatePlayerDown() {
        currentPlayer = R.drawable.ic_dog_down;
        currentPlayerDirection= "down";
    }

    private void rotatePlayerUp() {
        currentPlayer = R.drawable.ic_dog_up;
        currentPlayerDirection = "up";
    }

    private void rotatePlayerLeft() {
        currentPlayer = R.drawable.ic_dog_left;
        currentPlayerDirection ="left";
    }

    private void rotatePlayerRight() {
        // Set current player to look at the right direction
        currentPlayer = R.drawable.ic_dog_right;
        currentPlayerDirection = "right";
    }

    private int rotateEnemy() {
        GameManager.DIRECTION ghostDirection =  gameManager.getCurrentGhostDirection();
        switch (ghostDirection){
            case UP:
                return R.drawable.ic_ghost_up;
            case DOWN:
                return R.drawable.ic_ghost_down;
            case LEFT:
                return R.drawable.ic_ghost_left;
            case RIGHT:
                return R.drawable.ic_ghost_right;
        }
        return 0;
    }

    private void move() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Player
                int currentIndex = gameManager.getCurrentPlayerIndex();
                blocks.get(currentIndex).setImageDrawable(null);
                int nextIndex = gameManager.setNextPlayerIndex(currentPlayerDirection).getCurrentPlayerIndex();
                blocks.get(nextIndex).setImageResource(currentPlayer);

                //Ghost
                int currentGhostIndex = gameManager.getCurrenGhostIndex();
                blocks.get(currentGhostIndex).setImageDrawable(null);
                int nextGhostIndex = gameManager.nextGhostIndex().getCurrenGhostIndex();
                blocks.get(nextGhostIndex).setImageResource(rotateEnemy());
            }
        });

    }

    // ----------- ----------- -----TIMER HANDLER------ ----------- ----------- -----------

    private final int DELAY = 1000;
    private enum TIMER_STATUS {
        OFF,
        RUNNING,
        PAUSE
    }
    private TIMER_STATUS timerStatus = TIMER_STATUS.OFF;
    private int counter = 0;


    private void tick() {
        Log.d("ptttTick", "Tick Thread A = " + Thread.currentThread().getName() + "   " + counter);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ++counter;
                main_LBL_info.setText("" + counter);
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (timerStatus == TIMER_STATUS.RUNNING) {
            stopTimer();
            timerStatus = TIMER_STATUS.PAUSE;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (timerStatus == TIMER_STATUS.PAUSE) {
            startTimer();
        }
    }

    private Timer timer;

    private void startTimer() {
        timerStatus = TIMER_STATUS.RUNNING;

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkIfGameContinue();
                move();
                tick();

            }
        }, 0, DELAY);

    }

    private void checkIfGameContinue() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boolean crashed = gameManager.isCrashed();
                if(crashed){
                    vibrateOnce();
                    if(gameManager.isGameContinue()) {
                        updateHearts();
                        startNewGame();
                    }
                    else finish();
                }
            }
        });

    }

    private void updateHearts() {
        for(int i=gameManager.getNumOfHearts() ; i< hearts.size(); i++)
            hearts.get(i).setVisibility(View.INVISIBLE);
    }


    private void stopTimer() {
        timer.cancel();
    }


    private void vibrateOnce() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }
}