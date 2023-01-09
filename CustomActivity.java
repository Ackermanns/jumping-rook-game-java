package com.example.jumpingrookgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class CustomActivity extends AppCompatActivity {
    private Button buttonReturnToMenu;
    private Button buttonGenerate;
    private SeekBar xValueSeekBar;
    private SeekBar yValueSeekBar;
    private SeekBar pathValueSeekBar;
    private TextView setxLabel;
    private TextView setyLabel;
    private TextView setPathLabel;

    //default values
    int customX = 5;
    int customY = 5;
    int customPath = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        setxLabel = (TextView) findViewById(R.id.setxLabel);
        setyLabel = (TextView) findViewById(R.id.setyLabel);
        setPathLabel = (TextView) findViewById(R.id.setPathLabel);

        xValueSeekBar = findViewById(R.id.xValueSeekBar);
        yValueSeekBar = findViewById(R.id.yValueSeekBar);
        pathValueSeekBar = findViewById(R.id.pathValueSeekBar);


        //Set default value
        setxLabel.setText("X value: 5");
        setyLabel.setText("Y value: 5");
        setPathLabel.setText("Path value: 3");

        //seekbar setup
        xValueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                setxLabel.setText("X value: "+ String.valueOf(progress));
                customX = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        yValueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                setyLabel.setText("Y value: "+ String.valueOf(progress));
                customY = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        pathValueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                setPathLabel.setText("Path value: "+ String.valueOf(progress));
                customPath = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        //Button setup
        buttonReturnToMenu = findViewById(R.id.buttonReturnToMenu);
        buttonGenerate = findViewById(R.id.buttonGenerate);

        //button click handlers
        buttonReturnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMenu();
            }
        });

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateCustom();
            }
        });

    }
    private void backToMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void generateCustom() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("gametype","custom");
        intent.putExtra("customX",customX);
        intent.putExtra("customY",customY);
        intent.putExtra("customPath",customPath);
        startActivity(intent);

    }

}