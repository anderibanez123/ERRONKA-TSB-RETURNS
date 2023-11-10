package com.example.tsb_kudeapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper {

    // datu basearen bertsioa
    private static final int DATABASE_VERSION = 1;

    // datu basearen izena
    private static final String DATABASE_NAME = "tsbKOM.db";

    // taularen izena
    public static final String TABLE_KOMERTZIALA = "tbl_komertzialak";

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // taula sortu
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_KOMERTZIALA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "erabiltzailea TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "Enpresa TEXT" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
