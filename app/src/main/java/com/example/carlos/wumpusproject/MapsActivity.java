package com.example.carlos.wumpusproject;

import android.*;
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
import android.widget.Toast;

import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.Grafo;
import com.example.carlos.wumpusproject.utils.Pair;
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
 * Clase de la activity maps.
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    /** Variables pra latitud y longitud. */
    double longitudeNetwork = 0, latitudeNetwork = 0;
    private int contadorMarcas = 0;



    private Grafo laberinto;
    private List<Integer> tiposDeCuevas;

    private int tamGrafo;
    private int posInicialJugador;
    private int posInicialWumpus;
    private double distancia;

    private Vector< Vector<Double> > coordenadasCuevas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        PackageManager pm = getBaseContext().getPackageManager();
        int hasPerm1 = pm.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, getBaseContext().getPackageName());
        if (hasPerm1 != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        toggleNetworkUpdates();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        laberinto = Config.laberinto;
       // map = Config.map;
       // tamGrafo = laberinto.getDimensionMatriz();
        coordenadasCuevas = new Vector<>();
        distancia = Config.distancia;

       // ();

    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == 1){
            for(int i = 0, len = permissions.length; i < len; i++){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    //Permiso concedido
                    Toast.makeText(MapsActivity.this, "Permiso concedido", Toast.LENGTH_SHORT).show();
                }
                else{
                    //permiso denegado
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
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20 * 1000, 10, locationListenerNetwork);
    }

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (contadorMarcas == 0) {
                longitudeNetwork = location.getLongitude();
                latitudeNetwork = location.getLatitude();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        agregarMarca(latitudeNetwork, longitudeNetwork);
                        Toast.makeText(MapsActivity.this, "Marcador creado", Toast.LENGTH_SHORT).show();
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitudeNetwork, longitudeNetwork)));
                    }
                });

                Config.latUsuario = latitudeNetwork;
                Config.lonUsuario = longitudeNetwork;
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
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void agregarMarca(double lat, double lon) {
        LatLng temp = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(temp).title("Marker in " + lat + ", " + lon + " (Cueva " + contadorMarcas + ")"));

        if (contadorMarcas == 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp, 20));
        }

        Toast.makeText(MapsActivity.this, "Marcador agregado", Toast.LENGTH_SHORT).show();
        contadorMarcas++;
    }











    /**
     * Define los tipos de las cuevas; es decir, si hay pozos o murcielagos.
     * Se usaran los siguientes numeros:
     * 0 -> cueva Libre.
     * 1 -> cueva con Wumpus.
     * 2 -> cueva con pozo.
     * 3 -> cueva con murcielagos.
     * 4 -> cueva inicial del personaje.
     */
    public void generarTiposDeCuevas() {
        tiposDeCuevas = new ArrayList<>(tamGrafo);
        for (int x = 0; x < tamGrafo ; x++) {
            int tipo = (int) (Math.random() * 3);
            switch (tipo){
                case 0: tiposDeCuevas.add(0);
                    break;
                case 1: tiposDeCuevas.add(2);
                    break;
                default:tiposDeCuevas.add(3);
                    break;
            }
        }
        posInicialJugador = (int) (Math.random()*tamGrafo);
        posInicialWumpus = (int) (Math.random()*tamGrafo);
        tiposDeCuevas.add(posInicialJugador, 4);
        tiposDeCuevas.add(posInicialWumpus, 1);
        Config.tiposDeCuevas = tiposDeCuevas;
    }

    public void crearMapMarks() {
        //9.93
        //-84.05
        //radio: 5 mts
        this.generarTiposDeCuevas();




        double latInicial = Config.latUsuario;                                         //llenar con usuario
        double lonInicial = Config.lonUsuario;
        int nodoInicial = 0;


        for (int x = 0; x < tamGrafo; x++) {
            Vector<Double> coordenada = new Vector<Double>();
            if (x == posInicialJugador) {
                coordenada.add(latInicial);//usuario
                coordenada.add(lonInicial);//usuario
                coordenadasCuevas.add(x, coordenada);
                nodoInicial = x;
                System.out.println("x: " + x + "lat: " + latInicial + "lon: " + lonInicial);

                agregarMarca(latInicial,lonInicial);                                                                 //generar marcador

            } else {
                coordenada.add(0.0);
                coordenada.add(0.0);
                coordenadasCuevas.add(x, coordenada);
            }
        }



        boolean encontrado = false;
        Pair pairInicial;
        pairInicial = laberinto.obtenerFilaColumna(nodoInicial);

        int filas, columnas = 0;


        for (int nodo = 0; nodo < tamGrafo; nodo++) {

            if (nodo != nodoInicial) {

                if (laberinto.presenteEnElGrafo(nodo)) {

                    Pair pairNodo;
                    Pair pairDistancia;
                    pairNodo = laberinto.obtenerFilaColumna(nodo);

                    pairDistancia = pairInicial.restarPares(pairNodo);

                    filas = pairDistancia.getX();
                    columnas = pairDistancia.getY();

                    Vector<Double> coordenada = new Vector<Double>();
                    coordenada.add(latInicial + distancia * filas);
                    coordenada.add(lonInicial + distancia * columnas);
                    coordenadasCuevas.setElementAt(coordenada, nodo);

                }

            }

        }


        for (int i = 0; i < tamGrafo ; i++) { // Recorre coordenadasCuevas y hace Marks

            if( (coordenadasCuevas.get(i).get(0) != 0.0 ) && (coordenadasCuevas.get(i).get(1) != 0.0 ) ) { //los nodos no presentes en el grafo tienen coor 0.0

                agregarMarca(coordenadasCuevas.get(i).get(0), coordenadasCuevas.get(i).get(1));
            }
        }

    }














}