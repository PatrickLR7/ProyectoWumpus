package com.example.carlos.wumpusproject;

import com.example.carlos.wumpusproject.utils.DBConnection;
import com.example.carlos.wumpusproject.utils.DBManager;
import com.example.carlos.wumpusproject.utils.DrawingCanvas;
import com.example.carlos.wumpusproject.utils.Grafo;

import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GraphDrawActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton[][] matriz;
    Button finalizeButton;
    private float inicioX = -1;
    private float inicioY = -1;
    private float finalX = -1;
    private float finalY = -1;
    DrawingCanvas dw;

    private List<Pair<Integer, Integer>> listaAristas;
    private int origen = -1; //Se usa para guardar las aristas
    private int destino = -1; //Se usa para guardar las aristas

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_draw);

        matriz = new ImageButton[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                String nombreBoton = "imageButton" + x + y;
                int id = getResources().getIdentifier(nombreBoton, "id", getPackageName());
                matriz[x][y] = (ImageButton) findViewById(id);
                matriz[x][y].setOnClickListener(this);
            }
        }

        finalizeButton = (Button) findViewById(R.id.graphDrawButton);
        finalizeButton.setOnClickListener(this);

        dbManager = new DBManager(this);
        dbManager.openDataBase();
        listaAristas = new LinkedList<>();
    }

    @Override
    public void onClick(View v) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
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
                    if (inicioX == finalX && inicioY == finalY) {
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
                                Toast.makeText(this, "La arista ya existe", Toast.LENGTH_SHORT).show();
                            } else {
                                dw = (DrawingCanvas) findViewById(R.id.drawingCanvas);
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

        if (v.getId() == R.id.graphDrawButton) {
            int error = 0;
            List<Integer> nodos = new LinkedList<>();
            for (int x = 0; x < listaAristas.size(); x++) {
                int nodo = listaAristas.get(x).first;
                if (!nodos.contains(nodo)) {
                    nodos.add(nodo);
                }
            }

            Grafo g = new Grafo(nodos.size());
            List<Integer> tipos = new ArrayList<>(nodos.size());
            //Se crean los tipos de cuevas
            boolean wumpus = false;
            for (int x = 0; x < nodos.size(); x++) {
                int tipo = (int) (Math.random() * 3);
                if (tipo == 1 && wumpus) {
                    x--;
                } else {
                    tipos.add(tipo);
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
                g.addArista(nodo1, nodo2);
            }

            if (error == 0) {
                Toast.makeText(this, "Su laberinto se guardó correctamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Hubo un error al intentar guardar el laberinto. Por favor intente de nuevo", Toast.LENGTH_LONG).show();
            }
        }
    }


}