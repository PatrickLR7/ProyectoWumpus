package com.example.carlos.wumpusproject.utils;

import com.google.android.gms.maps.GoogleMap;

import java.util.List;
import java.util.Vector;

/**
 * Created by carlos on 11/10/17.
 */

public class Config {
    public static boolean labEsRegular;
    public static Grafo laberinto;
    public static List<Integer> tiposDeCuevas;
    public static List<Integer> caminoDeCuevas;
    public static GoogleMap map;
    public static List<Vector> coordenadasCuevas;
    public static Pair coordenadasIniciales;
    public static double latUsuario,lonUsuario;
    public static double distancia = 5.0;

    public static final int NUM_FLECHAS = 5;

    // Filas y columnas para grafos irregulares
    public static int numFilas = 6;
    public static int numColumnas = 6;
}
