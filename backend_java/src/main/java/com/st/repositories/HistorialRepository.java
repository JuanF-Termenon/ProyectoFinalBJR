package com.st.repositories;

import com.st.database.ConnectionFactory;
import com.st.models.Historial;
import java.sql.*;
import java.util.ArrayList;

public class HistorialRepository {

    public ArrayList<Historial> findByIdIncidencia(int idIncidencia) throws SQLException {
        ArrayList<Historial> lista = new ArrayList<>();
        String sql = "SELECT h.*, u.nombre_visible FROM historial_incidencia h " +
                     "JOIN usuario u ON h.id_usuario = u.id_usuario " +
                     "WHERE h.id_incidencia = ? ORDER BY h.fecha_evento ASC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idIncidencia);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Historial(
                        rs.getInt("id_historial"), rs.getString("accion"),
                        rs.getString("estado_anterior"), rs.getString("estado_nuevo"),
                        rs.getString("comentario"), rs.getTimestamp("fecha_evento"),
                        rs.getInt("id_incidencia"), rs.getInt("id_usuario"),
                        rs.getString("nombre_visible")
                    ));
                }
            }
        }
        return lista;
    }
}
