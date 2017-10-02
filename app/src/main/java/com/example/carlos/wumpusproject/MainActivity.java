package com.example.carlos.wumpusproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button poliedro;
    Button laberinto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        poliedro = (Button) findViewById(R.id.PoliRegButton);
        poliedro.setOnClickListener((View.OnClickListener) this);
        laberinto = (Button) findViewById(R.id.LabIrregButton);
        laberinto.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.PoliRegButton:
                Intent i1 = new Intent(getApplicationContext(), GrafosRegulares.class);
                startActivity(i1);
                break;
            case R.id.LabIrregButton:
                break;
        }
    }
}