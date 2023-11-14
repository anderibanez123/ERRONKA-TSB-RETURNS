package com.example.tsb_kudeapp.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
public class dbCompras {

    private SQLite SQLite;

    private Context context;

    public dbCompras(Context context){
        this.context = context;
        SQLite = new SQLite(context);

        // Datu berriak sartu aurretik, lehengoko datuak ezabatzen ditugu
        SQLiteDatabase db = SQLite.getWritableDatabase();
        db.delete(SQLite.TABLE_COMPRAS, null, null);
    }

    public long ComprasSartu(String izena, String estatua, String faktura, String klientea, String enpresa, String prezio_base, String bez, String prezio_totala, String eskera_data, String baimentze_data){

        SQLiteDatabase db = SQLite.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("izena", izena);
        values.put("estatua", estatua);
        values.put("faktura", faktura);
        values.put("klientea", klientea);
        values.put("enpresa", enpresa);
        values.put("prezio_base", prezio_base);
        values.put("bez", bez);
        values.put("prezio_totala", prezio_totala);
        values.put("eskaera_data", eskera_data);
        values.put("baimentze_data", baimentze_data);

        long id = db.insert(SQLite.TABLE_COMPRAS,null, values);
        db.close();

        return id;


    }

}
