package com.example.carlos.wumpusproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class GraphDraw extends AppCompatActivity{


    private ArrayList<ImageButton> listaBotones = new ArrayList<ImageButton>();
    private String nombreBoton = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_draw);

    }

    public void inicializarBotones(View v){

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

    public void clickBoton (View v){

    }

}

