package com.example.carlos.wumpusproject.utils;

import java.util.List;

/**
 * Clase que guarda la configuracion escogida por el usuario.
 */

public class Config {
    /** Indica si el laberinto es regular o no. true => labRegular; false => labIrregular */
    public static boolean labEsRegular;
    /** Grafo que representa el laberinto. */
    public static Grafo laberinto;
    /** Tipos de cuevas del juego. */
    public static List<Integer> tiposDeCuevas;
}