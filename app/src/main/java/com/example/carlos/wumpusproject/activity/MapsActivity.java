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
 * Clase de la activity maps.
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    /** Variables pra latitud y longitud. */

    private int contadorMarcas = 0;

    private Grafo laberinto = Config.laberinto;
    private List<Integer> tiposDeCuevas;

    private int tamGrafo;
    private int posInicialJugador;
    private int posInicialWumpus;
    private double distancia = Config.distancia;

    private List<Vector<Double>> coordenadasCuevas;

    private boolean primera = true;


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

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
        //locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,  );
    }

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
           // Toast.makeText(getApplicationContext(), "prueba", Toast.LENGTH_SHORT).show();
            if (location.getLongitude() != 0 && location.getLatitude() != 0 && primera) {

                primera = false;
                    //Config.lonUsuario = location.getLongitude();
                    //Config.latUsuario = location.getLatitude();
                Config.lonUsuario =-84.052001;
                Config.latUsuario = 9.937921;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           // agregarMarca(latitudeNetwork, longitudeNetwork);
                          //  Toast.makeText(MapsActivity.this, "Marcador creado", Toast.LENGTH_SHORT).show();
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

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        toggleNetworkUpdates();
        tamGrafo = laberinto.getDimensionMatriz();
        coordenadasCuevas = new Vector<>();

       // if(latitudeNetwork != 0 && longitudeNetwork != 0) {
       //     crearMapMarks();
       // }
    }

    public void agregarMarca(double lat, double lon) {
        LatLng temp = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(temp).title("Marker in " + lat + ", " + lon + " (Cueva " + contadorMarcas + ")"));



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
   /** public void generarTiposDeCuevas() {
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
        posInicialJugador = (int) (Math.random() * tamGrafo); // no se puede crear el personaje en nodos inexistentes.
                                                                //De los 9 nodos que hay solo en 4 se puede crear el personaje en 4 de ellos
        posInicialWumpus = (int) (Math.random() * tamGrafo);
        tiposDeCuevas.add(posInicialJugador, 4);
        tiposDeCuevas.add(posInicialWumpus, 1);
        Config.tiposDeCuevas = tiposDeCuevas;
    }
    */



   public void generarPersonaje() {




       boolean personaje = false;
       tiposDeCuevas = new ArrayList<>(tamGrafo);

       for (int x = 0; x < tamGrafo ; x++) {

           if (laberinto.presenteEnElGrafo(x) == true) {

               int tipo = (int) (Math.random() * 5);

               if (tipo == 4 && personaje == false) {
                   //x--;
                   tiposDeCuevas.add(4);
                   personaje = true;
                   posInicialJugador = x;
               }

           }else{

               tiposDeCuevas.add(-1);
           }

       }


       Config.tiposDeCuevas = tiposDeCuevas;


   }


    public void crearMapMarks() {

       // tetrahedro();


        //9.93
        //-84.05
        //radio: 5 mts






        //this.generarTiposDeCuevas();

        generarPersonaje();

        int nodoInicial = 0;

        for (int x = 0; x < tamGrafo; x++) {
            Vector<Double> coordenada = new Vector<>();
            if (x == posInicialJugador) {

                coordenada.add(Config.latUsuario);//usuario
                 coordenada.add(Config.lonUsuario);//usuario

               // coordenada.add(9.937921);
               // coordenada.add(-84.052001);
                coordenadasCuevas.add(x, coordenada);
                Config.coordenadasIniciales = coordenada;

                nodoInicial = x;

                Config.cuevaInicial = nodoInicial;

                System.out.println("x: " + x + "lat: " + Config.latUsuario + "lon: " + Config.lonUsuario);

                agregarMarca(Config.latUsuario,Config.lonUsuario);                                                                 //generar marcador

            } else {
                coordenada.add(0.0);
                coordenada.add(0.0);
                coordenadasCuevas.add(x, coordenada);
            }
        }

        boolean encontrado = false;
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

       // for (int i = 0; i < tamGrafo ; i++) { // Recorre coordenadasCuevas y hace Marks
         //   if( (coordenadasCuevas.get(i).get(0) != 0.0 ) && (coordenadasCuevas.get(i).get(1) != 0.0 ) ) { //los nodos no presentes en el grafo tienen coor 0.0
              //  agregarMarca(coordenadasCuevas.get(i).get(0), coordenadasCuevas.get(i).get(1));
         //   }
        //}

        Config.coordenadasCuevas = coordenadasCuevas;
    }




    public void tetrahedro(){

        for (int nodo = 0; nodo < 3; nodo++) {

            agregarMarca(9.937977,-84.051858);
            agregarMarca(9.937942,-84.051847);
            agregarMarca(9.937898,-84.051889);
            agregarMarca(9.937914,-84.051800);

        }



    }


    public void startAR(View v){
        Intent i = new Intent(getApplicationContext(), SimpleCamera.class);
        startActivity(i);
    }







}