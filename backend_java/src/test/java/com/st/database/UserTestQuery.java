package com.st.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserTestQuery {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/SistemaGestor";

    private static final String USER = "postgres";
    private static final String PASSWORD = "pass";

    public static void main(String[] args) {

        String sql = "SELECT id_usuario, username, nombre_visible, password_hash FROM usuario";

        try (
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {

            System.out.println("LISTA DE USUARIOS:");

            while (rs.next()) {

                int id = rs.getInt("id_usuario");
                String username = rs.getString("username");
                String nombre = rs.getString("nombre_visible");
                String pass = rs.getString("password_hash");

                System.out.println(id + " - " + username + " - " + nombre + " - " + pass);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}