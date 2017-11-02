package com.example.carlos.wumpusproject.beyondAR;

import android.annotation.SuppressLint;
import android.content.Context;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
import com.example.carlos.wumpusproject.R;
import com.example.carlos.wumpusproject.utils.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos on 26/10/17.
 */

// TODO
@SuppressLint("SdCardPath")
public class CustomWorldHelper {
    public static final int LIST_TYPE_EXAMPLE_1 = 1;

    private World sharedWorld;
    private List<GeoObject> geoObjects;

    public CustomWorldHelper(){
        geoObjects = new ArrayList<>();
    }

    /**
     * Genera el mundo donde se juega en caso de que no haya sido generado anteriormente.
     * @param context: Actividad desde donde se llama.
     * @param coordenada: Coordenadas iniciales de ubicacion.
     * @return
     */
   public World generateObjects(Context context, Pair coordenada) {
        if (sharedWorld != null) {
            return sharedWorld;
        }

        sharedWorld = new World(context);

        // The user can set the default bitmap. This is useful if you are
        // loading images form Internet and the connection get lost
        sharedWorld.setDefaultImage(R.drawable.wumpuslogo);

        // User position (you can change it using the GPS listeners form Android
        // API)
        sharedWorld.setGeoPosition(coordenada.getX(), coordenada.getY());

        // Is it also possible to load the image asynchronously form internet
       /*
        GeoObject go2 = new GeoObject(2L);
       go2.setGeoPosition(41.90518966360719d, 2.56582424468222d);
               go2.setImageUri("http://beyondar.github.io/beyondar/images/logo_512.png");
        go2.setName("Online image");
        */

        // sharedWorld.addBeyondarObject(go2, LIST_TYPE_EXAMPLE_1);
        return sharedWorld;
    }

    /**
     *
     * @param coordenadasAdyacentes
     */
    public void updateObjects(List<Pair> coordenadasAdyacentes){
        for (int i = 0; i < geoObjects.size(); i++) {
            sharedWorld.remove( geoObjects.get(i) ); // Removes previous objects
        }
        geoObjects.clear();

        for (int i = 0; i < coordenadasAdyacentes.size(); i++) {
            GeoObject go1 = new GeoObject(1L);
            Pair coordenada = coordenadasAdyacentes.get(i);
            go1.setGeoPosition(coordenada.getX(), coordenada.getY());
            go1.setImageResource(R.drawable.rotulo_direccion);
            go1.setName("Cave " + i);
            geoObjects.add(go1);
            sharedWorld.addBeyondarObject(go1);
        }

    }
}
