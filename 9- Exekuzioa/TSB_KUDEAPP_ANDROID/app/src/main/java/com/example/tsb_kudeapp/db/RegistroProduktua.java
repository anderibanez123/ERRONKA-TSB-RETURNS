package com.example.tsb_kudeapp.db;

public class RegistroProduktua {

    // izena, kategoria, mota, prezioa, pisua, saldu_ok, erosi_ok, faktura_politika, deskribapena

    private String izena;
    private String kategoria;
    private String mota;
    private String prezioa;
    private String pisua;
    private String saldu_ok;
    private String erosi_ok;
    private String faktura_politika;
    private String deskribapena;


    public RegistroProduktua(String izena, String kategoria, String mota, String prezioa, String pisua, String saldu_ok, String erosi_ok, String faktura_politika, String deskribapena) {

        this.izena = izena;
        this.kategoria = kategoria;
        this.mota = mota;
        this.prezioa = prezioa;
        this.pisua = pisua;
        this.saldu_ok = saldu_ok;
        this.erosi_ok = erosi_ok;
        this.faktura_politika = faktura_politika;
        this.deskribapena = deskribapena;

    }


    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getPrezioa() {
        return prezioa;
    }

    public void setPrezioa(String prezioa) {
        this.prezioa = prezioa;
    }

    public String getPisua() {
        return pisua;
    }

    public void setPisua(String pisua) {
        this.pisua = pisua;
    }

    public String getSaldu_ok() {
        return saldu_ok;
    }

    public void setSaldu_ok(String saldu_ok) {
        this.saldu_ok = saldu_ok;
    }

    public String getErosi_ok() {
        return erosi_ok;
    }

    public void setErosi_ok(String erosi_ok) {
        this.erosi_ok = erosi_ok;
    }

    public String getFaktura_politika() {
        return faktura_politika;
    }

    public void setFaktura_politika(String faktura_politika) {
        this.faktura_politika = faktura_politika;
    }

    public String getDeskribapena() {
        return deskribapena;
    }

    public void setDeskribapena(String deskribapena) {
        this.deskribapena = deskribapena;
    }
}
