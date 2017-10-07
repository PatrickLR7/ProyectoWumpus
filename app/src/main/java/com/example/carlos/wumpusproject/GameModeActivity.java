package com.example.carlos.wumpusproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GameModeActivity extends AppCompatActivity implements View.OnClickListener {

    Button social;
    Button individual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);

        social = (Button) findViewById(R.id.socialMode);
        social.setOnClickListener(this);
        individual = (Button) findViewById(R.id.individualMode);
        individual.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        switch (view.getId()) {
            case R.id.individualMode:
                Toast.makeText(this, "Ha escogido el modo individual", Toast.LENGTH_LONG).show();
                startActivity(i);
                break;

            case R.id.socialMode:
                Toast.makeText(this, "Ha escogido el modo social", Toast.LENGTH_LONG).show();
                startActivity(i);
                break;
        }
    }
}