package com.example.carlos.wumpusproject.beyondAR;

import android.annotation.SuppressLint;
import android.content.Context;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
import com.example.carlos.wumpusproject.R;
import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by carlos on 26/10/17.
 */

// TODO
@SuppressLint("SdCardPath")
public class CustomWorldHelper {
    public static final int LIST_TYPE_EXAMPLE_1 = 1;

    private World sharedWorld;
    private List<GeoObject> geoObjects;

    private List<Vector> coordCuevas;

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
        sharedWorld.setDefaultImage(R.drawable.wumpuslogogreen);

        // User position (you can change it using the GPS listeners form Android
        // API)
        //sharedWorld.setGeoPosition(coordenada.getX(), coordenada.getY());
        coordCuevas = Config.coordenadasCuevas;
       sharedWorld.setGeoPosition(9.937977, -84.051858);
       GeoObject go1 = new GeoObject(1);
       go1.setGeoPosition(9.937977, -84.051858);
       go1.setName("Cueva1");
       go1.setImageResource(R.drawable.cueva1);
       GeoObject go2 = new GeoObject(2);
       go2.setGeoPosition(9.937942, -84.051847);
       go2.setName("Cueva2");
       go1.setImageResource(R.drawable.cueva1);
       GeoObject go3 = new GeoObject(3);
       go3.setGeoPosition(9.937898, -84.051889);
       go3.setName("Cueva3");
       go3.setImageResource(R.drawable.cueva1);
       GeoObject go4 = new GeoObject(3);
       go4.setGeoPosition(9.937914, -84.051800);
       go4.setName("Cueva3");
       go4.setImageResource(R.drawable.cueva1);
        // Is it also possible to load the image asynchronously form internet
       /*
        GeoObject go2 = new GeoObject(2L);
       go2.setGeoPosition(41.90518966360719d, 2.56582424468222d);
               go2.setImageUri("http://beyondar.github.io/beyondar/images/logo_512.png");
        go2.setName("Online image");
        */

        // sharedWorld.addBeyondarObject(go2, LIST_TYPE_EXAMPLE_1);

       sharedWorld.addBeyondarObject(go1);
       sharedWorld.addBeyondarObject(go2);
       sharedWorld.addBeyondarObject(go3);
       sharedWorld.addBeyondarObject(go4);

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
