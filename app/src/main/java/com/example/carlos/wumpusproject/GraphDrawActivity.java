package com.example.carlos.wumpusproject;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Iterator;

public class GraphDrawActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<ImageButton> listaBotones = new ArrayList<ImageButton>();
    private String nombreBoton = "";
    private Button finalizeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_draw);

        finalizeButton = (Button) findViewById(R.id.graphDrawButton);
        finalizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Saving graph to database

            }
        });
    }

    public void inicializarBotones(View v) {
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                nombreBoton = "imageButton";
                nombreBoton += i;
                nombreBoton += j;
                int btnId = getResources().getIdentifier(nombreBoton, "id", getPackageName());
                listaBotones.add((ImageButton) findViewById(btnId));
            }
        }
    }

    @Override
    public void onClick(View view) {
        int btnId = getResources().getIdentifier(nombreBoton, "id", getPackageName());

        for (int i = 0; i < listaBotones.size(); i++){
           if(listaBotones.get(i).getId() == view.getId()) {
              //  listaBotones.get(i).setBackground(R.mipmap.cueva);
           }
        }
    }
}

