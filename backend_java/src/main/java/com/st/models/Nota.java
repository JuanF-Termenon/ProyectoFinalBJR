package com.st.models;

import java.sql.Timestamp;

public class Nota {
    private int idNota;
    private int idIncidencia;
    private int idUsuario;
    private String nota;
    private Timestamp fechaCreacion;
    private String nombreUsuario;

    public Nota(int idNota, int idIncidencia, int idUsuario, String nota, Timestamp fechaCreacion) {
        this.idNota = idNota;
        this.idIncidencia = idIncidencia;
        this.idUsuario = idUsuario;
        this.nota = nota;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdNota() { return idNota; }
    public void setIdNota(int idNota) { this.idNota = idNota; }
    public int getIdIncidencia() { return idIncidencia; }
    public void setIdIncidencia(int idIncidencia) { this.idIncidencia = idIncidencia; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }
    public Timestamp getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Timestamp fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
}
