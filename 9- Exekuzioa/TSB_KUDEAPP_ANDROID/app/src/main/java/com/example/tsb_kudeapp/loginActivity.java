package com.example.tsb_kudeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

/** @noinspection ALL*/
public class loginActivity extends AppCompatActivity {

    private Button bt_sesioaHasi;
    private EditText erabiltzailea_Text;
    private EditText pasahitza_Text;
    private ImageButton huella_BT;

    private long backPressedTime;

    private FingerprintManager fingerprintManager;
    private CancellationSignal cancellationSignal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.nav_login);

        bt_sesioaHasi = findViewById(R.id.bt_sesioaHasi);
        erabiltzailea_Text = findViewById(R.id.erabiltzailea_ET);
        pasahitza_Text = findViewById(R.id.password_ET);
        huella_BT = findViewById(R.id.huella_BT);


        fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        if (!fingerprintManager.isHardwareDetected()) {
            // Mugikorrak ez du hatz marka baimentzen
            huella_BT.setEnabled(false);
        }

        huella_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "SARTU ZURE HATZ MARKA", Snackbar.LENGTH_LONG).show();
                startFingerprintAuthentication();
            }
        });

        bt_sesioaHasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (erabiltzailea_Text.length() > 1) {

                    String erabiltzailea = erabiltzailea_Text.getText().toString().toUpperCase();
                    String pasahitza = pasahitza_Text.getText().toString().toUpperCase();

                    if (erabiltzailea.equals("USER") & pasahitza.equals("KOM")) {
                        // Mezuak irakutsi
                        Toast.makeText(loginActivity.this, "ONGI ETORRI / BIENVENIDO", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(loginActivity.this, "Erabiltzaile edo pasahitz ez zuzena.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(loginActivity.this, "Erabiltzailea utzik dago.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public void onBackPressed() {
        // Denbora lortu
        long currentTime = System.currentTimeMillis();

        // Denbora + 2000 barruan atzera sakatzen badiozu, app-a itxiko da.
        if (backPressedTime + 2000 > currentTime) {
            finishAffinity();
            super.onBackPressed();

        } else {
            // Lehenengo pasadan mezua irakutsi
            Toast.makeText(this, "Berriro sakatu irteteko", Toast.LENGTH_SHORT).show();
        }

        // Denbora aktualizatu
        backPressedTime = currentTime;
    }


    private void startFingerprintAuthentication() {
        if (cancellationSignal == null) {
            cancellationSignal = new CancellationSignal();

            fingerprintManager.authenticate(null, cancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);

                    // Bihetza ondo hartuta
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(loginActivity.this, "ONGI ETORRI / BIENVENIDO", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }

                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Toast.makeText(loginActivity.this, "Ezin izan dugu sesioa hasi.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Toast.makeText(loginActivity.this, "Ezin izan dugu sesioa hasi.", Toast.LENGTH_SHORT).show();
                }
            }, null);
        }
    }

}
