package com.example.tsb_kudeapp.db;

import java.sql.Connection;
import java.util.List;

public interface DBCallback {
    void onConnectionEstablished(Connection connection);
    void onConnectionFailed(Exception e);
    void onQueryCompleted(List<RegistroUser> registros);
    void onQueryFailed(Exception e);
}

