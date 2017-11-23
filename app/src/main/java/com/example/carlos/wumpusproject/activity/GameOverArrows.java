package com.example.carlos.wumpusproject.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
import com.example.carlos.wumpusproject.R;

/**
 * Clase que maneja una derrota por quedarse sin flechas.
 */
public class GameOverArrows extends AppCompatActivity implements View.OnClickListener {

    /** Boton menu. */
    private Button menu;

    /**
     * Metodo que crea el layout.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.gameover_arrows);

        menu = (Button) findViewById(R.id.buttonRestart2);
        menu.setOnClickListener(this);

        //Prepara el video
        VideoView videoview = findViewById(R.id.videoArrows);
        videoview.requestFocus();
        Uri video = Uri.parse("android.resource://com.example.carlos.wumpusproject/raw/arrows2");
        videoview.setVideoURI(video);
        videoview.setMediaController(new MediaController(this));
        videoview.start();
    }

    /**
     * Metodo que controla cuando se hace click en un boton.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRestart2:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                break;
        }
    }

    /**
     * Metodo que crea un cambio en la configuración.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}