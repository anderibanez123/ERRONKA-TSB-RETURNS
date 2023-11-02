package com.example.tsb_kudeapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.tsb_kudeapp.db.PostgreSQLConnection;
import com.example.tsb_kudeapp.db.PostgreSQLData;
import com.example.tsb_kudeapp.db.Registro;
import com.example.tsb_kudeapp.db.dbHelper;
import com.example.tsb_kudeapp.db.dbUsers;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tsb_kudeapp.databinding.ActivityMainBinding;

import java.sql.Connection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public PostgreSQLConnection konexioa = new PostgreSQLConnection();

    // APPeko MENUAren aukerak
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    // PROGRAMA HASI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SESIOAREN LEIHOA IREKI
        // En lugar de startActivityForResult
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        // KLIK DATU BASE BOTOIARI
        binding.appBarMain.dbAktualizatuBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Datuak berritzen...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // Obtener una conexión a PostgreSQL
                Connection connection = konexioa.konexioaIreki();

                if (connection != null) {
                    // PostgreSQL-ko datuak lortu
                    List<Registro> erabiltzaileak = konexioa.erabiltzeDatuakLortu();

                    // dbUser sortu
                    dbUsers usersDB = new dbUsers(MainActivity.this);

                    // Datuak datu base barruan sartu
                    for (Registro registro : erabiltzaileak) {
                        usersDB.erabiltzaileakSartu(registro.getErabiltzailea(), registro.getEmail(), registro.getEnpresa());
                    }

                    // Konexioa itxi
                    konexioa.konexioaItxi();

                } else {
                    // Datuak ezin baditugu berritu
                    Toast.makeText(MainActivity.this, "Ezin izan ditugu datuak eguneratu", Toast.LENGTH_LONG).show();
                }

            }

        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_salmenta, R.id.nav_stock, R.id.nav_crm, R.id.nav_mezua, R.id.nav_grafika)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }





}