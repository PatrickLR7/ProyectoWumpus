package com.example.carlos.wumpusproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button poliedro;
    Button laberinto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);

        poliedro = (Button) findViewById(R.id.PoliRegButton);
        poliedro.setOnClickListener((View.OnClickListener) this);

    }
}