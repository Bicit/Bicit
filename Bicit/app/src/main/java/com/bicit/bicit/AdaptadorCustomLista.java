package com.bicit.bicit;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

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

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nombre.setText(data.get(position).getNombreEvento());
        holder.participantes.setText("Asistiran: "+Integer.toString(data.get(position).getParticipantes()));
        holder.fechaPublicacion.setText(data.get(position).getFechaPublicacion());
        holder.fechaEvento.setText("Fecha: "+data.get(position).getFechaInicio());

        return convertView;
    }

    public void add(Evento evento){
        this.data.add(evento);
    }

    @Override
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();
    }
}
