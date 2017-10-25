package com.example.carlos.wumpusproject;

import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Grafo;

import java.util.List;

public class Jugar
{
    private Grafo tablero;
    private List<Integer> tiposCueva;
    private int flechasRestantes = 5;
    private int cuevaActual = -1; //Posición actual del jugador

    public Jugar() //Recibe el tablero con el que se va a jugar
    {
        tablero = Config.laberinto;
        tiposCueva = Config.tiposDeCuevas;
    }

    public void mostrarIndicios()
    {
        List<Integer> vecinos = tablero.obtenerVecinos(cuevaActual);

        int vecinoActual = -1;
        int tipo = -1;
        for(int i = 0; i < vecinos.size(); i++)
        {
            vecinoActual = vecinos.get(i);
            tipo = tiposCueva.get(vecinoActual);
            switch (tipo)
            {
                case 1:
                    //Wumpus
                    break;
                case 2:
                    //Pozo
                    break;
                case 3:
                    //Murc
                    break;
            }
        }
    }
}