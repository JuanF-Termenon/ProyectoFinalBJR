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

	private int userId;

	public IncidenciaRepository() {
		this.userId = 0;
	}

	public IncidenciaRepository(int userId) {
		this.userId = userId;
	}

	/*
	 * Registra una nueva incidencia en el sistema. La base de datos asignará
	 * automáticamente ID, estado 'ACTIVA' y fecha_creacion.
	 */
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
			if (userId > 0) ConnectionFactory.setSessionUser(conn, userId);

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
		String sql = "SELECT i.*, p.departamento, u.nombre_visible " +
				"FROM incidencia i " +
				"JOIN puesto p ON i.id_puesto = p.id_puesto " +
				"LEFT JOIN usuario u ON i.id_usuario_creador = u.id_usuario " +
				"ORDER BY i.fecha_creacion DESC";

		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				lista.add(new Incidencia(rs.getInt("id_incidencia"), rs.getString("descripcion"),
						rs.getString("prioridad"), rs.getString("estado"), rs.getTimestamp("fecha_creacion"),
						rs.getTimestamp("fecha_inicio_atencion"), rs.getTimestamp("fecha_resolucion"),
						rs.getInt("id_puesto"), rs.getInt("id_usuario_creador"),
						(Integer) rs.getObject("id_usuario_cierre"),
						rs.getString("departamento"),
						rs.getString("nombre_visible")
				));
			}
		}
		return lista;
	}
}