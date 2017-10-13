package com.example.carlos.wumpusproject.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carlos.wumpusproject.GraphDrawActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 10/11/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

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

    public boolean addData(String name, int origin, int destiny) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Name, name);
        contentValues.put(Origin, origin);
        contentValues.put(Destiny, destiny);

        long result = db.insert(TABLE_NAME, null, contentValues);
        // If data is inserted incorrectly it will return -1

        return result != -1;
    }

    /**
     * Returns all the names from database
     *
     * @return
     */
    public Cursor getNames() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT DISTINCT " + Name + " FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    /**
     * Returns only the ID that matches the name passed in
     *
     * @param graphName
     * @return
     */
    public Cursor getTuples(String graphName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + Name + " = '" + graphName + "'";
        return db.rawQuery(query, null);
    }

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

    public List<String> obtenerNombresDeGrafos(){
        List<String> nombres = new ArrayList<>();
        Cursor cursor = this.getNames();
        while (cursor.moveToNext()) {
            nombres.add(cursor.getString(0) );
        }
        cursor.close();
        return nombres;
    }

    /*
     * Revisar TODO
     */
    public void limpiarAristas(){
        String peticion = "TRUNCATE TABLE " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(peticion);
    }

    /*
     * Revisar TODO
     */
    public void borrarArista(String nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        String peticion = "DELETE FROM " + TABLE_NAME + " WHERE " + Name + " = " + nombre;
        db.execSQL(peticion);
    }

}