package com.example.carlos.wumpusproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Grafo;
import com.example.carlos.wumpusproject.utils.Pair;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by b40569 on 23/10/2017.
 */

public class Emplazar extends AppCompatActivity {

     private Grafo laberinto;
     private List<Integer> tiposDeCuevas;
     private GoogleMap map;
     private int tamGrafo;
     private int posInicialJugador;
     private int posInicialWumpus;
     private double distancia;

     private Vector< Vector<Double> > coordenadasCuevas;
    //MapsActivity mapsActivity = new MapsActivity(map);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setTitle("Caza al Wumpus!");

        laberinto = Config.laberinto;
        map = Config.map;
        tamGrafo = laberinto.getDimensionMatriz();
        coordenadasCuevas = new Vector<>();
        distancia = Config.distancia;
    }



    /**
     * Define los tipos de las cuevas; es decir, si hay pozos o murcielagos.
     * Se usaran los siguientes numeros:
     * 0 -> cueva Libre.
     * 1 -> cueva con Wumpus.
     * 2 -> cueva con pozo.
     * 3 -> cueva con murcielagos.
     * 4 -> cueva inicial del personaje.
     */
    public void generarTiposDeCuevas() {
        tiposDeCuevas = new ArrayList<>(tamGrafo);
        for (int x = 0; x < tamGrafo ; x++) {
            int tipo = (int) (Math.random() * 3);
            switch (tipo){
                case 0: tiposDeCuevas.add(0);
                    break;
                case 1: tiposDeCuevas.add(2);
                    break;
                default:tiposDeCuevas.add(3);
                    break;
            }
        }
        posInicialJugador = (int) (Math.random()*tamGrafo);
        posInicialWumpus = (int) (Math.random()*tamGrafo);
        tiposDeCuevas.add(posInicialJugador, 4);
        tiposDeCuevas.add(posInicialWumpus, 1);
        Config.tiposDeCuevas = tiposDeCuevas;
    }

   public void crearMapMarks() {
       //9.93
       //-84.05
       //radio: 5 mts
       this.generarTiposDeCuevas();


       //getlocation usuario
       double latInicial = 0;                           //llenar con usuario
       double lonInicial = 0;
       int nodoInicial = 0;


       for (int x = 0; x < tamGrafo; x++) {
           Vector<Double> coordenada = new Vector<Double>();
           if (x == posInicialJugador) {
               coordenada.add(latInicial);//usuario
               coordenada.add(lonInicial);//usuario
               coordenadasCuevas.add(x, coordenada);
               nodoInicial = x;
               System.out.println("x: " + x + "lat: " + latInicial + "lon: " + lonInicial);
                                                                                       //generar marcador
           } else {
               coordenada.add(0.0);
               coordenada.add(0.0);
               coordenadasCuevas.add(x, coordenada);
           }
       }

       boolean encontrado = false;
       Pair pairInicial;
       pairInicial = laberinto.obtenerFilaColumna(nodoInicial);

       int filas, columnas;


       for (int nodo = 0; nodo < tamGrafo; nodo++) {

           if (nodo != nodoInicial) {
               if (laberinto.presenteEnElGrafo(nodo)) {
                   Pair pairNodo;
                   Pair pairDistancia;
                   pairNodo = laberinto.obtenerFilaColumna(nodo);

                   pairDistancia = pairInicial.restarPares(pairNodo);

                   filas = pairDistancia.getX();
                   columnas = pairDistancia.getY();

                   Vector<Double> coordenada = new Vector<Double>();
                   coordenada.add(latInicial + distancia * filas);
                   coordenada.add(lonInicial + distancia * columnas);
                   coordenadasCuevas.setElementAt(coordenada, nodo);
               }
           }

       }

   }

}













