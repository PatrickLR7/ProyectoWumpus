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
 * Clase que maneja una victoria.
 */
public class GameOverVictory extends AppCompatActivity implements View.OnClickListener {

    /** Boton menu. */
    private Button menu;

    /**
     * Método encargado de iniciar la actividad.
     * @param savedInstanceState: Instancia antigua guardada acerca de esta actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.gameover_victory);

        menu = (Button) findViewById(R.id.buttonRestart4);
        menu.setOnClickListener(this);

        //Prepara el video
        VideoView videoview = findViewById(R.id.videoVictory);
        videoview.requestFocus();
        Uri video = Uri.parse("android.resource://com.example.carlos.wumpusproject/raw/victory2");
        videoview.setVideoURI(video);
        videoview.setMediaController(new MediaController(this));
        videoview.start();
    }

    /**
     * Metodo que controla cuando se hace click en un boton.
     * @param view: La vista sobre la que se ubican los botones.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRestart4:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                break;
        }
    }

    /**
     * Metodo que crea un cambio en la configuración.
     * @param newConfig: La nueva configuración deseada.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}