package com.example.tsb_kudeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.tsb_kudeapp.db.PostgreSQL;
import com.example.tsb_kudeapp.db.RegistroUser;
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
import com.example.tsb_kudeapp.db.DBCallback;


public class MainActivity extends AppCompatActivity {

    private PostgreSQL konexioa = new PostgreSQL();

    // APPeko MENUAren aukerak
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;



    // PROGRAMA HASI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // SESIOAREN LEIHOA IREKI
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        // DATU BASE BOTOIA
        binding.appBarMain.dbAktualizatuBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Datuak berritzen...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // PostgreSQL-ekin konexioa ezarri
                konexioa.konexioaIrekiAsync(new DBCallback() {
                    @Override
                    public void onConnectionEstablished(Connection connection) {
                        if (connection != null) {
                            // ERABILTZAILEAK HASIERA -->

                            // PostgreSQL-ko datuak lortu - Erabiltzaileak
                            List<RegistroUser> erabiltzaileak = konexioa.erabiltzeDatuakLortu();


                            // dbUsers objetua sortu
                            dbUsers usersDB = new dbUsers(MainActivity.this);

                            // Datuak datu base barruan sartu
                            for (RegistroUser registro : erabiltzaileak) {
                                usersDB.erabiltzaileakSartu(registro.getErabiltzailea(), registro.getEmail(), registro.getEnpresa());
                                
                            }

                            // <-- ERABILTZAILEAK BUKAERA

                            // Konexioa itxi
                            konexioa.konexioaItxi();

                        } else {
                            // Datuak ezin baditugu berritu
                            runOnUiThread(() -> {
                                Toast.makeText(MainActivity.this, "Ezin izan ditugu datuak eguneratu", Toast.LENGTH_LONG).show();
                            });
                        }
                    }

                    @Override
                    public void onConnectionFailed(Exception e) {
                        // Manejar la falla en la conexión
                        e.printStackTrace();
                    }

                    @Override
                    public void onQueryCompleted(List<RegistroUser> registros) {
                        // Manejar la finalización de la consulta aquí
                        Toast.makeText(MainActivity.this, "Ondo", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onQueryFailed(Exception e) {
                        // Manejar la falla en la consulta aquí
                        Toast.makeText(MainActivity.this, "Ezin izan ditugu datuak eguneratu", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Aplikazioaren menuaren konfigurazioa
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