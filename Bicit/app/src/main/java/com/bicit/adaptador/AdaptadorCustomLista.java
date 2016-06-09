package com.bicit.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bicit.modelo.Evento;
import com.bicit.bicit.R;

import java.util.ArrayList;

/**
 * Created by jgavi on 17/05/2016.
 */
public class AdaptadorCustomLista extends BaseAdapter{

    private static int convertViewCounter = 0;

    static class ViewHolder
    {
        TextView nombre;
        TextView fechaPublicacion;
        TextView fechaEvento;
        TextView participantes;
        ImageView fotoPerfil;
    }

    private ArrayList<Evento> data;
    private LayoutInflater inflater = null;

    public AdaptadorCustomLista(Context c, ArrayList<Evento> data){
        this.data = data;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public int getItemViewType(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.textview_personalizado, null);
            convertViewCounter++;

            holder = new ViewHolder();
            holder.fechaEvento = (TextView) convertView.findViewById(R.id.LVfechaEvento);
            holder.fechaPublicacion = (TextView) convertView.findViewById(R.id.LVfechaPublicacion);
            holder.nombre = (TextView) convertView.findViewById(R.id.LVnombre);
            holder.participantes = (TextView) convertView.findViewById(R.id.LVparticipantes);
            holder.fotoPerfil = (ImageView) convertView.findViewById(R.id.fotoPerfilMiniatura);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nombre.setText(data.get(position).getNombreEvento());
        holder.participantes.setText("Asistiran: "+Integer.toString(data.get(position).getParticipantes()));
        holder.fechaPublicacion.setText(data.get(position).getFechaPublicacion());
        holder.fechaEvento.setText("Fecha: "+data.get(position).getFechaInicio());
        new AdaptadorImagen(holder.fotoPerfil).execute(data.get(position).getImagen());

        return convertView;
    }

    public void add(Evento evento){
        this.data.add(evento);
    }

    public void clear(){
        this.data.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();
    }
}
