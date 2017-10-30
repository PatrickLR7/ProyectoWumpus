package com.example.carlos.wumpusproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Grafo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

/**
 * Created by b40569 on 23/10/2017.
 */

public class Emplazar extends AppCompatActivity {

     Grafo laberinto = Config.laberinto;
     Vector tiposDeCuevas = new Vector();
     GoogleMap map = Config.map;
     int tamGrafo = laberinto.getDimensionMatriz();

     Vector<Vector> coordenadasCuevas = new Vector() ;


    //MapsActivity mapsActivity = new MapsActivity(map);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setTitle("Caza al Wumpus!");


    }




    public void generarPersonaje() {


        //tiposDeCuevas = new List<Integer> (tamGrafo * tamGrafo);

        boolean personaje = false;

        for (int x = 0; x < tamGrafo ; x++) {

           // if (laberinto.presenteEnElGrafo(x) == true) {

                int tipo = (int) (Math.random() * 5);

                if (tipo == 4 && personaje == false) {
                    //x--;
                    tiposDeCuevas.add(4);
                    personaje = true;
                }else{

                    tiposDeCuevas.add(-1);
                }


            if(x == tamGrafo - 1 && personaje == false){
                tiposDeCuevas.clear();
                    x=0;

            }
        }


        Config.tiposDeCuevas = tiposDeCuevas;


    }






   public void crearMapMarks() {

//9.93
// -84.05
//radio: 5 mts
        this.generarPersonaje();



       //getlocation usuario

       MapsActivity mapa = new MapsActivity();
       Intent intent;
      // intent = new Intent(getApplicationContext(), MapsActivity.class);
       //startActivity(intent);



       double latInicial = 0;
       double lonInicial = 0;
       Vector<Double> coordenada = new Vector();
       int nodoInicial = 0;
       int nodoPer = tiposDeCuevas.indexOf(4);

       boolean presente = false;




       for (int x = 0; x < tamGrafo; x++) {

           if( x == nodoPer ){


               coordenada.add(latInicial);//usuario
               coordenada.add(lonInicial);//usuario
               coordenadasCuevas.add(x,coordenada);
               coordenada.clear();

               nodoInicial = x;

               System.out.println("x: " + x + "lat: " + latInicial + "lon: " + lonInicial );
               //generar marcador

           }else {

               coordenada.add(-1.0);
               coordenada.add(-1.0);
               coordenadasCuevas.add(x,coordenada);
               coordenada.clear();

           }



       }


       //ver nodos del laberinto
       List<Integer> nodos = laberinto.obtenerNodos();

       for (int i = 0; i <  nodos.size(); i++) {
           int x = nodos.get(i);
           System.out.println(x);

       }


       GoogleMap map;

       int dis = 5;
       if(Config.labEsRegular){
           switch(tamGrafo) {

               case 4:

                   if(nodoInicial == 0){


                       coordenada.add(latInicial+dis*2);//fila
                       coordenada.add(lonInicial+dis);//colum
                       coordenadasCuevas.setElementAt(coordenada,1);
                       coordenada.clear();


                       coordenada.add(latInicial+dis*2);
                       coordenada.add(lonInicial-dis);
                       coordenadasCuevas.setElementAt(coordenada,2);
                       coordenada.clear();

                       coordenada.add(latInicial+dis);
                       coordenada.add(lonInicial);
                       coordenadasCuevas.setElementAt(coordenada,3);
                       coordenada.clear();
                   }

                   if(nodoInicial == 1){


                       coordenada.add(latInicial-dis*2);//fila
                       coordenada.add(lonInicial-dis);//colum
                       coordenadasCuevas.setElementAt(coordenada,0);
                       coordenada.clear();

                       coordenada.add(latInicial);
                       coordenada.add(lonInicial-dis*2);
                       coordenadasCuevas.setElementAt(coordenada,2);
                       coordenada.clear();

                       coordenada.add(latInicial+dis);
                       coordenada.add(lonInicial-dis);
                       coordenadasCuevas.setElementAt(coordenada,3);
                       coordenada.clear();
                   }

                   if(nodoInicial == 2){


                       coordenada.add(latInicial-dis*2);//fila
                       coordenada.add(lonInicial+dis);//colum
                       coordenadasCuevas.setElementAt(coordenada,0);
                       coordenada.clear();

                       coordenada.add(latInicial);
                       coordenada.add(lonInicial+dis*2);
                       coordenadasCuevas.setElementAt(coordenada,1);
                       coordenada.clear();

                       coordenada.add(latInicial-dis);
                       coordenada.add(lonInicial+dis);
                       coordenadasCuevas.setElementAt(coordenada,3);
                       coordenada.clear();

                   }

                   if(nodoInicial == 3){


                       coordenada.add(latInicial-dis);//fila
                       coordenada.add(lonInicial);//colum
                       coordenadasCuevas.setElementAt(coordenada,0);
                       coordenada.clear();

                       coordenada.add(latInicial+dis);
                       coordenada.add(lonInicial+dis);
                       coordenadasCuevas.setElementAt(coordenada,1);
                       coordenada.clear();

                       coordenada.add(latInicial+dis);
                       coordenada.add(lonInicial-dis);
                       coordenadasCuevas.setElementAt(coordenada,2);
                       coordenada.clear();

                   }
                   break;

           }


           for (int i = 0; i < tamGrafo; i++) {

               Vector<Double> coor = new Vector();
               coor = coordenadasCuevas.elementAt(i);


               mapa.agregarMarca((Double)coordenadasCuevas.get(i).get(0),(Double)coordenadasCuevas.get(i).get(1));

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













