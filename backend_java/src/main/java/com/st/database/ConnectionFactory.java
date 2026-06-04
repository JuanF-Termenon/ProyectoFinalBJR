package com.st.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://10.170.210.122:5432/SistemaGestor";
    private static final String USER = "postgres";
    private static final String PASS = "pass";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void setSessionUser(Connection conn, int userId) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("SET st.usuario_activo = " + userId);
        }
    }
}
