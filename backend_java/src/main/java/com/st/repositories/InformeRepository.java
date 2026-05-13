package com.st.repositories;

import com.st.database.ConnectionFactory;
import java.sql.*;

/**
 * Repositorio para la gestión de informes de resolución. Esencial para
 * registrar la actividad de los técnicos y permitir el cierre de tickets.
 */
public class InformeRepository {
	public boolean guardarInforme(int idIncidencia, int idTecnico, String contenido) throws SQLException {
		String sql = "INSERT INTO informe_resolucion (id_incidencia, id_usuario_st, informe) VALUES (?, ?, ?)";

		try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, idIncidencia);
			stmt.setInt(2, idTecnico);
			stmt.setString(3, contenido);

			// Si el INSERT funciona, devuelve true
			return stmt.executeUpdate() > 0;
		}
	}
}