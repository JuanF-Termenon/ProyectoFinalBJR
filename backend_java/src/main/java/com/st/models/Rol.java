package com.st.models;

/*
 * Modelo que representa los roles o perfiles de usuario en el sistema.
 * Define los permisos y el tipo de interfaz que se mostrará al hacer login.
 */

public class Rol {

	private int idRol;
	private String nombreRol;
	private String descripcion;

	// Constructor vacío
	public Rol() {
	}

	/*
	 * Constructor completo para asignar datos desde el Repositorio.
	 */
	public Rol(int idRol, String nombreRol, String descripcion) {
		this.idRol = idRol;
		this.nombreRol = nombreRol;
		this.descripcion = descripcion;
	}

	// Getters y Setters
	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return nombreRol;
	}
}