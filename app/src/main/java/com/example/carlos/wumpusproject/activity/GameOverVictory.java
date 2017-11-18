package com.example.carlos.wumpusproject.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.carlos.wumpusproject.R;

/**
 * Created by Carlos on 18/11/2017.
 */

public class GameOverVictory extends AppCompatActivity implements View.OnClickListener
{
    private Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.gameover_victory);

        //Configura el botón
        menu = (Button) findViewById(R.id.buttonRestart4);
        menu.setOnClickListener(this);

        //Prepara el video
        VideoView videoview = findViewById(R.id.videoVictory);
        videoview.requestFocus();
        Uri video = Uri.parse("android.resource://com.example.carlos.wumpusproject/raw/victory2");
        videoview.setVideoURI(video);
        videoview.setMediaController(new MediaController(this));
        videoview.start();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonRestart4:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
