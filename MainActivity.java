package com.example.jumpingrookgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonToCampaign;
    private Button buttonCustom;
    private Button buttonInstructions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button setup
        buttonToCampaign = findViewById(R.id.buttonToCampaign);
        buttonCustom = findViewById(R.id.buttonCustom);
        buttonInstructions = findViewById(R.id.buttonInstructions);

        //button click handlers
        buttonToCampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCampaign();
            }
        });

        buttonCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCustom();
            }
        });

        buttonInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInstruction();
            }
        });
    }

    private void openCustom() {
        Intent intent = new Intent(this, CustomActivity.class);
        startActivity(intent);
    }
    private void openCampaign() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("gametype","campaign");
        startActivity(intent);
    }
    private void openInstruction() {
        Intent intent = new Intent(this, InstructionActivity.class);
        startActivity(intent);
    }


}