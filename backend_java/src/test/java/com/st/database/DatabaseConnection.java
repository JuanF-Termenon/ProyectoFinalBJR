package com.st.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Datos conexión PostgreSQL
    private static final String URL =
            "jdbc:postgresql://localhost:5432/SistemaGestor";

    private static final String USER =
            "postgres";

    private static final String PASSWORD =
            "pass";

    public static void main(String[] args) {

        try {

            Connection connection =
                    DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Conexión exitosa a PostgreSQL");

            connection.close();

        } catch (SQLException e) {

            System.out.println("Error de conexión");

            e.printStackTrace();
        }
    }
}
