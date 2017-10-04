package com.example.carlos.wumpusproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class GraphDrawActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton[][] matriz;
    private Button finalizeButton;

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
                    Toast.makeText(this, "imagenbutton" + x + y, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}