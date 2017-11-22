package com.example.carlos.wumpusproject.utils;

import com.beyondar.android.world.GeoObject;
import com.example.carlos.wumpusproject.beyondAR.SimpleCamera;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Clase usada para guardar las diferentes escogencias de configuracion escogidas por el usuario.
 * La idea es mantenerlas de forma publica para poder acceder a ellas desde cualquier otra clase o
 * actividad.
 */
public class Config {
    public static boolean labEsRegular; // Si laberinto es regular o no.
    public static Grafo laberinto; // Guarda el grafo dibujado o seleccionado por el usuario.
    public static List<Integer> tiposDeCuevas; // Guarda los tipos de cuevas asociados.
    public static List<Integer> caminoDeCuevas; // Guarda el camino de cuevas  que el usuario lleva en su recorrido.
    public static GoogleMap map; // Guarda el mapa de juego.
    public static List< Vector<Double> > coordenadasCuevas; // Guarda las coordenadas asociadas a cada cueva en el mapa.
    public static Vector<Double> coordenadasIniciales; // Guarda las coordenadas iniciales del jugador
    public static double latUsuario, lonUsuario; // Guardan las coordenadas actuales del jugador
    public static double distancia = 0.000109; // 20 metros Distancia entre cuevas adyacentes .000006 => 1 Metro
    public static int cuevaInicial;
    public static ArrayList<GeoObject> listaGeoObj;
    public static HashMap mapOriginalANombre;
    public static HashMap mapNombreAOriginal;
    public static int numFlechas = 5; // Numero de flechas del jugador
    public static Jugar jugar;
    public static Boolean wumpus = false;
    public static Boolean sinFlechas = false;
    public static Boolean muerte = false;
    public static Boolean pozo = false;
}