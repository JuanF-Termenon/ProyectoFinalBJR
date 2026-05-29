package com.st.repositories;

import com.st.database.ConnectionFactory;
import com.st.models.Incidencia;
import java.sql.*;
import java.util.ArrayList;

public class IncidenciaRepository {

    private int userId;
    private String sqlBase = "SELECT i.*, p.departamento, u.nombre_visible, " +
            "(SELECT u2.nombre_visible FROM asignacion a " +
            "JOIN usuario u2 ON a.id_usuario_st = u2.id_usuario " +
            "WHERE a.id_incidencia = i.id_incidencia " +
            "ORDER BY a.fecha_asignacion DESC LIMIT 1) as nombre_asignado " +
            "FROM incidencia i " +
            "JOIN puesto p ON i.id_puesto = p.id_puesto " +
            "LEFT JOIN usuario u ON i.id_usuario_creador = u.id_usuario ";

    public IncidenciaRepository() { this.userId = 0; }

    public IncidenciaRepository(int userId) { this.userId = userId; }

    public boolean reportarIncidencia(String descripcion, String prioridad, int idPuesto, int idUsuario)
            throws SQLException {
        String sql = "INSERT INTO incidencia (descripcion, prioridad, id_puesto, id_usuario_creador) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (userId > 0) ConnectionFactory.setSessionUser(conn, userId);
            stmt.setString(1, descripcion);
            stmt.setString(2, prioridad.toUpperCase());
            stmt.setInt(3, idPuesto);
            stmt.setInt(4, idUsuario);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean actualizarEstado(int idIncidencia, String nuevoEstado, Integer idUsuarioCierre) throws SQLException {
        String sql = "UPDATE incidencia SET estado = ?, id_usuario_cierre = ? WHERE id_incidencia = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (userId > 0) ConnectionFactory.setSessionUser(conn, userId);
            stmt.setString(1, nuevoEstado.toUpperCase());
            if (idUsuarioCierre != null) stmt.setInt(2, idUsuarioCierre);
            else stmt.setNull(2, java.sql.Types.INTEGER);
            stmt.setInt(3, idIncidencia);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int idIncidencia) throws SQLException {
        String sql = "DELETE FROM incidencia WHERE id_incidencia = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (userId > 0) ConnectionFactory.setSessionUser(conn, userId);
            stmt.setInt(1, idIncidencia);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean actualizarPrioridad(int idIncidencia, String prioridad) throws SQLException {
        String sql = "UPDATE incidencia SET prioridad = ? WHERE id_incidencia = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (userId > 0) ConnectionFactory.setSessionUser(conn, userId);
            stmt.setString(1, prioridad.toUpperCase());
            stmt.setInt(2, idIncidencia);
            return stmt.executeUpdate() > 0;
        }
    }

    public ArrayList<Incidencia> findAll() throws SQLException {
        ArrayList<Incidencia> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlBase + "ORDER BY i.fecha_creacion DESC");
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public ArrayList<Incidencia> findByUsuarioCreador(int idUsuario) throws SQLException {
        ArrayList<Incidencia> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBase + "WHERE i.id_usuario_creador = ? ORDER BY i.fecha_creacion DESC")) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public ArrayList<Incidencia> findByAsignadoA(int idUsuarioSt) throws SQLException {
        ArrayList<Incidencia> lista = new ArrayList<>();
        String sql = sqlBase +
                "WHERE EXISTS (SELECT 1 FROM asignacion a2 WHERE a2.id_incidencia = i.id_incidencia AND a2.id_usuario_st = ?) " +
                "ORDER BY i.fecha_creacion DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuarioSt);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public boolean asignarTecnico(int idIncidencia, int idUsuarioSt) throws SQLException {
        String sql = "INSERT INTO asignacion (id_incidencia, id_usuario_st, tipo_participacion) VALUES (?, ?, 'ASIGNADO')";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (userId > 0) ConnectionFactory.setSessionUser(conn, userId);
            stmt.setInt(1, idIncidencia);
            stmt.setInt(2, idUsuarioSt);
            return stmt.executeUpdate() > 0;
        }
    }

    public ArrayList<String[]> getStatsByTecnico() throws SQLException {
        ArrayList<String[]> lista = new ArrayList<>();
        String sql = "SELECT u.nombre_visible, " +
            "COUNT(*) FILTER (WHERE i.estado IN ('ACTIVA','REABIERTA')) AS activas, " +
            "COUNT(*) FILTER (WHERE i.estado = 'EN_CURSO') AS en_curso, " +
            "COUNT(*) FILTER (WHERE i.estado = 'RESUELTA') AS resueltas, " +
            "COUNT(*) AS total, " +
            "COALESCE(ROUND(AVG(EXTRACT(EPOCH FROM (i.fecha_resolucion - i.fecha_creacion))/3600),1),0) AS media_horas " +
            "FROM usuario u JOIN asignacion a ON u.id_usuario = a.id_usuario_st " +
            "JOIN incidencia i ON a.id_incidencia = i.id_incidencia " +
            "WHERE u.id_rol = 2 GROUP BY u.nombre_visible ORDER BY total DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("nombre_visible"),
                    String.valueOf(rs.getInt("activas")),
                    String.valueOf(rs.getInt("en_curso")),
                    String.valueOf(rs.getInt("resueltas")),
                    String.valueOf(rs.getInt("total")),
                    String.valueOf(rs.getDouble("media_horas"))
                });
            }
        }
        return lista;
    }

    public ArrayList<String[]> getStatsByDepartamento() throws SQLException {
        ArrayList<String[]> lista = new ArrayList<>();
        String sql = "SELECT p.departamento, COUNT(*) AS total, " +
            "COUNT(*) FILTER (WHERE i.estado IN ('ACTIVA','REABIERTA')) AS activas, " +
            "COUNT(*) FILTER (WHERE i.estado = 'EN_CURSO') AS en_curso, " +
            "COUNT(*) FILTER (WHERE i.estado = 'RESUELTA') AS resueltas " +
            "FROM puesto p JOIN incidencia i ON p.id_puesto = i.id_puesto " +
            "GROUP BY p.departamento ORDER BY total DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("departamento"),
                    String.valueOf(rs.getInt("total")),
                    String.valueOf(rs.getInt("activas")),
                    String.valueOf(rs.getInt("en_curso")),
                    String.valueOf(rs.getInt("resueltas"))
                });
            }
        }
        return lista;
    }

    private Incidencia mapear(ResultSet rs) throws SQLException {
        return new Incidencia(
            rs.getInt("id_incidencia"), rs.getString("descripcion"),
            rs.getString("prioridad"), rs.getString("estado"), rs.getTimestamp("fecha_creacion"),
            rs.getTimestamp("fecha_inicio_atencion"), rs.getTimestamp("fecha_resolucion"),
            rs.getInt("id_puesto"), rs.getInt("id_usuario_creador"),
            (Integer) rs.getObject("id_usuario_cierre"),
            rs.getString("departamento"), rs.getString("nombre_visible"),
            rs.getString("nombre_asignado")
        );
    }
}
