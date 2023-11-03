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

            String sql = "SELECT DISTINCT nombre.name AS Erabiltzailea, usuario.login AS Email, com.name AS Enpresa " +
                    "FROM public.res_company AS com " +
                    "JOIN public.res_users AS usuario ON com.id = usuario.company_id " +
                    "JOIN public.res_partner AS nombre ON usuario.partner_id = nombre.id " +
                    "WHERE nombre.name NOT IN ('OdooBot', 'Default User Template', 'Portal User Template', 'Public user');";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String erabiltzailea = resultSet.getString("Erabiltzailea");
                String email = resultSet.getString("Email");
                String enpresa = resultSet.getString("Enpresa");

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



}
