package com.example.tsb_kudeapp.db;

public class RegistroHornitzaileak {

    private String izena;
    private String herria;
    private String mota;
    private String korreoa;
    private String mugikorra;
    private String komentarioak;


    public RegistroHornitzaileak(String izena, String herria, String mota, String korreoa, String mugikorra, String komentarioak) {

        this.izena = izena;
        this.herria = herria;
        this.mota = mota;
        this.korreoa = korreoa;
        this.mugikorra = mugikorra;
        this.komentarioak = komentarioak;

    }


    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getHerria() {
        return herria;
    }

    public void setHerria(String herria) {
        this.herria = herria;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getKorreoa() {
        return korreoa;
    }

    public void setKorreoa(String korreoa) {
        this.korreoa = korreoa;
    }

    public String getMugikorra() {
        return mugikorra;
    }

    public void setMugikorra(String mugikorra) {
        this.mugikorra = mugikorra;
    }

    public String getKomentarioak() {
        return komentarioak;
    }

    public void setKomentarioak(String komentarioak) {
        this.komentarioak = komentarioak;
    }
}
