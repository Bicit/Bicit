package com.bicit.adaptador;

import com.bicit.modelo.Evento;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by jgavi on 7/06/2016.
 */
public class AdaptadorRequest {

    public static JSONObject crearRequestEvento(Evento evento){
        HashMap<String, String> parametros = new HashMap<>();
        parametros.put("nombre", evento.getNombreEvento());
        parametros.put("autor", "bicit");
        parametros.put("privacidad", evento.getPrivacidad());
        parametros.put("fecha_publicacion", evento.getFechaPublicacion());
        parametros.put("fecha_evento", evento.getFechaInicio());
        parametros.put("distancia", Integer.toString(evento.getDistancia()));
        parametros.put("duracion", Integer.toString(evento.getDuracion()));
        parametros.put("asistentes", Integer.toString(evento.getParticipantes()));
        parametros.put("tal_ves", Integer.toString(evento.getQuizas()));
        parametros.put("descripcion", evento.getDescripcion());
        parametros.put("autor", evento.getAutor());
        parametros.put("imagen", evento.getImagen());
        parametros.put("tipo", evento.getTipo());
        parametros.put("imagengrande", evento.getImagenGrande());
        JSONObject eventoJson = new JSONObject(parametros);
        return eventoJson;
    }

    public static String fechaActual(){
        return new SimpleDateFormat( "yyyy-MM-dd", java.util.Locale.getDefault()).format(Calendar.getInstance() .getTime());
    }
}
