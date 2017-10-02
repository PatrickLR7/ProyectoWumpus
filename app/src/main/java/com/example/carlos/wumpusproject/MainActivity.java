package com.example.carlos.wumpusproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button poliedro;
    Button laberinto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        poliedro = (Button) findViewById(R.id.PoliRegButton);
        poliedro.setOnClickListener(this);
        laberinto = (Button) findViewById(R.id.LabIrregButton);
        laberinto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.PoliRegButton:
                intent = new Intent(getApplicationContext(), GrafosRegulares.class);
                startActivity(intent);
                break;
            case R.id.LabIrregButton:
                intent = new Intent(getApplicationContext(), GraphDraw.class);
                startActivity(intent);
                break;
        }
    }
}