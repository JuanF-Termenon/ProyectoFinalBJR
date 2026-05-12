package com.st.models;

/*
 * Modelo que representa la ubicación física o terminal desde la que se trabaja.
 * Permite la trazabilidad de las incidencias vinculando usuarios con equipos.
 */

public class Puesto {
	private int idPuesto;
	private int idUsuario; // FK: Usuario operario asignado a este puesto
	private String codigoPuesto;
	private String departamento;
	private boolean activo;

	public Puesto(int idPuesto, int idUsuario, String codigoPuesto, String departamento, boolean activo) {
		this.idPuesto = idPuesto;
		this.idUsuario = idUsuario;
		this.codigoPuesto = codigoPuesto;
		this.departamento = departamento;
		this.activo = activo;
	}

	// Getters y Setters
	public int getIdPuesto() {
		return idPuesto;
	}

	public void setIdPuesto(int idPuesto) {
		this.idPuesto = idPuesto;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getCodigoPuesto() {
		return codigoPuesto;
	}

	public void setCodigoPuesto(String codigoPuesto) {
		this.codigoPuesto = codigoPuesto;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
