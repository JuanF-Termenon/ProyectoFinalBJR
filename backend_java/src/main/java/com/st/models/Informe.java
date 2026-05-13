package com.st.models;

import java.sql.Timestamp;

/*
 * Modelo que representa el informe final de resolución de una incidencia.
 * Esta entidad es obligatoria según las reglas de negocio para poder 
 * marcar una incidencia como 'RESUELTA'.
 */
public class Informe {
	private int idInforme;
	private int idIncidencia;
	private int idUsuarioSt; // Referencia al técnico de soporte que resuelve
	private String informe; // Texto detallado con la solución aplicada
	private Timestamp fechaCreacion;

	public Informe(int idInforme, int idIncidencia, int idUsuarioSt, String informe, Timestamp fechaCreacion) {
		this.idInforme = idInforme;
		this.idIncidencia = idIncidencia;
		this.idUsuarioSt = idUsuarioSt;
		this.informe = informe;
		this.fechaCreacion = fechaCreacion;
	}

	// Getters y Setters
	public int getIdInforme() {
		return idInforme;
	}

	public void setIdInforme(int idInforme) {
		this.idInforme = idInforme;
	}

	public int getIdIncidencia() {
		return idIncidencia;
	}

	public void setIdIncidencia(int idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public int getIdUsuarioSt() {
		return idUsuarioSt;
	}

	public void setIdUsuarioSt(int idUsuarioSt) {
		this.idUsuarioSt = idUsuarioSt;
	}

	public String getInforme() {
		return informe;
	}

	public void setInforme(String informe) {
		this.informe = informe;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}