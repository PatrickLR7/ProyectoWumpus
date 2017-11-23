package com.example.carlos.wumpusproject.utils;

import com.beyondar.android.world.GeoObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Clase usada para guardar las diferentes escogencias de configuracion escogidas por el usuario.
 * La idea es mantenerlas de forma publica para poder acceder a ellas desde cualquier otra clase o
 * actividad.
 */
public class Config {

    /** Indica si el laberinto es regular o irregular. */
    public static boolean labEsRegular;

    /** Guarda el grafo dibujado o seleccionado por el usuario. */
    public static Grafo laberinto;

    /** Guarda los tipos de cuevas asociados. */
    public static List<Integer> tiposDeCuevas;

    /** Guarda el camino de las cuevas que el usuario ha recorrido. */
    public static List<Integer> caminoDeCuevas;

    /** Guarda las coordenadas asociadas a cada cueva en el mapa. */
    public static List< Vector<Double> > coordenadasCuevas;

    /** Guarda las coordenadas iniciales del jugador. */
    public static Vector<Double> coordenadasIniciales;

    /** Guardan las coordenadas actuales del jugador. */
    public static double latUsuario, lonUsuario;

    /** Distancia entre cuevas. */
    public static double distancia = 0.000109; //Distancia entre cuevas adyacentes: 20 metros. 000006 => 1 Metro

    /** Cueva inicial del jugador. */
    public static int cuevaInicial;

    /** Lista de GeoObjetos. */
    public static ArrayList<GeoObject> listaGeoObj;

    /** Mapea el número de cueva original a nombres de los objetos de BeyondAR. */
    public static HashMap mapOriginalANombre;

    /** Mapea nombres de los objetos de BeyondAR a el número de cueva original. */
    public static HashMap mapNombreAOriginal;

    /** Numero de flechas del jugador. */
    public static int numFlechas = 5;

    /** Instancia del juego actual. */
    public static Jugar jugar;

    /** Indica si el wumpus se ha encontrado. */
    public static Boolean wumpus = false;

    /** Indica si me quedé sin flechas. */
    public static Boolean sinFlechas = false;

    /** Indica si el wumpus me comió. */
    public static Boolean muerte = false;

    /** Indica si me caí en un pozo. */
    public static Boolean pozo = false;

    /** Cueva actual del jugador. */
    public static int cuevaActual = -1;
}