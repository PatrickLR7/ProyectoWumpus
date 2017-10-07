package com.example.carlos.wumpusproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.carlos.wumpusproject.utils.DrawingCanvas;

public class GraphDrawActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton[][] matriz;
    private Button finalizeButton;
    private float inicioX = -1;
    private float inicioY = -1;
    private float finalX = -1;
    private float finalY = -1;
    private DrawingCanvas dw;

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
    }

    @Override
    public void onClick(View v) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (matriz[x][y].getId() == v.getId()) {
                    matriz[x][y].setImageResource(R.drawable.cueva1);
                    int[] ubicacion = new int[2];
                    matriz[x][y].getLocationOnScreen(ubicacion);
                    if(inicioX == -1 && inicioY == -1) {
                        inicioX = ubicacion[0] + matriz[x][y].getWidth()/2;
                        inicioY = ubicacion[1] + (matriz[x][y].getHeight()/2) - 250;
                    } else  {
                        finalX = ubicacion[0] + matriz[x][y].getWidth()/2;
                        finalY = ubicacion[1] + (matriz[x][y].getHeight()/2) - 250;
                    }
                    if (inicioX != -1 && inicioY != -1 && finalX != -1 && finalY != -1) {
                        dw = (DrawingCanvas)findViewById(R.id.drawingCanvas);
                        dw.dibujarLinea(inicioX, inicioY, finalX, finalY);
                        inicioX = -1;
                        inicioY = -1;
                        finalX = -1;
                        finalY = -1;
                    }
                }
            }
        }

        if (v.getId() == R.id.graphDrawButton) {
            Toast.makeText(this, "Se guardó su laberinto", Toast.LENGTH_LONG).show();
        }
    }
}