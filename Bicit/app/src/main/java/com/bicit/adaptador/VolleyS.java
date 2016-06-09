package com.bicit.adaptador;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by jgavi on 16/05/2016.
 */
public class VolleyS {
    private static VolleyS mVolleyS = null;
    //Cola que usa la aplicacion para las solicitudes web
    private RequestQueue colaRequest;

    private VolleyS(Context context){
        colaRequest = Volley.newRequestQueue(context);
    }

    public static VolleyS getInstance(Context context){
        if(mVolleyS == null){
            mVolleyS = new VolleyS(context);
        }
        return mVolleyS;
    }

    public RequestQueue getColaRequest(){
        return colaRequest;
    }

    public void agregarPila(Request request){
        if (request != null && mVolleyS != null) {
            request.setTag(this);
            if (colaRequest == null)
                colaRequest = mVolleyS.getColaRequest();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            colaRequest.add(request);
        }
    }



}
