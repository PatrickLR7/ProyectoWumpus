package com.example.carlos.wumpusproject.beyondAR;

import android.annotation.SuppressLint;
import android.content.Context;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
import com.example.carlos.wumpusproject.R;

/**
 * Created by carlos on 26/10/17.
 */

// TODO
@SuppressLint("SdCardPath")
public class CustomWorldHelper {
    public static final int LIST_TYPE_EXAMPLE_1 = 1;

    public static World sharedWorld;

   public static World generateObjects(Context context) {
        if (sharedWorld != null) {
            return sharedWorld;
        }
        sharedWorld = new World(context);

        // The user can set the default bitmap. This is useful if you are
        // loading images form Internet and the connection get lost
        sharedWorld.setDefaultImage(R.drawable.wumpuslogo);

        // User position (you can change it using the GPS listeners form Android
        // API)
        sharedWorld.setGeoPosition(41.90533734214473d, 2.565848038959814d);

        // Create an object with an image in the app resources.
       GeoObject go1 = new GeoObject(1L);
        go1.setGeoPosition(41.90523339794433d, 2.565036406654116d);
        go1.setImageResource(R.drawable.cueva1);
        go1.setName("Creature 1");

        // Is it also possible to load the image asynchronously form internet
        GeoObject go2 = new GeoObject(2L);
       go2.setGeoPosition(41.90518966360719d, 2.56582424468222d);
               go2.setImageUri("http://beyondar.github.io/beyondar/images/logo_512.png");
        go2.setName("Online image");

        sharedWorld.addBeyondarObject(go1);
       sharedWorld.addBeyondarObject(go2, LIST_TYPE_EXAMPLE_1);
        return sharedWorld;
    }
}
