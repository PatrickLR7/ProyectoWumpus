package com.example.carlos.wumpusproject.utils;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.carlos.wumpusproject.R;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * Created by Carlos on 11/12/2017.
 */

public class GeofenceTransitionIntentService extends IntentService {

    private static final String TAG = "GeofenceTransitionsIS";

    public GeofenceTransitionIntentService(){
        super(TAG);
    }

    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e(TAG, "Error manejando Intent de Geofence!");
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            Geofence geofence = triggeringGeofences.get(0);
            int nuevaCueva = Integer.parseInt(geofence.getRequestId());
            Config.camera.cambioDeCueva(nuevaCueva);
            Toast.makeText(getApplicationContext(), "Ha entrado a la cueva: " + nuevaCueva, Toast.LENGTH_SHORT).show();
        } else {
            // Log the error.
            Log.e(TAG, "Transicion fue salida de un geofence!\nPor tanto no interesa.");
        }
    }

}