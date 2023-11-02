package com.example.tsb_kudeapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class dbUsers {

    private dbHelper dbHelper;
    private Context context;

    public dbUsers(Context context) {
        this.context = context;
        dbHelper = new dbHelper(context);
    }

    public long erabiltzaileakSartu(String erabiltzailea, String email, String enpresa) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("erabiltzailea", erabiltzailea);
        values.put("email", email);
        values.put("Enpresa", enpresa);

        long id = db.insert(dbHelper.TABLE_KOMERTZIALA, null, values);

        db.close();

        return id;
    }

    public void close() {
    }
}
