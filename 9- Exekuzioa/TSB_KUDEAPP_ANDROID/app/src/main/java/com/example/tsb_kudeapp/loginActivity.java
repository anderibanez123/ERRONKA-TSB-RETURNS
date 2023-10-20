package com.example.tsb_kudeapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tsb_kudeapp.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class loginActivity extends AppCompatActivity {

    // Login leihoaren parametroak
    private Button bt_sesioaHasi;
    private EditText erabiltzailea_Text;



    // PROGRAMA HASI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_login);

        bt_sesioaHasi = findViewById(R.id.bt_sesioaHasi);
        erabiltzailea_Text = findViewById(R.id.erabiltzailea_ET);

        // KLIK sesioa hasi botoiari
        bt_sesioaHasi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                if (erabiltzailea_Text.length() > 1) {

                    String erabiltzailea = erabiltzailea_Text.getText().toString().toUpperCase();

                    if (erabiltzailea.equals("KOM")){

                        Intent resultIntent = new Intent();
                        finish();

                    }else {
                        Toast.makeText(getApplicationContext(), "Sartutako erabiltzailea ez da egokia.", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "Ez da erabiltzailerik sartu.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }





}
