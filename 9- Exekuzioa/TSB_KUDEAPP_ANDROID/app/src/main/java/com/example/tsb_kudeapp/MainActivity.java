package com.example.tsb_kudeapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.tsb_kudeapp.db.PostgreSQLConnection;
import com.example.tsb_kudeapp.db.dbHelper;
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
import java.sql.ResultSet;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity {

    private PostgreSQLConnection pgKonexioa = new PostgreSQLConnection();
    private dbHelper SQLiteKonexioa;

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

        // KLIK DATU BASE BOTOIARI
        binding.appBarMain.dbAktualizatuBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Datuak berritzen...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // SQLiteko datuak aktualizatzeko funtzioa
                datuakAktualizatu();

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

    // Datuak aktualizatzeko funtzioa
    public void datuakAktualizatu(){

        // USUARIOS taulako datuak aktualizatu
        String usuarios_taula = "SELECT DISTINCT nombre.name, usuario.login, com.name FROM public.res_company AS com LEFT JOIN public.res_users AS usuario ON com.id = usuario.company_id\n" +
                "LEFT JOIN public.res_partner AS nombre ON usuario.partner_id = nombre.id WHERE nombre.name NOT IN ('OdooBot', 'Default User Template', 'Portal User Template', 'Public user');";

        erabiltzaileak_kargatu(usuarios_taula, this);

        // VENTAS taulako datuak aktualizatu
        String ventas_taula = "SELECT so.name, so.invoice_status, so.state, partner.name, company.name, so.validity_date so.amount_untaxed, so.amount_tax, so.amount_total,\n" +
                "so.create_date, so.date_order FROM public.sale_order so LEFT JOIN public.res_partner partner ON so.partner_id = partner.id LEFT JOIN public.res_company company ON so.company_id = company.id;";

        salmentak_kargatu(ventas_taula);

        // PROVEEDORES taulako datuak aktualizatu
        String crm_taula = "SELECT lead.name, lead.type, lead.partner_name, com.name, stage.name, camp.name, sou.name, med.name, sta.name, country.code, lead.phone, lead.email_normalized," +
                " lead.contact_name, lead.date_deadline, lead.expected_revenue, lead.prorated_revenue, lead.probability, lead.date_closed, lead.date_open " +
                "FROM public.crm_lead lead\n" +
                "LEFT JOIN public.res_company com ON lead.company_id = com.id\n" +
                "LEFT JOIN public.crm_stage stage ON lead.stage_id = stage.id\n" +
                "LEFT JOIN public.utm_campaign camp ON lead.campaign_id = camp.id\n" +
                "LEFT JOIN public.utm_source sou ON lead.source_id = sou.id\n" +
                "LEFT JOIN public.utm_medium med ON lead.medium_id = med.id\n" +
                "LEFT JOIN public.res_country_state sta ON lead.state_id = sta.id\n" +
                "LEFT JOIN public.res_country country ON lead.country_id = country.id\n" +
                "WHERE lead.active = true";

        crm_kargatu(crm_taula);

        // PROVEEDORES taulako datuak aktualizatu
        String proveedores_taula = "SELECT r.name, c.code, r.type, r.email, r.phone, r.comment\n" +
                "FROM public.res_partner AS r\n" +
                "LEFT JOIN public.res_country AS c ON r.country_id = c.id\n" +
                "WHERE r.commercial_company_name IS NOT NULL AND r.commercial_company_name != 'TSB Enpresa';";

        hornitzaileak_kargatu(proveedores_taula);

        // PROVEEDORES taulako datuak aktualizatu
        String productos_taula = "SELECT default_code, pc.name, detailed_type, list_price, weight, sale_ok, purchase_ok, invoice_policy, description\n" +
                "FROM public.product_template pt\n" +
                "LEFT JOIN public.product_category pc ON pt.categ_id = pc.id\n" +
                "WHERE active = true;";

        produktuak_kargatu(productos_taula);

    }

    public void erabiltzaileak_kargatu(String selectSQL, Context context) {
        // PostgreSQL-ra konexioa ireki
        pgKonexioa.executeQueryInBackground(selectSQL, new PostgreSQLConnection.QueryExecutionListener() {
            @Override
            public void onQueryExecuted(ResultSet resultSet) {
                // Datu baseko konexioa lortu
                SQLiteDatabase sqliteDB = new dbHelper(context).getWritableDatabase();

                try {
                    // Datu zaharrak ezabatzeko
                    sqliteDB.delete("tbl_komertzialak", null, null);

                    // DAtuak lortu
                    while (resultSet.next()) {
                        ContentValues values = new ContentValues();
                        values.put("erabiltzailea", resultSet.getString(1));
                        values.put("email", resultSet.getString(2));
                        values.put("Enpresa", resultSet.getString(3));

                        // Inserta en la tabla erabiltzaileak de SQLite
                        sqliteDB.insert("tbl_komertzialak", null, values);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (java.sql.SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    // Cierra conexiones
                    try {
                        if (resultSet != null) resultSet.close();
                        if (sqliteDB != null) sqliteDB.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (java.sql.SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onQueryFailed(Exception e) {
                e.printStackTrace();
                // Manejar el fallo de la consulta
            }
        });
    }


    public void salmentak_kargatu(String selectSQL){

    }

    public void crm_kargatu(String selectSQL){

    }

    public void hornitzaileak_kargatu(String selectSQL){

    }

    public void produktuak_kargatu(String selectSQL){

    }





}