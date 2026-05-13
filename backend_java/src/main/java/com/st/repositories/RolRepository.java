package com.st.repositories;

import com.st.database.ConnectionFactory;
import com.st.models.Rol;
import java.sql.*;
import java.util.ArrayList;

/**
 * Repositorio encargado de gestionar los perfiles de usuario (Roles). Es
 * fundamental para la gestión administrativa y la asignación de permisos.
 */
public class RolRepository {

	/**
	 * Recupera todos los roles definidos en el sistema.
	 * 
	 * @return ArrayList con los objetos Rol (Admin, Técnico, Operario, etc.)
	 * @throws SQLException Si hay un error de acceso a la base de datos.
	 */
	public ArrayList<Rol> findAll() throws SQLException {
		
		ArrayList<Rol> roles = new ArrayList<>();
		
		String sql = "SELECT * FROM rol";
		
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			
			while (rs.next()) {
				// Instanciación de objetos Rol basada en las columnas de la tabla
				roles.add(new Rol(rs.getInt("id_rol"), rs.getString("nombre_rol"), rs.getString("descripcion")));
			}
		}
		return roles;
	}
}
