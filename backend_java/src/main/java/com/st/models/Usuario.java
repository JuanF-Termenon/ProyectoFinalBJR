package com.st.models;

import java.sql.Timestamp;

/*
 * Entidad principal del sistema que representa a los usuarios.
 * Gestiona tanto la identidad (login) como el estado de la cuenta 
 * y su relación jerárquica con los roles.
 */

public class Usuario {

	private int idUsuario;
	private String username;
	private String passwordHash; // Almacena la contraseña
	private String nombreVisible;
	private boolean activo; // Permite deshabilitar el acceso sin borrar el registro
	private boolean primerAcceso; // Flag para obligar al cambio de contraseña inicia
	private Timestamp ultimoLogin;

	// Relación con Rol
	private Rol rol;

	/*
	 * Constructor completo para la reconstrucción de usuarios desde la base de
	 * datos.
	 */
	public Usuario(int idUsuario, String username, String passwordHash, String nombreVisible, boolean activo,
			boolean primerAcceso, Timestamp ultimoLogin, Rol rol) {

		this.idUsuario = idUsuario;
		this.username = username;
		this.passwordHash = passwordHash;
		this.nombreVisible = nombreVisible;
		this.activo = activo;
		this.primerAcceso = primerAcceso;
		this.ultimoLogin = ultimoLogin;
		this.rol = rol;
	}

	// Getters y Setters
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getNombreVisible() {
		return nombreVisible;
	}

	public void setNombreVisible(String nombreVisible) {
		this.nombreVisible = nombreVisible;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean isPrimerAcceso() {
		return primerAcceso;
	}

	public void setPrimerAcceso(boolean primerAcceso) {
		this.primerAcceso = primerAcceso;
	}

	public Timestamp getUltimoLogin() {
		return ultimoLogin;
	}

	public void setUltimoLogin(Timestamp ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return idUsuario + " - " + username + " - " + nombreVisible;
	}
}
