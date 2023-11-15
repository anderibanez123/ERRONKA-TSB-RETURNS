package com.example.tsb_kudeapp.db;

public class RegistroCompras {

    private String izena;

    private String estatua;

    private String faktura;

    private String klientea;

    private String enpresa;

    private String prezio_base;

    private String bez;

    private String prezio_totala;

    private String eskera_data;

    private String baimentze_data;

    public RegistroCompras(String izena, String estatua, String faktura, String klientea, String enpresa, String prezio_base, String bez, String prezio_totala, String eskaera_data, String baimentze_data){

        this.izena = izena;
        this.estatua = estatua;
        this.faktura = faktura;
        this.klientea = klientea;
        this.enpresa = enpresa;
        this.prezio_base = prezio_base;
        this.bez = bez;
        this.prezio_totala = prezio_totala;
        this.eskera_data = eskaera_data;
        this.baimentze_data = baimentze_data;


    }

    public String getIzena() {
        return izena;
    }
    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getEstatua() {
        return estatua;
    }

    public void setEstatua(String estatua) {
        this.estatua = estatua;
    }

    public String getFaktura() {
        return faktura;
    }

    public void setFaktura(String faktura) {
        this.faktura = faktura;
    }

    public String getKlientea() {
        return klientea;
    }

    public void setKlientea(String klientea) {
        this.klientea = klientea;
    }

    public String getEnpresa() {
        return enpresa;
    }

    public void setEnpresa(String enpresa) {
        this.enpresa = enpresa;
    }

    public String getPrezio_base() {
        return prezio_base;
    }

    public void setPrezio_base(String prezio_base) {
        this.prezio_base = prezio_base;
    }

    public String getBez() {
        return bez;
    }

    public void setBez(String bez) {
        this.bez = bez;
    }

    public String getPrezio_totala() {
        return prezio_totala;
    }

    public void setPrezio_totala(String prezio_totala) {
        this.prezio_totala = prezio_totala;
    }

    public String getEskera_data() {
        return eskera_data;
    }

    public void setEskera_data(String eskera_data) {
        this.eskera_data = eskera_data;
    }

    public String getBaimentze_data() {
        return baimentze_data;
    }

    public void setBaimentze_data(String baimentze_data) {
        this.baimentze_data = baimentze_data;
    }
}
