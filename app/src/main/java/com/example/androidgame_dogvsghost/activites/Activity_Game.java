package com.example.androidgame_dogvsghost.activites;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.androidgame_dogvsghost.R;
import com.example.androidgame_dogvsghost.callbacks.CallBack_GPSLocationReturned;
import com.example.androidgame_dogvsghost.callbacks.CallBack_SensorDirectionChanged;
import com.example.androidgame_dogvsghost.callbacks.CallBack_checkIfGameContinue;
import com.example.androidgame_dogvsghost.callbacks.CallBack_move;
import com.example.androidgame_dogvsghost.callbacks.CallBack_tick;
import com.example.androidgame_dogvsghost.controllers.GameManager;
import com.example.androidgame_dogvsghost.controllers.Player;
import com.example.androidgame_dogvsghost.controllers.TimerController;
import com.example.androidgame_dogvsghost.utils.MyAccelerometer;
import com.example.androidgame_dogvsghost.utils.MyLoaction;
import com.example.androidgame_dogvsghost.utils.MyLocationProvider;
import com.example.androidgame_dogvsghost.utils.MySoundPlayer;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Locale;

public class Activity_Game extends AppCompatActivity {

    private final String ENEMY = "ghost";
    private final String PLAYER = "dog";
    private MyLoaction currPlayerLocation;
    private TimerController timerController;
    private MaterialTextView main_LBL_info;
    private ArrayList<ImageView> blocks = new ArrayList<>();
    private ArrayList<ImageView> hearts = new ArrayList<>();
    private ExtendedFloatingActionButton main_FAB_moveDown;
    private ExtendedFloatingActionButton main_FAB_moveUp;
    private ExtendedFloatingActionButton main_FAB_moveLeft;
    private ExtendedFloatingActionButton main_FAB_moveRight;
    private int currentPlayer = R.drawable.ic_dog_down;
    private int currentEnemy = R.drawable.ic_ghost_down;
    private String currentPlayerDirection = "down";
    private String currentEnemyDirection = "down";
    private GameManager gameManager;
    private String gameType;
    private static final String LINK = "https://cdn.dribbble.com/users/1818193/screenshots/5874799/by_the_lake_zuairia_zaman_4x.jpg?compress=1&resize=1200x900&vertical=top";
    private ImageView game_IMG_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Check if Location permission is enabled
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            setBackground();
            initiateLocationProvider();
            initGameType();
            initTimerController();
            setCallbacks();
            initiateBlocksArray();
            initiateHearts();
            initiateGameManager();
            findViews();
        } else {
            // Location permission isn't enabled - ask for permission.
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            Toast.makeText(Activity_Game.this, "Please enable location permission, and try again!:)", Toast.LENGTH_SHORT).show();
            finish();

        }
    }

    private void findViews() {
        // Score label
        main_LBL_info = findViewById(R.id.main_LBL_info);
    }

    private void initTimerController() {
        timerController = new TimerController();
        timerController.startTimer();
    }

    private void setBackground() {
        game_IMG_back = findViewById(R.id.game_IMG_back);
        Glide
                .with(this)
                .load(LINK)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error)
                .into(game_IMG_back);
    }

    private void initGameType() {
        String type = (String) getIntent().getSerializableExtra("Type");
        switch (type){
            case "buttons":
                initiateFloatingButtons();
                gameType = "buttons";
                break;
            case "sensors":
                initSensors();
                gameType = "sensors";
                break;
        }
    }

    private void initSensors() {
        //Init sensor
        MyAccelerometer.getMe().initServices();
        // Set callBack
        MyAccelerometer.getMe().setCallBack_sensorDirectionChanged(callBack_sensorDirectionChanged);
    }

    private void setCallbacks() {
        timerController.setCallBack_checkIfGameContinue(callBack_checkIfGameContinue);
        timerController.setCallBack_tick(callBack_tick);
        timerController.setCallBack_move(callBack_move);
        MyLocationProvider.getMe().setCallBack_gpsLocationReturned(callBack_gpsLocationReturned);
    }

    private void initiateBlocksArray() {
        blocks.add(findViewById(R.id.main_IMG_row0col1));
        blocks.add(findViewById(R.id.main_IMG_row0col2));
        blocks.add(findViewById(R.id.main_IMG_row0col3));
        blocks.add(findViewById(R.id.main_IMG_row0col4));
        blocks.add(findViewById(R.id.main_IMG_row0col5));

        blocks.add(findViewById(R.id.main_IMG_row1col1));
        blocks.add(findViewById(R.id.main_IMG_row1col2));
        blocks.add(findViewById(R.id.main_IMG_row1col3));
        blocks.add(findViewById(R.id.main_IMG_row1col4));
        blocks.add(findViewById(R.id.main_IMG_row1col5));

        blocks.add(findViewById(R.id.main_IMG_row2col1));
        blocks.add(findViewById(R.id.main_IMG_row2col2));
        blocks.add(findViewById(R.id.main_IMG_row2col3));
        blocks.add(findViewById(R.id.main_IMG_row2col4));
        blocks.add(findViewById(R.id.main_IMG_row2col5));

        blocks.add(findViewById(R.id.main_IMG_row3col1));
        blocks.add(findViewById(R.id.main_IMG_row3col2));
        blocks.add(findViewById(R.id.main_IMG_row3col3));
        blocks.add(findViewById(R.id.main_IMG_row3col4));
        blocks.add(findViewById(R.id.main_IMG_row3col5));

        blocks.add(findViewById(R.id.main_IMG_row4col1));
        blocks.add(findViewById(R.id.main_IMG_row4col2));
        blocks.add(findViewById(R.id.main_IMG_row4col3));
        blocks.add(findViewById(R.id.main_IMG_row4col4));
        blocks.add(findViewById(R.id.main_IMG_row4col5));

        blocks.add(findViewById(R.id.main_IMG_row5col1));
        blocks.add(findViewById(R.id.main_IMG_row5col2));
        blocks.add(findViewById(R.id.main_IMG_row5col3));
        blocks.add(findViewById(R.id.main_IMG_row5col4));
        blocks.add(findViewById(R.id.main_IMG_row5col5));

    }

    private void initiateHearts() {
        hearts.add(findViewById(R.id.main_IMG_heart1));
        hearts.add(findViewById(R.id.main_IMG_heart2));
        hearts.add(findViewById(R.id.main_IMG_heart3));
    }

    private void initiateGameManager() {
        gameManager = new GameManager();
        String playerName = (String) getIntent().getSerializableExtra("Name");
        gameManager.setPlayer(new Player().setName(playerName));
        Log.d("newplayer", playerName);
        gameManager.setCoinCounter(0);
        startNewGame();
    }

    private void startNewGame() {
        // Clear all images src from screen
        for (ImageView imgView : blocks) {
            imgView.setImageDrawable(null);
        }
        gameManager.setInitialIndex();
        currentPlayer = getRotatedIMG(PLAYER,"down");
        setPlayerCurrDirection("down");
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
        main_FAB_moveDown  = findViewById(R.id.main_FAB_moveDown);
        main_FAB_moveUp    = findViewById(R.id.main_FAB_moveUp);
        main_FAB_moveLeft  = findViewById(R.id.main_FAB_moveLeft);
        main_FAB_moveRight = findViewById(R.id.main_FAB_moveRight);

        main_FAB_moveDown.setVisibility(View.VISIBLE);
        main_FAB_moveUp.setVisibility(View.VISIBLE);
        main_FAB_moveLeft.setVisibility(View.VISIBLE);
        main_FAB_moveRight.setVisibility(View.VISIBLE);
        setListenersForFloatingButtons();
    }

    private void setListenersForFloatingButtons() {
        main_FAB_moveDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPlayerCurrDirection("down");

            }
        });
        main_FAB_moveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPlayerCurrDirection("up");

            }
        });
        main_FAB_moveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPlayerCurrDirection("left");

            }
        });
        main_FAB_moveRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPlayerCurrDirection("right");

            }
        });

    }
    private void setPlayerCurrDirection(String direction){
        currentPlayer = getRotatedIMG(PLAYER,direction);
        currentPlayerDirection = direction;
    }

    /**
     * @param typePlayer - Enemy/Player
     * @param direction - Direction of movement
     * @return ID of drawable (player or enemy with direction)
     */
    private int getRotatedIMG(String typePlayer, String direction){
        return this.getResources().getIdentifier("ic_"+typePlayer+"_"+direction, "drawable", this.getPackageName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerController.onStopTimer();
        if(gameType == "sensors") MyAccelerometer.getMe().stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timerController.onStartTimer();
        if(gameType == "sensors") MyAccelerometer.getMe().initServices();
    }

    /**
     * When game finishes, stop timer, and if game is sensor game- stop sensors as well.
     */
    private void finishGame() {
        timerController.onStopTimer();
        getCurrentLocation();
        if(gameType == "sensors") MyAccelerometer.getMe().stop();

    }

    private void updateHearts() {
        for (int i = gameManager.getNumOfHearts(); i < hearts.size(); i++)
            hearts.get(i).setVisibility(View.INVISIBLE);
    }

    private void playSound(String audioName) {
        MySoundPlayer.getMe().playSound(audioName);
    }

    /**
     * Callback that is called by MyAccelerometer Instance each time onSensorChanged listener is called.
     * Changing the currentPlayerDirection variable.
     * If MyAccelerometer returns "DEFAULT" direction - use current direction.
     */
    CallBack_SensorDirectionChanged callBack_sensorDirectionChanged = new CallBack_SensorDirectionChanged() {
        @Override
        public void directionChanged(String direction) {
            if(!direction.equals("DEFAULT"))
                setPlayerCurrDirection(direction.toLowerCase(Locale.ROOT));
        }
    };
    /***
     * CallBack that is called each second by the TimerController.
     * Moving enemy and player, and changing image direction.
     */
    CallBack_move callBack_move = new CallBack_move() {
        @Override
        public void move() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    coinHandler();
                    // Set img src to null
                    int currentPlayerIndex = gameManager.getCurrentPlayerIndex();
                    blocks.get(currentPlayerIndex).setImageDrawable(null);
                    int currentGhostIndex = gameManager.getCurrenGhostIndex();
                    blocks.get(currentGhostIndex).setImageDrawable(null);

                    // Player
                    int nextIndex = gameManager.setNextPlayerIndex(currentPlayerDirection).getCurrentPlayerIndex();
                    blocks.get(nextIndex).setImageResource(currentPlayer);

                    //Ghost
                    int nextGhostIndex = gameManager.nextGhostIndex().getCurrenGhostIndex();
                    GameManager.DIRECTION ghostDirection = gameManager.getCurrentGhostDirection();
                    blocks.get(nextGhostIndex).setImageResource(getRotatedIMG(ENEMY, ghostDirection.name().toLowerCase(Locale.ROOT)));
                }
            });
        }
    };
    /**
     * Callback that is called each second by the TimerController,
     * Increases score by 1 (1 second passed), and increases coin counter.
     */
    CallBack_tick callBack_tick = new CallBack_tick() {
        @Override
        public void tick() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int currTimer = timerController.getCounter();
                    timerController.setCounter(++currTimer);
                    gameManager.setCoinCounter(gameManager.getCoinCounter()+1);
                    increaseScore(1);
                    main_LBL_info.setText("" + gameManager.getPlayer().getScore());
                }
            });
        }
    };

    /**
     * If coin Counter is devided by 10 (10 seconds passed )
     * Or if coin collected
     * Or Enemy collected coin
     * -> Remove coin from current index, and put it on another random index on matrix.
     */
    private void coinHandler() {
        if(gameManager.getCoinCounter() % 10 == 0){
            if(!gameManager.twoObjectsMet(gameManager.getCurrentCoinPosition(),gameManager.getCurrenGhostIndex())
                        && !gameManager.twoObjectsMet(gameManager.getCurrentCoinPosition(), gameManager.getCurrentPlayerIndex()))
                    blocks.get(gameManager.getCurrentCoinPosition()).setImageDrawable(null);
            initCoin();
        }
        else if(gameManager.isColectedCoin()){
            initCoin();
        }
        else if(gameManager.twoObjectsMet(gameManager.getCurrentCoinPosition(),gameManager.getCurrenGhostIndex())){
            initCoin();
        }
    }

    private void initCoin(){
        int nextCoinIndex = gameManager.nextCoinIndex();
        blocks.get(nextCoinIndex).setImageResource(R.drawable.ic_coin);
        gameManager.setCoinCounter(0);
    }

    /**
     * The GPS location function is Async, so this callback is called when location is returned by MyLocationProvider.
     * After Location returned - start Top10 Activity, and finish thisOne.
     */
    CallBack_GPSLocationReturned callBack_gpsLocationReturned = new CallBack_GPSLocationReturned() {
        @Override
        public void GPSLocationReturned() {
            gameManager.getPlayer().setLocation(new MyLoaction().setLat(currPlayerLocation.getLat()).setLng(currPlayerLocation.getLng()));
            Intent gameIntent = new Intent(Activity_Game.this, Activity_topten.class);
            gameIntent.putExtra("Player", gameManager.getPlayer());
            gameIntent.putExtra("Type", gameType);
            Activity_Game.this.startActivity(gameIntent);
            finish();
        }
    };
    /**
     * TimerController callback, called each second and responsible for updating hearts and finishing the game.
     */
    CallBack_checkIfGameContinue callBack_checkIfGameContinue = new CallBack_checkIfGameContinue() {
        @Override
        public void checkIfGameContinue() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    boolean crashed = gameManager.isCrashed();
                    if (crashed) {

                        if (gameManager.isGameContinue()) {
                            updateHearts();
                            startNewGame();
                        } else finishGame();
                    }
                }
            });
        }
    };

    /**
     *
     * @param num - Increases the score by 1 (1 sencond passes) or by 10 (coin collected)
     */
    private void increaseScore(int num){
        gameManager.setPlayer(gameManager.getPlayer().setScore(gameManager.getPlayer().getScore()+num));
    }

    /**
     * When game finishes, This function calls MyLocationProvider instance, and getting player's location.
     */
    private void getCurrentLocation() {
        currPlayerLocation = new MyLoaction();
        MyLocationProvider.getMe().getCurrentLocation(currPlayerLocation, this);
    }

    /**
     * Initiating Location provider
     */
    private void initiateLocationProvider() {
        MyLocationProvider.getMe().initiateLocationProvider();
    }
}