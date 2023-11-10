package com.example.tsb_kudeapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {

    // datu basearen bertsioa
    private static final int DATABASE_VERSION = 1;

    // datu basearen izena
    private static final String DATABASE_NAME = "tsbKOM.db";

    // taularen izena
    public static final String TABLE_KOMERTZIALA = "tbl_komertzialak";

    public static final String TABLE_SALMENTAK = "tbl_salmentak";

    public static final String TABLE_CRM = "tbl_crm";

    public static final String TABLE_PROBEDOREAK = "tbl_probedoreak";

    public static final String TABLE_PRODUKTUAK = "tbl_produktuak";

    public dbHelper(Context context) {
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

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_SALMENTAK+ "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "izena TEXT , " +
                "faktura TEXT , " +
                "Estatua TEXT ," +
                "Caducidad TEXT ,"+
                "prezio_base TEXT ," +
                "iva TEXT ," +
                "Prezio_Finala TEXT ," +
                "Sortu_data TEXT ," +
                "FechaPedido TEXT "+
                ")");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_CRM+ "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "crm_izena TEXT , " +
                "mota TEXT , " +
                "klientea TEXT ," +
                "enpresa TEXT ,"+
                "etapa TEXT ," +
                "kanpaina TEXT," +
                "sozial_media_hasiera TEXT ," +
                "sozial_media TEXT ," +
                "Estatua TEXT ," +
                "herri_kodea TEXT ," +
                "telf_zenbakia TEXT ," +
                "email TEXT ," +
                "kontaktu_izena TEXT ," +
                "epemuga TEXT ," +
                "espero_dirua TEXT," +
                "sarrera_proportzionala TEXT," +
                "probabilitatea TEXT," +
                "itxi_data TEXT," +
                "ireki_data TEXT"+
                ")");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_PROBEDOREAK+ "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Izena TEXT , " +
                "Herria TEXT , " +
                "Mota TEXT ," +
                "Korreoa TEXT ,"+
                "Mugikorra TEXT ," +
                "Komentarioak TEXT " +
                ")");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_PRODUKTUAK+ "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "izena TEXT , " +
                "Kategoria TEXT , " +
                "mota TEXT ," +
                "prezioa TEXT ,"+
                "pisua TEXT ," +
                "saldu_ok TEXT," +
                "erosi_ok TEXT," +
                "faktura_politika TEXT," +
                "deskribapena TEXT " +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
