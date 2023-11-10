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

    public long salmentakSartu(String izena, String faktura, String estatua, String caducidad, String prezio_base, String iva, String Prezio_Finala, String Sortu_Data, String FechaPedido){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("izena",izena);
        values.put("faktura",faktura);
        values.put("Estatua",estatua);
        values.put("Caducidad",caducidad);
        values.put("prezio_base",prezio_base);
        values.put("iva",iva);
        values.put("Prezio_Finala",Prezio_Finala);
        values.put("Sortu_data",Sortu_Data);
        values.put("FechaPedido",FechaPedido);

        long id = db.insert(dbHelper.TABLE_SALMENTAK, null, values);

        db.close();

        return id;
    }

    public long crmSartu(String crm_izena, String mota, String klientea, String enpresa, String etapa, String kanpaina, String sozial_media_hasiera, String sozial_media, String Estatua, String herri_kodea, String telf_zenbakia, String email, String kontaktu_izena, String epemuga, String espero_dirua, String sarrera_proportzionala, String probabilitatea, String itxi_data, String ireki_data) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("crm_izena",crm_izena);
        values.put("mota",mota);
        values.put("klientea",klientea);
        values.put("enpresa",enpresa);
        values.put("etapa",etapa);
        values.put("kanpaina",kanpaina);
        values.put("sozial_media_hasiera",sozial_media_hasiera);
        values.put("sozial_media",sozial_media);
        values.put("Estatua",Estatua);
        values.put("herri_kodea",herri_kodea);
        values.put("telf_zenbakia",telf_zenbakia);
        values.put("email",email);
        values.put("kontaktu_izena",kontaktu_izena);
        values.put("epemuga",epemuga);
        values.put("espero_dirua",espero_dirua);
        values.put("sarrera_proportzionala",sarrera_proportzionala);
        values.put("probabilitatea",probabilitatea);
        values.put("itxi_data",itxi_data);
        values.put("ireki_data",ireki_data);


        long id = db.insert(dbHelper.TABLE_CRM, null, values);

        db.close();

        return id;
    }

    public long probedoreakSartu(String Izena, String Herria, String Mota, String Korreoa, String Mugikorra, String Komentarioak) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("Izena",Izena);
        values.put("Herria",Herria);
        values.put("Mota",Mota);
        values.put("Korreoa",Korreoa);
        values.put("Mugikorra",Mugikorra);
        values.put("Komentarioak",Komentarioak);


        long id = db.insert(dbHelper.TABLE_PROBEDOREAK, null, values);

        db.close();

        return id;
    }

    public long produktuakSartu(String izena, String Kategoria, String Mota, String prezioa, String pisua, String saldu_ok, String erosi_ok, String faktura_politika, String deskribapena) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("izena",izena);
        values.put("Kategoria",Kategoria);
        values.put("Mota",Mota);
        values.put("prezioa",prezioa);
        values.put("pisua",pisua);
        values.put("saldu_ok",saldu_ok);
        values.put("erosi_ok",erosi_ok);
        values.put("faktura_politika",faktura_politika);
        values.put("deskribapena",deskribapena);


        long id = db.insert(dbHelper.TABLE_PRODUKTUAK, null, values);

        db.close();

        return id;
    }

    public void close() {
    }
}
