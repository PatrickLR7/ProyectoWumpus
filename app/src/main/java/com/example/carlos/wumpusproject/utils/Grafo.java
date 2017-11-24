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

    /**
     * Constructor
     * @param tam Cantidad de nodos del grafo.
     */
    public Grafo(int tam) {
        dimensionMatriz = tam;
        matriz = new boolean[tam][tam];
        nodos = new ArrayList<>();
    }

    /**
     * Devuelve el tamaño de la matriz del grafo.
     * @return Tamaño de la matriz del grafo.
     */
    public int getDimensionMatriz(){
        return dimensionMatriz;
    }

    /**
     * Metodo para agregar aristas en la matriz. Dado que el grafo es no dirigido, la arista
     * se agrega en ambos sentidos.
     * @param nodoX: Nodo origen
     * @param nodoY: Nodo destino
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
     * Borra la arista de la clase grafo. Borra dos aristas ya que el grafo es no dirigido.
     * @param nodoX: Primer nodo
     * @param nodoY Segundo nodo
     */
    public void deleteArista(Integer nodoX, Integer nodoY) {
        if (matriz[nodoX][nodoY]) {
            matriz[nodoX][nodoY] = false;
            matriz[nodoY][nodoX] = false;
            if (this.presenteEnElGrafo(nodoX))
                nodos.remove(nodoX); // Borra nodo solo si no esta involucrado en otra arista del grafo
            if (this.presenteEnElGrafo(nodoY))
                nodos.remove(nodoY);
        }
    }

    /**
     * Permite saber si existe una arista entre dos nodos dados
     * @param nodoX: Primer nodo
     * @param nodoY: Segundo nodo
     * @return true si la arista existe, false caso contrario.
     */
    public boolean hayArista(int nodoX, int nodoY){
        return matriz[nodoX][nodoY];
    }

    /**
     * Permite obtener los vecinos de un nodo determinado
     * @param Nodo: El nodo que se quiere consultar
     * @return Una lista ordenada con los nodos adyacentes al nodo consultado.
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
     * Permite conocer la cantidad de nodos presentes en el grafo. Es decir aquellos que
     * participan en al menos una arista.
     * @return Número de nodos.
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

    /**
     * Metodo que mapea un entero de 0 - (n-1) con un par de entradas fila-columna.
     * Este par representa la posicion del elemento en la matriz de juego (NO CONFUNDIR CON MATRIZ
     * DE ADYACENCIA).
     * @param nodo: Entero asociado a cueva del grafo.
     * @return El par con entradas fila y columna.
     */
    public Pair obtenerFilaColumna(int nodo){
        int n = (int) Math.sqrt(dimensionMatriz);
        int x = nodo / n;
        int y = nodo % n;
        return new Pair(x, y);
    }
}