package com.bicit.manejadorArchivos;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bicit.bicit.R;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DespliegueArchivos extends AppCompatActivity {

    public static final int CODIGO_RUTA_ARCHIVO = 1;

    private List<String> listaNombresArchivos;
    private List<String> listaRutasArchivos;
    private ArrayAdapter<String> adaptador;
    private String directorioRaiz;
    private TextView carpetaActual;
    private ListView listaArchivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despliegue_archivos);

        //Se obtiene la ubicacion del directorio raiz
        this.directorioRaiz = Environment.getExternalStorageDirectory().getPath();

        //Se instancian los elementos de la interfaz grafica
        this.carpetaActual = (TextView) findViewById(R.id.ubicacionActual);
        this.listaArchivos = (ListView) findViewById(R.id.listaArchivos);

        //Se lista los elementos de la ubicacion raiz
        verArchivosDirectorio(directorioRaiz);

        listaArchivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Archivo que se selecciono
                File archivo = new File(listaRutasArchivos.get(position));

                //Se revisa si es archivo o directorio
                if(archivo.isFile()){
                    //Se pone como resultado de la actividad la ruta del archivo
                    //Finaliza la actividad

                    Intent resultado = new Intent();
                    resultado.putExtra("Ruta",listaRutasArchivos.get(position));
                    resultado.putExtra("Nombre",archivo.getName());
                    setResult(DespliegueArchivos.CODIGO_RUTA_ARCHIVO, resultado);
                    finish();
                }else{ //Es un directorio
                    verArchivosDirectorio(listaRutasArchivos.get(position));
                }
            }
        });
    }

    //Muestra los archivos del directorio
    private void verArchivosDirectorio(String directorio){
        this.carpetaActual.setText(directorio);

        //Se intancian las listas que contienen la informacion del directorio
        listaNombresArchivos = new ArrayList<String>();
        listaRutasArchivos = new ArrayList<String>();

        //Se crean los archivos para acceder al sistema
        File directorioActual = new File(directorio);
        File[] listaArchivosDirectorio = directorioActual.listFiles();

        int x=0;

        //Se crea un directorio que permita regresar en caso de no ser el directorio actual
        if(!directorio.equals(this.directorioRaiz)){
            listaNombresArchivos.add("../");
            listaRutasArchivos.add(directorioActual.getParent());
            x=1;
        }

        //Se almacenan las rutas de todos los archivos listados
        for(File archivo : listaArchivosDirectorio) {
            listaRutasArchivos.add(archivo.getPath());
        }

        //Se ordenan en orden alfabetico
        Collections.sort(listaRutasArchivos, String.CASE_INSENSITIVE_ORDER);

        for(int i = x; i < listaRutasArchivos.size(); i++){
            File archivo = new File(listaRutasArchivos.get(i));
            if(archivo.isFile()){
                listaNombresArchivos.add(archivo.getName());
            }else{
                listaNombresArchivos.add("/"+archivo.getName());
            }
        }

        if(listaArchivosDirectorio.length < 1){
            listaNombresArchivos.add("Directorio vacio");
            listaRutasArchivos.add(directorio);
        }

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaNombresArchivos);
        this.listaArchivos.setAdapter(adaptador);
    }
}
