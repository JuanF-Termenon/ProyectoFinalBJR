package com.st.models;

import java.sql.Timestamp;

public class Informe {
    private int idInforme;
    private int idIncidencia;
    private int idUsuarioSt;
    private String informe;
    private Timestamp fechaCreacion;

    public Informe(int idInforme, int idIncidencia, int idUsuarioSt, String informe, Timestamp fechaCreacion) {
        this.idInforme = idInforme;
        this.idIncidencia = idIncidencia;
        this.idUsuarioSt = idUsuarioSt;
        this.informe = informe;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdInforme() { return idInforme; }
    public void setIdInforme(int idInforme) { this.idInforme = idInforme; }
    public int getIdIncidencia() { return idIncidencia; }
    public void setIdIncidencia(int idIncidencia) { this.idIncidencia = idIncidencia; }
    public int getIdUsuarioSt() { return idUsuarioSt; }
    public void setIdUsuarioSt(int idUsuarioSt) { this.idUsuarioSt = idUsuarioSt; }
    public String getInforme() { return informe; }
    public void setInforme(String informe) { this.informe = informe; }
    public Timestamp getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Timestamp fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
