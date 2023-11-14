package com.example.tsb_kudeapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQL {
    private static final String JDBC_URL = "jdbc:postgresql://10.23.28.188:8068/tsb";
    private static final String JDBC_USER = "tsb";
    private static final String JDBC_PASSWORD = "tsb";

    private Connection connection;

    public PostgreSQL() {
        // Konexioa null bezala hasi
        connection = null;
    }

    // Konexioa irekitzeko funtzioa
    public Connection konexioaIreki() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);


                }
                catch (Exception e)
                {

                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return connection;
    }

    public void konexioaItxi() {
        try {
            if (connection != null && !connection.isClosed()) {

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RegistroUser> erabiltzeDatuakLortu() {
        List<RegistroUser> registros = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            String sql = "SELECT DISTINCT nombre.name, usuario.login , com.name\n" +
                    "FROM public.res_company com\n" +
                    "JOIN public.res_users usuario ON com.id = usuario.company_id\n" +
                    "JOIN public.res_partner nombre ON usuario.partner_id = nombre.id\n" +
                    "WHERE nombre.name NOT IN ('OdooBot', 'Default User Template', 'Portal User Template', 'Public user');";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String erabiltzailea = resultSet.getString(1);
                String email = resultSet.getString(2);
                String enpresa = resultSet.getString(3);

                RegistroUser registro = new RegistroUser(erabiltzailea, email, enpresa);
                registros.add(registro);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return registros;
    }

    public List<RegistroSalmenta> salmentaDatuakLortu() {

        List<RegistroSalmenta> registros = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            String sql = "SELECT so.name, so.invoice_status, so.state, partner.name, company.name, so.validity_date, so.amount_untaxed, so.amount_tax, so.amount_total, so.create_date, so.date_order\n" +
                    "FROM public.sale_order so\n" +
                    "LEFT JOIN public.res_partner partner ON so.partner_id = partner.id\n" +
                    "LEFT JOIN public.res_company company ON so.company_id = company.id;";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String izena = resultSet.getString(1);
                String faktura = resultSet.getString(2);
                String estatua = resultSet.getString(3);
                String klientea = resultSet.getString(4);
                String enpresa = resultSet.getString(5);
                String iraungitzea = resultSet.getString(6);
                String prezio_base = resultSet.getString(7);
                String bez = resultSet.getString(8);
                String prezio_finala = resultSet.getString(9);
                String sortu_data = resultSet.getString(10);
                String eskaera_data = resultSet.getString(11);

                RegistroSalmenta registro = new RegistroSalmenta(izena, faktura, estatua, klientea, enpresa, iraungitzea, prezio_base, bez, prezio_finala, sortu_data, eskaera_data);
                registros.add(registro);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return registros;
    }

    public List<RegistroCRM> CRMDatuakLortu() {

        List<RegistroCRM> registros = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            String sql = "SELECT lead.name, lead.type, lead.partner_name, com.name, stage.name, camp.name, sou.name, med.name, sta.name,\n" +
                    "country.code, lead.phone, lead.email_normalized, lead.contact_name, lead.date_deadline, lead.expected_revenue,\n" +
                    "lead.prorated_revenue, lead.probability, lead.date_closed, lead.date_open\n" +
                    "FROM public.crm_lead lead\n" +
                    "LEFT JOIN public.res_company com ON lead.company_id = com.id\n" +
                    "LEFT JOIN public.crm_stage stage ON lead.stage_id = stage.id\n" +
                    "LEFT JOIN public.utm_campaign camp ON lead.campaign_id = camp.id\n" +
                    "LEFT JOIN public.utm_source sou ON lead.source_id = sou.id\n" +
                    "LEFT JOIN public.utm_medium med ON lead.medium_id = med.id\n" +
                    "LEFT JOIN public.res_country_state sta ON lead.state_id = sta.id\n" +
                    "LEFT JOIN public.res_country country ON lead.country_id = country.id\n" +
                    "WHERE lead.active = true";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String izena = resultSet.getString(1);
                String mota = resultSet.getString(2);
                String klientea = resultSet.getString(3);
                String enpresa = resultSet.getString(4);
                String etapa = resultSet.getString(5);
                String kanpaina = resultSet.getString(6);
                String iturria = resultSet.getString(7);
                String komunikabidea = resultSet.getString(8);
                String estatua = resultSet.getString(9);
                String herri_kodea = resultSet.getString(10);
                String telf_zenbakia = resultSet.getString(11);
                String email = resultSet.getString(12);
                String kontaktu_izena = resultSet.getString(13);
                String epemuga = resultSet.getString(14);
                String espero_dirua = resultSet.getString(15);
                String sarrera_proportzionala = resultSet.getString(16);
                String probabilitatea = resultSet.getString(17);
                String itxi_data = resultSet.getString(18);
                String ireki_data = resultSet.getString(19);

                RegistroCRM registro = new RegistroCRM(izena, mota, klientea, enpresa, etapa, kanpaina, iturria, komunikabidea, estatua, herri_kodea, telf_zenbakia,
                        email, kontaktu_izena, epemuga, espero_dirua, sarrera_proportzionala, probabilitatea, itxi_data, ireki_data);
                registros.add(registro);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return registros;
    }

    public List<RegistroHornitzaileak> HornitzaileakDatuakLortu() {

        List<RegistroHornitzaileak> registros = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            // izena, herria, mota, korreoa, mugikorra, komentarioak

            String sql = "SELECT r.name, c.code, r.type, r.email, r.phone, r.comment\n" +
                    "FROM public.res_partner AS r\n" +
                    "LEFT JOIN public.res_country AS c ON r.country_id = c.id\n" +
                    "WHERE r.commercial_company_name IS NOT NULL AND r.commercial_company_name != 'TSB Enpresa';";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String izena = resultSet.getString(1);
                String herria = resultSet.getString(2);
                String mota = resultSet.getString(3);
                String korreoa = resultSet.getString(4);
                String mugikorra = resultSet.getString(5);
                String komentarioak = resultSet.getString(6);

                RegistroHornitzaileak registro = new RegistroHornitzaileak(izena, herria, mota, korreoa, mugikorra, komentarioak);
                registros.add(registro);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return registros;
    }

    public List<RegistroProduktua> ProduktuaDatuakLortu() {

        List<RegistroProduktua> registros = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();


            String sql = "SELECT default_code, pc.name, detailed_type, list_price, weight, sale_ok, purchase_ok, invoice_policy, description\n" +
                    "FROM public.product_template pt\n" +
                    "LEFT JOIN public.product_category pc ON pt.categ_id = pc.id\n" +
                    "WHERE active = true;";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String izena = resultSet.getString(1);
                String kategoria = resultSet.getString(2);
                String mota = resultSet.getString(3);
                String prezioa = resultSet.getString(4);
                String pisua = resultSet.getString(5);
                String saldu_ok = resultSet.getString(6);
                String erosi_ok = resultSet.getString(7);
                String faktura_politika = resultSet.getString(8);
                String deskribapena = resultSet.getString(9);

                RegistroProduktua registro = new RegistroProduktua(izena, kategoria, mota, prezioa, pisua, saldu_ok, erosi_ok, faktura_politika, deskribapena);
                registros.add(registro);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return registros;
    }
        public List<RegistroCompras> ComprasDatuakLortu() {

        List<RegistroCompras> registros = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();


            String sql = "SELECT\n" +
                    "    po.name,\n" +
                    "    po.state,\n" +
                    "    po.invoice_status,\n" +
                    "    partner.name,\n" +
                    "    company.name,\n" +
                    "    po.amount_untaxed,\n" +
                    "    po.amount_tax,\n" +
                    "    po.amount_total,\n" +
                    "    po.date_order,\n" +
                    "    po.date_approve\n" +
                    "    FROM public.purchase_order po\n" +
                    "    LEFT JOIN public.res_partner partner ON po.partner_id = partner.id\n" +
                    "    LEFT JOIN public.res_company company ON po.company_id = company.id;";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String izena = resultSet.getString(1);
                String estatua = resultSet.getString(2);
                String faktura = resultSet.getString(3);
                String klientea = resultSet.getString(4);
                String enpresa = resultSet.getString(5);
                String prezio_base = resultSet.getString(6);
                String bez = resultSet.getString(7);
                String prezio_totala = resultSet.getString(8);
                String eskaera_data = resultSet.getString(9);
                String baimentze_data = resultSet.getString(10);

                RegistroCompras registro = new RegistroCompras(izena, estatua, faktura,klientea, enpresa, prezio_base, bez, prezio_totala, eskaera_data, baimentze_data);
                registros.add(registro);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return registros;
    }



    public void konexioaIrekiAsync(DBCallback callback) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("org.postgresql.Driver");
                    connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                    callback.onConnectionEstablished(connection);
                } catch (Exception e) {
                    callback.onConnectionFailed(e);
                }
            }
        });
        thread.start();
    }




}
