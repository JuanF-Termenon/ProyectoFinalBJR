package com.st.repositories;

import com.st.database.ConnectionFactory;
import com.st.models.Informe;
import java.sql.*;

public class InformeRepository {

    private int userId;

    public InformeRepository() { this.userId = 0; }

    public InformeRepository(int userId) { this.userId = userId; }

    public boolean guardarInforme(int idIncidencia, int idTecnico, String contenido) throws SQLException {
        String sql = "INSERT INTO informe_resolucion (id_incidencia, id_usuario_st, informe) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (userId > 0) ConnectionFactory.setSessionUser(conn, userId);
            stmt.setInt(1, idIncidencia);
            stmt.setInt(2, idTecnico);
            stmt.setString(3, contenido);
            return stmt.executeUpdate() > 0;
        }
    }

    public Informe findByIdIncidencia(int idIncidencia) throws SQLException {
        String sql = "SELECT * FROM informe_resolucion WHERE id_incidencia = ?";
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idIncidencia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Informe(
                        rs.getInt("id_informe"),
                        rs.getInt("id_incidencia"),
                        rs.getInt("id_usuario_st"),
                        rs.getString("informe"),
                        rs.getTimestamp("fecha_creacion")
                    );
                }
            }
        }
        return null;
    }
}
