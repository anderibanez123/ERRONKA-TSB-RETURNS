package com.example.tsb_kudeapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class dbUsers {

    private SQLite SQLite;
    private Context context;

    public dbUsers(Context context) {
        this.context = context;
        SQLite = new SQLite(context);

        // Datu berriak sartu aurretik, lehengoko datuak ezabatzen ditugu
        SQLiteDatabase db = SQLite.getWritableDatabase();
        db.delete(SQLite.TABLE_KOMERTZIALA, null, null);
    }

    public long erabiltzaileakSartu(String erabiltzailea, String email, String enpresa) {

        SQLiteDatabase db = SQLite.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put("erabiltzailea", erabiltzailea);
        values.put("email", email);
        values.put("Enpresa", enpresa);

        long id = db.insert(SQLite.TABLE_KOMERTZIALA, null, values);

        db.close();

        return id;
    }

    public void close() {
    }
}
