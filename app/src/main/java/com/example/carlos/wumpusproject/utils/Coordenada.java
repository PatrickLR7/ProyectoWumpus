package com.example.carlos.wumpusproject.utils;

/**
 * Created by carlos on 08/11/17.
 */

public class Coordenada {

    private double latitud;
    private double longitud;

    public Coordenada(double latitud, double longitud){
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud(){
        return latitud;
    }

    public double getLongitud(){
        return longitud;
    }
}
