package com.example.carlos.wumpusproject.beyondAR;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.beyondar.android.fragment.BeyondarFragmentSupport;
import com.beyondar.android.sensor.BeyondarSensorManager;
import com.beyondar.android.util.location.BeyondarLocationManager;
import com.beyondar.android.view.BeyondarGLSurfaceView;
import com.beyondar.android.view.OnClickBeyondarObjectListener;
import com.beyondar.android.world.BeyondarObject;
import com.beyondar.android.world.BeyondarObjectList;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
import com.example.carlos.wumpusproject.R;
import com.example.carlos.wumpusproject.utils.Config;

import com.example.carlos.wumpusproject.utils.Jugar;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import android.os.Handler;

//RADAR
    import android.graphics.Color;
    import android.os.Bundle;
    import android.support.v4.app.FragmentActivity;
    import android.view.Window;
    import android.widget.SeekBar;
    import android.widget.TextView;
    import android.widget.SeekBar.OnSeekBarChangeListener;
    import com.beyondar.android.plugin.radar.RadarView;
    import com.beyondar.android.plugin.radar.RadarWorldPlugin;
//RADAR

public class SimpleCamera extends AppCompatActivity implements OnClickBeyondarObjectListener, OnSeekBarChangeListener{

    private BeyondarFragmentSupport mBeyondarFragment;
    private World mWorld;
    private CustomWorldHelper customWorldHelper;
    private Vector<Double> coordenadasIniciales;
    private Jugar jugar;


    private Boolean location;
    private Boolean camera;

    private TextView textCuevaAct;

    //RADAR
    private RadarView mRadarView;
    private RadarWorldPlugin mRadarPlugin;
    private SeekBar mSeekBarMaxDistance;
    private TextView mTextviewMaxDistance;
    TextView flechasRestantes;
    //RADAR


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


        // We create the world and fill it ...
        mWorld = customWorldHelper.generateObjects(this, coordenadasIniciales);

        /* Parametros para variar la distancia de los objetos TODO cambiar parametros */
        mBeyondarFragment.setMaxDistanceToRender(3000); // Asigno distancia máxima de renderización de objetos
        mBeyondarFragment.setDistanceFactor(4); // El factor de distancia de objetos (más cerca entre mayor valor)
        mBeyondarFragment.setPushAwayDistance(0); // Para alejar un poco los objetos que están muy cerca
        mBeyondarFragment.setPullCloserDistance(0); // Para acercar un poco los objetos que están muy lejos
        mBeyondarFragment.setWorld(mWorld);



        mWorld.onResume();


        // Le pasamos el LocationManager al BeyondarLocationManager.
        BeyondarLocationManager.setLocationManager((LocationManager) getSystemService(Context.LOCATION_SERVICE));
        //Permitimos que BeyondAR actualice automáticamente la posición del mundo con respecto al usuario
        BeyondarLocationManager.addWorldLocationUpdate(mWorld);
        //Asigna la posicion inicial del usuario a uno de los geoObjetos en el mundo.
        //BeyondarLocationManager.addGeoObjectLocationUpdate((GeoObject)mWorld.getBeyondarObjectList(0).get(Config.cuevaInicial));
        //Activa los servicios de ubicacion para el ayudante BeyondarLocationManager.
        BeyondarLocationManager.enable();

        // We also can see the Frames per seconds
        //mBeyondarFragment.showFPS(true);
        mBeyondarFragment.setOnClickBeyondarObjectListener(this);

       // mGeofencingClient = LocationServices.getGeofencingClient(this);
       // mGeofenceList = new ArrayList<>();

        /*
        List< Vector<Double> > coordenadasCuevas = Config.coordenadasCuevas;
        for (int i = 0; i < coordenadasCuevas.size(); ++i) {
            Vector<Double> coord = coordenadasCuevas.get(i);
            if (!(coord.get(0) == 0 && coord.get(1) == 0)) {

                //mWorld = customWorldHelper.generateObjects(this, coord);





            }
        }

        Toast.makeText(this, "Distancia" + Config.listaGeoObj.get(0).getDistanceFromUser(), Toast.LENGTH_LONG).show();

        */
        //cambioDeCueva(Config.cuevaInicial);

                flechasRestantes = (TextView) findViewById(R.id.flechasR);
                flechasRestantes.setText("" + Config.NUM_FLECHAS);

                        //RADAR
                                mTextviewMaxDistance = (TextView) findViewById(R.id.textViewMax);
                mSeekBarMaxDistance = (SeekBar) findViewById(R.id.seekBar4);
                mRadarView = (RadarView) findViewById(R.id.radarView);
                // Create the Radar plugin
                        mRadarPlugin = new RadarWorldPlugin(this);
                // set the radar view in to our radar plugin
                       mRadarPlugin.setRadarView(mRadarView);
                // Set how far (in meters) we want to display in the view
                        mRadarPlugin.setMaxDistance(100);
                // We can customize the color of the items
                        mRadarPlugin.setListColor(CustomWorldHelper.LIST_TYPE_EXAMPLE_1, Color.RED);
                // and also the size
                        mRadarPlugin.setListDotRadius(CustomWorldHelper.LIST_TYPE_EXAMPLE_1, 3);
                // add the plugin
                        mWorld.addPlugin(mRadarPlugin);
                mSeekBarMaxDistance.setOnSeekBarChangeListener(this);
                mSeekBarMaxDistance.setMax(300);
                mSeekBarMaxDistance.setProgress(23);
                mRadarPlugin.setMaxDistance(45);
                //RADAR

                textCuevaAct = (TextView) findViewById(R.id.textNumCueva);
                numCuevaActual();
                //hiloNumCueva();
    }

    public void cambioDeCueva(int nuevaCueva){
        customWorldHelper.updateObjects(nuevaCueva);
        jugar.actualizarCuevaActual(nuevaCueva);
        jugar.mostrarIndicios();
    }

    /**
     * Metodo para manejar si el usuario toca un geo objeto presente en la camara.
     * @param arrayList: Lista de los geo objetos presentes. El primer elemento es el objeto que ha sido clickeado.
     */
    @Override
    public void onClickBeyondarObject(ArrayList<BeyondarObject> arrayList) {
        Toast toast1 =  Toast.makeText(this, "Cueva: " + arrayList.get(0).getName(), Toast.LENGTH_SHORT);
        View customT = toast1.getView();
        customT.setBackgroundColor(ContextCompat.getColor(this, R.color.cafeOscuro));
        TextView t = customT.findViewById(android.R.id.message);
        t.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        toast1.show();
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

        //RADAR
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mRadarPlugin == null)
                        return;
                if (seekBar == mSeekBarMaxDistance) {
                        // float value = ((float) progress/(float) 10000);
                                mTextviewMaxDistance.setText("Max distance Value: " + progress);
                        mRadarPlugin.setMaxDistance(progress);
                    }
            }

             @Override
     public void onStartTrackingTouch(SeekBar seekBar) {

     }

     @Override
     public void onStopTrackingTouch(SeekBar seekBar) {

     }
     //RADAR

    //Para el BeyondarLocationManager
    @Override
    protected void onResume(){
        // Enable GPS
        super.onResume();
        BeyondarLocationManager.enable();
    }

    @Override
    protected void onPause(){
        // Disable GPS
        super.onPause();
        BeyondarLocationManager.disable();
    }
    //Para el BeyondarLocationManager


    /*
     * Utilizado para indicarle al usuario el numero de la cueva en la que se encuentra.
     */
    public void numCuevaActual(){
        BeyondarObjectList listaObjetos = mWorld.getBeyondarObjectList(0);
        double distancia;
        for(int i = 0; i < listaObjetos.size(); i++){
            distancia = listaObjetos.get(i).getDistanceFromUser();
            if(distancia <= 5){
                textCuevaAct.setText(listaObjetos.get(i).getName());
            }else{
                textCuevaAct.setText("-");
            }
        }

    }

    /*
    *  Corre el metodo numCuevaActual cada 10 segundos para actualizar el numero de cueva segun la posicion del usuario.
    */
    public void hiloNumCueva(){
        final Handler ha = new Handler();
        ha.postDelayed(new Runnable() {

            @Override
            public void run() {
                //call function
                numCuevaActual();
                ha.postDelayed(this, 10000);
            }
        }, 10000);
    }

}