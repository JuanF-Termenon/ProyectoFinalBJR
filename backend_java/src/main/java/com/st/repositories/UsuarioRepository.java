package com.st.repositories;

import com.st.database.ConnectionFactory;
import com.st.models.Usuario;
import com.st.models.Rol;
import java.sql.*;
import java.util.ArrayList;

public class UsuarioRepository {

    private int userId;

    public UsuarioRepository() { this.userId = 0; }

    public UsuarioRepository(int userId) { this.userId = userId; }

    public ArrayList<Usuario> findAll() throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, r.nombre_rol, r.descripcion as rol_desc FROM usuario u JOIN rol r ON u.id_rol = r.id_rol";
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Rol rol = new Rol(rs.getInt("id_rol"), rs.getString("nombre_rol"), rs.getString("rol_desc"));
                usuarios.add(new Usuario(rs.getInt("id_usuario"), rs.getString("username"),
                        rs.getString("password_hash"), rs.getString("nombre_visible"), rs.getBoolean("activo"),
                        rs.getBoolean("primer_acceso"), rs.getTimestamp("ultimo_login"), rol));
            }
        }
        return usuarios;
    }

    public Usuario login(String username, String passwordHash) throws SQLException {
        String sql = "SELECT u.*, r.nombre_rol, r.descripcion as rol_desc FROM usuario u "
                + "JOIN rol r ON u.id_rol = r.id_rol "
                + "WHERE u.username = ? AND u.password_hash = ? AND u.activo = TRUE";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Rol rol = new Rol(rs.getInt("id_rol"), rs.getString("nombre_rol"), rs.getString("rol_desc"));
                    return new Usuario(rs.getInt("id_usuario"), rs.getString("username"), rs.getString("password_hash"),
                            rs.getString("nombre_visible"), rs.getBoolean("activo"), rs.getBoolean("primer_acceso"),
                            rs.getTimestamp("ultimo_login"), rol);
                }
            }
        }
        return null;
    }

    public boolean actualizarPassword(int idUsuario, String nuevoHash) throws SQLException {
        String sql = "UPDATE usuario SET password_hash = ?, primer_acceso = FALSE WHERE id_usuario = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (userId > 0) ConnectionFactory.setSessionUser(conn, userId);
            stmt.setString(1, nuevoHash);
            stmt.setInt(2, idUsuario);
            return stmt.executeUpdate() > 0;
        }
    }

    public ArrayList<Usuario> findByRolId(int rolId) throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.*, r.nombre_rol, r.descripcion as rol_desc "
                + "FROM usuario u JOIN rol r ON u.id_rol = r.id_rol "
                + "WHERE u.id_rol = ? AND u.activo = TRUE";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rolId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Rol rol = new Rol(rs.getInt("id_rol"), rs.getString("nombre_rol"), rs.getString("rol_desc"));
                    usuarios.add(new Usuario(rs.getInt("id_usuario"), rs.getString("username"),
                            rs.getString("password_hash"), rs.getString("nombre_visible"), rs.getBoolean("activo"),
                            rs.getBoolean("primer_acceso"), rs.getTimestamp("ultimo_login"), rol));
                }
            }
        }
        return usuarios;
    }

    public boolean actualizarUltimoLogin(int idUsuario) throws SQLException {
        String sql = "UPDATE usuario SET ultimo_login = CURRENT_TIMESTAMP WHERE id_usuario = ?";
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        }
    }
}
