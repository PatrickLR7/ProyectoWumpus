package com.example.carlos.wumpusproject.beyondAR;

import android.annotation.SuppressLint;
import android.content.Context;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
import com.example.carlos.wumpusproject.R;
import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Grafo;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@SuppressLint("SdCardPath")
public class CustomWorldHelper {
    public static final int LIST_TYPE_EXAMPLE_1 = 1;

    private World sharedWorld; // Representa el ambiente o entorno del juego.
    private List<GeoObject> geoObjects; // Lista de objetos que actualmente se muestran en la camara
    private Grafo grafo;
    private List< Vector<Double> > coordCuevas; // Coordenadas de cuevas

    public CustomWorldHelper(){
        geoObjects = new ArrayList<>();
    }

    /**
     * Genera el mundo donde se juega en caso de que no haya sido generado anteriormente.
     * @param context: Actividad desde donde se llama.
     * @return el mundo creado
     */
   public World generateObjects(Context context, Vector<Double> coordenadaInicial) {
        if (sharedWorld != null) {
            return sharedWorld;
        }

        sharedWorld = new World(context);

        // The user can set the default bitmap. This is useful if you are
        // loading images form Internet and the connection get lost
        sharedWorld.setDefaultImage(R.drawable.wumpuslogogreen);
        for (int i = 0; i < Config.coordenadasCuevas.size(); i++) {
            GeoObject go1 = new GeoObject();
            go1.setGeoPosition(Config.coordenadasCuevas.get(i).get(0), Config.coordenadasCuevas.get(i).get(1));
            go1.setImageResource(R.drawable.cuevaracamara);
            go1.setName("Cueva " + i);
            sharedWorld.addBeyondarObject(go1);
        }

        // User position (you can change it using the GPS listeners form Android
        // API)
        sharedWorld.setGeoPosition(Config.coordenadasIniciales.get(0), Config.coordenadasIniciales.get(1));
        coordCuevas = Config.coordenadasCuevas;
        grafo = Config.laberinto;

        return sharedWorld;
    }

    /**
     * Refresca la camara cada vez que el usuario cambia de una cueva a otra, para mostrar rotulos
     * sobre las nuevas cuevas adyacentes y borrar los rotulos viejo.
     * @param nodo: nodo actual
     */
    public void updateObjects(int nodo){
        for (int i = 0; i < geoObjects.size(); i++) { // Removes previous objects
            sharedWorld.remove( geoObjects.get(i) );
        }
        geoObjects.clear();

        List<Integer> adyacentes = grafo.obtenerVecinos(nodo);
        for (int i = 0; i < adyacentes.size(); i++) {
            int nodoVecino = adyacentes.get(i);
            GeoObject go1 = new GeoObject(nodoVecino);
            Vector<Double> coordenada = coordCuevas.get(nodoVecino);
            go1.setGeoPosition(coordenada.get(0), coordenada.get(1));
            go1.setImageResource(R.drawable.rotulo_direccion);
            go1.setName("Cave " + i);
            geoObjects.add(go1);
            sharedWorld.addBeyondarObject(go1);
        }


    }
}
