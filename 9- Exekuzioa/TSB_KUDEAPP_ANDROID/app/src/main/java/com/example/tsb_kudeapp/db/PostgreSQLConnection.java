package com.example.tsb_kudeapp.db;

import android.os.AsyncTask;

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

    public interface QueryExecutionListener {
        void onQueryExecuted(ResultSet resultSet);
        void onQueryFailed(Exception e);
    }

    public void executeQueryInBackground(final String query, final QueryExecutionListener listener) {
        new AsyncTask<Void, Void, ResultSet>() {

            @Override
            protected ResultSet doInBackground(Void... voids) {
                try {
                    Statement statement = connection.createStatement();
                    return statement.executeQuery(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ResultSet resultSet) {
                if (resultSet != null) {
                    if (listener != null) {
                        listener.onQueryExecuted(resultSet);
                    }
                } else {
                    if (listener != null) {
                        listener.onQueryFailed(new Exception("Query execution failed."));
                    }
                }
            }
        }.execute();
    }






}
