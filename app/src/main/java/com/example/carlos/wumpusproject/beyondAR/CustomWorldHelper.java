package com.example.carlos.wumpusproject.beyondAR;

import android.annotation.SuppressLint;
import android.content.Context;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
import com.example.carlos.wumpusproject.R;
import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Grafo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Clase que crea el entorno del juego.
 */
@SuppressLint("SdCardPath")
public class CustomWorldHelper {
    public static final int LIST_TYPE_EXAMPLE_1 = 1;
    /** Representa el ambiente o entorno del juego. */
    private World sharedWorld;
    /** Lista de objetos que actualmente se muestran en la camara. */
    private ArrayList<GeoObject> geoObjects;
    /** Grafo que guarda la estructura del laberinto. */
    private Grafo grafo;
    /** Lista que guarda las coordenadas de las cuevas. */
    private List<Vector<Double>> coordCuevas;
    /** Mapea el número de cueva original a nombres de los objetos de BeyondAR. */
    private HashMap mapeoOriginalANombre;
    /** Mapea nombres de los objetos de BeyondAR a el número de cueva original. */
    private HashMap mapeoNombreAOriginal;

    /**
     * Constructor.
     */
    public CustomWorldHelper(){
        geoObjects = new ArrayList<>();
        mapeoOriginalANombre = new HashMap<>();
        mapeoNombreAOriginal = new HashMap<>();
    }

    /**
     * Genera el mundo donde se juega en caso de que no haya sido generado anteriormente.
     * @param context: Actividad desde donde se llama.
     * @return el mundo creado
     */
    public World generateObjects(Context context) {
        if (sharedWorld != null) {
            return sharedWorld;
        }

        sharedWorld = new World(context);
        sharedWorld.setDefaultImage(R.drawable.wumpuslogogreen);
        int n = 1;
        int cInicial;
        for (int i = 0; i < Config.coordenadasCuevas.size(); i++) {
            if (!(Config.coordenadasCuevas.get(i).get(0) == 0 && Config.coordenadasCuevas.get(i).get(1)  == 0)) {
                GeoObject go1 = new GeoObject(i);
                go1.setGeoPosition(Config.coordenadasCuevas.get(i).get(0), Config.coordenadasCuevas.get(i).get(1));
                go1.setImageResource(R.drawable.cuevaracamara);
                go1.setName("" + n);
                sharedWorld.addBeyondarObject(go1);
                geoObjects.add(go1);
                if (Config.coordenadasCuevas.get(i).get(0) == Config.coordenadasIniciales.get(0) && Config.coordenadasCuevas.get(i).get(1) == Config.coordenadasIniciales.get(1)) {
                    cInicial = i;
                    Config.cuevaInicial = cInicial;
                }

                mapeoOriginalANombre.put(i, n);
                mapeoNombreAOriginal.put(n, i);
                n++;
            }
        }

        Config.mapOriginalANombre = mapeoOriginalANombre;
        Config.mapNombreAOriginal = mapeoNombreAOriginal;
        Config.listaGeoObj = geoObjects;

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
