package com.example.carlos.wumpusproject;

import com.example.carlos.wumpusproject.utils.Config;
import com.example.carlos.wumpusproject.utils.DataBaseHelper;
import com.example.carlos.wumpusproject.utils.DrawingCanvas;
import com.example.carlos.wumpusproject.utils.Grafo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de se encarga de graphdraw activity.
 */

public class GraphDrawActivity extends AppCompatActivity implements View.OnClickListener {

    /** Guarda indice del boton previamente seleccionado en el grafo. */
    private int botonPrevio = -1;
    /** Guarda indice del boton final en el grafo */
    private int botonFinal = -1;
    /** Matriz de botones. */
    private ImageButton[][] matriz;
    /** Boton finalizar. */
    private Button finalizeButton;
    /** Boton escoger de librería. */
    private Button btnBiblio;
    /** Boton compartir laberinto de bluetooth. */
    private Button btnBluetooth;
    /** Numero de filas. */
    public static int numeroFilas;
    /** Coordenada inicial x del canvas. */
    private float inicioX = -1;
    /** Coordenada inicial y del canvas. */
    private float inicioY = -1;
    /** Coordenada final x del canvas. */
    private float finalX = -1;
    /** Coordenada final y del canvas. */
    private float finalY = -1;
    /** Canvas donde se dibuja. */
    private DrawingCanvas dw;
    /** Tipos de cuevas del juego. */
    private List<Integer> tiposCuevas;
    /** Controlador de la base de datos. */
    private DataBaseHelper dbManager;
    /** Grafo que representa el laberinto creado. */
    private Grafo grafo;
    /** Vector de los nombres de los grafos guardados en la base de datos. */
    private String[] vectorNombres;

    /**
     * Metodo onCreate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_draw);
        setTitle("¡Escogencia de Laberinto!");

        numeroFilas = 12;
        matriz = new ImageButton[numeroFilas][numeroFilas];
        for (int x = 0; x < numeroFilas; x++) {
            for (int y = 0; y < numeroFilas; y++) {
                String nombreBoton = "imageButton" + x;
                if (x == 11 && y <= 1) {
                    nombreBoton += 0;
                }
                nombreBoton += y;
                int id = getResources().getIdentifier(nombreBoton, "id", getPackageName());
                matriz[x][y] = (ImageButton) findViewById(id);
                if (x % 2 == 1 && y % 2 == 0) {
                    matriz[x][y].setOnClickListener(this);
                }
            }
        }

        finalizeButton = (Button) findViewById(R.id.finalizeDrawButton);
        finalizeButton.setOnClickListener(this);
        btnBiblio = (Button) findViewById(R.id.elegirBiblio);
        btnBiblio.setOnClickListener(this);
        btnBluetooth = (Button) findViewById(R.id.compartirBluetooth);
        btnBluetooth.setOnClickListener(this);


        dbManager = new DataBaseHelper(this);
        tiposCuevas = new ArrayList<>();
        dw = (DrawingCanvas) findViewById(R.id.drawingCanvas);
        grafo = new Grafo( numeroFilas*numeroFilas/4 );
    }

    /**
     * Metodo onClick
     */
    @Override
    public void onClick(View v) {
        for (int x = 0; x < numeroFilas; x++) {
            for (int y = 0; y < numeroFilas; y++) {
                //Click en un boton
                if (matriz[x][y].getId() == v.getId()) {
                    int X = x/2;
                    int Y = y/2;
                    matriz[x][y].setImageResource(R.drawable.cueva1);
                    int[] ubicacion = new int[2];
                    matriz[x][y].getLocationOnScreen(ubicacion);

                    //click por primera vez en un boton
                    if (inicioX == -1 && inicioY == -1) {
                        inicioX = ubicacion[0] + matriz[x][y].getWidth() / 2;
                        inicioY = ubicacion[1] + (matriz[x][y].getHeight() / 2) - 250;
                        botonPrevio = (numeroFilas/2)*X + Y;
                    } else {
                        //click por segunda vez en un boton
                        finalX = ubicacion[0] + matriz[x][y].getWidth() / 2;
                        finalY = ubicacion[1] + (matriz[x][y].getHeight() / 2) - 250;
                        botonFinal = (numeroFilas/2)*X + Y;
                    }

                    // Se toca dos veces el mismo punto
                    if (inicioX == finalX && inicioY == finalY) {
                        List<Integer> lista = grafo.obtenerVecinos(botonFinal);
                        boolean encontrado = (lista.size() > 0);

                        if (!encontrado) {
                            matriz[x][y].setImageResource(R.drawable.punto1);
                        }

                        inicioX = -1;
                        inicioY = -1;
                        finalX = -1;
                        finalY = -1;
                        botonPrevio = -1;
                        botonFinal = -1;
                    } else {
                        if (inicioX != -1 && inicioY != -1 && finalX != -1 && finalY != -1) {
                            //Si ya existe una arista, se borra.
                            if (grafo.hayArista(botonPrevio, botonFinal)) {
                                grafo.deleteArista(botonPrevio, botonFinal);

                                List<Integer> vecinosInicial = grafo.obtenerVecinos(botonPrevio);
                                List<Integer> vecinosFinal = grafo.obtenerVecinos(botonFinal);
                                boolean encontrado1 = (vecinosInicial.size() > 0);
                                boolean encontrado2 = (vecinosFinal.size() > 0);

                                //Si el nodo queda sin ninguna arista, se le cambia la imagen de fondo.
                                if (!encontrado1) {
                                    int f = botonPrevio/(numeroFilas/2);
                                    int c = botonPrevio%(numeroFilas/2);
                                    matriz[f*2+1][c*2].setImageResource(R.drawable.punto1);
                                }

                                //Si el nodo queda sin ninguna arista, se le cambia la imagen de fondo.
                                if (!encontrado2)
                                    matriz[X * 2 + 1][Y * 2].setImageResource(R.drawable.punto1);
                                dw.borrarLinea(inicioX, inicioY, finalX, finalY);
                            } else {
                                // Se crea la arista.
                                dw.dibujarLinea(inicioX, inicioY, finalX, finalY);
                                grafo.addArista(botonPrevio, botonFinal);
                            }
                            inicioX = -1;
                            inicioY = -1;
                            finalX = -1;
                            finalY = -1;
                            botonPrevio = -1;
                            botonFinal = -1;
                        }
                    }
                }
            }
        }

        //Click en boton finalizar.
        if (v.getId() == R.id.finalizeDrawButton) {
            boolean condicion1 = grafo.getNumeroNodos() > 4;
            boolean condicion2 = grafo.totalmenteConectado();

            if (condicion1 && condicion2) {
                this.definirTiposDeCuevas();
                this.guardarConfiguracion();
                //Pedir nombre a usuario
                mostrarAlertDialog();
            } else {
                if (!condicion1) Toast.makeText(this, "ERROR!\nEL grafo contiene una cantidad insuficiente de cuevas. " + "Deben haber al menos 5.", Toast.LENGTH_LONG).show();
                if (!condicion2) Toast.makeText(this, "ERROR!\nEl grafo no es totalmente conexo.", Toast.LENGTH_LONG).show();
            }
        }

        // Click en elegir grafo desde biblioteca.
        if (v.getId() == R.id.elegirBiblio) {
            List<String> nombresGrafos = dbManager.obtenerNombresDeGrafos();
            vectorNombres = GraphDrawActivity.listAsStringArray(nombresGrafos);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Escoja el nombre del laberinto deseado.");
            builder.setItems(vectorNombres, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mostrarMensajeBiblio(vectorNombres[i]);
                }
            });
            builder.setNegativeButton("Cancel", null); // No tiene OnClickListener
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        // Click en compartir laberinto por bluetooth.
        if (v.getId() == R.id.compartirBluetooth) {
            Intent intent;
            intent = new Intent(this ,BluetoothActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    /**
     * Muestra un mensaje con el archivo seleccionado desde la base de datos.
     */
    public void mostrarMensajeBiblio(final String hilera){
        final EditText nombre = new EditText(this);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Wumpus")
                .setMessage("Ha escogido el laberinto: " + hilera)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // cargar juego
                        grafo = dbManager.obtenerGrafoDeLibreria(hilera);
                        definirTiposDeCuevas();
                        guardarConfiguracion();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //elegir otro laberinto
                    }
                }).create();
        alertDialog.show();
    }

    /**
     * Metodo que muestra alerta de guardado en base de datos.
     * Se solicita el nombre con el que se va a guardar el grafo.
     */
    public void mostrarAlertDialog(){
        final Context context = this;
        final EditText nombre = new EditText(context);

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("Nombre del laberinto")
                .setMessage("Ingrese el nombre del laberinto:")
                .setView(nombre)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String nombreUsuario = nombre.getText().toString();

                        //Obtiene la lista de nombres que hay en la base
                        List<String> listaNombres = dbManager.obtenerNombresDeGrafos();

                        AlertDialog.Builder alerta2 = new AlertDialog.Builder(context)
                                .setTitle("Guardar laberinto")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                    }
                                });
                         if (listaNombres.contains(nombreUsuario)) {
                             alerta2.setTitle("ERROR!!!");
                             alerta2.setMessage("Nombre repetido. No se ha podido guardar.");
                             alerta2.setIcon(android.R.drawable.stat_notify_error);
                         } else {
                             // Inserta en la base de datos
                             dbManager.insertarGrafo(grafo, nombreUsuario);
                             alerta2.setMessage("Grafo guardado exitosamente");
                             alerta2.setIcon(android.R.drawable.ic_dialog_info);
                             // Empezar el juego
                         }
                        alerta2.show();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).create();
         alertDialog.show();
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
    public  void definirTiposDeCuevas(){
        List<Integer> nodos = grafo.obtenerNodos();

        //Se crean los tipos de cuevas
        boolean wumpus = false;
        for (int x = 0; x < nodos.size(); x++) {
            int tipo = (int) (Math.random() * 3);
            if (tipo == 1 && wumpus) {
                x--;
            } else {
                tiposCuevas.add(tipo);
                if (tipo == 1) {
                    wumpus = true;
                }
            }
        }
    }

    /**
     * Guarda la configuracion del juego.
     */
    public void guardarConfiguracion(){
        Config.laberinto = grafo;
        Config.tiposDeCuevas = tiposCuevas;
    }

    /**
     * Devuelve los elmentos de una lista como un array.
     *
     * @return Un array con los elementos de la lista.
     */
    public static String[] listAsStringArray(List<String> list){
        String[] array = new String[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}