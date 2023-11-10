package com.example.tsb_kudeapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class dbHornitzaileak {

    private SQLite SQLite;
    private Context context;

    public dbHornitzaileak(Context context) {
        this.context = context;
        SQLite = new SQLite(context);

        // Datu berriak sartu aurretik, lehengoko datuak ezabatzen ditugu
        SQLiteDatabase db = SQLite.getWritableDatabase();
        db.delete(SQLite.TABLE_HORNITZAILEAK, null, null);
    }

    public long HornitzaileakSartu(String izena, String herria, String mota, String korreoa, String mugikorra, String komentarioak) {

        SQLiteDatabase db = SQLite.getWritableDatabase();

        ContentValues values = new ContentValues();

        // izena, herria, mota, korreoa, mugikorra, komentarioak

        values.put("izena", izena);
        values.put("herria", herria);
        values.put("mota", mota);
        values.put("korreoa", korreoa);
        values.put("mugikorra", mugikorra);
        values.put("komentarioak", komentarioak);


        long id = db.insert(SQLite.TABLE_HORNITZAILEAK, null, values);

        db.close();

        return id;
    }

    public void close() {
    }
}
