package com.bicit.bicit;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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



}
