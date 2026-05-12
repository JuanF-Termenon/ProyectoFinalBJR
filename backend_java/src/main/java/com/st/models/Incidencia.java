package com.st.models;

import java.sql.Timestamp;

/*
 * Modelo que representa una incidencia en el sistema.
 * Refleja la estructura de la tabla 'incidencia' en la base de datos.
 */
public class Incidencia {
	private int idIncidencia;
	private String descripcion;
	private String prioridad; // Valores: BAJA, MEDIA, ALTA, CRITICA
	private String estado; // Valores: ACTIVA, EN_CURSO, RESUELTA, REABIERTA, CANCELADA
	private Timestamp fechaCreacion;
	private Timestamp fechaInicioAtencion; // Puede ser null
	private Timestamp fechaResolucion; // Puede ser null
	private int idPuesto;
	private int idUsuarioCreador;
	private Integer idUsuarioCierre; // Puede ser null

	/*
	 * Constructor para nuevas incidencias. Se usa cuando el operario crea un
	 * ticket; el ID lo generará la DB automáticamente.
	 */
	public Incidencia(String descripcion, String prioridad, String estado, Timestamp fechaCreacion,
			Timestamp fechaInicioAtencion, Timestamp fechaResolucion, int idPuesto, int idUsuarioCreador,
			Integer idUsuarioCierre) {
		this.descripcion = descripcion;
		this.prioridad = prioridad;
		this.estado = "ACTIVA";
		this.fechaCreacion = fechaCreacion;
		this.fechaInicioAtencion = fechaInicioAtencion;
		this.fechaResolucion = fechaResolucion;
		this.idPuesto = idPuesto;
		this.idUsuarioCreador = idUsuarioCreador;
		this.idUsuarioCierre = idUsuarioCierre;
	}

	/*
	 * Constructor completo. Se utiliza en el Repositorio para reconstruir objetos a
	 * partir de los datos de la DB.
	 */
	public Incidencia(int idIncidencia, String descripcion, String prioridad, String estado, Timestamp fechaCreacion,
			Timestamp fechaInicioAtencion, Timestamp fechaResolucion, int idPuesto, int idUsuarioCreador,
			Integer idUsuarioCierre) {
		this.idIncidencia = idIncidencia;
		this.descripcion = descripcion;
		this.prioridad = prioridad;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.fechaInicioAtencion = fechaInicioAtencion;
		this.fechaResolucion = fechaResolucion;
		this.idPuesto = idPuesto;
		this.idUsuarioCreador = idUsuarioCreador;
		this.idUsuarioCierre = idUsuarioCierre;
	}

	// Getters y Setters
	public int getIdIncidencia() {
		return idIncidencia;
	}

	public Timestamp getFechaInicioAtencion() {
		return fechaInicioAtencion;
	}

	public void setFechaInicioAtencion(Timestamp fechaInicioAtencion) {
		this.fechaInicioAtencion = fechaInicioAtencion;
	}

	public Timestamp getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(Timestamp fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public Integer getIdUsuarioCierre() {
		return idUsuarioCierre;
	}

	public void setIdUsuarioCierre(Integer idUsuarioCierre) {
		this.idUsuarioCierre = idUsuarioCierre;
	}

	public void setIdIncidencia(int idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public void setIdPuesto(int idPuesto) {
		this.idPuesto = idPuesto;
	}

	public void setIdUsuarioCreador(int idUsuarioCreador) {
		this.idUsuarioCreador = idUsuarioCreador;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public String getEstado() {
		return estado;
	}

	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	public int getIdPuesto() {
		return idPuesto;
	}

	public int getIdUsuarioCreador() {
		return idUsuarioCreador;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}