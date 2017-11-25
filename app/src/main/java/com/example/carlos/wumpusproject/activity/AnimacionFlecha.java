package com.example.carlos.wumpusproject.activity;

import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;
import com.example.carlos.wumpusproject.R;

/**
 * Clase que controla la animación del lanzamiento de una flecha.
 */
public class AnimacionFlecha extends AppCompatActivity {

    /** Numero de veces que se ejecutó la animación. */
    int counter = 0;

    /**
     * Método encargado de iniciar la actividad.
     * @param savedInstanceState: Instancia antigua guardada acerca de esta actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_animacion_flecha);

        //Contador que permite entrar a ver el video y luego devolverse a la pantalla anterior.
        new CountDownTimer(3500, 3000) {
            @Override
            public void onTick(long l) {
                if (counter == 0) {
                    //Se crea el video
                    VideoView videoview = findViewById(R.id.animacionFlecha);
                    videoview.requestFocus();
                    Uri video = Uri.parse("android.resource://com.example.carlos.wumpusproject/raw/animacionflecha");
                    videoview.setVideoURI(video);
                    videoview.setMediaController(new MediaController(getApplicationContext()));
                    //Se reproduce el video
                    videoview.start();

                    counter++;
                }
            }

            @Override
            public void onFinish() {
                AnimacionFlecha.super.finish();
            }
        }.start();
    }
}