package com.example.carlos.wumpusproject.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.os.Vibrator;
import android.os.VibrationEffect;
import com.example.carlos.wumpusproject.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una instancia de la partida que esta actualmente llevandose a cabo
 */
public class Jugar extends AppCompatActivity {

    /** Laberinto del juego. */
    private Grafo tablero;

    /** Tipos de las cuevas. */
    private List<Integer> tiposCueva;

    /** Numero de flechas restantes. */
    private int flechasRestantes;

    /** Posicion actual del jugador. */
    private int cuevaActual;

    /** Contexto necesario para llamar varias funcionalidades del telefono. */
    private Context context;

    /** Para que el dispositivo vibre. */
    private Vibrator v;

    /** Efecto de sonido. */
    private MediaPlayer mp;

    /** Lista con las cuevas recorridas. */
    private List<Integer> caminoCuevas;

    /**
     * Constructor de la clase, recibe el contexto de la actividad que lo crea.
     * @param con: Contexto invocador.
     */
    public Jugar(Context con) {
        context = con;
        tablero = Config.laberinto;
        flechasRestantes = Config.numFlechas;
        tiposCueva = Config.tiposDeCuevas;
        v = (Vibrator)context.getSystemService(VIBRATOR_SERVICE); // Vibrador
        mp = MediaPlayer.create(context, R.raw.batsound); // Efecto de sonido
        cuevaActual = posicionarJugador();
        caminoCuevas = new ArrayList<>();
        Config.caminoDeCuevas = caminoCuevas;
    }

    /**
     * Asigna los tipos a cada cueva.
     */
    public void asignarTiposCuevas() {
        Boolean wumpus = false;
        for (int i = 0; i < tiposCueva.size() ; i++) {
            if (tablero.presenteEnElGrafo(i)) {
                int tipo = (int) (Math.random() * 5);
                if (tipo == 1 && wumpus==false && tiposCueva.get(i) != 4 ) { //Asigna Wumpus a un nodo existente que no tenga 4(Personaje)
                    tiposCueva.set(i,1);
                    wumpus = true;
                }
            }

            if (i == tiposCueva.size() - 1 && wumpus == false) { //Si recorrio el vector sin agregar un wumpus entonces se realiza otra vez el ciclo
                i = -1;
            }
        }

        for (int x = 0; x < tiposCueva.size() ; x++) {
            if (tablero.presenteEnElGrafo(x)) {
                int tipo = (int) (Math.random() * 5);
                if(tiposCueva.get(x) != 4 && tiposCueva.get(x) != 1){ //Si no es la cueva del personaje o wumpus
                    if (tipo != 4 && tipo != 1) {
                        tiposCueva.set(x, tipo); //Si no es personaje 4 ni wumpus 1 entonces pone otro tipo al azar
                    } else { //Si estaba en grafo, no era la cueva de personaje ni wumpus pero salió 4 o 1 entonces no aumenta y vuelve a intentar otro número
                        x--;
                    }
                }
            }
        }
        Config.tiposDeCuevas = tiposCueva;
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
     * @param cActual cueva Actual del jugador.
     */
    public void mostrarIndicios(int cActual) {
        cuevaActual = cActual;
        List<Integer> vecinos = tablero.obtenerVecinos(cuevaActual);
        int vecinoActual;
        int tipo = tiposCueva.get(cuevaActual);

        if (tipo == 1) {
            this.muerte();
        } else if (tipo == 2) {
            this.pozo();
        } else {
            Config.muerte = false;
            Config.pozo = false;

            for (int i = 0; i < vecinos.size(); i++) {
                vecinoActual = vecinos.get(i);
                tipo = tiposCueva.get(vecinoActual);
                switch (tipo) {
                    case 1: //Mensaje diciendo que hay wumpus
                        Toast.makeText(context, "Hay un desagradable olor a wumpus", Toast.LENGTH_LONG).show();
                        break;

                    case 2: //Vibra avisando que cerca hay un pozo.
                        if (v.hasVibrator()) { //Verifica que el dispositivo tiene la capacidad de vibrar
                            if (Build.VERSION.SDK_INT >= 26) { //La vibración se maneja distinto según el SDK
                                v.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                v.vibrate(500);
                            }
                        } else { //Si no puede vibrar, muestra un mensaje
                            Toast.makeText(context, "Se siente una brisa fría", Toast.LENGTH_LONG).show();
                        }
                        break;

                    case 3: //Murciélagos con efecto de sonido
                        mp.start();
                        break;
                }
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
        Config.numFlechas = flechasRestantes;
    }

    /**
     * Metodo para manejar el caso en que el usuario se quede sin flechas.
     */
    public void sinFlechas() {
        Config.sinFlechas = true;
    }

    /**
     * Metodo para manejar el caso en que el usuario haya exitosamente matado al Wumpus.
     */
    public void victoria() {
        Config.wumpus = true;
    }

    /**
     * Metodo para manejar el caso en que el usuario se lo coma el Wumpus.
     */
    public void muerte() {
        Config.muerte = true;
    }

    /**
     * Metodo para manejar el caso en que el usuario cayó en el pozo.
     */
    public void pozo() {
        Config.pozo = true;
    }
}