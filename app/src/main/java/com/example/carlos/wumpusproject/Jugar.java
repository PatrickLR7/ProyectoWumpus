package com.example.carlos.wumpusproject;

import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Grafo;
import java.util.List;

public class Jugar {

    /** Laberinto con el que se juega. */
    private Grafo tablero = Config.laberinto;
    /** Los tipos de las cuevas. */
    private List<Integer> tiposCueva = Config.tiposDeCuevas;
    /** Flechas que le quedan al jugador. */
    private int flechasRestantes = 5;
    /** Posicion actual del jugador. */
    private int cuevaActual = -1;

    /**
     * Muestra indicios de peligro en las cuevas cercanas.
     */
    public void mostrarIndicios() {

        List<Integer> vecinos = tablero.obtenerVecinos(cuevaActual);
        int tipo = -1;
        for (int i = 0; i < vecinos.size(); i++) {
            int vecinoActual = vecinos.get(i);
            tipo = tiposCueva.get(vecinoActual);
            switch (tipo) {
                case 1:
                    //Hay un Wumpus cerca
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