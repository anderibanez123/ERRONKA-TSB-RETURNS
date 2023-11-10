package com.example.tsb_kudeapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class dbSalmenta {

    private SQLite SQLite;
    private Context context;

    public dbSalmenta(Context context) {
        this.context = context;
        SQLite = new SQLite(context);

        // Datu berriak sartu aurretik, lehengoko datuak ezabatzen ditugu
        SQLiteDatabase db = SQLite.getWritableDatabase();
        db.delete(SQLite.TABLE_SALMENTA, null, null);
    }

    public long salmentakSartu(String izena, String faktura, String estatua, String klientea, String enpresa, String iraungitzea, String prezio_base, String bez,
                                    String prezio_finala, String sortu_data, String eskaera_data) {

        SQLiteDatabase db = SQLite.getWritableDatabase();

        ContentValues values = new ContentValues();

        // izena, faktura, estatua, klientea, enpresa, iraungitzea, prezio_base, bez, prezio_finala, sortu_data, eskaera_data

        values.put("izena", izena);
        values.put("faktura", faktura);
        values.put("estatua", estatua);
        values.put("klientea", klientea);
        values.put("enpresa", enpresa);
        values.put("iraungitzea", iraungitzea);
        values.put("prezio_base", prezio_base);
        values.put("bez", bez);
        values.put("prezio_finala", prezio_finala);
        values.put("sortu_data", sortu_data);
        values.put("eskaera_data", eskaera_data);


        long id = db.insert(SQLite.TABLE_SALMENTA, null, values);

        db.close();

        return id;
    }

    public void close() {
    }
}
