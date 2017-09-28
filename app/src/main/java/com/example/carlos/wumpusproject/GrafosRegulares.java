package com.example.carlos.wumpusproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.List;

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
                tipos = llenarCueva(6);
                laberinto.addArista(0,1);
                laberinto.addArista(0,2);
                laberinto.addArista(0,3);
                laberinto.addArista(0,4);
                laberinto.addArista(1,0);
                laberinto.addArista(1,2);
                laberinto.addArista(1,4);
                laberinto.addArista(1,5);
                laberinto.addArista(2,0);
                laberinto.addArista(2,1);
                laberinto.addArista(2,3);
                laberinto.addArista(2,5);
                laberinto.addArista(3,0);
                laberinto.addArista(3,2);
                laberinto.addArista(3,4);
                laberinto.addArista(3,5);
                laberinto.addArista(4,0);
                laberinto.addArista(4,1);
                laberinto.addArista(4,3);
                laberinto.addArista(4,5);
                laberinto.addArista(5,1);
                laberinto.addArista(5,2);
                laberinto.addArista(5,3);
                laberinto.addArista(5,4);
                break;
            case R.id.Cubo:
                laberinto = new Grafo(8);
                tipos = llenarCueva(8);
                laberinto.addArista(0,1);
                laberinto.addArista(0,3);
                laberinto.addArista(0,4);
                laberinto.addArista(1,0);
                laberinto.addArista(1,2);
                laberinto.addArista(1,5);
                laberinto.addArista(2,1);
                laberinto.addArista(2,3);
                laberinto.addArista(2,6);
                laberinto.addArista(3,0);
                laberinto.addArista(3,2);
                laberinto.addArista(3,7);
                laberinto.addArista(4,0);
                laberinto.addArista(4,5);
                laberinto.addArista(4,7);
                laberinto.addArista(5,1);
                laberinto.addArista(5,4);
                laberinto.addArista(5,6);
                laberinto.addArista(6,2);
                laberinto.addArista(6,5);
                laberinto.addArista(6,7);
                laberinto.addArista(7,3);
                laberinto.addArista(7,4);
                laberinto.addArista(7,6);
                break;
            case R.id.Icosahedro:
                laberinto = new Grafo(12);
                tipos = llenarCueva(12);

                laberinto.addArista(0,0);
                laberinto.addArista(0,1);
                laberinto.addArista(0,2);
                laberinto.addArista(0,3);
                laberinto.addArista(0,4);
                laberinto.addArista(0,5);
                laberinto.addArista(0,6);
                laberinto.addArista(0,7);
                laberinto.addArista(1,0);
                laberinto.addArista(1,1);
                laberinto.addArista(1,2);
                laberinto.addArista(1,3);
                laberinto.addArista(1,4);
                laberinto.addArista(1,5);
                laberinto.addArista(1,6);
                laberinto.addArista(1,7);
                laberinto.addArista(2,0);
                laberinto.addArista(2,1);
                laberinto.addArista(2,2);
                laberinto.addArista(2,3);
                laberinto.addArista(2,4);
                laberinto.addArista(2,5);
                laberinto.addArista(2,6);
                laberinto.addArista(2,7);
                laberinto.addArista(3,0);
                laberinto.addArista(3,1);
                laberinto.addArista(3,2);
                laberinto.addArista(3,3);
                laberinto.addArista(3,4);
                laberinto.addArista(3,5);
                laberinto.addArista(3,6);
                laberinto.addArista(3,7);
                laberinto.addArista(4,0);
                laberinto.addArista(4,1);
                laberinto.addArista(4,2);
                laberinto.addArista(4,3);
                laberinto.addArista(4,4);
                laberinto.addArista(4,5);
                laberinto.addArista(4,6);
                laberinto.addArista(4,7);
                laberinto.addArista(5,0);
                laberinto.addArista(5,1);
                laberinto.addArista(5,2);
                laberinto.addArista(5,3);
                laberinto.addArista(5,4);
                laberinto.addArista(5,5);
                laberinto.addArista(5,6);
                laberinto.addArista(5,7);
                laberinto.addArista(6,0);
                laberinto.addArista(6,1);
                laberinto.addArista(6,2);
                laberinto.addArista(6,3);
                laberinto.addArista(6,4);
                laberinto.addArista(6,5);
                laberinto.addArista(6,6);
                laberinto.addArista(6,7);
                laberinto.addArista(7,0);
                laberinto.addArista(7,1);
                laberinto.addArista(7,2);
                laberinto.addArista(7,3);
                laberinto.addArista(7,4);
                laberinto.addArista(7,5);
                laberinto.addArista(7,6);
                laberinto.addArista(7,7);

                break;
            case R.id.Dodecahedro:
                laberinto = new Grafo(20);
                tipos = llenarCueva(20);
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
    private List<Integer> llenarCueva(int tamGrafo) {
        List<Integer> tiposC = new ArrayList<>(tamGrafo);
        boolean wumpus = false;
        for (int x = 0; x < tamGrafo; x++) {
            int tipo = (int) (Math.random() * 3);
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