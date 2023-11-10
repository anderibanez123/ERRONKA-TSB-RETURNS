package com.example.tsb_kudeapp.db;

public class RegistroUser {
    private String erabiltzailea;
    private String email;
    private String enpresa;

    public RegistroUser(String erabiltzailea, String email, String enpresa) {
        this.erabiltzailea = erabiltzailea;
        this.email = email;
        this.enpresa = enpresa;
    }



    public String getErabiltzailea() {
        return this.erabiltzailea;
    }

    public String getEmail() {
        return this.email;
    }

    public String getEnpresa() {
        return this.enpresa;
    }


}
