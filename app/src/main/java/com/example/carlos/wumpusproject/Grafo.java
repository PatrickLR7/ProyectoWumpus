package com.example.carlos.wumpusproject;

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

    /** Lista de todas las cuevas del juego. Cada cueva está representada por un numero. Cada elemento de la lista será el tipo de cueva. */
    /*private List<Integer> listaCuevas;*/

    public Grafo(int tam) {
        totalCuevas = tam;
        matriz = new boolean[tam][tam];
        for (int x = 0; x < tam; x++) {
            for (int y = 0; y < tam; y++) {
                matriz[x][y] = false;
            }
        }
        //listaCuevas = new LinkedList<>();
    }

    /**
     * Se agrega un camino entre los nodos especificados.
     */
    public void addArista(int nodoX, int nodoY) {
        if (matriz[nodoX][nodoY] == false) {
            matriz[nodoX][nodoY] = true;
        }
    }

    /**
     * Se borra un camino entre loa nodos especificados.
     */
    public void deleteArista(int nodoX, int nodoY) {
        if (matriz[nodoX][nodoY] == true) {
            matriz[nodoX][nodoY] = false;
        }
    }

    /**
     * Se obtienen los nodos adyacentes al nodo especificado por parametro.
     */
    public List<Integer> obtenerVecinos(int Nodo) {
        List<Integer> lista = new LinkedList<>();
        for (int x = 0; x < totalCuevas; x++) {
            if (matriz[Nodo][x] == true) {
                lista.add(x);
            }
        }
        return lista;
    }

    /**
     * Se le asigna el tipo a cada cueva.
     * 0 => Sin nada
     * 1 => Cazador
     * 2 => Murcielagos
     * 3 => Pozo
     * 4 => Wumpus
     */
    /*public void asignarTipo() {
        boolean wumpus = false;
        for (int x = 0; x < totalCuevas; x++) {
            int tipo = (int) (Math.random() * 4);
            if (tipo == 4 && wumpus == true) {
                x--;
            } else {
                listaCuevas.add(tipo);
                if (tipo == 4) {
                    wumpus = true;
                }
            }
        }
    }*/
}