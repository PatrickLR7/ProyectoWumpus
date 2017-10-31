package com.example.carlos.wumpusproject;

import android.content.Context;
import android.widget.Toast;
import android.os.Vibrator;

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

        int vecinoActual;
        int tipo;
        for(int i = 0; i < vecinos.size(); i++)
        {
            vecinoActual = vecinos.get(i);
            tipo = tiposCueva.get(vecinoActual);
            switch (tipo)
            {
                case 1:
                    //Mensaje diciendo que hay wumpus
                    break;
                case 2:
                    //Pozo vibra
                    break;
                case 3:
                    //Murcielagos con efecto de sonido
                    break;
            }
        }
    }
}