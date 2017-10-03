package com.example.carlos.wumpusproject.utils;

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
        for (int x = 0; x < tam; x++) {
            for (int y = 0; y < tam; y++) {
                matriz[x][y] = false;
            }
        }
    }

    /**
     * Se agrega un camino entre los nodos especificados.
     */
    public void addArista(int nodoX, int nodoY) {
        if (!matriz[nodoX][nodoY]) {
            matriz[nodoX][nodoY] = true;
        }
    }

    /**
     * Se borra un camino entre los nodos especificados.
     */
    public void deleteArista(int nodoX, int nodoY) {
        if (matriz[nodoX][nodoY]) {
            matriz[nodoX][nodoY] = false;
        }
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
}