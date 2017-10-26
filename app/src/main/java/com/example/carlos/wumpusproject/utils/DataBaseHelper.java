package com.example.carlos.wumpusproject.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.carlos.wumpusproject.GraphDrawActivity;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja la conexion con la base de datos.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    /** Nombres de columnas de la tabla. */
    private static final String TABLE_NAME = "Aristas";
    private static final String ID = "ID";
    private static final String Name = "GraphName";
    private static final String Origin = "Origin";
    private static final String Destiny = "Destiny";

    public DataBaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Name + " TEXT, " + Origin + " INTEGER, " + Destiny + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Agrega elementos a la base de datos.
     *
     * @return true si se finalizó con exito; false en caso conrario.
     */
    public boolean addData(String name, int origin, int destiny) {
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
     *
     * @return Un cursor que apunta a el conjunto de tuplas asociadas a los nombres que pertenecen
     * a la base de datos.
     */
    public Cursor getNames() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT DISTINCT " + Name + " FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    /**
     * Obtiene todas las aristas asociados a un grafo particular.
     *
     * @param graphName: El nombre del grafo por consultar.
     * @return Un cursor que apunta a la tabla asociado al grafo consultado.
     */
    public Cursor getTuples(String graphName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + Name + " = '" + graphName + "'";
        return db.rawQuery(query, null);
    }

    /**
     * Inserta un grafo a la base de datos.
     *
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
     *
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
     * Vacia la tabla.
     */
    public void limpiarAristas(){
        String peticion = "TRUNCATE TABLE " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(peticion);
    }

    /**
     * Borra un elemento de la base de datos.
     */
    public void borrarArista(String nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        String peticion = "DELETE FROM " + TABLE_NAME + " WHERE " + Name + " = " + nombre;
        db.execSQL(peticion);
    }

    /**
     * Escribe el archivo a almacenamiento interno, de forma privada, es decir solo es visible para
     * esta aplicacion.
     */
    public void grafoComoArchivo(String nombre, Context context) {
        Cursor cursor = this.getTuples(nombre);
        try {
            FileOutputStream stream =  context.openFileOutput(nombre, Context.MODE_PRIVATE);
            while ( cursor.moveToNext() ){
                String hilera = cursor.getInt(2) + "-" + cursor.getInt(3) + "\n";
                stream.write( hilera.getBytes() );
            }
            stream.close();
        }catch (FileNotFoundException e){
            System.out.println("File not found");
        }catch (IOException e){
            System.out.println("IO exception");
        }
    }

    /**
     * Recrea un grafo representado como archivo.
     * @param nombre El nombre con el que se va a guardar el grafo.
     */
    public void leerGrafoComoArchivo(String nombre, Context context){
        try {
            FileInputStream stream = context.openFileInput(nombre);
            String hilera = "";
            int nodos = GraphDrawActivity.numeroFilas / 2;
            nodos = nodos*nodos;
            Grafo grafo = new Grafo(nodos);
            Integer num1 = 0, num2;
            while (stream.available() > 0){
                char a = (char) stream.read();
                switch (a){
                    case 32: // Espacio en blanco
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

            /*
             * Se hace pues la ultima tupla no se agrego, ya que
             * no habia un cambio de linea despues de ella
             */
            num2 = Integer.parseInt(hilera);
            grafo.addArista(num1, num2);
            stream.close();
            this.insertarGrafo(grafo, nombre);
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        } catch (IOException e){
            System.out.println("IO exception");
        }
    }
}