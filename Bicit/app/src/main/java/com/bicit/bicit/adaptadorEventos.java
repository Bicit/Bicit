package com.bicit.bicit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jgavi on 27/04/2016.
 */
public class adaptadorEventos<T> extends ArrayAdapter <T>{

    public adaptadorEventos(Context context, List<T> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItemView = convertView;

        if(convertView == null){
            listItemView = inflater.inflate(android.R.layout.two_line_list_item, parent, false);
        }
        TextView titulo = (TextView) listItemView.findViewById(android.R.id.text1);
        TextView subtitulo = (TextView) listItemView.findViewById(android.R.id.text2);

        T item = (T) getItem(position);

        String itemTexto = item.toString();
        String[] parametrosItem = itemTexto.split(",", 2);

        titulo.setText(parametrosItem[0]);
        subtitulo.setText(parametrosItem[1]);

        return listItemView;
    }
}
