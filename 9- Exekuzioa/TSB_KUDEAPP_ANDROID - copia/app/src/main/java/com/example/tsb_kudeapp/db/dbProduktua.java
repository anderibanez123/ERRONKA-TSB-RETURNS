package com.example.tsb_kudeapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class dbProduktua {

    private SQLite SQLite;
    private Context context;

    public dbProduktua(Context context) {
        this.context = context;
        SQLite = new SQLite(context);

        // Datu berriak sartu aurretik, lehengoko datuak ezabatzen ditugu
        SQLiteDatabase db = SQLite.getWritableDatabase();
        db.delete(SQLite.TABLE_PRODUKTUA, null, null);
    }

    public long ProduktuakSartu(String izena, String kategoria, String mota, String prezioa, String pisua, String saldu_ok, String erosi_ok, String faktura_politika,
                               String deskribapena) {

        SQLiteDatabase db = SQLite.getWritableDatabase();

        ContentValues values = new ContentValues();

        //izena, kategoria, mota, prezioa, pisua, saldu_ok, erosi_ok, faktura_politika, deskribapena

        values.put("izena", izena);
        values.put("kategoria", kategoria);
        values.put("mota", mota);
        values.put("prezioa", prezioa);
        values.put("pisua", pisua);
        values.put("saldu_ok", saldu_ok);
        values.put("erosi_ok", erosi_ok);
        values.put("faktura_politika", faktura_politika);
        values.put("deskribapena", deskribapena);


        long id = db.insert(SQLite.TABLE_PRODUKTUA, null, values);

        db.close();

        return id;
    }

    public void close() {
    }
}
