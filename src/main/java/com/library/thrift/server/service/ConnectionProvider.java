package com.library.thrift.server.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionProvider {
    private static ConnectionProvider instance = new ConnectionProvider();

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/library?autoReconnect=true&useSSL=false", "root", "root");
    }

    public static ConnectionProvider getInstance() {
        return instance;
    }

    private ConnectionProvider() {
    }
}
