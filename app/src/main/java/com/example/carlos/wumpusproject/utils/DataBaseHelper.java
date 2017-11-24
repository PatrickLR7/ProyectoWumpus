package com.example.carlos.wumpusproject.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import com.example.carlos.wumpusproject.activity.GraphDrawActivity;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja la conexion con la base de datos.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    /** Nombres de columnas de la tabla. Representan el esquema de la tabla.*/
    private static final String TABLE_NAME = "Aristas";
    private static final String ID = "ID";
    private static final String Name = "GraphName";
    private static final String Origin = "Origin";
    private static final String Destiny = "Destiny";

    /**
     * Constructor de la clase.
     * @param context: Contexto desde el que es creada esta clase.
     */
    public DataBaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    /**
     * Metodo onCreate.
     * @param db Instancia de la base de datos
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Name + " TEXT, " + Origin + " INTEGER, " + Destiny + " INTEGER)";
        db.execSQL(createTable);
    }

    /**
     * Método llamado para recrear la base de datos.
     * @param db: instancia de la base de datos asociada a esta aplicacion
     * @param i:
     * @param i1:
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Metodo que permite la inserción de aristas sobre cierta instancia de un grafo.
     * @param name: Nombre del grafo por insertar
     * @param origin: nodo origen
     * @param destiny: nodo destino
     * @return true si los datos se agregaron correctamente, false caso contrario.
     */
    private boolean addData(String name, int origin, int destiny) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Name, name);
        contentValues.put(Origin, origin);
        contentValues.put(Destiny, destiny);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    /**
     * Returns all the names from database
     * @return Un cursor que apunta a el conjunto de tuplas asociadas a los nombres que pertenecen
     * a la base de datos.
     */
    private Cursor getNames() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT DISTINCT " + Name + " FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    /**
     * Obtiene todas las aristas asociados a un grafo particular.
     * @param graphName: El nombre del grafo por consultar.
     * @return Un cursor que apunta a la tabla asociado al grafo consultado.
     */
    private Cursor getTuples(String graphName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + Name + " = '" + graphName + "'";
        return db.rawQuery(query, null);
    }

    /**
     * Inserta un grafo a la base de datos.
     * @param grafo El grafo que se va a insertar
     * @param nombre El nombre con el que se va a guardar.
     */
    public void insertarGrafo(Grafo grafo, String nombre){
        int n = grafo.getDimensionMatriz();
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = k; j < n; j++) {
                if (grafo.hayArista(i, j)){
                    this.addData(nombre, i, j);
                }
            }
            ++k;
        }
    }

    /**
     * Se obtiene un grafo guardado en la base de datos.
     * @param nombreGrafo El nombre del grafo que se quiere obtener.
     * @return El grafo guardado con el nombre especificado.
     */
    public Grafo obtenerGrafoDeLibreria(String nombreGrafo){
        int nodos = GraphDrawActivity.numeroFilas / 2;
        nodos = nodos*nodos;
        Grafo grafo = new Grafo(nodos);
        Cursor cursor = this.getTuples(nombreGrafo);
        while (cursor.moveToNext()){
            grafo.addArista( cursor.getInt(2), cursor.getInt(3) );
        }
        return grafo;
    }

    /**
     * Se obtiene una lista con todos los nombres de los grafos guardados en la base de datos.
     * @return Lista de los nombres de grafos.
     */
    public List<String> obtenerNombresDeGrafos(){
        List<String> nombres = new ArrayList<>();
        Cursor cursor = this.getNames();
        while (cursor.moveToNext()) {
            nombres.add(cursor.getString(0) );
        }
        cursor.close();
        return nombres;
    }

    /**
     * Metodo que permite crear un archivo a partir de los datos asociados a un grafo y lo guarda
     * en el almacenamiento externo del dispositivo.
     * @param nombre: Cumple dos roles, el nombre del grafo guardado en la base de datos
     *              y también el nombre que se le asigna al archivo creado.
     */
    public void grafoComoArchivo(String nombre) {
        Cursor cursor = this.getTuples(nombre);
        File almacenamiento = Environment.getExternalStorageDirectory();
        File directorio = new File(almacenamiento.getAbsolutePath() + "/WumpusApp/");
        directorio.mkdirs();
        File file = new File(directorio, nombre + ".txt");

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream stream = new FileOutputStream(file);
            while (cursor.moveToNext()) {
                String hilera = cursor.getInt(2) + "-" + cursor.getInt(3) + "\n";
                stream.write( hilera.getBytes() );
            }
            stream.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO exception");
        }
    }

    /**
     * Recrea un grafo representado como archivo.
     * @param nombre El nombre del archivo asociado al grafo en almacenamiento externo.
     */
    public void leerArchivoComoGrafo(String nombre) {
        File almacenamiento = Environment.getExternalStorageDirectory();
        File directorio = new File(almacenamiento.getAbsolutePath() + "/bluetooth/");
        File file = new File(directorio, nombre+".txt");
        try {
            FileInputStream stream = new FileInputStream(file);
            String hilera = "";
            int nodos = GraphDrawActivity.numeroFilas / 2;
            nodos = nodos*nodos;
            Grafo grafo = new Grafo(nodos);
            Integer num1 = 0, num2;
            while (stream.available() > 0){
                char a = (char) stream.read();
                switch (a){
                    case 10: // Cambio de linea
                        num2 = Integer.parseInt(hilera);
                        grafo.addArista(num1, num2);
                        hilera = "";
                        break;
                    case 45: // Guion que separa numeros
                        num1 = Integer.parseInt(hilera);
                        hilera = "";
                        break;
                    default: // Digito numerico
                        hilera += a;
                        break;
                }
            }

            //Se hace pues la ultima tupla no se agrego, ya que no habia un cambio de linea despues de ella.
            stream.close();
            this.insertarGrafo(grafo, nombre);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO exception");
        }
    }
}