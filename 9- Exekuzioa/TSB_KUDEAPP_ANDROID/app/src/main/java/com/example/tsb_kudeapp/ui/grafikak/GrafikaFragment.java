package com.example.tsb_kudeapp.ui.grafikak;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grafikoa, container, false);

        // pieChart-a layout-etik lortu
        pieChart = view.findViewById(R.id.pieChart);

        // barChart-a layout-etik lortu
        barChart = view.findViewById(R.id.barChart);

        GrafikoBorobila();

        BarraGrafika();

        return view;
    }

    public void BarraGrafika(){

        // Kolorea aldatzeko background-ekoa
        int colorFondo = Color.rgb(220, 220, 220);
        barChart.setBackgroundColor(colorFondo);

        // Datuak barChart-ean sartu
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(2,2));
        barEntries.add(new BarEntry(3,5));
        barEntries.add(new BarEntry(7,9));
        barEntries.add(new BarEntry(8,10));
        barEntries.add(new BarEntry(4,4));



        BarDataSet barDataSet = new BarDataSet(barEntries,"MAYORES VENTAS");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        // Estetika
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(colorFondo);


        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);

        barChart.getDescription().setEnabled(false);
        barChart.invalidate();





    }
    public void GrafikoBorobila(){

        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setDrawHoleEnabled(false);

        // Kolorea aldatzeko background-ekoa
        int colorFondo = Color.rgb(220, 220, 220);
        pieChart.setBackgroundColor(colorFondo);

        // Datuak pieChart-ean sartu
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(2, "Mario"));
        pieEntries.add(new PieEntry(3, "Ander"));
        pieEntries.add(new PieEntry(6, "Lander"));


        // Datuak estetikoki konfiguratu
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        pieDataSet.setValueTextSize(16f);

        // Data insertatu
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1500);
        pieChart.animate();

        // pieChart-a eguneratzeko
        pieChart.invalidate();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}