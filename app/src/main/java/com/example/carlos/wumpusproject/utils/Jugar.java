package com.example.carlos.wumpusproject.utils;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.os.Vibrator;
import android.os.VibrationEffect;

import com.example.carlos.wumpusproject.R;
import com.example.carlos.wumpusproject.activity.GameOverArrows;
import com.example.carlos.wumpusproject.activity.GameOverVictory;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una instancia de la partida que esta actualmente llevandose a cabo
 */
public class Jugar extends AppCompatActivity {
    private Grafo tablero; // Laberinto de juego
    private List<Integer> tiposCueva; // Tipos de las cuevas
    private int flechasRestantes; //Numero de flechas restantes
    private int cuevaActual; //Posición actual del jugador
    private Context context; //Contexto necesario para llamar varias funcionalidades del teléfono
    private Vibrator v; //Vibra el dispositivo
    private MediaPlayer mp; //Hace un efecto de sonido
    private List<Integer> caminoCuevas;

    /**
     * Constructor de la clase, recibe el contexto de la actividad que lo crea.
     * @param con: Contexto invocador.
     */
    public Jugar(Context con) {
        context = con;
        tablero = Config.laberinto;
        flechasRestantes = Config.NUM_FLECHAS;
        tiposCueva = Config.tiposDeCuevas;
        v = (Vibrator)context.getSystemService(VIBRATOR_SERVICE); // Vibrador
        mp = MediaPlayer.create(context, R.raw.batsound); // Efecto de sonido
        cuevaActual = posicionarJugador();
        caminoCuevas = new ArrayList<>();
        Config.caminoDeCuevas = caminoCuevas;
    }

    /**
     * Ubica al jugador en el laberinto.
     * @return La ubicacion.
     */
    public int posicionarJugador() {
        int pos = -1;
        for (int i = 0; i < tiposCueva.size(); i++)
            if (tiposCueva.get(i) == 4)
                pos = i;
        return pos;
    }

    /**
     * Metodo usado para mostrar indicios basandose en la posicion actual del jugador en el laberinto
     */
    public void mostrarIndicios() {
        List<Integer> vecinos = tablero.obtenerVecinos(cuevaActual);
        int vecinoActual;
        int tipo;
        for (int i = 0; i < vecinos.size(); i++) {
            vecinoActual = vecinos.get(i);
            tipo = tiposCueva.get(vecinoActual);
            switch (tipo) {
                case 1:
                    //Mensaje diciendo que hay wumpus
                    Toast.makeText(context, "Hay un desagradable olor a wumpus", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    //Pozo vibra
                    if (v.hasVibrator()) //Verifica que el dispositivo tiene la capacidad de vibrar
                    {
                        if (Build.VERSION.SDK_INT >= 26) //La vibración se maneja distinto según el SDK
                        {
                            v.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            v.vibrate(500);
                        }
                    } else //Si no puede vibrar, muestra un mensaje
                    {
                        Toast.makeText(context, "Se siente una brisa fría", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 3:
                    //Murciélagos con efecto de sonido
                    mp.start();
                    break;
            }
        }
    }

    /**
     * Metodo que permite lanzar una flecha y verificar si en ella se encuentra el Wumpus.
     * @param numCueva: La cueva hacia la que se dirige la flecha.
     */
    public void lanzarFlecha(int numCueva) {
        if (tiposCueva.get(numCueva) == 1) // Wumpus esta en la cueva
            victoria();
        else {
            flechasRestantes--;
            if (flechasRestantes == 0) {
                sinFlechas();
            } else {
                Toast.makeText(context, "El wumpus no estaba en esa cueva", Toast.LENGTH_LONG).show();
            }
        }
        Config.NUM_FLECHAS = flechasRestantes;
    }

    /**
     * Metodo para manejar el caso en que el usuario se quede sin flechas.
     */
    public void sinFlechas() {
        Intent intent = new Intent(this, GameOverArrows.class);
        startActivity(intent);
    }

    /**
     * Metodo para manejar el caso en que el usuario haya exitosamente matado al Wumpus.
     */
    public void victoria() {
        Intent intent = new Intent(getApplicationContext(), GameOverVictory.class);
        startActivity(intent);
    }

    /**
     * Actualiza el numero de cueva
     * @param nuevaCueva: La cueva a la que el jugador ha llegado
     */
    public void actualizarCuevaActual(int nuevaCueva){
        this.cuevaActual = nuevaCueva;
        caminoCuevas.add(cuevaActual);
    }
}