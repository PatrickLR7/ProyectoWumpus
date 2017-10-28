package com.example.carlos.wumpusproject;

import android.os.Bundle;

import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Grafo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Created by b40569 on 23/10/2017.
 */

public class Emplazar {

     Grafo laberinto = Config.laberinto;
     Vector tiposDeCuevas = null;
     GoogleMap map = Config.map;
     int tamGrafo = laberinto.getDimensionMatriz();
     List<Vector> coordenadasCuevas = null;
    MapsActivity mapsActivity = new MapsActivity(map);

    public void generarPersonaje() {


        //tiposDeCuevas = new List<Integer> (tamGrafo * tamGrafo);

        boolean personaje = false;

        for (int x = 0; x < tamGrafo * tamGrafo; x++) {

            if (laberinto.presenteEnElGrafo(x) == true) {

                int tipo = (int) (Math.random() * 4);

                if (tipo == 4 && personaje == false) {
                    //x--;
                    tiposDeCuevas.add(4);
                    personaje = true;
                }

            }else{

                tiposDeCuevas.add(-1);
            }

        }


        Config.tiposDeCuevas = tiposDeCuevas;


    }






   public void crearMapMarks() {

//9.93
// -84.05
//radio: 5 mts


       //getlocation usuario


       double latInicial = 0;
       double lonInicial = 0;
       Vector coordenada = null;
       int nodoInicial = 0;

       boolean presente = false;
       Iterator iterTiposCuevas = tiposDeCuevas.iterator();
       Iterator iterCoordendas = coordenadasCuevas.iterator();

       for (int x = 0; x < tamGrafo*tamGrafo; x++) {

           if(iterTiposCuevas.equals(4) == true  ){


               coordenada.add(latInicial);//usuario
               coordenada.add(lonInicial);//usuario
               coordenadasCuevas.set(x,coordenada);

               nodoInicial = x;

               System.out.println("x: " + x + "lat: " + latInicial + "lon: " + lonInicial );
               //generar marcador

           }else {

               coordenada.add(-1);
               coordenada.add(-1);
               coordenadasCuevas.set(x,coordenada);

           }


           iterTiposCuevas.next();
           iterCoordendas.next();
       }



           boolean encontrado = false;

           for (int i = 0; i < nodoInicial; i++) {//arriba

               presente = laberinto.presenteEnElGrafo(i);

               if (presente == true) {

                   for (int colum = i; colum >= 0; colum--) {

                       for (int fila = 1; fila < tamGrafo; fila++) {

                           if ((colum + tamGrafo) == nodoInicial) {

                               //Aumentar lat con fila y resta lon con colum
                               //crear mark
                               encontrado = true;
                           }

                       }

                   }

                   if (encontrado == false) {

                       for (int colum = i; colum <= nodoInicial; colum++) {

                           for (int fila = 1; fila < tamGrafo; fila++) {

                               if ((colum + tamGrafo) == nodoInicial) {

                                   //Aumentar lat con fila y aumenta lon con colum
                                   //crear mark
                                   encontrado = true;
                               }

                           }

                       }

                   }
               }

           }

           for (int i = tamGrafo * tamGrafo; i > nodoInicial; i--) {//abajo

               presente = laberinto.presenteEnElGrafo(i);

               if (presente == true) {

                   for (int colum = i; colum >= nodoInicial; colum--) {

                       for (int fila = 1; fila < tamGrafo; fila++) {

                           if ((colum - tamGrafo) == nodoInicial) {

                               //restar lat con fila y lon con colum
                               //crear mark
                               encontrado = true;
                           }

                       }

                   }


                   if (encontrado == false) {

                       for (int colum = i; colum <= tamGrafo*tamGrafo; colum++) {

                           for (int fila = 1; fila < tamGrafo; fila++) {

                               if ((colum - tamGrafo) == nodoInicial) {

                                   //Aumentar lat con fila y aumenta lon con colum
                                   //crear mark
                                   encontrado = true;
                               }

                           }

                       }

                   }

               }

           }


       }

   }













