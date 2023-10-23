package com.example.tsb_kudeapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_IZENA = "tsbKOM.db";

    // Lan egiteko behar dugun taula
    public static final String TABLE_KOMERTZIALA = "t_komertzialak";


    public dbHelper(@Nullable Context context) {
        super(context, DATABASE_IZENA, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // SQLite barrun taulak sortzeko
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_KOMERTZIALA + "(" + "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "izena TEXT NOT NULL," + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
