package com.bicit.bicit;

import java.util.ArrayList;

/**
 * Created by Felipe Montoya on 19/04/2016.
 */
public class Evento {
    private final String nombreEvento;
    private final String fechaInicio;
    private final String fechaPublicacion;
    private final String descripcion;
    private final String privacidad;
    private final int duracion;
    private final int distancia;

    public Evento(String nombreEvento, String fechaInicio, String fechaPublicacion, String descripcion, String privacidad, int duracion, int distancia){
        this.nombreEvento = nombreEvento;
        this.fechaInicio = fechaInicio;
        this.fechaPublicacion = fechaPublicacion;
        this.descripcion = descripcion;
        this.privacidad = privacidad;
        this.duracion = duracion;
        this.distancia = distancia;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrivacidad() {
        return privacidad;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getDistancia() {
        return distancia;
    }

    @Override
    public String toString(){
        return this.nombreEvento+","+"Fecha: "+this.fechaInicio+" duracion: "+this.duracion;
    }
}
