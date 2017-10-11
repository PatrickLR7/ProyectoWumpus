package com.example.carlos.wumpusproject;

import com.example.carlos.wumpusproject.utils.DBManager;
import com.example.carlos.wumpusproject.utils.DrawingCanvas;
import com.example.carlos.wumpusproject.utils.Grafo;
import com.example.carlos.wumpusproject.utils.ListaBiblio;

import android.content.DialogInterface;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GraphDrawActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton[][] matriz;
    private int numeroPuntos;
    private Button finalizeButton;
    private Button btnBiblio;
    private Button btnBluetooth;
    private float inicioX = -1;
    private float inicioY = -1;
    private float finalX = -1;
    private float finalY = -1;
    DrawingCanvas dw;

    private List<Pair<Integer, Integer>> listaAristas;
    private int origen = -1; //Se usa para guardar las aristas
    private int destino = -1; //Se usa para guardar las aristas

    private DBManager dbManager;
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

        numeroPuntos = 14;
        matriz = new ImageButton[numeroPuntos][numeroPuntos];
        for (int x = 0; x < numeroPuntos; x++) {
            for (int y = 0; y < numeroPuntos; y++) {
                String nombreBoton = "imageButton" + x;
                if (x == 11 && y < 4) {
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

        dbManager = new DBManager(this);
        dbManager.openDataBase();
        listaAristas = new LinkedList<>();
        dw = (DrawingCanvas) findViewById(R.id.drawingCanvas);
        grafo = new Grafo(numeroPuntos);
    }

    @Override
    public void onClick(View v) {
        for (int x = 0; x < numeroPuntos; x++) {
            for (int y = 0; y < numeroPuntos; y++) {
                if (matriz[x][y].getId() == v.getId()) {
                    matriz[x][y].setImageResource(R.drawable.cueva1);
                    int[] ubicacion = new int[2];
                    matriz[x][y].getLocationOnScreen(ubicacion);
                    if (inicioX == -1 && inicioY == -1) {
                        inicioX = ubicacion[0] + matriz[x][y].getWidth() / 2;
                        inicioY = ubicacion[1] + (matriz[x][y].getHeight() / 2) - 250;
                        origen = matriz[x][y].getId();
                    } else {
                        finalX = ubicacion[0] + matriz[x][y].getWidth() / 2;
                        finalY = ubicacion[1] + (matriz[x][y].getHeight() / 2) - 250;
                        destino = matriz[x][y].getId();
                    }
                    if (inicioX == finalX && inicioY == finalY) { // Se toca dos veces el mismo punto
                        boolean encontrado = false;
                        for (int a = 0; a < listaAristas.size(); a++) {
                            if (listaAristas.get(a).first == origen) {
                                encontrado = true;
                                a = listaAristas.size() + 10;
                            }
                        }
                        if (!encontrado) {
                            matriz[x][y].setImageResource(R.drawable.punto1);
                        }
                        inicioX = -1;
                        inicioY = -1;
                        finalX = -1;
                        finalY = -1;
                        origen = -1;
                        destino = -1;
                    } else {
                        if (inicioX != -1 && inicioY != -1 && finalX != -1 && finalY != -1) {
                            if (listaAristas.contains(new Pair<>(origen, destino))) {
                                listaAristas.remove(new Pair<>(origen, destino));
                                listaAristas.remove(new Pair<>(destino, origen));

                                boolean encontrado1 = false;
                                boolean encontrado2 = false;
                                for (int a = 0; a < listaAristas.size(); a++) {
                                    if (listaAristas.get(a).first == origen && !encontrado1) {
                                        encontrado1 = true;
                                    }
                                    if (listaAristas.get(a).first == destino && !encontrado2) {
                                        encontrado2 = true;
                                    }
                                }

                                if (!encontrado1) {
                                    for (int a = 0; a < numeroPuntos; a++) {
                                        for (int b = 0; b < numeroPuntos; b++) {
                                            if (matriz[a][b].getId() == origen) {
                                                matriz[a][b].setImageResource(R.drawable.punto1);
                                                a = numeroPuntos * 2;
                                                b = numeroPuntos * 2;
                                            }
                                        }
                                    }
                                }
                                if (!encontrado2) {
                                    for (int a = 0; a < numeroPuntos; a++) {
                                        for (int b = 0; b < numeroPuntos; b++) {
                                            if (matriz[a][b].getId() == destino) {
                                                matriz[a][b].setImageResource(R.drawable.punto1);
                                                a = numeroPuntos * 2;
                                                b = numeroPuntos * 2;
                                            }
                                        }
                                    }
                                }
                                dw.borrarLinea(inicioX, inicioY, finalX, finalY);
                            } else {
                                dw.dibujarLinea(inicioX, inicioY, finalX, finalY);
                                listaAristas.add(new Pair<>(origen, destino));
                                listaAristas.add(new Pair<>(destino, origen));
                            }
                            inicioX = -1;
                            inicioY = -1;
                            finalX = -1;
                            finalY = -1;
                            origen = -1;
                            destino = -1;
                        }
                    }
                }
            }
        }

        if (v.getId() == R.id.finalizeDrawButton) {
            int error = 0;
            List<Integer> nodos = new LinkedList<>();
            for (int x = 0; x < listaAristas.size(); x++) {
                int nodo = listaAristas.get(x).first;
                if (!nodos.contains(nodo)) {
                    nodos.add(nodo);
                }
            }

            List<Integer> tiposCuevas = new ArrayList<>(nodos.size());
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

            for (int x = 0; x < listaAristas.size(); x++) {
                int id1 = listaAristas.get(x).first;
                int id2 = listaAristas.get(x).second;
                int nodo1 = -1;
                int nodo2 = -1;
                int counter = 0;
                while ((nodo1 == -1 && nodo2 == -1) || counter < nodos.size()) {
                    if (nodos.get(counter) == id1) {
                        nodo1 = counter;
                    }
                    if (nodos.get(counter) == id2) {
                        nodo2 = counter;
                    }
                    counter++;
                }
                if (nodo1 == -1 || nodo2 == -1) {
                    error = -1;
                    x = listaAristas.size() + 10;
                }
                grafo.addArista(nodo1, nodo2);
            }


            //Pedir nombre a usuario

                mostrarAlertDialog();


            //Obtiene la lista de nombres que hay en la base
           // listaNombres = dbManager.obtenerNombresDeGrafos();

          // if (listaNombres.contains(nombreUsuario)) {
                //Pedir otro nombre
          // } else {
                // Inserta en la base de datos
          //      dbManager.insertarGrafo(grafo, nombreUsuario);
          // }


            if (error == 0) {
                Toast.makeText(this, "Su laberinto se guardó correctamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Hubo un error al intentar guardar el laberinto. Por favor intente de nuevo", Toast.LENGTH_LONG).show();
            }

            if(repetido = true) {
                Toast.makeText(this, "El nombre utilizado ya se encuentra en la base de datos. Por favor intente de nuevo", Toast.LENGTH_LONG).show();
            }

        }

        if(v.getId() == R.id.elegirBiblio){
            nombresBiblio = dbManager.obtenerNombresDeGrafos();
            ArrayList<String> miLista = new ArrayList<String>(nombresBiblio);
            listaBiblio = new ListaBiblio(this, miLista);
            listView1.setAdapter(listaBiblio);
        }
    }


    public void mostrarAlertDialog(){

        final EditText nombre = new EditText(this);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Nombre del grafo")
                .setMessage("Ingrese el nombre del grafo:")
                .setView(nombre)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        nombreUsuario = nombre.getText().toString();

                        System.out.println(nombreUsuario);
                        //Obtiene la lista de nombres que hay en la base
                         listaNombres = dbManager.obtenerNombresDeGrafos();

                         if (listaNombres.contains(nombreUsuario)) {
                        //Pedir otro nombre
                             repetido = true;
                         } else {
                        // Inserta en la base de datos
                            dbManager.insertarGrafo(grafo, nombreUsuario);
                             repetido = false;
                         }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).create();
         alertDialog.show();



    }
}




