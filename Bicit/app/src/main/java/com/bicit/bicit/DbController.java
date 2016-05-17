package com.bicit.bicit;

import java.util.ArrayList;

/**
 * Created by Felipe Montoya on 25/04/2016.
 */
public class DbController {
    private static DbController current;
    private ArrayList<Evento> eventos;

    public DbController(){
        eventos = new ArrayList<>();
    }

    public ArrayList<Evento> obtenerEventos(){
        //ArrayList<Evento> lista = new ArrayList<>();

        return eventos;
    }

    public Evento obtenerEvento(int identificador){
        String nombreEvento="";
        String fechaInicio="";
        String fechaPublicacion="";
        String descripcion="";
        String privacidad="";
        int duracion=0;
        int distancia=0;
        Evento evento = new Evento(nombreEvento,fechaInicio,fechaPublicacion,descripcion,privacidad,duracion,distancia);

        return evento;
    }

    public void agregarEvento(Evento evento){
        eventos.add(evento);
        System.out.println(DbController.current.obtenerEventos().size());
    }

    public static DbController obtenerControlador(){
        if(current == null){
            System.out.println("es nulo");
            current = new DbController();
        }
        return current;
    }
}
