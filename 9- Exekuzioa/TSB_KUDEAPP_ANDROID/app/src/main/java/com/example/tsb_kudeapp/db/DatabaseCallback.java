package com.example.tsb_kudeapp.db;

import java.sql.Connection;
import java.util.List;

public interface DatabaseCallback {
    void onConnectionEstablished(Connection connection);
    void onConnectionFailed(Exception e);
    void onQueryCompleted(List<Registro> registros);
    void onQueryFailed(Exception e);
}

