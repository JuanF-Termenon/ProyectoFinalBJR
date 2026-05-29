package com.st.repositories;

import com.st.database.ConnectionFactory;
import com.st.models.Nota;
import java.sql.*;
import java.util.ArrayList;

public class NotaRepository {

    private int userId;

    public NotaRepository() { this.userId = 0; }

    public NotaRepository(int userId) { this.userId = userId; }

    public boolean guardarNota(int idIncidencia, int idUsuario, String contenido) throws SQLException {
        String sql = "INSERT INTO nota_interna (id_incidencia, id_usuario, nota) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (userId > 0) ConnectionFactory.setSessionUser(conn, userId);
            stmt.setInt(1, idIncidencia);
            stmt.setInt(2, idUsuario);
            stmt.setString(3, contenido);
            return stmt.executeUpdate() > 0;
        }
    }

    public ArrayList<Nota> findByIdIncidencia(int idIncidencia) throws SQLException {
        String sql = "SELECT n.*, u.nombre_visible FROM nota_interna n JOIN usuario u ON n.id_usuario = u.id_usuario WHERE n.id_incidencia = ? ORDER BY n.fecha_creacion DESC";
        ArrayList<Nota> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idIncidencia);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Nota n = new Nota(
                        rs.getInt("id_nota"),
                        rs.getInt("id_incidencia"),
                        rs.getInt("id_usuario"),
                        rs.getString("nota"),
                        rs.getTimestamp("fecha_creacion")
                    );
                    n.setNombreUsuario(rs.getString("nombre_visible"));
                    lista.add(n);
                }
            }
        }
        return lista;
    }
}
