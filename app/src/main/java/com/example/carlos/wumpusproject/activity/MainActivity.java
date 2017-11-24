package com.example.carlos.wumpusproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.carlos.wumpusproject.R;
import com.example.carlos.wumpusproject.utils.Config;

/**
 * Actividad inicial de la aplicación.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /** Boton que muestra el layout de poliedros. */
    private Button poliedro;
    /** Boton que muestra el layout de drawing canvas. */
    private Button laberinto;

    /**
     * Método invocado al crear el layout
     * @param savedInstanceState: Instancia anterior guardada acerca de esta actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.carlos.wumpusproject.R.layout.activity_main);
        setTitle("Caza al Wumpus!");

        poliedro = (Button) findViewById(R.id.PoliRegButton);
        poliedro.setOnClickListener(this);
        laberinto = (Button) findViewById(R.id.LabIrregButton);
        laberinto.setOnClickListener(this);

    }

    /**
     * Metodo que controla cuando se hace click en un boton.
     * @param view: Objeto vista sobre el que se clickea.
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            // Caso de un laberinto regular
            case R.id.PoliRegButton:
                Config.labEsRegular = true;
                intent = new Intent(getApplicationContext(), GrafosRegulares.class);
                startActivity(intent);
                break;

            // Caso de un laberinto irregular
            case R.id.LabIrregButton:
                Config.labEsRegular = false;
                intent = new Intent(getApplicationContext(), GraphDrawActivity.class);
                startActivity(intent);
                break;
        }
    }
}