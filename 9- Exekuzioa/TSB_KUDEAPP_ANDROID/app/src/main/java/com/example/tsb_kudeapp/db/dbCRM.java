package com.example.tsb_kudeapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class dbCRM {

    private SQLite SQLite;
    private Context context;

    public dbCRM(Context context) {
        this.context = context;
        SQLite = new SQLite(context);

        // Datu berriak sartu aurretik, lehengoko datuak ezabatzen ditugu
        SQLiteDatabase db = SQLite.getWritableDatabase();
        db.delete(SQLite.TABLE_CRM, null, null);
    }

    public long CRMSartu(String izena, String mota, String klientea, String enpresa, String etapa, String kanpaina, String iturria, String komunikabidea, String estatua,
                         String herri_kodea, String telf_zenbakia, String email, String kontaktu_izena, String epemuga, String espero_dirua, String sarrera_proportzionala,
                         String probabilitatea, String itxi_data, String ireki_data) {

        SQLiteDatabase db = SQLite.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put("izena", izena);
        values.put("mota", mota);
        values.put("klientea", klientea);
        values.put("enpresa", enpresa);
        values.put("etapa", etapa);
        values.put("kanpaina", kanpaina);
        values.put("iturri", iturria);
        values.put("komunikabidea", komunikabidea);
        values.put("estatua", estatua);
        values.put("herri_kodea", herri_kodea);
        values.put("telf_zenbakia", telf_zenbakia);
        values.put("email", email);
        values.put("kontaktu_izena", kontaktu_izena);
        values.put("epemuga", epemuga);
        values.put("espero_dirua", espero_dirua);
        values.put("sarrera_proportzionala", sarrera_proportzionala);
        values.put("probabilitatea", probabilitatea);
        values.put("itxi_data", itxi_data);
        values.put("ireki_data", ireki_data);


        long id = db.insert(SQLite.TABLE_CRM, null, values);

        db.close();

        return id;
    }

    public void close() {
    }
}
