package com.example.carlos.wumpusproject;

import com.example.carlos.wumpusproject.utils.DataBaseHelper;
import com.example.carlos.wumpusproject.utils.DrawingCanvas;
import com.example.carlos.wumpusproject.utils.Grafo;
import com.example.carlos.wumpusproject.utils.ListaBiblio;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GraphDrawActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton[][] matriz;
    public static int numeroFilas;
    private Button finalizeButton;
    private Button btnBiblio;
    private Button btnBluetooth;
    private float inicioX = -1;
    private float inicioY = -1;
    private float finalX = -1;
    private float finalY = -1;
    private DrawingCanvas dw;

    private List<Pair<Integer, Integer>> listaAristas;
    private List<Integer> tiposCuevas;
    private int botonPrevio = -1; // Guarda indice del boton previamente seleccionado en el grafo
    private int botonFinal = -1; // Guarda indice de boton final en el grafo

    private DataBaseHelper dbManager;
    private Grafo grafo;
    private List<String> listaNombres;
    private String nombreUsuario = "";
    private Boolean repetido = true;

    private List<String> nombresBiblio = new ArrayList<>();
    private ListaBiblio listaBiblio; //ADAPTADOR entre lista de laberintos y la vista en el layout
    private ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_draw);

        numeroFilas = 12;
        matriz = new ImageButton[numeroFilas][numeroFilas];
        for (int x = 0; x < numeroFilas; x++) {
            for (int y = 0; y < numeroFilas; y++) {
                String nombreBoton = "imageButton" + x;
                if (x == 11 && y == 1) {
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
        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dbManager = new DataBaseHelper(this);
        listaAristas = new LinkedList<>();
        tiposCuevas = new ArrayList<>();
        dw = (DrawingCanvas) findViewById(R.id.drawingCanvas);
        grafo = new Grafo( numeroFilas*numeroFilas/4 );
    }

    @Override
    public void onClick(View v) {
        for (int x = 0; x < numeroFilas; x++) {
            for (int y = 0; y < numeroFilas; y++) {
                if (matriz[x][y].getId() == v.getId()) {
                    int X = x/2;
                    int Y = y/2;
                    matriz[x][y].setImageResource(R.drawable.cueva1);
                    int[] ubicacion = new int[2];
                    matriz[x][y].getLocationOnScreen(ubicacion);
                    if (inicioX == -1 && inicioY == -1) {
                        inicioX = ubicacion[0] + matriz[x][y].getWidth() / 2;
                        inicioY = ubicacion[1] + (matriz[x][y].getHeight() / 2) - 250;
                        botonPrevio = (numeroFilas/2)*X + Y;
                    } else {
                        finalX = ubicacion[0] + matriz[x][y].getWidth() / 2;
                        finalY = ubicacion[1] + (matriz[x][y].getHeight() / 2) - 250;
                        botonFinal = (numeroFilas/2)*X + Y;
                    }
                    if (inicioX == finalX && inicioY == finalY) { // Se toca dos veces el mismo punto
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
                            if (grafo.hayArista(botonPrevio, botonFinal)) {
                                grafo.deleteArista(botonPrevio, botonFinal);

                                List<Integer> vecinosInicial = grafo.obtenerVecinos(botonPrevio);
                                List<Integer> vecinosFinal = grafo.obtenerVecinos(botonFinal);
                                boolean encontrado1 = (vecinosInicial.size() > 0);
                                boolean encontrado2 = (vecinosFinal.size() > 0);

                                if (!encontrado1) {
                                    int f = botonPrevio/(numeroFilas/2);
                                    int c = botonPrevio%(numeroFilas/2);
                                    matriz[f*2+1][c*2].setImageResource(R.drawable.punto1);
                                }

                                if (!encontrado2)
                                    matriz[X * 2 + 1][Y * 2].setImageResource(R.drawable.punto1);


                                dw.borrarLinea(inicioX, inicioY, finalX, finalY);
                            } else {
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

        if (v.getId() == R.id.finalizeDrawButton) {
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

            //Pedir nombre a usuario
            mostrarAlertDialog();


        }

        if(v.getId() == R.id.elegirBiblio){
            nombresBiblio = dbManager.obtenerNombresDeGrafos();
            ArrayList<String> miLista = new ArrayList<>(nombresBiblio);
            listaBiblio = new ListaBiblio(this, miLista);
            listView1.setAdapter(listaBiblio);
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView txt1 = (TextView) view;
                    grafo = dbManager.obtenerGrafoDeLibreria(txt1.getText().toString());
                    mostrarMensajeBiblio(txt1.getText().toString());
                }
            });
        }

    }

    public void mostrarMensajeBiblio(String n){

        final EditText nombre = new EditText(this);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Wumpus")
                .setMessage("Ha escogido el laberinto: " + n)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // cargar juego
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //elegir otro laberinto
                    }
                }).create();
        alertDialog.show();
    }

    public void mostrarAlertDialog(){

        final Context context = this;
        final EditText nombre = new EditText(context);

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("Nombre del laberinto")
                .setMessage("Ingrese el nombre del laberinto:")
                .setView(nombre)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        nombreUsuario = nombre.getText().toString();

                        System.out.println(nombreUsuario);
                        //Obtiene la lista de nombres que hay en la base
                        listaNombres = dbManager.obtenerNombresDeGrafos();

                        AlertDialog.Builder alerta2 = new AlertDialog.Builder(context)
                                .setTitle("Estado")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                    }
                                });
                         if (listaNombres.contains(nombreUsuario)) {
                        //Pedir otro nombre
                             repetido = true;
                             alerta2.setMessage("Nombre repetido. Elija otro");
                             alerta2.setIcon(android.R.drawable.ic_dialog_alert);
                         } else {
                        // Inserta en la base de datos
                             dbManager.insertarGrafo(grafo, nombreUsuario);
                             repetido = false;
                             alerta2.setMessage("Grafo guardado exitosamente");
                             alerta2.setIcon(android.R.drawable.ic_dialog_info);
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



}




