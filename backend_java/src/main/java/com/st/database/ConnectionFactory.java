package com.st.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Realiza la conexion a la BBDD.
 * Solo esta clase conoce los detalles de conexion.
 */
public class ConnectionFactory {
	// Constantes de configuración
	private static final String URL = "jdbc:postgresql://localhost:5432/SistemaGestor";
	private static final String USER = "postgres";
	private static final String PASS = "pass";

	/*
	 * Establece y devuelve una conexión activa con la base de datos.
	 * 
	 * @return Connection objeto de conexión JDBC.
	 * 
	 * @throws SQLException Si hay un error de red, credenciales incorrectas o el
	 * servidor está offline.
	 */
	public static Connection getConnection() throws SQLException {
		// Utiliza el driver de PostgreSQL
		return DriverManager.getConnection(URL, USER, PASS);
	}
}