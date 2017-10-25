package com.example.carlos.wumpusproject;

import com.example.carlos.wumpusproject.utils.Grafo;

import java.util.List;

public class Jugar
{
    private Grafo tablero;
    private int flechasRestantes = 5;
    private int cuevaActual = -1;

    public Jugar(Grafo lab)
    {
        tablero = lab;
    }

    public void mostrarIndicios()
    {
        List<Integer> vecinos = tablero.obtenerVecinos(cuevaActual);
        int vecinoActual = -1;
        for(int i = 0; i < vecinos.size(); i++)
        {
            vecinoActual = vecinos.get(i);

        }
    }
}