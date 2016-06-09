package com.bicit.modelo;

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
    private final String url;
    private final String autor;
    private final String imagen;
    private final String tipo;
    private final String imagenGrande;
    private final int duracion;
    private final int distancia;
    private int participantes;
    private int quizas;

    public Evento(String nombreEvento, String fechaInicio, String fechaPublicacion, String descripcion, String privacidad, int duracion, int distancia, int participantes, int quizas, String url, String autor, String imagen, String tipo, String imagenGrande){
        this.nombreEvento = nombreEvento;
        this.fechaInicio = fechaInicio;
        this.fechaPublicacion = fechaPublicacion;
        this.descripcion = descripcion;
        this.privacidad = privacidad;
        this.duracion = duracion;
        this.distancia = distancia;
        this.participantes = participantes;
        this.quizas= quizas;
        this.url = url;
        this.autor = autor;
        this.tipo = tipo;
        this.imagen = imagen;
        this.imagenGrande = imagenGrande;
    }

    public Evento(JSONObject eventoJson){
        this.nombreEvento = eventoJson.optString("nombre").toString();
        this.fechaInicio = eventoJson.optString("fecha_evento").toString();
        this.fechaPublicacion = eventoJson.optString("fecha_publicacion").toString();
        this.descripcion = eventoJson.optString("descripcion");
        this.privacidad = eventoJson.optString("privacidad");
        this.duracion = (int)Float.parseFloat(eventoJson.optString("duracion").toString());
        this.distancia = (int)Float.parseFloat(eventoJson.optString("distancia").toString());
        this.participantes = (int)Float.parseFloat(eventoJson.optString("asistentes").toString());
        this.quizas = (int)Float.parseFloat(eventoJson.optString("tal_ves").toString());
        this.url = eventoJson.optString("url");
        this.autor = eventoJson.optString("autor");
        this.imagen = eventoJson.optString("imagen");
        this.tipo = eventoJson.optString("tipo");
        this.imagenGrande = eventoJson.optString("imagengrande");
    }

    public Evento(JSONObject eventoJson, String url){
        this.nombreEvento = eventoJson.optString("nombre").toString();
        this.fechaInicio = eventoJson.optString("autor").toString();
        this.fechaPublicacion = eventoJson.optString("fecha_publicacion").toString();
        this.descripcion = eventoJson.optString("descripcion");
        this.privacidad = eventoJson.optString("privacidad");
        this.duracion = (int)Float.parseFloat(eventoJson.optString("duracion").toString());
        this.distancia = (int)Float.parseFloat(eventoJson.optString("distancia").toString());
        this.participantes = (int)Float.parseFloat(eventoJson.optString("participantes").toString());
        this.quizas = (int)Float.parseFloat(eventoJson.optString("tal_ves").toString());
        this.url = url;
        this.autor = eventoJson.optString("autor");
        this.imagen = eventoJson.optString("imagen");
        this.tipo = eventoJson.optString("tipo");
        this.imagenGrande = eventoJson.optString("imagengrande");
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

    public int getParticipantes() {
        return participantes;
    }

    public int getQuizas() {
        return quizas;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public String toString(){
        return this.nombreEvento+","+" Fecha: "+this.fechaInicio+"   |   Duracion: "+this.duracion+" horas" + "   |   Fecha publicacion: "+this.fechaPublicacion;
    }

    public String getAutor() {
        return autor;
    }

    public String getImagen() {
        return imagen;
    }

    public String getTipo() {
        return tipo;
    }

    public String getImagenGrande() {
        return imagenGrande;
    }

    public void agregarParticipante(){
        this.participantes++;
    }

    public void agregarQuizas(){
        this.quizas++;
    }
}
