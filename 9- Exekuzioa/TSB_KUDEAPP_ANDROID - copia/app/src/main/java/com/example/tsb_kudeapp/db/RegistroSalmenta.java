package com.example.tsb_kudeapp.db;

public class RegistroSalmenta {

    private String izena;
    private String faktura;
    private String estatua;
    private String klientea;
    private String enpresa;
    private String iraungitzea;
    private String prezio_base;
    private String bez;
    private String prezio_finala;
    private String sortu_data;
    private String eskaera_data;

    public RegistroSalmenta(String izena, String faktura, String estatua, String klientea, String enpresa, String iraungitzea, String prezio_base, String bez, String prezio_finala,
                            String sortu_data, String eskaera_data) {
        this.izena = izena;
        this.faktura = faktura;
        this.estatua = estatua;
        this.klientea = klientea;
        this.enpresa = enpresa;
        this.iraungitzea = iraungitzea;
        this.prezio_base = prezio_base;
        this.bez = bez;
        this.prezio_finala = prezio_finala;
        this.sortu_data = sortu_data;
        this.eskaera_data = eskaera_data;
    }


    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getFaktura() {
        return faktura;
    }

    public void setFaktura(String faktura) {
        this.faktura = faktura;
    }

    public String getEstatua() {
        return estatua;
    }

    public void setEstatua(String estatua) {
        this.estatua = estatua;
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

    public String getIraungitzea() {
        return iraungitzea;
    }

    public void setIraungitzea(String iraungitzea) {
        this.iraungitzea = iraungitzea;
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

    public String getPrezio_finala() {
        return prezio_finala;
    }

    public void setPrezio_finala(String prezio_finala) {
        this.prezio_finala = prezio_finala;
    }

    public String getSortu_data() {
        return sortu_data;
    }

    public void setSortu_data(String sortu_data) {
        this.sortu_data = sortu_data;
    }

    public String getEskaera_data() {
        return eskaera_data;
    }

    public void setEskaera_data(String eskaera_data) {
        this.eskaera_data = eskaera_data;
    }
}
