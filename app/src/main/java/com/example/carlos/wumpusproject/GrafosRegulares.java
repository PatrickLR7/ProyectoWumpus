package com.example.carlos.wumpusproject;

import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Grafo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class GrafosRegulares extends AppCompatActivity implements View.OnClickListener {

    ImageButton tetraedro;
    ImageButton octaedro;
    ImageButton cubo;
    ImageButton icosaedro;
    ImageButton dodecaedro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regular);

        tetraedro = (ImageButton) findViewById(R.id.Tetraedro);
        octaedro = (ImageButton) findViewById(R.id.Octaedro);
        cubo = (ImageButton) findViewById(R.id.Cubo);
        icosaedro = (ImageButton) findViewById(R.id.Icosaedro);
        dodecaedro = (ImageButton) findViewById(R.id.Dodecaedro);
        tetraedro.setOnClickListener(this);
        octaedro.setOnClickListener(this);
        cubo.setOnClickListener(this);
        icosaedro.setOnClickListener(this);
        dodecaedro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Grafo laberinto = null;
        List<Integer> tipos = null;
        switch(v.getId()){
            case R.id.Tetraedro:
                Toast.makeText(this, "Su laberinto tiene 4 cuevas", Toast.LENGTH_LONG).show();
                laberinto = new Grafo(9);
               // tipos = llenarCueva(4);


                laberinto.addArista(1,4);
                laberinto.addArista(1,6);
                laberinto.addArista(1,8);
                laberinto.addArista(4,8);
                laberinto.addArista(4,6);
                laberinto.addArista(6,8);


                break;

            case R.id.Octaedro:
                Toast.makeText(this, "Su laberinto tiene 6 cuevas", Toast.LENGTH_LONG).show();
                laberinto = new Grafo(25);
                laberinto.addArista(2,20);
                laberinto.addArista(2,13);
                laberinto.addArista(2,11);
                laberinto.addArista(2,24);
                laberinto.addArista(11,13);
                laberinto.addArista(11,20);
                laberinto.addArista(11,17);
                laberinto.addArista(13,17);
                laberinto.addArista(13,24);
                laberinto.addArista(17,20);
                laberinto.addArista(17,24);
                laberinto.addArista(20,24);
                break;

            case R.id.Cubo:
                Toast.makeText(this, "Su laberinto tiene 8 cuevas", Toast.LENGTH_LONG).show();
                laberinto = new Grafo(16);
               // tipos = llenarCueva(8);
                laberinto.addArista(0,3);
                laberinto.addArista(3,6);
                laberinto.addArista(5,6);
                laberinto.addArista(6,10);
                laberinto.addArista(10,15);
                laberinto.addArista(0,5);
                laberinto.addArista(3,15);
                laberinto.addArista(5,9);
                laberinto.addArista(9,10);
                laberinto.addArista(12,15);
                laberinto.addArista(0,12);
                laberinto.addArista(9,12);

                break;

            case R.id.Icosaedro:
                Toast.makeText(this, "Su laberinto tiene 12 cuevas", Toast.LENGTH_LONG).show();
                laberinto = new Grafo(81);
             //   tipos = llenarCueva(12);
                laberinto.addArista(4,72);
                laberinto.addArista(4,47);
                laberinto.addArista(4,40);
                laberinto.addArista(4,51);
                laberinto.addArista(4,80);
                laberinto.addArista(40,47);
                laberinto.addArista(40,48);
                laberinto.addArista(40,50);
                laberinto.addArista(40,51);
                laberinto.addArista(47,48);
                laberinto.addArista(47,56);
                laberinto.addArista(47,72);
                laberinto.addArista(48,50);
                laberinto.addArista(48,58);
                laberinto.addArista(48,56);
                laberinto.addArista(50,51);
                laberinto.addArista(50,58);
                laberinto.addArista(50,60);
                laberinto.addArista(51,60);
                laberinto.addArista(51,80);
                laberinto.addArista(56,58);
                laberinto.addArista(56,67);
                laberinto.addArista(56,72);
                laberinto.addArista(58,60);
                laberinto.addArista(58,67);
                laberinto.addArista(60,67);
                laberinto.addArista(60,80);
                laberinto.addArista(67,72);
                laberinto.addArista(67,80);
                laberinto.addArista(72,80);

                break;

            case R.id.Dodecaedro:
                Toast.makeText(this, "Su laberinto tiene 20 cuevas", Toast.LENGTH_LONG).show();
                laberinto = new Grafo(121);
             //   tipos = llenarCueva(20);
                laberinto.addArista(15,26);
                laberinto.addArista(15,43);
                laberinto.addArista(15,53);
                laberinto.addArista(26,35);
                laberinto.addArista(26,39);
                laberinto.addArista(35,44);
                laberinto.addArista(35,47);
                laberinto.addArista(39,49);
                laberinto.addArista(39,52);
                laberinto.addArista(43,44);
                laberinto.addArista(44,98);
                laberinto.addArista(47,49);
                laberinto.addArista(47,57);
                laberinto.addArista(49,61);
                laberinto.addArista(52,53);
                laberinto.addArista(52,62);
                laberinto.addArista(53,106);
                laberinto.addArista(56,57);
                laberinto.addArista(56,88);
                laberinto.addArista(57,69);
                laberinto.addArista(61,62);
                laberinto.addArista(61,69);
                laberinto.addArista(62,94);
                laberinto.addArista(69,80);
                laberinto.addArista(80,88);
                laberinto.addArista(80,94);
                laberinto.addArista(88,98);
                laberinto.addArista(94,106);
                laberinto.addArista(98,106);

                break;
        }

        Config.laberinto = laberinto;
        // Config.tiposDeCuevas = tipos;

        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(i);

        //Emplazar emplazar = new Emplazar();
        //emplazar.crearMapMarks();
    }
}