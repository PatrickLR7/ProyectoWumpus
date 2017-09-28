package com.example.carlos.wumpusproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by b40569 on 28/09/2017.
 */

public class GrafosRegulares extends AppCompatActivity implements View.OnClickListener {

    ImageButton tetrahedro;
    ImageButton octahedro;
    ImageButton cubo;
    ImageButton icosahedro;
    ImageButton dodecahedro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regular);

        tetrahedro = (ImageButton) findViewById(R.id.Tetrahedro);
        octahedro = (ImageButton) findViewById(R.id.Octahedro);
        cubo = (ImageButton) findViewById(R.id.Cubo);
        icosahedro = (ImageButton) findViewById(R.id.Icosahedro);
        dodecahedro = (ImageButton) findViewById(R.id.Dodecahedro);

        tetrahedro.setOnClickListener(this);
        octahedro.setOnClickListener(this);
        cubo.setOnClickListener(this);
        icosahedro.setOnClickListener(this);
        dodecahedro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Grafo laberinto;
        List<Integer> tipos;

        switch(v.getId()){
            case R.id.Tetrahedro:
                laberinto = new Grafo(4);
                tipos = llenarCueva(4);
                laberinto.addArista(0,1);
                laberinto.addArista(0,2);
                laberinto.addArista(0,3);
                laberinto.addArista(1,0);
                laberinto.addArista(1,2);
                laberinto.addArista(1,3);
                laberinto.addArista(2,0);
                laberinto.addArista(2,1);
                laberinto.addArista(2,3);
                laberinto.addArista(3,0);
                laberinto.addArista(3,1);
                laberinto.addArista(3,2);
                break;
            case R.id.Octahedro:
                laberinto = new Grafo(6);
                break;
            case R.id.Cubo:
                laberinto = new Grafo(8);
                break;
            case R.id.Icosahedro:
                laberinto = new Grafo(12);
                break;
            case R.id.Dodecahedro:
                laberinto = new Grafo(20);
                break;
        }



    }

    /**
     * 0 -> Cueva libre
     * 1 -> Wumpus
     * 2 -> Pozo
     * 3 -> Murcielago
     * 4 -> Cazador
     */
    public List<Integer> llenarCueva(int tamGrafo) {
        List<Integer> tiposC = new ArrayList<>(tamGrafo);
        boolean wumpus = false;
        for (int x = 0; x < tamGrafo; x++) {
            int tipo = (int) Math.random() * 3;
            if (tipo == 1 && wumpus == true) {
                x--;
            } else {
                tiposC.add(tipo);
                if (tipo == 1) {
                    wumpus = true;
                }
            }
        }
        return tiposC;
    }
}
