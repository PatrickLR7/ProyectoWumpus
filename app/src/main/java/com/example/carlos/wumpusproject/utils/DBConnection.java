package com.example.carlos.wumpusproject.utils;

/**
 * Created by Carlos on 02/10/2017.
 */
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBConnection extends SQLiteOpenHelper {

    public static final String DataBase = "grafos";
    public static final String Aristas = "aristas";
    public static final String GraphName = "graphName";
    public static final String Origin = "originDot";
    public static final String Destiny = "destinyDot";

    public DBConnection(Context context) {
        super(context, DataBase, null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + Aristas);
        this.onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Aristas + " (" +
                GraphName + " TEXT PRIMARY KEY, " +
                Origin + " INTEGER, " +
                Destiny + " INTEGER)" );
    }

}
