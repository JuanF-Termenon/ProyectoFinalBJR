package com.st.repositories;

import com.st.database.ConnectionFactory;

import com.st.models.Usuario;
import com.st.models.Rol;
import java.sql.*;

import java.util.ArrayList;

/**
 * Repositorio central para la gestión de usuarios y seguridad. Implementa la
 * lógica de autenticación y persistencia de credenciales.
 */
public class UsuarioRepository {

	/**
	 * Recupera todos los usuarios con sus respectivos roles mediante un JOIN.
	 * 
	 * @return Lista de usuarios con objetos Rol integrados.
	 */
	public ArrayList<Usuario> findAll() throws SQLException {

		ArrayList<Usuario> usuarios = new ArrayList<>();
		// Consulta SQL con JOIN para evitar múltiples llamadas a la base de datos
		String sql = "SELECT u.*, r.nombre_rol, r.descripcion as rol_desc "
				+ "FROM usuario u JOIN rol r ON u.id_rol = r.id_rol";

		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				// Instanciamos el Rol para componer el objeto Usuario
				Rol rol = new Rol(rs.getInt("id_rol"), rs.getString("nombre_rol"), rs.getString("rol_desc"));
				usuarios.add(new Usuario(rs.getInt("id_usuario"), rs.getString("username"),
						rs.getString("password_hash"), rs.getString("nombre_visible"), rs.getBoolean("activo"),
						rs.getBoolean("primer_acceso"), rs.getTimestamp("ultimo_login"), rol));
			}
		}
		return usuarios;
	}

	/**
     * Valida las credenciales de un usuario para el inicio de sesión.
     * Solo permite el acceso si el usuario existe, la contraseña coincide y está ACTIVO.
     */
	public Usuario login(String username, String passwordHash) throws SQLException {
		// Buscamos el usuario, su rol y verificamos que esté activo
		String sql = "SELECT u.*, r.nombre_rol, r.descripcion as rol_desc " + "FROM usuario u "
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
		return null; // Si las credenciales son incorrectas o el usuario está inactivo
	}

	/**
     * Actualiza la contraseña y desactiva el flag de 'primer_acceso'.
     * @return true si la actualización fue exitosa.
     */
	public boolean actualizarPassword(int idUsuario, String nuevoHash) throws SQLException {
		String sql = "UPDATE usuario SET password_hash = ?, primer_acceso = FALSE WHERE id_usuario = ?";
		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, nuevoHash);
			stmt.setInt(2, idUsuario);
			return stmt.executeUpdate() > 0;
		}
	}
}