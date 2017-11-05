package com.example.carlos.wumpusproject;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlos.wumpusproject.utils.DataBaseHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BluetoothActivity extends AppCompatActivity {
    private static final int RECORD_REQUEST_CODE = 1;
    //Create Objects-------------------------------------------------------
    Button buttonopenDailog, send, btnLaberinto, btnRecibido; //Botones para el layout.
    EditText dataPath; //Muestra el texto del laberinto recibido.
    private static final int DISCOVER_DURATION = 300; //Duración durante la cuál el dispositivo será visible por medio de bluetooth.
    private static final int REQUEST_BLU = 1; //Código de respuesta cuando se activa el permiso para el bluetooth.
    BluetoothAdapter btAdatper = BluetoothAdapter.getDefaultAdapter(); //Obtiene el adaptador de bluetooth por defecto.
    String path = ""; //ruta del laberinto seleccionado para enviar.
    private String[] vectorNombres; //nombres de los laberintos que están guardados en la biblioteca.
    private DataBaseHelper dbManager; //utilizado para manejar la biblioteca con laberintos.
    private ArrayList<String> archCreados; //nombres de los laberintos que se enviarán por bluetooth que ya tienen un archivo creado para enviar.
    String pathRecibido = ""; //ruta del laberinto recibido, para guardarlo en la biblioteca.

    //---------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) { /* Inicializa todas las variables requeridas. */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        dataPath=(EditText)findViewById(R.id.FilePath);
        buttonopenDailog= (Button) findViewById(R.id.opendailog);
        send=(Button)findViewById(R.id.sendBtooth);
        btnLaberinto = (Button) findViewById(R.id.btnLaberinto);
        btnRecibido = (Button) findViewById(R.id.btnRecibido);
        dbManager = new DataBaseHelper(this);
        archCreados = new ArrayList<String>();
        dataPath.setText("");
        PackageManager pm = getBaseContext().getPackageManager();
        int hasPerm = pm.checkPermission(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                getBaseContext().getPackageName());
        if (hasPerm != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }
    }

    /*solicita permiso para escribir en el almacenamiento del celular, es necesario para
    * guardar los laberintos recibidos. */
    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    /* Muestra un mensaje cuando se acepta el permiso o cuando no es aceptado.*/
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
            if(requestCode == 1){
                for(int i = 0, len = permissions.length; i < len; i++){
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        //Permiso concedido
                        Toast.makeText(BluetoothActivity.this, "Permiso concedido", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //permiso denegado
                        Toast.makeText(BluetoothActivity.this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                    }
                }
            }

    }

    //Utilizado para salir del activity de bluetooth.
    public void exit(View V) {
        //btAdatper.disable();
        Toast.makeText(getApplicationContext(),"Saliendo de Bluetooth",Toast.LENGTH_LONG).show();
        finish(); }

    //Método que se ejecuta cuando se envía un archivo por bluetooth (se activa con el boton enviar del layout).
    public void sendViaBluetooth(View v) {
        if(!dataPath.equals(null)){
        if (btAdatper == null) {
            Toast.makeText(this, "El dispositivo no tiene bluetooth disponible", Toast.LENGTH_LONG).show();
        } else {
            enableBluetooth();
        }
    }else{
            Toast.makeText(this,"Seleccione un archivo",Toast.LENGTH_LONG).show();
        }
    }

    /*Activa el bluetooth y hace al dispositivo visible por 300 segundos.*/
    public void enableBluetooth() {
        Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVER_DURATION);
        startActivityForResult(discoveryIntent, REQUEST_BLU);
    }

    /*Abre el almacenamiento del smartphone para seleccionar un archivo. */
    public void getFile(View v) {
        Intent mediaIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mediaIntent.setType("*/*"); //establece el tipo de archivo que se va a seleccionar.
        startActivityForResult(mediaIntent, 1001); //El código 1001 se maneja en el método onActivityResult.
    }

    /* Obtiene la ruta de un archivo seleccionado. */
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKatOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // Proveedor de Documentos.
        if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)) {
            // Proveedor del Almacenamiento Externo.
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
        }
        // MediaStore (Archivos multimedia).
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // Archivo general.
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Obtiene el valor de la columna de datos para este Uri. Es útil para uris
     * de la MediaStore y otros proveedores de contenido basados en archivos.
     *
     * @param context       El contexto.
     * @param uri           El uri a consultar.
     * @param selection     (Optional) Filtro utilizado en la consulta.
     * @param selectionArgs (Optional) Argumentos de selección usados en la consulta.
     * @return El valor de la columna de datos, que típicamente es una ruta de archivo.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri El Uri a revisar.
     * @return Si la autoridad del Uri es "ExternalStorageProvider".
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    //Método que se encarga de manejar los códigos de respuesta del activity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* Si el código de respuesta es DISCOVER_DURATION ejecuta lo necesario para enviar
         * el archivo mediante bluetooth. */
        if (resultCode == DISCOVER_DURATION && requestCode == REQUEST_BLU) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.setType("*/*");
            File file = new File(path);

             i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            //i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(getFileStreamPath(h)));
            //i.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(BluetoothActivity.this,
            //BuildConfig.APPLICATION_ID + ".provider", file));

            PackageManager pm = getPackageManager();
            List<ResolveInfo> list = pm.queryIntentActivities(i, 0);
            if (list.size() > 0) {
                String packageName = null;
                String className = null;
                boolean found = false;

                for (ResolveInfo info : list) {
                    packageName = info.activityInfo.packageName;
                    if (packageName.equals("com.android.bluetooth")) {
                        className = info.activityInfo.name;
                        found = true;
                        break;
                    }
                }
                //Revisa si el bluetooth está disponible o no.
                if (!found) {
                    Toast.makeText(this, "No se ha encontrado Bluetooth", Toast.LENGTH_LONG).show();
                } else {
                    i.setClassName(packageName, className);
                    i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(i);
                }
            }
            //Si el código de respuesta es 1001, guarda la ruta del archivo en la variable pathRecibido.
            //Se utiliza en la parte de guardar en la biblioteca un laberinto recibido por bluetooth.
        } else if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            Uri uriPath = data.getData();
            Log.d("", "Video URI= " + uriPath);

            pathRecibido = getPath(this, uriPath);// "/mnt/sdcard/FileName.mp3"
            System.out.println("pathhhh " + path);
            //dataPath.setText(path);

        }

        else {
            Toast.makeText(this, "Bluetooth se ha cancelado", Toast.LENGTH_LONG).show(); //Se ejecuta cuando se cancela bluetooth.
        }
    }

    /* Permite elegir un laberinto de la biblioteca para enviar. */
    public void elegirLaberinto(View view){
        List<String> nombresGrafos = dbManager.obtenerNombresDeGrafos();
        vectorNombres = GraphDrawActivity.listAsStringArray(nombresGrafos);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escoja el nombre del laberinto deseado.");
        builder.setItems(vectorNombres, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                msjBiblioYGenerarArchivo(vectorNombres[i]);
                dataPath.setText("Ha elegido el laberinto: " + vectorNombres[i]);
            }
        });
        builder.setNegativeButton("Cancel", null); // No tiene OnClickListener
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    /**
     * Muestra un mensaje con el archivo seleccionado desde la base de datos (laberinto de la biblioteca).
     *
     * @param hilera:
     */
    public void msjBiblioYGenerarArchivo(final String hilera){
        final EditText nombre = new EditText(this);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Wumpus")
                .setMessage("Ha escogido el laberinto: " + hilera)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // generar archivo para enviar
                        /*
                        grafo = dbManager.obtenerGrafoDeLibreria(hilera);
                        definirTiposDeCuevas();
                        guardarConfiguracion();
                        */
                        boolean encontrado = false;
                        String nombre = hilera;
                        for(int i = 0; i < archCreados.size(); i++){
                            if(nombre.equals(archCreados.get(i))){
                                encontrado = true;
                            } else{
                                encontrado = false;
                            }
                        }
                        if(!encontrado){
                            dbManager.grafoComoArchivo(hilera, BluetoothActivity.this);
                            archCreados.add(hilera);
                            path = (Environment.getExternalStorageDirectory().getPath() + "/WumpusApp/" + hilera + ".txt");
                        } else {
                            path = (Environment.getExternalStorageDirectory().getPath() + "/WumpusApp/" + hilera + ".txt");
                        }




                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //elegir otro laberinto
                    }
                }).create();
        alertDialog.show();
    }

    /* Guarda un laberinto recibido por bluetooth en la biblioteca. */
    public void guardarEnBiblioteca(View view){
        String nom;
        File file = new File(pathRecibido);
        nom = file.getName();
        int pos = nom.lastIndexOf(".");
        if (pos > 0) {
            nom = nom.substring(0, pos);
        }
        dbManager.leerArchivoComoGrafo(nom, BluetoothActivity.this);
        msjRecibido();
    }

    /* Muestra un mensaje cuando el laberinto recibido por bluetooth se activa o guarda en la biblioteca. */
    public void msjRecibido(){
        final EditText nombre = new EditText(this);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Wumpus")
                .setMessage("El laberinto recibido se ha activado correctamente")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create();
        alertDialog.show();
    }
}


