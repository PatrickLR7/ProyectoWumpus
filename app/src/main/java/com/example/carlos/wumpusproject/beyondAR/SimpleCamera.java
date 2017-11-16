package com.example.carlos.wumpusproject.beyondAR;

import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.beyondar.android.fragment.BeyondarFragmentSupport;
import com.beyondar.android.util.location.BeyondarLocationManager;
import com.beyondar.android.view.OnClickBeyondarObjectListener;
import com.beyondar.android.world.BeyondarObject;
import com.beyondar.android.world.World;
import com.example.carlos.wumpusproject.R;
import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Jugar;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SimpleCamera extends AppCompatActivity implements OnClickBeyondarObjectListener, OnCompleteListener<Void>{

    private BeyondarFragmentSupport mBeyondarFragment;
    private World mWorld;
    private CustomWorldHelper customWorldHelper;
    private Vector<Double> coordenadasIniciales;
    private Jugar jugar;
    private GeofencingClient mGeofencingClient;
    private List<Geofence> mGeofenceList;
    private PendingIntent mGeofencePendingIntent;
    private TextView flechasRestantes;


    /**
     * Tracks whether the user requested to add or remove geofences, or to do neither.
     */
    private enum PendingGeofenceTask {
        ADD, REMOVE, NONE
    }
    private PendingGeofenceTask mPendingGeofenceTask = PendingGeofenceTask.NONE;

    private Boolean location;
    private Boolean camera;

    /** Llamado cuando se crea la actividad. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config.camera = this;

        String Permiso[] = {"android.permission.CAMERA", "android.permission.ACCESS_FINE_LOCATION"};
        // Start home activity
        requestPermission(Permiso, 1);
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_simple_camera);
        customWorldHelper = new CustomWorldHelper();
        jugar = new Jugar(this);
        coordenadasIniciales = Config.coordenadasIniciales;

        mBeyondarFragment = (BeyondarFragmentSupport) getSupportFragmentManager().findFragmentById(
                R.id.beyondarFragment);

        BeyondarLocationManager.setLocationManager((LocationManager) this.getSystemService(Context.LOCATION_SERVICE));

        // We create the world and fill it ...
        mWorld = customWorldHelper.generateObjects(this, coordenadasIniciales);

        /* Parametros para variar la distancia de los objetos TODO cambiar parametros */
        mBeyondarFragment.setMaxDistanceToRender(3000); // Asigno distancia máxima de renderización de objetos
        mBeyondarFragment.setDistanceFactor(4); // El factor de distancia de objetos (más cerca entre mayor valor)
        mBeyondarFragment.setPushAwayDistance(0); // Para alejar un poco los objetos que están muy cerca
        mBeyondarFragment.setPullCloserDistance(0); // Para acercar un poco los objetos que están muy lejos
        mBeyondarFragment.setWorld(mWorld);

        BeyondarLocationManager.enable();

        //Permitimos que BeyondAR actualice automáticamente la posición del mundo con respecto al usuario
        BeyondarLocationManager.addWorldLocationUpdate(mWorld);

        // Le pasamos el LocationManager al BeyondarLocationManager.
        BeyondarLocationManager
                .setLocationManager((LocationManager) getSystemService(Context.LOCATION_SERVICE));

        // We also can see the Frames per seconds
        mBeyondarFragment.showFPS(true);

        mGeofencingClient = LocationServices.getGeofencingClient(this);
        mGeofenceList = new ArrayList<>();

        List< Vector<Double> > coordenadasCuevas = Config.coordenadasCuevas;
        for (int i = 0; i < coordenadasCuevas.size(); ++i) {
            Vector<Double> coord = coordenadasCuevas.get(i);
            if (!(coord.get(0) == 0 && coord.get(1) == 0)) {
                mGeofenceList.add(new Geofence.Builder()
                        // Set the request ID of the geofence. This is a string to identify this geofence.
                        .setRequestId(i + "")

                        .setCircularRegion( coord.get(0),
                                coord.get(1),
                                Config.radioCuevas
                        )
                        .setExpirationDuration(Config.tiempoExpiracion)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                                Geofence.GEOFENCE_TRANSITION_EXIT)
                        .build());
            }
        }

        TextView flechasRestantes = (TextView) findViewById(R.id.flechasR);
        flechasRestantes.setText("" + Config.NUM_FLECHAS);

        // jugar = new Jugar(this);
        // jugar.mostrarIndicios();
    }

    public void cambioDeCueva(int nuevaCueva){
        customWorldHelper.updateObjects(nuevaCueva);
        jugar.actualizarCuevaActual(nuevaCueva);
        jugar.mostrarIndicios();
    }

    /**
     * Metodo para manejar si el usuario toca un geo objeto presente en la camara.
     * @param arrayL: Lista de los geo objetos presentes. El primer elemento es el objeto que ha sido clickeado.
     */
    @Override
    public void onClickBeyondarObject(ArrayList<BeyondarObject> arrayL) {
        Toast.makeText(this, "Ha clickeado: " + arrayL.get(0).getName(), Toast.LENGTH_LONG).show();

        /*AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Desea lanzar una flecha a esta cueva?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "Flecha Lanzada", Toast.LENGTH_LONG).show();
                //jugar.lanzarFlecha(arrayL.get(0));
            }
        });
        // Create the AlertDialog object and return it
        builder.create();*/


    }

    /**
     * Metodo encargado de mostrar los dialogos de solicitud de permisos si es necesario.
     *
     * @param permiso               hilera de permisos por pedir
     * @param permissionRequestCode resultado de obtencion de permisos
     */
    public void requestPermission(String permiso[], int permissionRequestCode) {
        //Preguntar por permiso
        if (askPermissions()) {
            ActivityCompat.requestPermissions(this, permiso, permissionRequestCode);
        }
    }

    /**
     * Metodo encargado de cerciorarse si es o no necesaria la solicitud dinamica de permisos.
     *
     * @return verdadero si android del dispositivo es mayor a Lollipop, en caso contrario falso
     */
    private boolean askPermissions() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    /**
     * Verifica que tenga los permisos apropiados para acceder a la ubicación de usuario y cámara.
     *
     * @param requestCode  codigo del permiso
     * @param permissions  los permisos que se solicitan
     * @param grantResults indica si permiso es concedido o no
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                camera = this.checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
                location = this.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                if (location && camera) {
                    //se crea bien
                }
                break;
        }
    }

    /**
     * Stores whether geofences were added ore removed in {@link SharedPreferences};
     *
     * @param added Whether geofences were added or removed.
     */
    private void updateGeofencesAdded(boolean added) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean("com.example.carlos.wumpusproject" + ".GEOFENCES_ADDED_KEY", added)
                .apply();
    }

    /**
     * Returns true if geofences were added, otherwise false.
     */
    private boolean getGeofencesAdded() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("com.example.carlos.wumpusproject" + ".GEOFENCES_ADDED_KEY", false);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        mPendingGeofenceTask = PendingGeofenceTask.NONE;
        if (task.isSuccessful()) {
            updateGeofencesAdded(!getGeofencesAdded());

            Toast.makeText(this, "Geofence Agregados", Toast.LENGTH_SHORT).show();
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            Toast.makeText(this, "Geofence Error", Toast.LENGTH_SHORT).show();
        }
    }
}