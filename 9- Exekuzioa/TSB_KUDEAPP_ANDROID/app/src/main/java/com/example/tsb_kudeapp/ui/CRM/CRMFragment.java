package com.example.tsb_kudeapp.ui.CRM;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import com.example.tsb_kudeapp.databinding.FragmentCrmBinding;
import com.example.tsb_kudeapp.db.RegistroCRM;
import com.example.tsb_kudeapp.db.RegistroProduktua;
import com.example.tsb_kudeapp.db.SQLite;

import java.util.ArrayList;

public class CRMFragment extends Fragment {

    private FragmentCrmBinding binding;
    private SQLite dbHelper;
    private ListView listViewCRM;
    private FrameLayout frameCRM;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_crm, container, false);
        dbHelper = new SQLite(getContext());

        // Bistak lortu
        listViewCRM = root.findViewById(R.id.listViewCRM);
        frameCRM = root.findViewById(R.id.frameCRM);

        // listView konfiguratu
        ListViewKonfiguratu();

        return root;
    }

    private void ListViewKonfiguratu() {

        ArrayList<String> CrmIzena = CRMIzenakLortu();

        // Adaptadorea jarri Crm-arentzako
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, CrmIzena);

        // Adaptadorea asignatu
        listViewCRM.setAdapter(adapter);


        listViewCRM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String crmIzena = CrmIzena.get(position);
                InformazioaErakutsi(crmIzena);
            }
        });
    }

    private ArrayList<String> CRMIzenakLortu() {
        ArrayList<String> izenakCRM = new ArrayList<>();

        // Sql kontsulta
        String sql = "SELECT izena FROM tbl_CRM";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        try {
            int indexIzena = cursor.getColumnIndex("izena");
            if (indexIzena != -1) {
                if (cursor.moveToFirst()) {
                    do {
                        // Produktuaren izena lortu eta listara sartu
                        String izenaCRM = cursor.getString(indexIzena);
                        izenakCRM.add(izenaCRM);
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            // Kurtsorea eta datu basea itxi
            cursor.close();
            db.close();
        }

        return izenakCRM;
    }

    private void InformazioaErakutsi(String produktuIzena) {
        // Sql kontsulta
        String sql = "SELECT * FROM tbl_CRM WHERE izena = ?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{produktuIzena});

        if (cursor.moveToFirst()) {
            // Kolumnetara sartu
            int indexIzena = cursor.getColumnIndex("izena");
            int indexMota = cursor.getColumnIndex("mota");
            int indexKlientea = cursor.getColumnIndex("klientea");
            int indexEnpresa = cursor.getColumnIndex("enpresa");
            int indexEtapa = cursor.getColumnIndex("etapa");
            int indexKanpaina= cursor.getColumnIndex("kanpaina");
            int indexIturri = cursor.getColumnIndex("iturri");
            int indexKomunikabidea = cursor.getColumnIndex("komunikabidea");
            int indexEstatua = cursor.getColumnIndex("estatua");
            int indexHerriKodea = cursor.getColumnIndex("herri_kodea");
            int indexTelfZenbakia = cursor.getColumnIndex("telf_zenbakia");
            int indexEmail = cursor.getColumnIndex("email");
            int indexKontaktuIzena = cursor.getColumnIndex("kontaktu_izena");
            int indexEpemuga = cursor.getColumnIndex("epemuga");
            int indexEsperoDirua = cursor.getColumnIndex("espero_dirua");
            int indexSarreraProportzionala = cursor.getColumnIndex("sarrera_proportzionala");
            int indexProbabilitatea = cursor.getColumnIndex("probabilitatea");
            int indexItxiData = cursor.getColumnIndex("itxi_data");
            int indexIrekiData = cursor.getColumnIndex("ireki_data");




            if (indexIzena != -1 && indexMota != -1 && indexKlientea != -1 && indexEnpresa != -1 && indexEtapa != -1 &&
            indexKanpaina != -1 && indexIturri != -1 && indexKomunikabidea !=-1 && indexEstatua != -1 &&
            indexHerriKodea != -1 && indexTelfZenbakia != -1 && indexEmail != -1 && indexKontaktuIzena != -1 &&
            indexEpemuga != -1 && indexEsperoDirua != -1 && indexSarreraProportzionala != -1 && indexProbabilitatea != -1 &&
            indexItxiData != -1 && indexIrekiData != -1) {

                // Kolumnetara sartu
                String izena = cursor.getString(indexIzena);
                String mota = cursor.getString(indexMota);
                String klientea = cursor.getString(indexKlientea);
                String enpresa = cursor.getString(indexEnpresa);
                String etapa = cursor.getString(indexEtapa);
                String kanpaina = cursor.getString(indexKanpaina);
                String iturri = cursor.getString(indexIturri);
                String komunikabidea = cursor.getString(indexKomunikabidea);
                String estatua = cursor.getString(indexEstatua);
                String herriKodea = cursor.getString(indexHerriKodea);
                String telfZenbakia = cursor.getString(indexTelfZenbakia);
                String email = cursor.getString(indexEmail);
                String kontaktuIzena = cursor.getString(indexKontaktuIzena);
                String epemuga = cursor.getString(indexEpemuga);
                String esperoDirua = cursor.getString(indexEsperoDirua);
                String sarreraProportzionala = cursor.getString(indexSarreraProportzionala);
                String probabilitatea = cursor.getString(indexProbabilitatea);
                String itxiData = cursor.getString(indexItxiData);
                String irekiData = cursor.getString(indexIrekiData);





                // Objetu bat sortu datuekin
                RegistroCRM detallesCRM = new RegistroCRM(izena,mota,klientea,enpresa,etapa,kanpaina,iturri,komunikabidea,estatua,herriKodea,telfZenbakia,email,kontaktuIzena,epemuga,esperoDirua,sarreraProportzionala,probabilitatea,itxiData,irekiData);

                // Informazioa erakutsi
                InformazioaFrameLayout(detallesCRM);
            }
        }
        // Kurtsorea itxi eta datu basea
        cursor.close();
        db.close();


    }
    private void InformazioaFrameLayout(RegistroCRM detallesCRM) {
        /// TextView lortu
        TextView textViewMota = getView().findViewById(R.id.textViewMota);
        TextView textViewKlientea = getView().findViewById(R.id.textViewKlientea);
        TextView textViewEnpresa = getView().findViewById(R.id.textViewEnpresa);
        TextView textViewEtapa = getView().findViewById(R.id.textViewEtapa);
        TextView textViewKanpaina = getView().findViewById(R.id.textViewKanpaina);
        TextView textViewIturri = getView().findViewById(R.id.textViewIturri);
        TextView textViewKomunikabidea = getView().findViewById(R.id.textViewKomunikabidea);
        TextView textViewEstatua = getView().findViewById(R.id.textViewEstatua);
        TextView textViewHerriKodea = getView().findViewById(R.id.textViewHerriKodea);
        TextView textViewTelfZenbakia = getView().findViewById(R.id.textViewTelfZenbakia);
        TextView textViewEmail = getView().findViewById(R.id.textViewEmail);
        TextView textViewKontaktuIzena = getView().findViewById(R.id.textViewKontaktuIzena);
        TextView textViewEpemuga = getView().findViewById(R.id.textViewEpemuga);
        TextView textViewEsperoDirua = getView().findViewById(R.id.textViewEsperoDirua);
        TextView textViewSarreraProportzionala = getView().findViewById(R.id.textViewSarreraProportzionala);
        TextView textViewProbabilitatea = getView().findViewById(R.id.textViewProbabilitatea);
        TextView textViewItxiData = getView().findViewById(R.id.textViewItxiData);
        TextView textViewIrekiData = getView().findViewById(R.id.textViewIrekiData);



        // Baloreak asignatu
        textViewMota.setText("Mota: " + detallesCRM.getMota());
        textViewKlientea.setText("Klientea: " + detallesCRM.getKlientea());
        textViewEnpresa.setText("Enpresa: " + detallesCRM.getEnpresa());
        textViewEtapa.setText("Etapa: " + detallesCRM.getEtapa());
        textViewKanpaina.setText("Kanpaina: " + detallesCRM.getKanpaina());
        textViewIturri.setText("Iturria: " + detallesCRM.getIturria());
        textViewKomunikabidea.setText("Komunikabidea: "+ detallesCRM.getKomunikabidea());
        textViewEstatua.setText("Estatua: " + detallesCRM.getEstatua());
        textViewHerriKodea.setText("Herri Kodea: " + detallesCRM.getHerri_kodea());
        textViewTelfZenbakia.setText("Telf Zenbakia: " + detallesCRM.getTelf_zenbakia());
        textViewEmail.setText("Email: " + detallesCRM.getEmail());
        textViewKontaktuIzena.setText("Kontaktu Izena: " + detallesCRM.getKontaktu_izena());
        textViewEpemuga.setText("Epemuga: " + detallesCRM.getEpemuga());
        textViewEsperoDirua.setText("Espero Dirua: " + detallesCRM.getEspero_dirua()+"€");
        textViewSarreraProportzionala.setText("Sarrera Proportzionala: " + detallesCRM.getSarrera_proportzionala());
        textViewProbabilitatea.setText("Probabilitatea: "+"%"+ detallesCRM.getProbabilitatea());
        textViewItxiData.setText("Itxi Data: " + detallesCRM.getItxi_data());
        textViewIrekiData.setText("Ireki Data: " + detallesCRM.getIreki_data());


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}