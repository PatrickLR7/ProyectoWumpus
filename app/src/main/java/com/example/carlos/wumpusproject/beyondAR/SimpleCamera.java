package com.example.carlos.wumpusproject.beyondAR;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import com.beyondar.android.fragment.BeyondarFragmentSupport;
import com.beyondar.android.util.location.BeyondarLocationManager;
import com.beyondar.android.view.OnClickBeyondarObjectListener;
import com.beyondar.android.world.BeyondarObject;
import com.beyondar.android.world.BeyondarObjectList;
import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
import com.example.carlos.wumpusproject.R;
import com.example.carlos.wumpusproject.activity.AnimacionFlecha;
import com.example.carlos.wumpusproject.activity.GameOverArrows;
import com.example.carlos.wumpusproject.activity.GameOverVictory;
import com.example.carlos.wumpusproject.activity.GameOverWell;
import com.example.carlos.wumpusproject.activity.GameOverWumpus;
import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Grafo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.graphics.Color;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.beyondar.android.plugin.radar.RadarView;
import com.beyondar.android.plugin.radar.RadarWorldPlugin;

/**
 * Clase que se encarga de la camara y mostrar los objetos del mundo.
 */
public class SimpleCamera extends AppCompatActivity implements OnClickBeyondarObjectListener, OnSeekBarChangeListener, android.location.LocationListener{

    /** Fragmento de BeyondAR, permite la interacción con los objetos del mundo */
    private BeyondarFragmentSupport mBeyondarFragment;
    /** Mundo. */
    private World mWorld;
    /** Entorno del juego. */
    private CustomWorldHelper customWorldHelper;
    /** Muestra la cueva actual del jugador. */
    private TextView textCuevaAct;

    /** Vista del radar y otras utilidades que utiliza */
    private RadarView mRadarView;
    private RadarWorldPlugin mRadarPlugin;
    private SeekBar mSeekBarMaxDistance;
    private TextView mTextviewMaxDistance;

    /** Muestra las flechas restantes. */
    private TextView flechasRestantes;
    /** Boton que permite lanzar flechas. */
    private Button lanzarFlechaBoton;
    /** Indica si se pueden o no lanzar flechas. */
    private boolean lanzandoFlecha;
    /** Usuario. */
    private GeoObject user;
    /** Grafo que representa el laberinto. */
    private Grafo grafo;
    /** Mapea el número de cueva original a nombres de los objetos de BeyondAR. */
    private HashMap mapeoOriginalANombre;
    /** Mapea nombres de los objetos de BeyondAR a el número de cueva original. */
    private HashMap mapeoNombreAOriginal;

    /**
     * Metodo que se ejecuta al iniciar la actividad.
     * @param savedInstanceState: Guarda una instancia del estado anterior de la actividad.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String Permiso[] = {"android.permission.CAMERA", "android.permission.ACCESS_FINE_LOCATION"};
        // Start home activity
        requestPermission(Permiso, 1);
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_simple_camera);
        customWorldHelper = new CustomWorldHelper();

        mBeyondarFragment = (BeyondarFragmentSupport) getSupportFragmentManager().findFragmentById(R.id.beyondarFragment);

        // We create the world and fill it ...
        mWorld = customWorldHelper.generateObjects(this);

        // Parametros para variar la distancia de los objetos
        mBeyondarFragment.setMaxDistanceToRender(3000); // Asigno distancia máxima de renderización de objetos
        mBeyondarFragment.setDistanceFactor(4); // El factor de distancia de objetos (más cerca entre mayor valor)
        mBeyondarFragment.setPushAwayDistance(0); // Para alejar un poco los objetos que están muy cerca
        mBeyondarFragment.setPullCloserDistance(0); // Para acercar un poco los objetos que están muy lejos
        mBeyondarFragment.setWorld(mWorld);

        mWorld.onResume();
        grafo = Config.laberinto;

        // Agregamos la posición del usuario al mundo de beyondAR.
        user = new GeoObject(1000l);
        user.setGeoPosition(mWorld.getLatitude(), mWorld.getLongitude());
        user.setImageResource(R.drawable.bat);
        user.setName("Posicion del usuario");
        mWorld.addBeyondarObject(user);

        //Permitimos que BeyondAR actualice automáticamente la posición del mundo con respecto al usuario
        BeyondarLocationManager.addWorldLocationUpdate(mWorld);

        //Asigna la posicion inicial del usuario al GeoObjeto correspondiente.
        BeyondarLocationManager.addGeoObjectLocationUpdate(user);

        // Le pasamos el LocationManager al BeyondarLocationManager.
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5, this);
        }
        BeyondarLocationManager.setLocationManager(locationManager);

        //Activa los servicios de ubicacion para el ayudante BeyondarLocationManager.
        BeyondarLocationManager.enable();

        mBeyondarFragment.setOnClickBeyondarObjectListener(this);

        lanzandoFlecha = false;
        flechasRestantes = findViewById(R.id.flechasR);
        flechasRestantes.setText("" + Config.numFlechas);
        lanzarFlechaBoton = findViewById(R.id.lanzaFlechaBoton);
        lanzarFlechaBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje;
                if (lanzandoFlecha){
                    lanzandoFlecha = false;
                    mensaje = "Lanzar flecha desactivado";
                    lanzarFlechaBoton.setBackgroundColor(Color.GRAY);
                } else {
                    lanzandoFlecha = true;
                    mensaje = "Lanzar flecha activado";
                    lanzarFlechaBoton.setBackgroundColor(Color.GREEN);
                }
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
            }
        });

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

        mapeoOriginalANombre = Config.mapOriginalANombre;
        mapeoNombreAOriginal = Config.mapNombreAOriginal;
        textCuevaAct = (TextView) findViewById(R.id.textNumCueva);

        Config.wumpus = false;
        Config.sinFlechas = false;
        Config.muerte = false;
        Config.pozo = false;
        Config.cuevaActual = -1;
        Config.numFlechas = 5;
        String hilFlechasRestantes = "" + Config.numFlechas;
        flechasRestantes.setText(hilFlechasRestantes);
        numCuevaActual();
    }

    /**
     * Metodo para manejar si el usuario toca un geo objeto presente en la camara.
     * @param arrayList: Lista de los geo objetos presentes. El primer elemento es el objeto que ha sido clickeado.
     */
    @Override
    public void onClickBeyondarObject(ArrayList<BeyondarObject> arrayList) {
        if (lanzandoFlecha) {
            int numCueva = (int) arrayList.get(0).getId();
            this.lanzarFlecha(numCueva);

            if (Config.wumpus) {
                this.wumpus();
            } else if(Config.sinFlechas) {
                this.sinFlechas();
            }
        } else {
            Toast toast1 = Toast.makeText(this, "Cueva: " + arrayList.get(0).getName(), Toast.LENGTH_SHORT);
            View customT = toast1.getView();
            customT.setBackgroundColor(ContextCompat.getColor(this, R.color.cafeOscuro));
            TextView t = customT.findViewById(android.R.id.message);
            t.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            toast1.show();
        }
    }

    /**
     * Metodo encargado de mostrar los dialogos de solicitud de permisos si es necesario.
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
     * @return verdadero si android del dispositivo es mayor a Lollipop, en caso contrario falso
     */
    private boolean askPermissions() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    /**
     * Verifica que tenga los permisos apropiados para acceder a la ubicación de usuario y cámara.
     * @param requestCode  codigo del permiso
     * @param permissions  los permisos que se solicitan
     * @param grantResults indica si permiso es concedido o no
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                boolean camera = this.checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
                boolean location = this.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                if (location && camera) {}
                break;
        }
    }

    /**
     * Utilizado para cambiar la distancia máxima del radar mediante un seekBar (no se utiliza en la versión final del juego).
     * @param seekBar: Barra para cambiar la distancia máxima.
     * @param progress: Distancia máxima seleccionada en la barra.
     * @param fromUser: Indica si el cambio en la barra (seekbar) fue inicializado por el usuario.
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mRadarPlugin == null)
            return;
        if (seekBar == mSeekBarMaxDistance) {
            String str = "Max distance Value: " + progress;
            mTextviewMaxDistance.setText(str);
            mRadarPlugin.setMaxDistance(progress);
        }
    }

    /**
     * Activado cuando el usuario toca el seekBar para cambiar la distancia máxima
     * @param seekBar: La barra en la cual se cambia la distancia.
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    /**
     * Activado cuando el usuario deja de tocar el seekBar.
     * @param seekBar: La barra en la cual se cambia la distancia.
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    /**
     * Cuando la aplicación continúa ejecutandose, habilida los servicios de BeyondarLocationManager.
     */
    @Override
    protected void onResume(){
        super.onResume();
        BeyondarLocationManager.enable();
    }

    /**
     * Cuando la aplicación se pausa, desactiva el BeyondarLocationManager.
     */
    @Override
    protected void onPause(){
        super.onPause();
        BeyondarLocationManager.disable();
    }

    /**
     * Actualiza el número de la cueva en la que se encuentra el usuario actualmente.
     * @return Retorna el número de cueva actual.
     */
    public int numCuevaActual(){
        BeyondarObjectList listaObjetos = mWorld.getBeyondarObjectList(0);
        double distancia;
        GeoObject gObjeto;
        int res = -1;
        boolean actualizado = false;

        for (int i = 0; i < listaObjetos.size() && !actualizado; i++) {
            gObjeto = (GeoObject) listaObjetos.get(i);
            if (!(gObjeto.getName().equals("Posicion del usuario"))) {
                distancia = gObjeto.calculateDistanceMeters(user);
                if (distancia <= 10) {
                    textCuevaAct.setText(listaObjetos.get(i).getName());
                    res =  Integer.parseInt(gObjeto.getName());
                    actualizado = true;
                } else {
                    textCuevaAct.setText("-");
                    actualizado = false;
                }
            }
        }

        if (actualizado) {
            int cuevaActual = (Integer) mapeoNombreAOriginal.get(res);
            actualizarCuevasAdyacentes(cuevaActual);

            // Si la cueva cambió muestra indicios pero si sigue en la misma cueva ya no muestra más indicios hasta que sea diferente
            if (cuevaActual != Config.cuevaActual) {
                Config.cuevaActual = cuevaActual;
                Config.jugar.mostrarIndicios(cuevaActual);
            }
        }

        if (Config.muerte) {
            this.muerte();
        } else if (Config.pozo) {
            this.pozo();
        }

        return res;
    }

    /**
     * Revisa las cuevas adyacentes a la cueva actual y las pone como visibles.
     * El resto de las cuevas las coloca invisibles.
     * @param nodo Recibe el número de cueva actual.
     */
    public void actualizarCuevasAdyacentes(int nodo){
        BeyondarObjectList listita = mWorld.getBeyondarObjectList(0);
        for (int i = 0; i < listita.size(); i++) {
            listita.get(i).setVisible(false);
        }

        List<Integer> adyacentes = grafo.obtenerVecinos(nodo);
        int n = (Integer) mapeoOriginalANombre.get(nodo);
        listita.get(n).setVisible(false);
        for (int ii = 0; ii < adyacentes.size(); ii++) {
            int nodoVecino = adyacentes.get(ii);
            String m = "" + mapeoOriginalANombre.get(nodoVecino);
            for (int j = 0; j < listita.size(); j++) {
                if (listita.get(j).getName().equals(m)) {
                    listita.get(j).setVisible(true);
                    listita.get(j).setImageResource(R.drawable.cuevaracamara);
                }
            }
        }
    }

    /**
     * Metodo que revisa cambios en la ubicacion del usuario. Ejecuta al método que actualiza el número de cueva actual.
     * @param location La nueva ubicacion
     */
    @Override
    public void onLocationChanged(Location location) {
        if (!Config.muerte && !Config.pozo && !Config.wumpus && !Config.sinFlechas) {
            numCuevaActual();
        }
    }

    /**
     *
     * @param provider:
     */
    @Override
    public void onProviderDisabled(String provider) {}

    /**
     *
     * @param provider:
     */
    @Override
    public void onProviderEnabled(String provider) {}

    /**
     *
     * @param provider:
     * @param status:
     * @param extras:
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    /**
     * Metodo encargado de lanzar una flecha a la cueva seleccionada.
     * @param cueva El numero de la cueva a la que se va a enviar la flecha
     */
    public void lanzarFlecha(int cueva) {
        Intent intent = new Intent(getApplicationContext(), AnimacionFlecha.class);
        startActivity(intent);
        Config.jugar.lanzarFlecha(cueva);
        String numActualFlechas = "" + Config.numFlechas;
        flechasRestantes.setText(numActualFlechas);
    }

    /**
     * Se inicia la actividad que muestra victoria por caza al wumpus.
     */
    public void wumpus() {
        Intent intent = new Intent(getApplicationContext(), GameOverVictory.class);
        startActivity(intent);
    }

    /**
     * Se inicia la actividad que muestra derrota por quedarse sin flechas.
     */
    public void sinFlechas() {
        Intent intent = new Intent(getApplicationContext(), GameOverArrows.class);
        startActivity(intent);
    }

    /**
     * Se inicia la actividad que muestra derrota por caer en las garras del wumpus.
     */
    public void muerte() {
        Intent intent = new Intent(getApplicationContext(), GameOverWumpus.class);
        startActivity(intent);
    }

    /**
     * Se inicia la actividad que muestra derrota por caer en un pozo.
     */
    public void pozo() {
        Intent intent = new Intent(getApplicationContext(), GameOverWell.class);
        startActivity(intent);
    }
}