package com.example.carlos.wumpusproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Grafo;
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
        // -84.05
        //radio: 5 mts
       this.generarTiposDeCuevas();
       MapsActivity mapa = new MapsActivity();
       Intent intent;
      // intent = new Intent(getApplicationContext(), MapsActivity.class);
       //startActivity(intent);
        //getlocation usuario
       double latInicial = 0; //llenar con usuario
       double lonInicial = 0;
       int nodoInicial = 0;
       //boolean presente = false;

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


       /*
       //ver nodos del laberinto
       List<Integer> nodos = laberinto.obtenerNodos();
       for (int i = 0; i <  nodos.size(); i++) {
           int x = nodos.get(i);
           System.out.println(x);
       }*/

       int dis = 5;
       if(Config.labEsRegular){
           switch(tamGrafo) {
               case 4:
                   Vector<Double> coordenada = new Vector<Double>();
                   if(nodoInicial == 0){
                       coordenada.add(latInicial + dis*2);//fila
                       coordenada.add(lonInicial + dis);//colum
                       coordenadasCuevas.setElementAt(coordenada,1);

                       coordenada = new Vector<Double>();

                       coordenada.add(latInicial + dis*2);
                       coordenada.add(lonInicial - dis);
                       coordenadasCuevas.setElementAt(coordenada,2);

                       coordenada = new Vector<Double>();

                       coordenada.add(latInicial+dis);
                       coordenada.add(lonInicial);
                       coordenadasCuevas.setElementAt(coordenada,3);
                   }

                   if(nodoInicial == 1){
                       coordenada.add(latInicial-dis*2);//fila
                       coordenada.add(lonInicial-dis);//colum
                       coordenadasCuevas.setElementAt(coordenada,0);

                       coordenada = new Vector<Double>();

                       coordenada.add(latInicial);
                       coordenada.add(lonInicial-dis*2);
                       coordenadasCuevas.setElementAt(coordenada,2);


                       coordenada = new Vector<Double>();

                       coordenada.add(latInicial+dis);
                       coordenada.add(lonInicial-dis);
                       coordenadasCuevas.setElementAt(coordenada,3);

                   }

                   if(nodoInicial == 2){
                       coordenada.add(latInicial-dis*2);//fila
                       coordenada.add(lonInicial+dis);//colum
                       coordenadasCuevas.setElementAt(coordenada,0);

                       coordenada = new Vector<Double>();

                       coordenada.add(latInicial);
                       coordenada.add(lonInicial+dis*2);
                       coordenadasCuevas.setElementAt(coordenada,1);

                       coordenada = new Vector<Double>();

                       coordenada.add(latInicial-dis);
                       coordenada.add(lonInicial+dis);
                       coordenadasCuevas.setElementAt(coordenada,3);

                   }

                   if(nodoInicial == 3){

                       coordenada.add(latInicial-dis);//fila
                       coordenada.add(lonInicial);//colum
                       coordenadasCuevas.setElementAt(coordenada,0);

                       coordenada = new Vector<Double>();

                       coordenada.add(latInicial+dis);
                       coordenada.add(lonInicial+dis);
                       coordenadasCuevas.setElementAt(coordenada,1);

                       coordenada = new Vector<Double>();

                       coordenada.add(latInicial+dis);
                       coordenada.add(lonInicial-dis);
                       coordenadasCuevas.setElementAt(coordenada,2);
                   }
                   break;

           }


           for (int i = 0; i < tamGrafo; i++) {

              // Vector<Double> coor = new Vector<Double>();
              // coor = coordenadasCuevas.elementAt(i);

               Double x = (Double) coordenadasCuevas.elementAt(i).elementAt(0);

               mapa.agregarMarca((Double)coordenadasCuevas.get(i).get(0),(Double)coordenadasCuevas.get(i).get(1));

           }
       }
       else {
           int numNodos = Config.numFilas * Config.numColumnas;
           for (int i = 0; i < numNodos; i++) {
               if (laberinto.presenteEnElGrafo(i)){

               }
           }

       }

      // startActivity(intent);


         //  boolean encontrado = false;

      //     for (int i = 0; i < nodoInicial; i++) {//arriba

       //        presente = laberinto.presenteEnElGrafo(i);

       //        if (presente == true) {

           //        for (int colum = i; colum >= 0; colum--) {

             //          for (int fila = 1; fila < tamGrafo; fila++) {

                  //         if ((colum + tamGrafo) == nodoInicial) {

                               //Aumentar lat con fila y resta lon con colum
                               //crear mark
                 //              encontrado = true;
                  //         }

                 //      }

                 //  }

               //    if (encontrado == false) {

                  //     for (int colum = i; colum <= nodoInicial; colum++) {

                   //        for (int fila = 1; fila < tamGrafo; fila++) {

                    //           if ((colum + tamGrafo) == nodoInicial) {

                                   //Aumentar lat con fila y aumenta lon con colum
                                   //crear mark
                      //             encontrado = true;
                     //          }

                    //       }

                    //   }

                //   }
           //    }

         //  }

          // for (int i = tamGrafo * tamGrafo; i > nodoInicial; i--) {//abajo

          //     presente = laberinto.presenteEnElGrafo(i);

          //     if (presente == true) {

           //        for (int colum = i; colum >= nodoInicial; colum--) {

               //        for (int fila = 1; fila < tamGrafo; fila++) {

             //              if ((colum - tamGrafo) == nodoInicial) {

                               //restar lat con fila y lon con colum
                               //crear mark
                 //              encontrado = true;
                     //      }

                //       }

               //    }


              //     if (encontrado == false) {

                    //   for (int colum = i; colum <= tamGrafo*tamGrafo; colum++) {

                    //       for (int fila = 1; fila < tamGrafo; fila++) {

                      //         if ((colum - tamGrafo) == nodoInicial) {

                                   //Aumentar lat con fila y aumenta lon con colum
                                   //crear mark
                        //           encontrado = true;
                      //         }

                       //    }

                    //   }

                //   }

             //  }

         //  }


       }



}













