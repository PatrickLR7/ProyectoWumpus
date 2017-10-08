package com.example.carlos.wumpusproject.utils;

/**
 * Created by Patrick on 08/10/2017.
 */
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import com.example.carlos.wumpusproject.R;

import java.io.File;
import java.util.List;

public class UsoBluetooth extends Activity {
    private static final int DISCOVER_DURATION = 300;
    private static final int  REQUEST_BLU = 1;
    private File archivoCompartir;

    //Método para solicitar permiso para activar bluetooth por 300 segundos
    public void activarBlu() {
        Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVER_DURATION );
        startActivityForResult(discoveryIntent, REQUEST_BLU);
    }

    //Método para compartir archivo
    public void compartirBluetooth(File laberintoACompartir) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(laberintoACompartir));

        PackageManager pm = getPackageManager();
        List listaApps = pm.queryIntentActivities(intent, 0);

        String nombrePaquete = null;
        String nombreClase = null;
        boolean encontrado = false;
        ResolveInfo info;

        if(listaApps.size() > 0) {
            for(int i=0; i < listaApps.size(); i++) {
                info = (ResolveInfo) listaApps.get(i);
                nombrePaquete = info.activityInfo.packageName;
                if(nombrePaquete.equals("com.android.bluetooth")) {
                    nombreClase = info.activityInfo.name;
                    encontrado = true;
                }
            }
            if(!encontrado) {
                Toast.makeText(this, "No se encontró el paquete en la lista", Toast.LENGTH_SHORT).show();
            } else {
                intent.setClassName(nombrePaquete, nombreClase);
                startActivity(intent);
            }
        }
    }


    // Cuando se completa startActivityForResult…
    //Método para revisar si enableBlu retornó DISCOVER_DURATION y así continuar

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == DISCOVER_DURATION
                && requestCode == REQUEST_BLU) {
            // el código a procesar va aquí.
            compartirBluetooth(archivoCompartir);
        }  else { // cancelado o error.
            Toast.makeText(this, android.R.string.cancel,
                    Toast.LENGTH_SHORT).show();

        }
    }

    public void asignarArchivo(File archivo) {
        archivoCompartir = archivo;
    }

}
