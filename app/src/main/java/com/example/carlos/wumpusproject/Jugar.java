package com.example.carlos.wumpusproject;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.Toast;
import android.os.Vibrator;
import android.os.VibrationEffect;

import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Grafo;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.VIBRATOR_SERVICE;

public class Jugar
{
    private Grafo tablero;
    private List<Integer> tiposCueva;
    private int flechasRestantes = 5;
    private int cuevaActual = -1; //Posición actual del jugador
    private Context context; //Contexto necesario para llamar varias funcionalidades del teléfono
    private Vibrator v; //Vibra el dispositivo
    private MediaPlayer mp; //Hace un efecto de sonido
    private List<Integer> caminoCuevas;

    public Jugar(Context con) //Recibe el tablero con el que se va a jugar
    {
        context = con;
        tablero = Config.laberinto;
        tiposCueva = Config.tiposDeCuevas;
        v = (Vibrator)context.getSystemService(VIBRATOR_SERVICE); //Vibrador que se usa después
        mp = MediaPlayer.create(context, R.raw.batsound); //Inicializa el efecto de sonido
        cuevaActual = posicionarJugador();
        caminoCuevas = new ArrayList<>();
        Config.caminoDeCuevas = caminoCuevas;
    }

    public int posicionarJugador()
    {
        int pos = -1;
        for(int i = 0; i < tiposCueva.size(); i++)
            if(tiposCueva.get(i) == 4)
                pos = i;
        return pos;
    }

    public void mostrarIndicios()
    {
        List<Integer> vecinos = tablero.obtenerVecinos(cuevaActual);
        caminoCuevas.add(cuevaActual);
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
                    Toast.makeText(context, "Hay un desagradable olor a wumpus", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    //Pozo vibra
                    if(v.hasVibrator()) //Verifica que el dispositivo tiene la capacidad de vibrar
                    {
                        if (Build.VERSION.SDK_INT >= 26) //La vibración se maneja distinto según el SDK
                        {
                            v.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
                        }
                        else
                        {
                            v.vibrate(150);
                        }
                    }
                    else //Si no puede vibrar, muestra un mensaje
                    {
                        Toast.makeText(context, "Se siente una brisa fría", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 3:
                    //Murciélagos con efecto de sonido
                    mp.start(); //Suena el efecto de sonido de murciélagos
                    break;
            }
        }
    }

    public void lanzarFlecha(int numCueva)
    {
        flechasRestantes--;
        if(tiposCueva.get(numCueva) == 1)
            victoria();
        else
        {
            if(flechasRestantes == 0)
                sinFlechas();
            else
                Toast.makeText(context, "El wumpus no estaba en esa cueva", Toast.LENGTH_LONG).show();
        }
    }

    public void sinFlechas()
    {
        //Avisarle al usuario que perdió por quedarse sin flechas
    }

    public void victoria()
    {
        //Mostrar pantalla con Wumpus atravesado
    }
}