package com.example.tsb_kudeapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.tsb_kudeapp.db.PostgreSQL;
import com.example.tsb_kudeapp.db.RegistroCRM;
import com.example.tsb_kudeapp.db.RegistroCompras;
import com.example.tsb_kudeapp.db.RegistroHornitzaileak;
import com.example.tsb_kudeapp.db.RegistroProduktua;
import com.example.tsb_kudeapp.db.RegistroSalmenta;
import com.example.tsb_kudeapp.db.RegistroUser;
import com.example.tsb_kudeapp.db.SQLite;
import com.example.tsb_kudeapp.db.dbCRM;
import com.example.tsb_kudeapp.db.dbCompras;
import com.example.tsb_kudeapp.db.dbHornitzaileak;
import com.example.tsb_kudeapp.db.dbProduktua;
import com.example.tsb_kudeapp.db.dbSalmenta;
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

                            // SALMENTAK HASIERA -->

                            List<RegistroSalmenta> salmentak = konexioa.salmentaDatuakLortu();

                            dbSalmenta salmentaDB = new dbSalmenta(MainActivity.this);

                            for (RegistroSalmenta registro : salmentak){
                                salmentaDB.salmentakSartu(registro.getIzena(), registro.getFaktura(), registro.getEstatua(), registro.getKlientea(), registro.getEnpresa(),
                                        registro.getIraungitzea(), registro.getPrezio_base(), registro.getBez(), registro.getPrezio_finala(), registro.getSortu_data(), registro.getEskaera_data());
                            }

                            // <-- SALMENTAK BUKAERA

                            // CRM HASIERA -->

                            List<RegistroCRM> CRM = konexioa.CRMDatuakLortu();

                            dbCRM crmDB = new dbCRM(MainActivity.this);

                            for (RegistroCRM registro : CRM){
                                crmDB.CRMSartu(registro.getIzena(), registro.getMota(), registro.getKlientea(), registro.getEnpresa(), registro.getEtapa(), registro.getKanpaina(),
                                        registro.getIturria(), registro.getKomunikabidea(), registro.getEstatua(), registro.getHerri_kodea(), registro.getTelf_zenbakia(),
                                        registro.getEmail(), registro.getKontaktu_izena(), registro.getEpemuga(), registro.getEspero_dirua(), registro.getSarrera_proportzionala(),
                                        registro.getProbabilitatea(), registro.getItxi_data(), registro.getIreki_data());
                            }


                            // <-- CRM BUKAERA

                            // HORNITZAILEAK HASIERA -->


                            List<RegistroHornitzaileak> hornitzaileak = konexioa.HornitzaileakDatuakLortu();

                            dbHornitzaileak hornitzaileakDB = new dbHornitzaileak(MainActivity.this);

                            for (RegistroHornitzaileak registro : hornitzaileak){
                                hornitzaileakDB.HornitzaileakSartu(registro.getIzena(), registro.getHerria(), registro.getMota(), registro.getKorreoa(), registro.getMugikorra(),
                                        registro.getKomentarioak());
                            }

                            // <-- HORNITZAILEAK BUKAERA

                            // PRODUKTUA HASIERA -->

                            List<RegistroProduktua> produktuak = konexioa.ProduktuaDatuakLortu();

                            dbProduktua produktuakDB = new dbProduktua(MainActivity.this);

                            for (RegistroProduktua registro : produktuak){
                                produktuakDB.ProduktuakSartu(registro.getIzena(), registro.getKategoria(), registro.getMota(), registro.getPrezioa(), registro.getPisua(),
                                        registro.getSaldu_ok(), registro.getErosi_ok(), registro.getFaktura_politika(), registro.getDeskribapena());
                            }

                            // <-- PRODUKTUA BUKAERA

                            // COMPRAS HASIERA -->

                            List<RegistroCompras> compras = konexioa.ComprasDatuakLortu();

                            dbCompras comprasDB = new dbCompras(MainActivity.this);

                            for(RegistroCompras registro : compras){

                                comprasDB.ComprasSartu(registro.getIzena(),registro.getEstatua(),registro.getFaktura(),registro.getKlientea(),
                                        registro.getEnpresa(),registro.getPrezio_base(),registro.getBez(),registro.getPrezio_totala(),registro.getEskera_data(),registro.getBaimentze_data());

                            }
                             // <-- COMPRAS BUKAERA

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