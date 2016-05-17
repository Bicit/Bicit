package com.bicit.bicit;

import org.json.JSONObject;

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

    public Evento(JSONObject eventoJson){
        this.nombreEvento = eventoJson.optString("nombre").toString();
        this.fechaInicio = eventoJson.optString("autor").toString();
        this.fechaPublicacion = eventoJson.optString("fecha_publicacion").toString();
        this.descripcion = eventoJson.optString("descripcion");
        this.privacidad = eventoJson.optString("privacidad");
        this.duracion = (int)Float.parseFloat(eventoJson.optString("duracion").toString());
        this.distancia = (int)Float.parseFloat(eventoJson.optString("distancia").toString());
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
        return this.nombreEvento+","+" Fecha: "+this.fechaInicio+"   |   Duracion: "+this.duracion+" horas" + "   |   Fecha publicacion: "+this.fechaPublicacion;
    }
}
