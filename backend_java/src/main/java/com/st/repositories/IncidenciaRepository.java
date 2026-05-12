package com.st.repositories;

import com.st.database.ConnectionFactory;
import com.st.models.Incidencia;
import java.sql.*;
import java.util.ArrayList;

/*
 * Repositorio encargado de la persistencia de las incidencias.
 * Implementa la lógica necesaria para el ciclo de vida de un ticket de soporte.
 */
public class IncidenciaRepository {

	/*
	 * Registra una nueva incidencia en el sistema. La base de datos asignará
	 * automáticamente ID, estado 'ACTIVA' y fecha_creacion.
	 */
	public boolean reportarIncidencia(String descripcion, String prioridad, int idPuesto, int idUsuario)
			throws SQLException {
		String sql = "INSERT INTO incidencia (descripcion, prioridad, id_puesto, id_usuario_creador) VALUES (?, ?, ?, ?)";

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, descripcion);
			stmt.setString(2, prioridad.toUpperCase()); // Aseguramos MAYÚSCULAS para el CHECK del SQL
			stmt.setInt(3, idPuesto);
			stmt.setInt(4, idUsuario);

			return stmt.executeUpdate() > 0;
		}
	}

	/*
	 * Actualiza el estado de una incidencia y registra el usuario que realiza el
	 * cierre.
	 * 
	 * @param idUsuarioCierre Puede ser null si el estado no implica el cierre de la
	 * incidencia.
	 */
	public boolean actualizarEstado(int idIncidencia, String nuevoEstado, Integer idUsuarioCierre) throws SQLException {
		// Si se cierra, guardamos quién la cerró, si no, solo el estado
		String sql = "UPDATE incidencia SET estado = ?, id_usuario_cierre = ? WHERE id_incidencia = ?";

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, nuevoEstado.toUpperCase());
			if (idUsuarioCierre != null) {
				stmt.setInt(2, idUsuarioCierre);
			} else {
				stmt.setNull(2, java.sql.Types.INTEGER);
			}
			stmt.setInt(3, idIncidencia);

			return stmt.executeUpdate() > 0;
		}
	}

	/**
	 * Recupera el listado completo de incidencias ordenado por las más recientes.
	 * 
	 * @return ArrayList de objetos Incidencia mapeados desde la DB.
	 */
	public ArrayList<Incidencia> findAll() throws SQLException {
		ArrayList<Incidencia> lista = new ArrayList<>();
		String sql = "SELECT * FROM incidencia ORDER BY fecha_creacion DESC";

		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				lista.add(new Incidencia(rs.getInt("id_incidencia"), rs.getString("descripcion"),
						rs.getString("prioridad"), rs.getString("estado"), rs.getTimestamp("fecha_creacion"),
						rs.getTimestamp("fecha_inicio_atencion"), rs.getTimestamp("fecha_resolucion"),
						rs.getInt("id_puesto"), rs.getInt("id_usuario_creador"),
						(Integer) rs.getObject("id_usuario_cierre") // Maneja el null correctamente
				));
			}
		}
		return lista;
	}
}