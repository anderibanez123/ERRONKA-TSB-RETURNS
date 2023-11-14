package com.example.tsb_kudeapp.ui.grafikak;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.sql.Array;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GrafikaFragment extends Fragment {


    private FragmentGrafikoaBinding binding;
    private GrafikaViewModel GrafikaViewModel;

    private PieChart pieChart;

    private TextView textView;

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

        textView = view.findViewById(R.id.chartDescription);

        textView.setTextColor(Color.BLACK);

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

    private BarData getBarData() {
        ArrayList<BarEntry> dataValues = new ArrayList<>();
        ArrayList<String> productNames = new ArrayList<>();

        Cursor cursor = dbHelper.getValuesBarChart();

        // Klientea eta TotalCompras zutabeen indezua lortu
        int izenaIndex = cursor.getColumnIndex("izena");
        int kopuruaIndex = cursor.getColumnIndex("kopurua");

        int index = 0;

        while (cursor.moveToNext()) {
            // Egiaztatu zutabea emaitza-setan existitzen den
            if (izenaIndex != -1 && kopuruaIndex != -1) {
                // Klientea eta TotalCompras balioak hartu
                String izena = cursor.getString(izenaIndex);
                int kopurua = cursor.getInt(kopuruaIndex);

                // Agregar entrada de barra y nombre del producto
                dataValues.add(new BarEntry(index, kopurua));
                productNames.add(izena);

                index++;
            }
        }

        cursor.close();

        // Crear un conjunto de datos de barras
        BarDataSet dataSet = new BarDataSet(dataValues, "Gehien saldutako produktuak");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // Asociar nombres de productos a las barras
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);  // Ajustar el ancho de las barras según tus necesidades

        // Configurar etiquetas en el eje X
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(productNames));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        return barData;
    }

    // BarChart-a konfiguratzeko metodoa
    public void BarraGrafika() {
        // Kolorea aldatzeko background-ekoa
        int colorFondo = Color.WHITE;
        barChart.setBackgroundColor(colorFondo);

        // Estetika
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(colorFondo);

        // Obtener datos de la barra
        BarData barData = getBarData();

        // Aplicar datos a la gráfica de barras
        barChart.setData(barData);

        // Deskribapena ezabatu
        barChart.getDescription().setEnabled(false);
        barChart.animateX(1500);
        barChart.animateY(1500);
        barChart.animate();
        barChart.invalidate();

    }


    // PieChart-a konfiguratzeko metodoa
    public void GrafikoBorobila() {
        // PieChart-a konfiguratu
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setDrawHoleEnabled(false);


        // Kolorea aldatzeko background-ekoa
        int colorFondo = Color.WHITE;
        pieChart.setBackgroundColor(colorFondo);

        // Cursor-etik datuak lortu eta PieChart-a eguneratu
        ArrayList<PieEntry> dataValues = getPieValues();
        pieDataSet.setValues(dataValues);
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);
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
