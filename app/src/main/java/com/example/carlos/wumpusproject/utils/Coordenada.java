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

    @Override
    public boolean equals(Object obj) {
        boolean esIgual = obj instanceof Coordenada;
        if (esIgual){
            Coordenada coordenada = (Coordenada) obj;
            esIgual = latitud == coordenada.latitud && longitud == coordenada.longitud;
        }
        return esIgual;
    }
}
