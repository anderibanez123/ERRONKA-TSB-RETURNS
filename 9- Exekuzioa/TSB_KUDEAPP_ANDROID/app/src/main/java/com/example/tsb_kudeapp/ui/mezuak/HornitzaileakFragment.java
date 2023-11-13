package com.example.tsb_kudeapp.ui.mezuak;

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
import com.example.tsb_kudeapp.databinding.FragmentHornitzaileakBinding;
import com.example.tsb_kudeapp.db.RegistroHornitzaileak;
import com.example.tsb_kudeapp.db.RegistroProduktua;
import com.example.tsb_kudeapp.db.SQLite;

import java.util.ArrayList;

import kotlin.reflect.KClassesImplKt;


public class HornitzaileakFragment extends Fragment {

    private FragmentHornitzaileakBinding binding;
    private SQLite dbHelper;
    private ListView listViewHornitzaileak;
    private FrameLayout frameHornitzaileak;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_hornitzaileak, container, false);
        dbHelper = new SQLite(getContext());

        // Bistak lortu
        listViewHornitzaileak = root.findViewById(R.id.listViewHornitzaileak);
        frameHornitzaileak = root.findViewById(R.id.frameHornitzaile);

        // listView konfiguratu
        ListViewKonfiguratu();

        return root;
    }

    private void ListViewKonfiguratu() {

        ArrayList<String> hornitzaileakIzena = IzenaHornitzaileakLortu();

        // Adaptadorea jarri hornitzaileentzako
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, hornitzaileakIzena);

        // Adaptadorea asignatu
        listViewHornitzaileak.setAdapter(adapter);


        listViewHornitzaileak.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String hornitzaileIzena = hornitzaileakIzena.get(position);
                InformazioaErakutsi(hornitzaileIzena);
            }
        });
    }

    private ArrayList<String> IzenaHornitzaileakLortu() {
        ArrayList<String> izenakHornitzailea = new ArrayList<>();

        // Sql kontsulta
        String sql = "SELECT izena FROM tbl_hornitzaileak";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {
            int indexIzena = cursor.getColumnIndex("izena");
            if (indexIzena != -1) {
                if (cursor.moveToFirst()) {
                    do {
                        // Hornitzailearen izena lortu eta listara sartu
                        String izenaProduktua = cursor.getString(indexIzena);
                        izenakHornitzailea.add(izenaProduktua);
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            // Kurtsorea eta datu basea itxi
            cursor.close();
            db.close();
        }

        return izenakHornitzailea;
    }

    private void InformazioaErakutsi(String hornitzaileIzena) {
        // Sql kontsulta
        String sql = "SELECT * FROM tbl_hornitzaileak WHERE izena = ?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{hornitzaileIzena});

        if (cursor.moveToFirst()) {
            // Kolumnetara sartu
            int indexIzena = cursor.getColumnIndex("izena");
            int indexHerria = cursor.getColumnIndex("herria");
            int indexMota = cursor.getColumnIndex("mota");
            int indexKorreoa = cursor.getColumnIndex("korreoa");
            int indexMugikorra = cursor.getColumnIndex("mugikorra");
            int indexKomentarioak = cursor.getColumnIndex("komentarioak");



            if (indexIzena != -1 && indexHerria != -1 && indexMota != -1 &&
                    indexKorreoa != -1 && indexMugikorra != -1 && indexKomentarioak != -1) {

                // Kolumnetara sartu
                String izena = cursor.getString(indexIzena);
                String herria = cursor.getString(indexHerria);
                String mota = cursor.getString(indexMota);
                String korreoa = cursor.getString(indexKorreoa);
                String mugikorra = cursor.getString(indexMugikorra);
                String komentarioak= cursor.getString(indexKomentarioak);




                // Objetu bat sortu datuekin
                RegistroHornitzaileak detallesHornitzaileak = new RegistroHornitzaileak(izena,herria,mota,korreoa,mugikorra,komentarioak);

                // Informazioa erakutsi
                InformazioaFrameLayout(detallesHornitzaileak);
            }
        }
        // Kurtsorea itxi eta datu basea
        cursor.close();
        db.close();


    }
    private void InformazioaFrameLayout(RegistroHornitzaileak detallesHornitzaileak) {
        /// TextView lortus
        TextView textViewHerria = getView().findViewById(R.id.textViewHerria);
        TextView textViewMota = getView().findViewById(R.id.textViewMota);
        TextView textViewKorreoa =getView().findViewById(R.id.textViewKorreoa);
        TextView textViewMugikorra = getView().findViewById(R.id.textViewMugikorra);
        TextView textViewKomentarioak = getView().findViewById(R.id.textViewKomentarioak);


        // Baloreak asignatu
        textViewHerria.setText("Herria: " + detallesHornitzaileak.getHerria());
        textViewMota.setText("Mota: " + detallesHornitzaileak.getMota());
        textViewKorreoa.setText("Korreoa: " + detallesHornitzaileak.getKorreoa());
        textViewMugikorra.setText("Mugikorra: " + detallesHornitzaileak.getMugikorra());
        textViewKomentarioak.setText("Komentarioak: " + detallesHornitzaileak.getKomentarioak());


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}