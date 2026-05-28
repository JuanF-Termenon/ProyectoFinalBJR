package com.st.repositories;

import com.st.database.ConnectionFactory;
import com.st.models.Puesto;
import java.sql.*;
import java.util.ArrayList;

/**
 * Repositorio para gestionar la ubicación técnica de los usuarios. Su función
 * principal es vincular al operario con su entorno físico de trabajo.
 */
public class PuestoRepository {

	public ArrayList<String> findAllDepartamentos() throws SQLException {
		ArrayList<String> deptos = new ArrayList<>();
		String sql = "SELECT DISTINCT departamento FROM puesto WHERE activo = TRUE ORDER BY departamento";
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				deptos.add(rs.getString("departamento"));
			}
		}
		return deptos;
	}

	public ArrayList<Puesto> findAll() throws SQLException {
		ArrayList<Puesto> puestos = new ArrayList<>();
		String sql = "SELECT * FROM puesto WHERE activo = TRUE";
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				puestos.add(new Puesto(rs.getInt("id_puesto"), rs.getInt("id_usuario"),
						rs.getString("codigo_puesto"), rs.getString("departamento"), rs.getBoolean("activo")));
			}
		}
		return puestos;
	}

	public Puesto findByUsuarioId(int idUsuario) throws SQLException {
		// Consulta filtrada por el ID de usuario para obtener su terminal asignada
		String sql = "SELECT * FROM puesto WHERE id_usuario = ?";

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, idUsuario);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Puesto(rs.getInt("id_puesto"), rs.getInt("id_usuario"), rs.getString("codigo_puesto"),
							rs.getString("departamento"), rs.getBoolean("activo"));
				}
			}
		}
		return null; // El usuario no tiene un puesto asignado
	}
}