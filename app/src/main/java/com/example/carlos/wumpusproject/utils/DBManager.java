package com.example.carlos.wumpusproject.utils;

/**
 * Created by carlos on 02/10/17.
 */

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.Cursor;
import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBManager {

    private SQLiteDatabase database;
    private DBConnection connection;
    private Context context;

    public DBManager(Context context){
        this.context = context;
    }

    public DBManager openDataBase() throws SQLiteException {
        connection = new DBConnection(context);
        database = connection.getWritableDatabase();
        return this;
    }

    public void close(){
        database.close();
    }

    private void insertData(String name, int origin, int destiny)
            throws SQLiteException{
        ContentValues values = new ContentValues();
        values.put(DBConnection.GraphName, name);
        values.put(DBConnection.Origin, origin);
        values.put(DBConnection.Destiny, destiny);
        database.insert(DBConnection.Aristas, null, values);
    }

    private Cursor readData(){
        String[] columns = new String[]{
                DBConnection.AristaID,
                DBConnection.GraphName,
                DBConnection.Origin,
                DBConnection.Destiny
        };

        Cursor cursor = database.query(DBConnection.Aristas, columns, null, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    private int updateDatabase(String aristaID, String name, int origin, int destiny) {
        ContentValues values = new ContentValues();
        values.put(DBConnection.GraphName, name);
        values.put(DBConnection.Origin, origin);
        values.put(DBConnection.Destiny, destiny);
        return database.update(DBConnection.Aristas, values, DBConnection.AristaID + " = " + aristaID, null);
    }

    private void eraseData(String name){
        database.delete(DBConnection.Aristas, DBConnection.GraphName + " = " + name, null);
    }

    private List<AristaGrafo> selectQuery(String query){
        Cursor cursor = database.rawQuery(query, null);
        List<AristaGrafo> aristas = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                AristaGrafo temp = new AristaGrafo(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4) );
                aristas.add(temp);
            }
        }
        cursor.close();
        database.close();
        return aristas;
    }

    public void insertarGrafo(Grafo grafo, String nombre){
        int n = grafo.getTotalCuevas();
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = k; j < n; j++) {
                if (grafo.hayArista(i, j)){
                    this.insertData(nombre, i, j);
                }
                ++k;
            }
        }
    }

    public Grafo obtenerGrafoDeLibreria(String nombreGrafo){
        Grafo grafo = new Grafo(5);
        String consulta = "SELECT * FROM " + DBConnection.Aristas + " WHERE " + DBConnection.GraphName + " = " + nombreGrafo;
        List<AristaGrafo> listaAristas = this.selectQuery(consulta);
        Iterator<AristaGrafo> it = listaAristas.iterator();
        while (it.hasNext()){
            DBManager.AristaGrafo aristaGrafo = it.next();
            grafo.addArista(aristaGrafo.origen, aristaGrafo.destino);
        }
        return grafo;
    }

    public List<String> obtenerNombresDeGrafos(){
        List<String> nombres = new ArrayList<>();
        String consulta = "SELECT DISTINCT " + DBConnection.GraphName + " FROM " + DBConnection.Aristas;
        Cursor cursor = database.rawQuery(consulta, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                nombres.add(cursor.getString(1) );
            }
        }
        cursor.close();
        database.close();
        return nombres;
    }

    private class AristaGrafo {
        private String nombre;
        private int id;
        private int origen;
        private int destino;

        public AristaGrafo(int id, String nombre, int origen, int destino){
            this.destino = destino;
            this.id = id;
            this.origen = origen;
            this.nombre = nombre;
        }
    }

}

