package com.example.carlos.wumpusproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.carlos.wumpusproject.utils.Config;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button poliedro;
    private Button laberinto;
    private Button emplazar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Bienvenido al \"The Wumpus\"");

        poliedro = (Button) findViewById(R.id.PoliRegButton);
        poliedro.setOnClickListener(this);
        laberinto = (Button) findViewById(R.id.LabIrregButton);
        laberinto.setOnClickListener(this);
        emplazar = (Button) findViewById(R.id.emplazar);
        emplazar.setOnClickListener(this);
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

            case R.id.emplazar:
                intent = new Intent(getApplicationContext(), MapsActivity.class); //Descomentar para Cargar Mapa para prueba
                startActivity(intent); //Descomentar para Cargar Mapa para prueba
                break;
        }
    }
}