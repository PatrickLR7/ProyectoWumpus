package com.example.carlos.wumpusproject;

import android.*;
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
    Button buttonopenDailog, buttonUp, send, btnLaberinto, btnRecibido;
    TextView textFolder;
    EditText dataPath;
    static final int CUSTOM_DIALOG_ID = 0;
    ListView dialog_ListView;
    File root, fileroot, curFolder;
    private List<String> fileList = new ArrayList<String>();
    private static final int DISCOVER_DURATION = 300;
    private static final int REQUEST_BLU = 1;
    BluetoothAdapter btAdatper = BluetoothAdapter.getDefaultAdapter();
    String path = "";
    private String[] vectorNombres;
    private DataBaseHelper dbManager;
    private ArrayList<String> archCreados;
    String pathRecibido = "";

    //---------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        /*buttonopenDailog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataPath.setText("");
                showDialog(CUSTOM_DIALOG_ID);
            }
        });
        */

        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        curFolder = root;
       /* send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendViaBluetooth();
            }
        });
        */
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

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

   @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case CUSTOM_DIALOG_ID:
                dialog = new Dialog(BluetoothActivity.this);
                dialog.setContentView(R.layout.dialoglayout);
                dialog.setTitle("File Selector");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                textFolder = (TextView) dialog.findViewById(R.id.folder);
                buttonUp = (Button) dialog.findViewById(R.id.up);
                buttonUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ListDir(curFolder.getParentFile());
                    }
                });
                dialog_ListView = (ListView) dialog.findViewById(R.id.dialoglist);
                dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        File selected = new File(fileList.get(position));
                        if (selected.isDirectory()) {
                            ListDir(selected);
                        } else if (selected.isFile()) {
                            getselectedFile(selected);
                        } else {
                            dismissDialog(CUSTOM_DIALOG_ID);
                        }
                    }
                });
                break;
        }
        return dialog;
    }


    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case CUSTOM_DIALOG_ID:
                ListDir(curFolder);
                break;
        }
    }

    public void getselectedFile(File f){
        dataPath.setText(f.getAbsolutePath());
        fileList.clear();
        dismissDialog(CUSTOM_DIALOG_ID);
    }


    public void ListDir(File f) {
        if (f.equals(root)) {
            buttonUp.setEnabled(false);
        } else {
            buttonUp.setEnabled(true);
        }
        curFolder = f;
        textFolder.setText(f.getAbsolutePath());
        dataPath.setText(f.getAbsolutePath());
        File[] files = f.listFiles();
        fileList.clear();

        for (File file : files) {
            fileList.add(file.getPath());
        }
        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileList);
        dialog_ListView.setAdapter(directoryList);
    }



    //exit to application---------------------------------------------------------------------------
    public void exit(View V) {
        btAdatper.disable();
        Toast.makeText(this,"Saliendo de Bluetooth",Toast.LENGTH_LONG).show();
        finish(); }

    //Method for send file via bluetooth------------------------------------------------------------
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

    public void enableBluetooth() {
        Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVER_DURATION);
        startActivityForResult(discoveryIntent, REQUEST_BLU);
    }

    public void getFile(View v) { /// obtiene la ubicación del archivo.
        Intent mediaIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mediaIntent.setType("*/*"); //set mime type as per requirement
        startActivityForResult(mediaIntent, 1001);
    }

    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKatOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
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
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
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
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    //Override method for sending data via bluetooth availability--------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                //CHECK BLUETOOTH available or not------------------------------------------------
                if (!found) {
                    Toast.makeText(this, "No se ha encontrado Bluetooth", Toast.LENGTH_LONG).show();
                } else {
                    i.setClassName(packageName, className);
                    i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(i);
                }
            }
        } else if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            Uri uriPath = data.getData();
            Log.d("", "Video URI= " + uriPath);

            pathRecibido = getPath(this, uriPath);// "/mnt/sdcard/FileName.mp3"
            System.out.println("pathhhh " + path);
            //dataPath.setText(path);

        }

        else {
            Toast.makeText(this, "Bluetooth se ha cancelado", Toast.LENGTH_LONG).show();
        }
    }

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
     * Muestra un mensaje con el archivo seleccionado desde la base de datos.
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
                            //path = (getFilesDir().getAbsolutePath() + "/" + hilera + ".txt");
                            //h = hilera;
                            path = (Environment.getExternalStorageDirectory().getPath() + "/WumpusApp/" + hilera + ".txt");
                        } else {
                            //path = (getFilesDir().getAbsolutePath() + "/" + hilera + ".txt");
                            //h = hilera;
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


