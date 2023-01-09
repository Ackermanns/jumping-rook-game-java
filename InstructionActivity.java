package com.example.jumpingrookgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class InstructionActivity extends AppCompatActivity {
    private Button buttonReturn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        //Button setup
        Button buttonReturn = findViewById(R.id.buttonReturn);

        //Video setup
        VideoView instructionVideo = findViewById(R.id.instructionVideo);
        instructionVideo.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.jumping_rook_rules);
        MediaController media = new MediaController(this);
        media.setAnchorView(instructionVideo);
        instructionVideo.setMediaController(media);

        //button click handlers
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMenu();
            }
        });
    }
    private void backToMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}