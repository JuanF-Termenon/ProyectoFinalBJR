package com.st.models;

import java.sql.Timestamp;

public class Historial {
    private int idHistorial;
    private String accion;
    private String estadoAnterior;
    private String estadoNuevo;
    private String comentario;
    private Timestamp fechaEvento;
    private int idIncidencia;
    private int idUsuario;
    private String nombreUsuario;

    public Historial(int idHistorial, String accion, String estadoAnterior, String estadoNuevo,
                     String comentario, Timestamp fechaEvento, int idIncidencia,
                     int idUsuario, String nombreUsuario) {
        this.idHistorial = idHistorial;
        this.accion = accion;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.comentario = comentario;
        this.fechaEvento = fechaEvento;
        this.idIncidencia = idIncidencia;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
    }

    public int getIdHistorial() { return idHistorial; }
    public String getAccion() { return accion; }
    public String getEstadoAnterior() { return estadoAnterior; }
    public String getEstadoNuevo() { return estadoNuevo; }
    public String getComentario() { return comentario; }
    public Timestamp getFechaEvento() { return fechaEvento; }
    public int getIdIncidencia() { return idIncidencia; }
    public int getIdUsuario() { return idUsuario; }
    public String getNombreUsuario() { return nombreUsuario; }
}
