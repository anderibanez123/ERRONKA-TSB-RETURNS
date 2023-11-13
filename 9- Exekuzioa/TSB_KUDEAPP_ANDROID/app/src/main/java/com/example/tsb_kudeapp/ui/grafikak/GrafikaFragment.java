package com.example.tsb_kudeapp.ui.grafikak;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tsb_kudeapp.R;
import com.example.tsb_kudeapp.databinding.FragmentGrafikoaBinding;
import com.example.tsb_kudeapp.db.SQLite;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GrafikaFragment extends Fragment {


    private FragmentGrafikoaBinding binding;
    private GrafikaViewModel GrafikaViewModel;

    private PieChart pieChart;

    private BarChart barChart;

    private SQLite dbHelper;
    private SQLite SQLite;

    private SQLiteDatabase sqLiteDatabase;

    private PieDataSet pieDataSet = new PieDataSet(null, null);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Azpiko diseinu-a kargatu
        View view = inflater.inflate(R.layout.fragment_grafikoa, container, false);

        // SQLite datu-basea inizializatu
        dbHelper = new SQLite(getContext());

        // PieChart elementua layout-etik eskuratzen da
        pieChart = view.findViewById(R.id.pieChart);

        // BarChart elementua layout-etik eskuratzen da
        barChart = view.findViewById(R.id.barChart);

        // SQLiteDatabase objektua lortu
        sqLiteDatabase = dbHelper.getWritableDatabase();

        // Grafikoak sortu
        GrafikoBorobila();

        // BarraGrafika() metodoa deitu
        BarraGrafika();

        return view;
    }

    // PieChart-a betetzeko datuak lortu
    private ArrayList<PieEntry> getPieValues() {
        ArrayList<PieEntry> dataValues = new ArrayList<>();
        Cursor cursor = dbHelper.getValuesPieChart();

        // Klientea eta TotalCompras zutabeen indezua lortu
        int klienteaIndex = cursor.getColumnIndex("klientea");
        int totalComprasIndex = cursor.getColumnIndex("TotalCompras");

        while (cursor.moveToNext()) {
            // Egiaztatu zutabea emaitza-setan existitzen den
            if (klienteaIndex != -1 && totalComprasIndex != -1) {
                // Klientea eta TotalCompras balioak hartu
                String klientea = cursor.getString(klienteaIndex);
                int totalCompras = cursor.getInt(totalComprasIndex);

                // PieEntry objektua sortu eta dataValues ArrayList-era gehitu
                dataValues.add(new PieEntry(totalCompras, klientea));
            }
        }

        // Cursor itxi
        cursor.close();
        return dataValues;
    }

    // BarChart-a konfiguratzeko metodoa
    public void BarraGrafika() {
        // Kolorea aldatzeko background-ekoa
        int colorFondo = Color.rgb(220, 220, 220);
        barChart.setBackgroundColor(colorFondo);

        // Estetika
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(colorFondo);

        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);

        // Deskribapena ezabatu
        barChart.getDescription().setEnabled(false);
        barChart.invalidate();
    }

    // PieChart-a konfiguratzeko metodoa
    public void GrafikoBorobila() {
        // PieChart-a konfiguratu
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setDrawHoleEnabled(false);

        // Kolorea aldatzeko background-ekoa
        int colorFondo = Color.rgb(220, 220, 220);
        pieChart.setBackgroundColor(colorFondo);

        // Cursor-etik datuak lortu eta PieChart-a eguneratu
        ArrayList<PieEntry> dataValues = getPieValues();
        pieDataSet.setValues(dataValues);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        // Deskribapena ezabatu
        pieChart.getDescription().setEnabled(false);

        // Animazioa
        pieChart.animateY(1500);
        pieChart.animate();

        // PieChart-a eguneratzeko
        pieChart.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
