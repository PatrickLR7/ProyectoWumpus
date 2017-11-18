package com.example.carlos.wumpusproject.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.carlos.wumpusproject.R;
import com.example.carlos.wumpusproject.utils.Config;

/**
 * Clase de main activity.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /** Boton que muestra el layout de poliedros. */
    private Button poliedro;
    /** Boton que muestra el layout de drawingcanvas. */
    private Button laberinto;
    /** Boton que muestra el layout de emplazar el laberinto. */
    private Button emplazar;

    private Button arrows;
    private Button victory;
    private Button well;
    private Button wumpus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.carlos.wumpusproject.R.layout.activity_main);
        setTitle("Caza al Wumpus!");

        poliedro = (Button) findViewById(R.id.PoliRegButton);
        poliedro.setOnClickListener(this);
        laberinto = (Button) findViewById(R.id.LabIrregButton);
        laberinto.setOnClickListener(this);

        arrows = (Button) findViewById(R.id.pruebaArrows);
        arrows.setOnClickListener(this);
        victory = (Button) findViewById(R.id.pruebaVictory);
        victory.setOnClickListener(this);
        well = (Button) findViewById(R.id.pruebaWell);
        well.setOnClickListener(this);
        wumpus = (Button) findViewById(R.id.pruebaWumpus);
        wumpus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.PoliRegButton:
                Config.labEsRegular = true;
                intent = new Intent(getApplicationContext(), GrafosRegulares.class);
                startActivity(intent);
                break;
            case R.id.LabIrregButton:
                Config.labEsRegular = false;
                intent = new Intent(getApplicationContext(), GraphDrawActivity.class);
                startActivity(intent);
                break;
            case R.id.pruebaArrows:
                intent = new Intent(getApplicationContext(), GameOverArrows.class);
                startActivity(intent);
                break;
            case R.id.pruebaVictory:
                intent = new Intent(getApplicationContext(), GameOverVictory.class);
                startActivity(intent);
                break;
            case R.id.pruebaWell:
                intent = new Intent(getApplicationContext(), GameOverWell.class);
                startActivity(intent);
                break;
            case R.id.pruebaWumpus:
                intent = new Intent(getApplicationContext(), GameOverWumpus.class);
                startActivity(intent);
                break;
        }
    }


    protected void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }

}