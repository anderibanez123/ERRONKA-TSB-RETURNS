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
    public static final String TABLE_SALMENTA = "tbl_salmentak";
    public  static final String TABLE_CRM = "tbl_CRM";
    public static final String TABLE_HORNITZAILEAK = "tbl_hornitzaileak";
    public static final String TABLE_PRODUKTUA = "tbl_produktuak";

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

            // izena, faktura, estatua, klientea, enpresa, iraungitzea, prezio_base, bez, prezio_finala, sortu_data, eskaera_data
            sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_SALMENTA + "(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "izena TEXT, " +
                    "faktura TEXT, " +
                    "estatua TEXT," +
                    "klientea TEXT," +
                    "enpresa TEXT," +
                    "iraungitzea TEXT," +
                    "prezio_base TEXT," +
                    "bez TEXT," +
                    "prezio_finala TEXT, " +
                    "sortu_data TEXT," +
                    "eskaera_data TEXT" +
                    ")");

        // izena, mota, klientea, enpresa, etapa, kanpaina, iturria, komunikabidea, estatua, herri_kodea, telf_zenbakia,
        // email, kontaktu_izena, epemuga, espero_dirua,sarrera_proportzionala, probabilitatea, itxi_data, ireki_data
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CRM + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "izena TEXT," +
                "mota TEXT," +
                "klientea TEXT," +
                "enpresa TEXT," +
                "etapa TEXT," +
                "kanpaina TEXT," +
                "iturri TEXT," +
                "komunikabidea TEXT," +
                "estatua TEXT," +
                "herri_kodea TEXT," +
                "telf_zenbakia TEXT," +
                "email TEXT," +
                "kontaktu_izena TEXT," +
                "epemuga TEXT," +
                "espero_dirua TEXT," +
                "sarrera_proportzionala TEXT," +
                "probabilitatea TEXT," +
                "itxi_data TEXT," +
                "ireki_data TEXT" +
                ")");

        // izena, herria, mota, korreoa, mugikorra, komentarioak
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_HORNITZAILEAK + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "izena TEXT," +
                "herria TEXT," +
                "mota TEXT," +
                "korreoa TEXT," +
                "mugikorra TEXT," +
                "komentarioak TEXT" +
                ")");

        // izena, kategoria, mota, prezioa, pisua, saldu_ok, erosi_ok, faktura_politika, deskribapena
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PRODUKTUA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "izena TEXT," +
                "kategoria TEXT," +
                "mota TEXT," +
                "prezioa TEXT," +
                "pisua TEXT," +
                "saldu_ok TEXT," +
                "erosi_ok TEXT," +
                "faktura_politika TEXT," +
                "deskribapena TEXT" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
