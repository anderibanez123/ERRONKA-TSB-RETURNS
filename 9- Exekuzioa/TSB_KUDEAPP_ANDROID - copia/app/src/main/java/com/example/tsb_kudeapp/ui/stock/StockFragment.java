package com.example.tsb_kudeapp.ui.stock;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tsb_kudeapp.R;
import com.example.tsb_kudeapp.databinding.FragmentStockBinding;
import com.example.tsb_kudeapp.db.RegistroProduktua;
import com.example.tsb_kudeapp.db.RegistroSalmenta;
import com.example.tsb_kudeapp.db.SQLite;

import java.util.ArrayList;
import java.util.List;

public class StockFragment extends Fragment {

    private FragmentStockBinding binding;
    private SQLite dbHelper;
    private ListView listViewProducts;
    private FrameLayout frameStockOrders;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stock, container, false);
        dbHelper = new SQLite(getContext());

        // Bistak lortu
        listViewProducts = root.findViewById(R.id.listViewProducts);
        frameStockOrders = root.findViewById(R.id.frameStockOrders);

        // listView konfiguratu
        ListViewKonfiguratu();

        return root;
    }

    private void ListViewKonfiguratu() {

        ArrayList<String> produktuakIzena = IzenaProduktuakLortu();

        // Adaptadorea jarri produktuentzako
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, produktuakIzena);

        // Adaptadorea asignatu
        listViewProducts.setAdapter(adapter);


        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String produktuIzena = produktuakIzena.get(position);
                InformazioaErakutsi(produktuIzena);
            }
        });
    }

    private ArrayList<String> IzenaProduktuakLortu() {
        ArrayList<String> izenakProduktua = new ArrayList<>();

        // Sql kontsulta
        String sql = "SELECT izena FROM tbl_produktuak";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {
            int indexIzena = cursor.getColumnIndex("izena");
            if (indexIzena != -1) {
                if (cursor.moveToFirst()) {
                    do {
                        // Produktuaren izena lortu eta listara sartu
                        String izenaProduktua = cursor.getString(indexIzena);
                        izenakProduktua.add(izenaProduktua);
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            // Kurtsorea eta datu basea itxi
            cursor.close();
            db.close();
        }

        return izenakProduktua;
    }

    private void InformazioaErakutsi(String produktuIzena) {
        // Sql kontsulta
        String sql = "SELECT * FROM tbl_produktuak WHERE izena = ?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{produktuIzena});

        if (cursor.moveToFirst()) {
            // Kolumnetara sartu
            int indexIzena = cursor.getColumnIndex("izena");
            int indexKategoria = cursor.getColumnIndex("kategoria");
            int indexMota = cursor.getColumnIndex("mota");
            int indexPrezioa = cursor.getColumnIndex("prezioa");
            int indexPisua = cursor.getColumnIndex("pisua");
            int indexSalduOk = cursor.getColumnIndex("saldu_ok");
            int indexErosiOk = cursor.getColumnIndex("erosi_ok");
            int indexFakturaPolitika = cursor.getColumnIndex("faktura_politika");
            int indexDeskribapena = cursor.getColumnIndex("deskribapena");


            if (indexKategoria != -1 && indexMota != -1 && indexPrezioa != -1 &&
                    indexPisua != -1 && indexSalduOk != -1 && indexErosiOk != -1 &&
                    indexDeskribapena != -1) {

                // Kolumnetara sartu
                String izena = cursor.getString(indexIzena);
                String kategoria = cursor.getString(indexKategoria);
                String mota = cursor.getString(indexMota);
                String prezioa = cursor.getString(indexPrezioa);
                String pisua = cursor.getString(indexPisua);
                String salduOk = cursor.getString(indexSalduOk);
                String erosiOk = cursor.getString(indexErosiOk);
                String faktura_politika = cursor.getString(indexFakturaPolitika);
                String deskribapena = cursor.getString(indexDeskribapena);


                // Objetu bat sortu datuekin
                RegistroProduktua detallesProducto = new RegistroProduktua(izena,
                        kategoria, mota, prezioa, pisua, salduOk, erosiOk,faktura_politika, deskribapena);

                // Informazioa erakutsi
                InformazioaFrameLayout(detallesProducto);
            }
        }
            // Kurtsorea itxi eta datu basea
            cursor.close();
            db.close();


    }
    private void InformazioaFrameLayout(RegistroProduktua detallesProducto) {
        /// TextView lortus
        TextView textViewKategoria = getView().findViewById(R.id.textViewKategoria);
        TextView textViewMota = getView().findViewById(R.id.textViewMota);
        TextView textViewPrezioa =getView().findViewById(R.id.textViewPrezioa);
        TextView textViewPisua = getView().findViewById(R.id.textViewPisua);
        TextView textViewSalduOk = getView().findViewById(R.id.textViewSalduOk);
        TextView textViewErosiOk = getView().findViewById(R.id.textViewErosiOk);
        TextView textViewFP = getView().findViewById(R.id.textViewFP);
        TextView textViewDeskribapena = getView().findViewById(R.id.textViewDeskribapena);

        // Baloreak asignatu
        textViewKategoria.setText("Kategoria: " + detallesProducto.getKategoria());
        textViewMota.setText("Mota: " + detallesProducto.getMota());
        textViewPrezioa.setText("Prezioa: " + detallesProducto.getPrezioa()+"€");
        textViewPisua.setText("Pisua: " + detallesProducto.getPisua()+"KG");
        textViewSalduOk.setText("Saldu Ok: " + detallesProducto.getSaldu_ok());
        textViewErosiOk.setText("Erosi Ok: " + detallesProducto.getErosi_ok());
        textViewFP.setText("Faktura Politika: "+ detallesProducto.getFaktura_politika());
        textViewDeskribapena.setText("Deskribapena: " + detallesProducto.getDeskribapena());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}