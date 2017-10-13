package com.example.carlos.wumpusproject.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Grafo implementado por matriz de adyacencia
 */

public class Grafo {

    /** Matriz que representa el grafo. */
    private boolean[][] matriz;

    /** Tamaño del juego. */
    private int dimensionMatriz;

    /* Lista de cuevas en el juego */
    private List<Integer> nodos;

    public Grafo(int tam) {
        dimensionMatriz = tam;
        matriz = new boolean[tam][tam];
        nodos = new ArrayList<>();
    }

    public int getDimensionMatriz(){
        return dimensionMatriz;
    }

    /**
     * Se agrega un camino entre los nodos especificados.
     */
    public void addArista(int nodoX, int nodoY) {
        if (!matriz[nodoX][nodoY]) {
            matriz[nodoX][nodoY] = true;
            matriz[nodoY][nodoX] = true;
            if (!nodos.contains(nodoX))
                nodos.add(nodoX);
            if (!nodos.contains(nodoY))
                nodos.add(nodoY);
        }
    }

    /**
     * Se borra un camino entre los nodos especificados.
     */
    public void deleteArista(Integer nodoX, Integer nodoY) {
        if (matriz[nodoX][nodoY]) {
            matriz[nodoX][nodoY] = false;
            matriz[nodoY][nodoX] = false;
            if (nodos.contains(nodoX))
                nodos.remove(nodoX);
            if (nodos.contains(nodoY))
                nodos.remove(nodoY);
        }
    }

    /**
     * Metodo para saber si hay una arista entre dos nodos
     */
    public boolean hayArista(int nodoX, int nodoY){
        return matriz[nodoX][nodoY];
    }

    /**
     * Se obtienen los nodos adyacentes al nodo especificado por parametro.
     */
    public List<Integer> obtenerVecinos(int Nodo) {
        List<Integer> lista = new LinkedList<>();
        for (int x = 0; x < dimensionMatriz; x++) {
            if (matriz[Nodo][x]) {
                lista.add(x);
            }
        }
        return lista;
    }

    /**
     * Obtiene los nodos del grafo
     */
    public List<Integer> obtenerNodos(){
        return nodos;
    }

    /*
     * Obtiene la cantidad de nodos presentes en el grafo
     */
    public int getNumeroNodos(){
        return nodos.size();
    }


}