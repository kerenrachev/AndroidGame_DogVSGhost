package com.example.androidgame_dogvsghost.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.androidgame_dogvsghost.callbacks.CallBack_StartNewGame;
import com.example.androidgame_dogvsghost.callbacks.Callback_PlayerClicked;
import com.example.androidgame_dogvsghost.controllers.Player;
import com.example.androidgame_dogvsghost.controllers.TopTenController;
import com.example.androidgame_dogvsghost.fragments.Fragment_GoogleMaps;
import com.example.androidgame_dogvsghost.fragments.Fragment_Players;
import com.example.androidgame_dogvsghost.R;
import com.example.androidgame_dogvsghost.utils.MyAlertDialog;
import com.example.androidgame_dogvsghost.utils.MySoundPlayer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Activity_topten extends AppCompatActivity {
    private FrameLayout topten_LAY_playerlist;
    private FrameLayout topten_LAY_location;
    private ImageView topen_BTN_playagain;
    private Fragment_GoogleMaps fragment_googleMaps;
    private TopTenController topTenController = new TopTenController();
    private Fragment_Players fragment_players;
    private Player player;
    private String gameType;
    private static final String LINK = "https://cdn.dribbble.com/users/4870/screenshots/7268538/media/1d1745660cbe38e9528a61fd59c437d8.jpg";
    private ImageView topen_LAY_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topten);
        getIntents();
        findViews();
        setBackground();
        initiateFragments();
    }

    /**
     * Getting "Player" and "Type" from Intent.
     * If they are empty- it means the intent came from main activity.
     * If not- intent came from game activity, and we should diaplay the "Play again" button, and add the player to top10.
     */
    private void getIntents() {
        player = (Player) getIntent().getSerializableExtra("Player");
        topTenController.setPlayers(player);
        topen_BTN_playagain = findViewById(R.id.topen_BTN_playagain);
        gameType = (String) getIntent().getSerializableExtra("Type");
        if(gameType!= null && gameType.length()!=0){
            topen_BTN_playagain.setVisibility(View.VISIBLE);
            topen_BTN_playagain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MySoundPlayer.getMe().playSound("click");
                    MyAlertDialog.getMe().setCallBack_startNewGame(callBack_startNewGame);
                    MyAlertDialog.getMe().startAlertDialog(gameType,Activity_topten.this);

                }
            });
        }
    }
    /**
     * Callback that is called from MyAlertDialog, that is responsible for getting username.
     * Will be called only if this activity opened by the Game Activity and if user inserted his name and pressed "OK"
     */
    CallBack_StartNewGame callBack_startNewGame = new CallBack_StartNewGame() {
        @Override
        public void startGame(String input, String type) {
            Intent gameIntent = new Intent(Activity_topten.this, Activity_Game.class);
            gameIntent.putExtra("Type", type);
            gameIntent.putExtra("Name", input);
            Activity_topten.this.startActivity(gameIntent);
            finish();
        }
    };

    private void setBackground() {
        Glide
                .with(this)
                .load(LINK)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error)
                .into(topen_LAY_back);
    }

    private void initiateFragments() {

        Bundle bundle = new Bundle();
        String JSONPlayers= new Gson().toJson(topTenController.getPlayers(),new TypeToken<ArrayList<Player>>(){}.getType());
        bundle.putString("Players", JSONPlayers);
        fragment_players = new Fragment_Players(this);
        fragment_players.setArguments(bundle);
        fragment_players.setCallback_playerClicked(callback_playerClicked);
        getSupportFragmentManager().beginTransaction().add(R.id.topten_LAY_playerlist, fragment_players).commit();

        fragment_googleMaps = new Fragment_GoogleMaps(this);
        getSupportFragmentManager().beginTransaction().add(R.id.topten_LAY_location, fragment_googleMaps).commit();
    }

    private void findViews() {
        topten_LAY_playerlist = findViewById(R.id.topten_LAY_playerlist);
        topten_LAY_location = findViewById(R.id.topten_LAY_location);
        topen_LAY_back = findViewById(R.id.topen_LAY_back);
    }

    Callback_PlayerClicked callback_playerClicked = new Callback_PlayerClicked() {
        @Override
        public void playerClicked(Player player) {
            fragment_googleMaps.setMapLocation(player.getLocation());
        }
    };
}