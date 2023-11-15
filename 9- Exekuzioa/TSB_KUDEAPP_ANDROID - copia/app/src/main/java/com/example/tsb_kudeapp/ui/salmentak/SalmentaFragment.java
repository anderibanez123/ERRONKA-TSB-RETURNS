package com.example.tsb_kudeapp.ui.salmentak;

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
import com.example.tsb_kudeapp.databinding.FragmentSalmentaBinding;
import com.example.tsb_kudeapp.db.RegistroProduktua;
import com.example.tsb_kudeapp.db.RegistroSalmenta;
import com.example.tsb_kudeapp.db.SQLite;

import java.util.ArrayList;

public class SalmentaFragment extends Fragment {

    private FragmentSalmentaBinding binding;
    private ListView listViewSalmentak;
    private FrameLayout frameSalmentak;
    private SQLite dbHelper;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_salmenta, container, false);
        dbHelper = new SQLite(getContext());

        listViewSalmentak = root.findViewById(R.id.listViewSalmentak);
        frameSalmentak = root.findViewById(R.id.frameSalmentak);

        ListViewKonfiguratu();


        return root;
    }

    private void ListViewKonfiguratu() {
        // Salmenta izenak lortzeko
        ArrayList<String> izenakSalmentak = IzenakSalmentakLortu();

        // Adaptadorea konfiguratu
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, izenakSalmentak);

        // Listview-ea adaptadorea konfiguratu
        listViewSalmentak.setAdapter(adapter);


        listViewSalmentak.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String izenaSalmenta = izenakSalmentak.get(position);
                InformazioaErakutsi(izenaSalmenta);
            }
        });
    }
    private ArrayList<String> IzenakSalmentakLortu() {
        ArrayList<String> izenakSalmentak = new ArrayList<>();

        // Sql kontsulta
        String consulta = "SELECT izena FROM tbl_salmentak";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(consulta, null);

        try {
            int indexIzena = cursor.getColumnIndex("izena");
            if (indexIzena != -1){
                if (cursor.moveToFirst()) {
                    do {
                        // Izena lortu eta listara sartu
                        String izenaSalmenta = cursor.getString(indexIzena);
                        izenakSalmentak.add(izenaSalmenta);
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            // Kurtsorea eta datu basea itxi
            cursor.close();
            db.close();
        }

        return izenakSalmentak;
    }

    private void InformazioaErakutsi(String izenaSalmenta) {
        // Sql kontsulta
        String sql = "SELECT * FROM tbl_salmentak WHERE izena = ?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{izenaSalmenta});

        if (cursor.moveToFirst()) {
            // Kolumnetara sartu
            int indexIzena = cursor.getColumnIndex("izena");
            int indexFaktura = cursor.getColumnIndex("faktura");
            int indexEstatua = cursor.getColumnIndex("estatua");
            int indexKlientea = cursor.getColumnIndex("klientea");
            int indexEnpresa = cursor.getColumnIndex("enpresa");
            int indexIraungitzea = cursor.getColumnIndex("iraungitzea");
            int indexPrezioBase = cursor.getColumnIndex("prezio_base");
            int indexBez = cursor.getColumnIndex("bez");
            int indexPrezioFinala = cursor.getColumnIndex("prezio_finala");
            int indexSortuData = cursor.getColumnIndex("sortu_data");
            int indexEskaeraData = cursor.getColumnIndex("eskaera_data");

            // Kolumnak berifikatu
            if (indexFaktura != -1 && indexEstatua != -1 && indexKlientea != -1 &&
                    indexEnpresa != -1 && indexIraungitzea != -1 && indexPrezioBase != -1 &&
                    indexBez != -1 && indexPrezioFinala != -1 && indexSortuData != -1 && indexEskaeraData != -1) {

                // kolumnetara sartu
                String izena = cursor.getString(indexIzena);
                String faktura = cursor.getString(indexFaktura);
                String estatua = cursor.getString(indexEstatua);
                String klientea = cursor.getString(indexKlientea);
                String enpresa = cursor.getString(indexEnpresa);
                String iraungitzea = cursor.getString(indexIraungitzea);
                String prezioBase = cursor.getString(indexPrezioBase);
                String bez = cursor.getString(indexBez);
                String prezioFinala = cursor.getString(indexPrezioFinala);
                String sortuData = cursor.getString(indexSortuData);
                String eskaeraData = cursor.getString(indexEskaeraData);


                // Obejetu bat sortu datuekin
                RegistroSalmenta detallesSalmenta = new RegistroSalmenta(izena,
                        faktura, estatua, klientea, enpresa, iraungitzea, prezioBase,bez, prezioFinala, sortuData,eskaeraData);

                // Datuak frame layoutean erakutsi
                InformazioaFrameLayoutErakutsi(detallesSalmenta);
            }
        }
        // Kurtsorea eta datu basea itxi
        cursor.close();
        db.close();


    }
    private void InformazioaFrameLayoutErakutsi(RegistroSalmenta detallesSalmenta) {
        /// TextView-ak lortu
        TextView textViewFaktura = getView().findViewById(R.id.textViewFaktura);
        TextView textViewEstatua = getView().findViewById(R.id.textViewEstatua);
        TextView textViewKlientea =getView().findViewById(R.id.textViewKlientea);
        TextView textViewEnpresa = getView().findViewById(R.id.textViewEnpresa);
        TextView textViewIraungitzea = getView().findViewById(R.id.textViewIraungitzea);
        TextView textViewPrezioBase = getView().findViewById(R.id.textViewPrezioBase);
        TextView textViewBez = getView().findViewById(R.id.textViewBez);
        TextView textViewPrezioFinala = getView().findViewById(R.id.textViewPrezioFinala);
        TextView textViewSortuData = getView().findViewById(R.id.textViewSortuData);
        TextView textViewEskaeraData = getView().findViewById(R.id.textViewEskaeraData);

        // Baloreak asignatu
        textViewFaktura.setText("Faktura: " + detallesSalmenta.getFaktura());
        textViewEstatua.setText("Estatua: " + detallesSalmenta.getEstatua());
        textViewKlientea.setText("Klientea: " + detallesSalmenta.getKlientea());
        textViewEnpresa.setText("Enpresa: " + detallesSalmenta.getEnpresa());
        textViewIraungitzea.setText("Iraungitzea: " + detallesSalmenta.getIraungitzea());
        textViewPrezioBase.setText("Prezio Base: " + detallesSalmenta.getPrezio_base()+"€");
        textViewBez.setText("Bez: "+ detallesSalmenta.getBez()+"€");
        textViewPrezioFinala.setText("Prezio Finala: " + detallesSalmenta.getPrezio_finala()+"€");
        textViewSortuData.setText("Sortu Data: " + detallesSalmenta.getSortu_data());
        textViewEskaeraData.setText("Eskaera Data: "+ detallesSalmenta.getEskaera_data());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}