package com.example.jumpingrookgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jumpingrookgame.model.GameActive;
import com.example.jumpingrookgame.model.GameSetup;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    TextView totalMovesLabel;
    TextView gameGridLabel;
    TextView nextMoveLabel;
    TextView userActionLabel;
    TextView levelName;
    TextView goalLabel;
    TextView timerLabel;
    MediaPlayer errorsound;
    MediaPlayer victorysound;
    Switch soundSwitch;
    int TOTAL_LEVELS = 3;
    int currentLevel = 1;
    int moveCounter = 0;
    int totalMoves = 0;
    int gameMenuOption = 0;
    int customX = 5;
    int customY = 5;
    int customPath = 3;
    //Use this to implement a countdown timer one day
    public int seconds = 0;
    String moveCounterMessage = "Total moves: ";
    String feedback;
    String solutionPath;
    String gameType;
    GameSetup gamegrid;
    GameActive active;
    Timer t;

    boolean enableSound = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Setup labels
        totalMovesLabel = (TextView) findViewById(R.id.totalMovesLabel);
        gameGridLabel = (TextView) findViewById(R.id.gameGridLabel);
        nextMoveLabel = (TextView) findViewById(R.id.nextMoveLabel);
        userActionLabel = (TextView) findViewById(R.id.userActionLabel);
        levelName = (TextView) findViewById(R.id.levelName);
        goalLabel = (TextView) findViewById(R.id.goalLabel);
        timerLabel = (TextView) findViewById(R.id.timerLabel);

        //Sounds
        errorsound = MediaPlayer.create(this, R.raw.windows_xp_error_sound_effect);
        victorysound = MediaPlayer.create(this, R.raw.microsoft_window_startup_sound);

        //Switch
        soundSwitch = (Switch) findViewById(R.id.soundSwitch);

        //Setup buttons
        Button buttonUp = findViewById(R.id.buttonUp);
        Button buttonRight = findViewById(R.id.buttonRight);
        Button buttonDown = findViewById(R.id.buttonDown);
        Button buttonLeft = findViewById(R.id.buttonLeft);
        Button buttonResetLevel = findViewById(R.id.buttonResetLevel);
        Button buttonShowSolution = findViewById(R.id.buttonShowSolution);
        Button buttonUndo = findViewById(R.id.buttonUndo);
        buttonUp.setOnClickListener(this);
        buttonRight.setOnClickListener(this);
        buttonDown.setOnClickListener(this);
        buttonLeft.setOnClickListener(this);
        buttonResetLevel.setOnClickListener(this);
        buttonShowSolution.setOnClickListener(this);
        buttonUndo.setOnClickListener(this);

        //get game type from main menu:
        setMode();

        //Sound switch, enabling sound effects
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    enableSound = true;
                } else {
                    enableSound = false;
                }
            }
        });
    }

    private void setMode() {
        gameType = getIntent().getStringExtra("gametype");

        if (gameType.equals("campaign")) {
            //By default start level 1
            //Update labels
            levelName.setText("Level name: Easy");
            goalLabel.setText("Level: 1/" + TOTAL_LEVELS);
            totalMovesLabel.setText(moveCounterMessage + moveCounter);
            userActionLabel.setText("No action taken yet");
            SetupLevel(3, 3, 3);
        }
        else if (gameType.equals("custom")) {
            //setup custom game
            customX = getIntent().getIntExtra("customX", 5);
            customY = getIntent().getIntExtra("customY", 5);
            customPath = getIntent().getIntExtra("customPath", 3);

            //expecting a big grid, so resizing to fit space
            if (customY > 8) {
                gameGridLabel.setTextSize(24);
            }

            levelName.setText("Level name: Custom");
            goalLabel.setText("Level: Custom");
            totalMovesLabel.setText(moveCounterMessage + moveCounter);
            userActionLabel.setText("No action taken yet");
            boolean setup = true;
            while (setup) {
                try {
                    SetupLevel(customX, customY, customPath);
                    setup = false;
                }
                catch (Exception e) {
                    Log.d("gamemode: ", "Error setting up, trying again");
                }
            }


        }
    }

    private void SetupLevel(int XAXIS, int YAXIS, int PATHNUMBER) {
        //GameSetup WORK HERE
        gamegrid = new GameSetup(YAXIS, XAXIS, PATHNUMBER, 0, 0);
        gamegrid.createGrid();
        gamegrid.setStart(0,0);
        gamegrid.generateSolutionPath();
        gamegrid.setFinish();
        gamegrid.populateGrid();
        solutionPath = gamegrid.getSolution();

        //GameActive WORK HERE
        String[][] grid = gamegrid.getGrid();
        List<Integer> xsol = gamegrid.getxSolution();
        List<Integer> ysol = gamegrid.getySolution();
        int startx = gamegrid.getStartingxPointer();
        int starty = gamegrid.getStartingyPointer();
        active = new GameActive(grid, XAXIS, YAXIS, PATHNUMBER, xsol, ysol, startx, starty);
        active.createIntegerMoves();
        active.pointerOverRide(0, 0);
        active.setPlayer();
        //Update game grid label
        gameGridLabel.setText(gamegrid.getBoard());
        nextMoveLabel.setText(String.valueOf(active.getthisPosition()));

        //Setup Timer
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Setup seconds sf format
                        if ((seconds%60) < 10) {
                            timerLabel.setText("Time: "+String.valueOf(seconds/60)+":"+String.valueOf("0"+seconds%60));
                        }
                        else {
                            timerLabel.setText("Time: "+String.valueOf(seconds/60)+":"+String.valueOf(seconds%60));
                        }
                        seconds += 1;
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonUp:
                active.moveControl("W");
                feedback = active.getFeedback();
                if (feedback == "valid up jump") {
                    gameGridLabel.setText(gamegrid.getBoard());
                    nextMoveLabel.setText(String.valueOf(active.getthisPosition()));
                    moveCounter++;
                    totalMovesLabel.setText(moveCounterMessage + moveCounter);
                }
                else if (feedback == "invalid up jump" && enableSound) {
                    //not valid move
                    errorsound.start();
                }
                userActionLabel.setText(active.getFeedback());
                break;
            case R.id.buttonRight:
                active.moveControl("D");
                feedback = active.getFeedback();
                if (feedback == "valid right jump") {
                    gameGridLabel.setText(gamegrid.getBoard());
                    nextMoveLabel.setText(String.valueOf(active.getthisPosition()));
                    moveCounter ++;
                    totalMovesLabel.setText(moveCounterMessage + moveCounter);
                }
                else if (feedback == "invalid right jump" && enableSound) {
                    //not valid move
                    errorsound.start();
                }
                userActionLabel.setText(active.getFeedback());
                break;
            case R.id.buttonDown:
                active.moveControl("S");
                feedback = active.getFeedback();
                if (feedback == "valid down jump") {
                    gameGridLabel.setText(gamegrid.getBoard());
                    nextMoveLabel.setText(String.valueOf(active.getthisPosition()));
                    moveCounter++;
                    totalMovesLabel.setText(moveCounterMessage + moveCounter);
                }
                else if (feedback == "invalid down jump" && enableSound) {
                    //not valid move
                    errorsound.start();
                }
                userActionLabel.setText(active.getFeedback());
                break;
            case R.id.buttonLeft:
                active.moveControl("A");
                feedback = active.getFeedback();
                if (feedback == "valid left jump") {
                    gameGridLabel.setText(gamegrid.getBoard());
                    nextMoveLabel.setText(String.valueOf(active.getthisPosition()));
                    moveCounter++;
                    totalMovesLabel.setText(moveCounterMessage + moveCounter);
                }
                else if (feedback == "invalid left jump" && enableSound) {
                    //not valid move
                    errorsound.start();
                }
                userActionLabel.setText(active.getFeedback());
                break;
            case R.id.buttonResetLevel:
                active.resetMoveset();
                moveCounter = 0;
                gameGridLabel.setText(gamegrid.getBoard());
                nextMoveLabel.setText(String.valueOf(active.getthisPosition()));
                totalMovesLabel.setText(moveCounterMessage + moveCounter);
                userActionLabel.setText(active.getFeedback());
                break;
            case R.id.buttonShowSolution:
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setTitle("Solution Path:");
                builder.setMessage(solutionPath);
                builder.setPositiveButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.buttonUndo:
                if (moveCounter > 0) {
                    active.undoLastMove();
                    moveCounter --;
                    gameGridLabel.setText(gamegrid.getBoard());
                    nextMoveLabel.setText(String.valueOf(active.getthisPosition()));
                    totalMovesLabel.setText(moveCounterMessage + moveCounter);
                    userActionLabel.setText(active.getFeedback());
                }
                else {
                    userActionLabel.setText("You are at the start");
                }
                break;
        }
        //Check if won
        active.checkIfWon();
        if (active.getIfWon() == true) {
            //Reset timer
            t.cancel();
            seconds = 0;
            if (enableSound) {
                victorysound.start();
            }
            //Set dialog
            String[] list = new String[] {"Next Level", "Main Menu", "Custom Mode", "Reshuffle level"};
            AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
            builder.setTitle("You Won! What Now?");
            builder.setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Do nothing on select
                    //Only want to make the final decision on button select
                    if (i == 0) {
                        gameMenuOption = 0;
                    }
                    else if (i == 1) {
                        //Go to main menu
                        gameMenuOption = 1;
                    }
                    else if (i == 2) {
                        //Go to Custom mode
                        gameMenuOption = 2;
                    }
                    else if (i == 3) {
                        //Replay level and reshuffle
                        gameMenuOption = 3;
                    }
                }
            });
            builder.setPositiveButton("accept", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (gameMenuOption == 0) {
                        //Continue to next level
                        currentLevel++;
                        totalMoves = totalMoves + moveCounter;
                        moveCounter = 0;
                        if (currentLevel == 1) {
                            //Update labels
                            levelName.setText("Level name: Easy");
                            goalLabel.setText("Level: 1/"+ TOTAL_LEVELS);
                            totalMovesLabel.setText(moveCounterMessage + moveCounter);
                            userActionLabel.setText("No action taken yet");
                            SetupLevel(3, 3, 3);
                        } else if (currentLevel == 2) {
                            //Update labels
                            levelName.setText("Level name: Medium");
                            goalLabel.setText("Level: 2/"+ TOTAL_LEVELS);
                            totalMovesLabel.setText(moveCounterMessage + moveCounter);
                            userActionLabel.setText("No action taken yet");
                            SetupLevel(5, 5, 5);
                        } else if (currentLevel == 3) {
                            //Update labels
                            levelName.setText("Level name: Hard");
                            goalLabel.setText("Level: 3/"+ TOTAL_LEVELS);
                            totalMovesLabel.setText(moveCounterMessage + moveCounter);
                            userActionLabel.setText("No action taken yet");
                            SetupLevel(8, 8, 5);
                        }
                        else {
                            backToMenu();
                        }
                        dialogInterface.dismiss();
                    }
                    else if (gameMenuOption == 1) {
                        //Go to main menu
                        backToMenu();
                    }
                    else if (gameMenuOption == 2) {
                        //Go to Custom mode
                        openCustom();
                    }
                    else if (gameMenuOption == 3) {
                        //Replay level and reshuffle
                        String value = getIntent().getStringExtra("gametype");
                        moveCounter = 0;
                        if (value.equals("campaign")) {
                            if (currentLevel == 1) {
                                //Update labels
                                levelName.setText("Level name: Easy");
                                goalLabel.setText("Level: 1/"+ TOTAL_LEVELS);
                                totalMovesLabel.setText(moveCounterMessage + moveCounter);
                                userActionLabel.setText("No action taken yet");
                                SetupLevel(3, 3, 3);
                            } else if (currentLevel == 2) {
                                //Update labels
                                levelName.setText("Level name: Medium");
                                goalLabel.setText("Level: 2/"+ TOTAL_LEVELS);
                                totalMovesLabel.setText(moveCounterMessage + moveCounter);
                                userActionLabel.setText("No action taken yet");
                                SetupLevel(5, 5, 5);
                            } else if (currentLevel == 3) {
                                //Update labels
                                levelName.setText("Level name: Hard");
                                goalLabel.setText("Level: 3/"+ TOTAL_LEVELS);
                                totalMovesLabel.setText(moveCounterMessage + moveCounter);
                                userActionLabel.setText("No action taken yet");
                                SetupLevel(8, 8, 5);
                            }
                            dialogInterface.dismiss();
                        }
                        else if (value.equals("custom")) {
                            SetupLevel(customX, customY, customPath);
                        }
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    private void openCustom() {
        Intent intent = new Intent(this, CustomActivity.class);
        startActivity(intent);
    }
    private void backToMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}