package com.bicit.bicit;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by jgavi on 4/04/2016.
 */
public class Mapa {
    private int archivoDescripcion;
    private GoogleMap mapaVisual;
    private Location posicion;
    private Context contextoActual;

    public Mapa(int archivoDescripcion, GoogleMap mapaVisual, Location posicion, Context contextoActual){
        this.archivoDescripcion = archivoDescripcion;
        this.mapaVisual = mapaVisual;
        this.posicion = posicion;
        this.contextoActual = contextoActual;
    }

    public GoogleMap cargarMapa(){
        //Se activa la capa para que el usuario se ubique en el mapa,
        //Es decir aparecera la opcion para ubicarse y aparecera un punto
        //Azul que indica su posicion geografica
        mapaVisual.setMyLocationEnabled(true);


        this.cambioPosicion(posicion);

        //Se accede al archivo kml que tiene la descripcion de las lineas que se deben dibujar y su posicion
        //El archivo se encuentra en la ruta /Bicit/Bicit/app/src/main/res/raw
        //Para modificar acceder a mymaps con la cuenta de bicit, una vez modificado descargar el archivo
        //Dando a la opcion export to kml y darle a la opcion de exportar en kml en vez de kmz.
        //Reemplazar el archivo descargado por el antiguo en la carpeta de resources
        try {
            KmlLayer layer = new KmlLayer(mapaVisual, archivoDescripcion, this.contextoActual);
            layer.addLayerToMap();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.mapaVisual;
    }

    public GoogleMap cambioPosicion(Location posicionNueva){
        //Se mueve el mapa al lugar y se hace un zoom de 15
        LatLng latitud = new LatLng(posicion.getLatitude(), posicion.getLongitude());
        mapaVisual.moveCamera(CameraUpdateFactory.newLatLng(latitud));
        mapaVisual.animateCamera(CameraUpdateFactory.zoomTo(15));
        return mapaVisual;
    }
}
