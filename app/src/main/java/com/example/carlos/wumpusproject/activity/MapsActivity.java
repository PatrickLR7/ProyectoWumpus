package com.example.carlos.wumpusproject.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;
import com.example.carlos.wumpusproject.R;
import com.example.carlos.wumpusproject.beyondAR.SimpleCamera;
import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Grafo;
import com.example.carlos.wumpusproject.utils.Jugar;
import com.example.carlos.wumpusproject.utils.Pair;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Clase que controla el emplazamiento utilizando mapa de Google.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /** Instancia de GoogleMap. */
    private GoogleMap mMap;
    /** Controlador de las coordenadas. */
    LocationManager locationManager;
    /** Contador de cuevas marcadas. */
    private int contadorMarcas = 0;
    /** Laberinto en el que se juega. */
    private Grafo laberinto = Config.laberinto;
    /** Tipos de las cuevas. */
    private List<Integer> tiposDeCuevas;
    /** Cantidad de cuevas. */
    private int tamGrafo;
    /** Posicion inicial del jugador. */
    private int posInicialJugador;
    /** Distancia entre marcas. */
    private double distancia = Config.distancia;
    /** Coordenadas de los marcadores que representan las cuevas. */
    private List<Vector<Double>> coordenadasCuevas;
    /** Contador. */
    private boolean primera = true;

    /**
     * Metodo que crea el layout.
     * @param savedInstanceState: Guarda una instancia del estado anterior de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        coordenadasCuevas = new ArrayList<>();
        PackageManager pm = getBaseContext().getPackageManager();
        int hasPerm1 = pm.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, getBaseContext().getPackageName());
        if (hasPerm1 != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Solicita permiso para escribir en el almacenamiento del celular
     * Es necesario para guardar los laberintos recibidos.
     */
    protected void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    /**
     * Muestra un mensaje cuando se acepta el permiso o cuando no es aceptado.
     *  @param requestCode: Código requerido
     * @param permissions: Permisos solicitados
     * @param grantResults: Resultados de solicitud.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0, len = permissions.length; i < len; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MapsActivity.this, "Permiso concedido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MapsActivity.this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * Revisa si el gps del dispositivo está activo.
     */
    private boolean checkLocation() {
        if (!(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)))
            showAlert();
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * Muestra una alerta de que el gps del dispositivo está o no activado.
     */
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    /**
     * Metodo que actuliza las coordenadas actuales del jugador.
     */
    public void toggleNetworkUpdates() {
        if (!checkLocation())
            return;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, locationListenerNetwork);
    }

    /**
     * Metodo que verifica los cambios de coordenadas.
     */
    private final LocationListener locationListenerNetwork = new LocationListener() {
        /** Metodo que se activa cuando se detecta un cambio de ubicación
         * @param location Objeto Location que contiene las coordenadas del usuario*
         */
        public void onLocationChanged(Location location) {
            if (location.getLongitude() != 0 && location.getLatitude() != 0 && primera) {
                primera = false;
                Config.lonUsuario = location.getLongitude();
                Config.latUsuario = location.getLatitude();
                runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Config.latUsuario,  Config.lonUsuario)));
                            CameraUpdate zoom=CameraUpdateFactory.zoomTo(20);
                            mMap.animateCamera(zoom);
                        }
                    });
                    crearMapMarks();
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {}

        @Override
        public void onProviderDisabled(String s) {}
    };

    /**
     *Metodo que se activa cuando el mapa está listo para usarse
     * @param googleMap Instancia de Google Map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        toggleNetworkUpdates();
        tamGrafo = laberinto.getDimensionMatriz();
        coordenadasCuevas = new Vector<>();
    }

    /**
     * Agrega una marca al mapa en las coordenadas especificadas.
     * @param lat Latitud de la marca
     * @param lon Longitud de la marca
     */
    public void agregarMarca(double lat, double lon) {
        LatLng temp = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(temp).title("Marker in " + lat + ", " + lon + " (Cueva " + contadorMarcas + ")"));
        contadorMarcas++;
    }

    /**
     * Metodo que genera el personaje y lo ubica en una cueva aleatoria.
     */
    public void generarPersonaje() {
        boolean personaje = false;
        tiposDeCuevas = new ArrayList<>(tamGrafo);
        for (int i = 0; i < tamGrafo ; i++) {
            tiposDeCuevas.add(-1);
        }

        for (int x = 0; x < tamGrafo ; x++) {
            if (laberinto.presenteEnElGrafo(x)) {
                int tipo = (int) (Math.random() * 5);
                if (tipo == 4 && !personaje) {
                    tiposDeCuevas.set(x,4);
                    personaje = true;
                    posInicialJugador = x;
                } else {
                    tiposDeCuevas.set(x,-1);
                }
            } else {
                tiposDeCuevas.set(x,-1);
            }

            if (x == tamGrafo-1 && !personaje){
               x = -1;
            }
        }
        Config.tiposDeCuevas = tiposDeCuevas;
    }

    /**
     * Metodo que emplaza el laberinto en el mapa.
     */
    public void crearMapMarks() {
        generarPersonaje();
        int nodoInicial = 0;
        for (int x = 0; x < tamGrafo; x++) {
            Vector<Double> coordenada = new Vector<>();
            if (x == posInicialJugador) {
                coordenada.add(Config.latUsuario);
                coordenada.add(Config.lonUsuario);
                coordenadasCuevas.add(x, coordenada);
                Config.coordenadasIniciales = coordenada;
                nodoInicial = x;
                Config.cuevaInicial = nodoInicial;
                System.out.println("x: " + x + "lat: " + Config.latUsuario + "lon: " + Config.lonUsuario);
                agregarMarca(Config.latUsuario,Config.lonUsuario);
            } else {
                coordenada.add(0.0);
                coordenada.add(0.0);
                coordenadasCuevas.add(x, coordenada);
            }
        }

        Pair pairInicial = laberinto.obtenerFilaColumna(nodoInicial);
        int filas, columnas;
        for (int nodo = 0; nodo < tamGrafo; nodo++) {
            if (nodo != nodoInicial) {
                if (laberinto.presenteEnElGrafo(nodo)) {
                    Pair pairNodo = new Pair(0,0);
                    pairNodo = laberinto.obtenerFilaColumna(nodo);
                    Pair pairDistancia = new Pair(0,0);
                    pairDistancia = pairNodo.restarPares(pairInicial);

                    filas = pairDistancia.getX();
                    columnas = pairDistancia.getY();

                    System.out.println("x: " + nodo + " pair: " + pairNodo.getX() + "," + pairNodo.getY() + " pairdis" + pairDistancia.getX() + "," + pairDistancia.getY()  );
                    Vector<Double> coordenada = new Vector<>();
                    coordenada.add( Config.latUsuario - distancia * filas);
                    coordenada.add( Config.lonUsuario + distancia * columnas);
                    coordenadasCuevas.set(nodo, coordenada);

                    agregarMarca(coordenadasCuevas.get(nodo).get(0), coordenadasCuevas.get(nodo).get(1));
                }
            }
        }

        Toast.makeText(MapsActivity.this, "Cuevas emplazadas correctamente!", Toast.LENGTH_SHORT).show();
        Config.coordenadasCuevas = coordenadasCuevas;
    }

    /**
     * Metodo que inicia la camara y lo relacionado AR.
     * @param v: Objeto vista
     */
    public void startAR(View v){
        Jugar jugar = new Jugar(getApplicationContext());
        Config.jugar = jugar;
        Config.jugar.asignarTiposCuevas();
        Intent i = new Intent(getApplicationContext(), SimpleCamera.class);
        startActivity(i);
    }
}