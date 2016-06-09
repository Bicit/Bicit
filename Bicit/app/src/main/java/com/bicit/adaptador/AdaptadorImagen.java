package com.bicit.adaptador;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Juan Pablo Gaviria on 9/06/2016.
 */
public class AdaptadorImagen extends AsyncTask<String, Void, Bitmap> {

    private ImageView imagen;

    public AdaptadorImagen(ImageView imagen){
        this.imagen = imagen;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urlImagen = urls[0];
        Bitmap iconImagen = null;
        try {
            InputStream in = new java.net.URL(urlImagen).openStream();
            iconImagen = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return iconImagen;
    }

    protected void onPostExecute(Bitmap result) {
        imagen.setImageBitmap(result);
    }
}
