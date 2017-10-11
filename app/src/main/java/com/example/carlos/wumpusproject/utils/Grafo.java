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
    private int totalCuevas;

    public Grafo(int tam) {
        totalCuevas = tam;
        matriz = new boolean[tam][tam];
    }

    public int getTotalCuevas(){
        return totalCuevas;
    }

    /**
     * Se agrega un camino entre los nodos especificados.
     */
    public void addArista(int nodoX, int nodoY) {
        if (!matriz[nodoX][nodoY]) {
            matriz[nodoX][nodoY] = true;
            matriz[nodoY][nodoX] = true;
        }
    }

    /**
     * Se borra un camino entre los nodos especificados.
     */
    public void deleteArista(int nodoX, int nodoY) {
        if (matriz[nodoX][nodoY]) {
            matriz[nodoX][nodoY] = false;
            matriz[nodoY][nodoX] = false;
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
        for (int x = 0; x < totalCuevas; x++) {
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
        List<Integer> nodos = new ArrayList<>();
        for (int i = 0; i < totalCuevas; i++) {
            boolean found = false;
            int j = 0;
            while (j < totalCuevas && !found){
                if (matriz[i][j]){
                    nodos.add(i);
                    found = true;
                }
                ++j;
            }
        }
        return nodos;
    }


}