package com.st.repositories;

import com.st.database.ConnectionFactory;
import com.st.models.Rol;
import java.sql.*;
import java.util.ArrayList;

public class RolRepository {

    public ArrayList<Rol> findAll() throws SQLException {
        ArrayList<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM rol";
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                roles.add(new Rol(rs.getInt("id_rol"), rs.getString("nombre_rol"), rs.getString("descripcion")));
            }
        }
        return roles;
    }
}
