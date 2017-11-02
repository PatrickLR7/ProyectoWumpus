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
    /** Lista de cuevas en el juego */
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
            if (this.presenteEnElGrafo(nodoX))
                nodos.remove(nodoX);
            if (this.presenteEnElGrafo(nodoY))
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

    /**
     * Obtiene la cantidad de nodos presentes en el grafo
     */
    public int getNumeroNodos(){
        return nodos.size();
    }

    /**
     * Metodo para verificar que el grafo posea un solo componente conectado.
     * @return true si solo hay un componente, falso en caso contrario.
     */
    public boolean totalmenteConectado(){
        List<Integer> encontrados = new ArrayList<>();
        if (nodos.size() > 0)
            this.totalmenteConectado(encontrados, nodos.get(0) );
        return Grafo.igualdadEntreListas(encontrados, nodos);
    }

    /**
     * Metodo recursivo que recorre los elementos conectados apartir de cierto nodo actual
     * @param encontrados: Los nodos encontrados hasta el momento
     * @param nodoActual: El nodo que estamos a punto de agregar y analizar.
     */
    private void totalmenteConectado(List<Integer> encontrados, int nodoActual){
        encontrados.add(nodoActual);
        List<Integer> listaVecinos = this.obtenerVecinos(nodoActual);
        for (int i = 0; i < listaVecinos.size(); i++) {
            Integer vecino = listaVecinos.get(i);
            if ( !encontrados.contains( vecino )){
                this.totalmenteConectado(encontrados, vecino );
            }
        }
    }

    /**
     * Metodo que verifica si dos listas contienen los mismos elementos.
     * @param L1: La primera lista
     * @param L2: La segunda lista
     * @return true si contienen los mismos elementos, false en caso contrario.
     */
    private static boolean igualdadEntreListas(List<Integer> L1, List<Integer> L2){
        int i = 0;
        boolean iguales = ( L1.size() == L2.size() );
        while (iguales && i < L1.size() ){
            if ( !L2.contains(L1.get(i)) ){
                iguales = false;
            }
            ++i;
        }
        return iguales;
    }

    /**
     * Metodo para verificar si un nodo tiene al menos alguna conexion dentro del grafo.
     * @param nodo: El nodo en la matriz
     * @return true si el nodo tiene una conexion en el grafo.
     */
    public boolean presenteEnElGrafo(int nodo){
        boolean presente = false;
        int i = 0;
        while (!presente && i < dimensionMatriz){
            presente = matriz[nodo][i];
            ++i;
        }
        return presente;
    }

    public Pair obtenerFilaColumna(int nodo){
        //int x = nodo % dimensionMatriz;
        //int y = nodo % dimensionMatriz;

        int contador = 0;

        int fila = 0;
        int columna = 0;

        while( contador != nodo){


            while( columna < 9/2){

                //



                columna++;
                contador++;
            }

            columna = 0;
            fila++;
        }



        return new Pair(fila,columna);
    }
}