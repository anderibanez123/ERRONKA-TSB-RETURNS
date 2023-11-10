package com.example.tsb_kudeapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLConnection {
    private static final String JDBC_URL = "jdbc:postgresql://10.23.28.188:8068/tsb";
    private static final String JDBC_USER = "tsb";
    private static final String JDBC_PASSWORD = "tsb";

    private Connection connection;

    public PostgreSQLConnection() {
        // Konexioa null bezala hasi
        connection = null;
    }

    // Konexioa irekitzeko funtzioa
    public Connection konexioaIreki()
    {
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

    public List<Registro> erabiltzeDatuakLortu() {
        List<Registro> registros = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            String sql = "SELECT DISTINCT nombre.name, usuario.login , com.name  " +
                    "FROM public.res_company " +
                    "JOIN public.res_users  ON com.id = usuario.company_id " +
                    "JOIN public.res_partner  ON usuario.partner_id = nombre.id " +
                    "WHERE nombre.name NOT IN ('OdooBot', 'Default User Template', 'Portal User Template', 'Public user');";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String erabiltzailea = resultSet.getString(0);
                String email = resultSet.getString(1);
                String enpresa = resultSet.getString(2);

                Registro registro = new Registro(erabiltzailea, email, enpresa);
                registros.add(registro);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return registros;
    }

    public void salmentaDatuakLortu(){



        try {
            Statement statement = connection.createStatement();

            String sql = "SELECT" +
                    "    so.name ," +
                    "    so.invoice_status ," +
                    "    so.state ," +
                    "    partner.name ," +
                    "    company.name ," +
                    "    so.validity_date ," +
                    "    so.amount_untaxed ," +
                    "    so.amount_tax ," +
                    "    so.amount_total ," +
                    "    so.create_date ," +
                    "    so.date_order " +
                    "FROM public.sale_order so" +
                    "LEFT JOIN public.res_partner partner ON so.partner_id = partner.id" +
                    "LEFT JOIN public.res_company company ON so.company_id = company.id;;";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String izena = resultSet.getString(0);
                String faktura = resultSet.getString(1);
                String Estatua = resultSet.getString(2);
                String Cliente = resultSet.getString(3);
                String Enpresa = resultSet.getString(4);
                String Caducidad = resultSet.getString(5);
                String prezio_base = resultSet.getString(6);
                String iva = resultSet.getString(7);
                String Prezio_Finala = resultSet.getString(8);
                String Sortu_data = resultSet.getString(9);
                String FechaPedido = resultSet.getString(10);


            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public void crmDatuakLortu(){



        try {
            Statement statement = connection.createStatement();

            String sql = "SELECT lead.name , lead.type, lead.partner_name , com.name , stage.name , camp.name ," +
                    "sou.name, med.name , sta.name , country.code , lead.phone ," +
                    "lead.email_normalized , lead.contact_name, lead.date_deadline , lead.expected_revenue ," +
                    "lead.prorated_revenue,lead.probability , lead.date_closed , lead.date_open" +
                    "FROM public.crm_lead lead" +
                    "LEFT JOIN public.res_company com ON lead.company_id = com.id" +
                    "LEFT JOIN public.crm_stage stage ON lead.stage_id = stage.id" +
                    "LEFT JOIN public.utm_campaign camp ON lead.campaign_id = camp.id" +
                    "LEFT JOIN public.utm_source sou ON lead.source_id = sou.id" +
                    "LEFT JOIN public.utm_medium med ON lead.medium_id = med.id" +
                    "LEFT JOIN public.res_country_state sta ON lead.state_id = sta.id" +
                    "LEFT JOIN public.res_country country ON lead.country_id = country.id" +
                    "WHERE lead.active = true";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String crm_izena = resultSet.getString(0);
                String mota = resultSet.getString(1);
                String klientea = resultSet.getString(2);
                String enpresa = resultSet.getString(3);
                String etapa = resultSet.getString(4);
                String kanpaina = resultSet.getString(5);
                String sozial_media_hasiera = resultSet.getString(6);
                String sozial_media = resultSet.getString(7);
                String Estatua = resultSet.getString(8);
                String herri_kodea = resultSet.getString(9);
                String telf_zenbakia = resultSet.getString(10);
                String email = resultSet.getString(11);
                String kontaktu_izena = resultSet.getString(12);
                String epemuga = resultSet.getString(13);
                String espero_dirua = resultSet.getString(14);
                String sarrera_proportzionala = resultSet.getString(15);
                String probabilitatea = resultSet.getString(16);
                String itxi_data = resultSet.getString(17);
                String ireki_data = resultSet.getString(18);


            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void probedoreaDatuakLortu(){



        try {
            Statement statement = connection.createStatement();

            String sql = "SELECT r.name, c.code , r.type , r.email , r.phone , r.comment " +
                    "FROM public.res_partner r" +
                    "LEFT JOIN public.res_country c ON r.country_id = c.id" +
                    "WHERE r.commercial_company_name IS NOT NULL AND r.commercial_company_name = 'TSB Enpresa';";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String Izena = resultSet.getString(0);
                String Herria = resultSet.getString(1);
                String Mota = resultSet.getString(2);
                String Korreoa = resultSet.getString(3);
                String Mugikorra = resultSet.getString(4);
                String Komentarioak = resultSet.getString(5);



            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void produktuDatuakLortu(){



        try {
            Statement statement = connection.createStatement();

            String sql = "SELECT default_code , pc.name , detailed_type , list_price , weight ," +
                    "sale_ok , purchase_ok , invoice_policy , description " +
                    "FROM public.product_template pt" +
                    "LEFT JOIN public.product_category pc ON pt.categ_id = pc.id" +
                    "WHERE active = true;";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String izena = resultSet.getString(0);
                String Kategoria = resultSet.getString(1);
                String mota = resultSet.getString(2);
                String prezioa = resultSet.getString(3);
                String pisua = resultSet.getString(4);
                String saldu_ok = resultSet.getString(5);
                String erosi_ok = resultSet.getString(6);
                String faktura_politika = resultSet.getString(7);
                String deskribapena = resultSet.getString(8);




            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void konexioaIrekiAsync(DatabaseCallback callback) {
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
