package com.bicit.bicit;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

public class DespliegueEvento extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;

    //Objetos para la conexion la servidor
    private VolleyS volley;
    private RequestQueue colaRequest;

    //Elementos de la interfaz de usuario a modificar
    private TextView txtNombre;
    private TextView txtFecha;
    private TextView txtQuiza;
    private TextView txtParticipantes;
    private TextView txtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despliegue_evento);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Se intancian los elementos de la interfaz de usuario
        txtNombre = (TextView) findViewById(R.id.nombreDespliegue);
        txtFecha = (TextView) findViewById(R.id.fechaTV);
        txtQuiza = (TextView) findViewById(R.id.quizaTV);
        txtParticipantes = (TextView) findViewById(R.id.participantesTV);
        txtDescripcion = (TextView) findViewById(R.id.descripcionTV);

        volley = VolleyS.getInstance(this.getApplicationContext());
        colaRequest = volley.getColaRequest();

        //Capturar la url enviada
        String urlEvento = this.getIntent().getStringExtra("url");
        crearEvento(urlEvento);
    }

    /**
     * Este metodo se ejecuta cuando el mapa esta listo para ser mostrado
     * Ubica la posicion del usuario y centra el mapa en ese punto
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setPadding(0,130,0,0);
        mMap.setMyLocationEnabled(true);
        //Se busca el mejor proveedor de localizacion que tenga el celular para luego
        //Obtener ultima posicion que se conoce del usuario
        //Y centrar el mapa en ese lugar
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        //Se revisa si se tienen los permisos para conocer la localizacion
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        Location location = null;
        if(locationManager.isProviderEnabled(provider)){
            location = locationManager.getLastKnownLocation(provider);
        }else{
            List<String> listaProviders = locationManager.getAllProviders();
            for(int i=0; i<listaProviders.size(); i++){
                if(locationManager.isProviderEnabled(listaProviders.get(i))){
                    location = locationManager.getLastKnownLocation(listaProviders.get(i));
                    break;
                }
            }
        }

        Mapa controladorMapa = new Mapa(R.raw.ciclorutasmap, mMap, location, getApplicationContext());
        mMap = controladorMapa.cargarMapa();
    }

    //Metodo que crea un evento a partir de una direccion url
    public void crearEvento(String url)
    {
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("hay respuesta");
                Evento evento = new Evento(response);
                desplegarEvento(evento);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
        addToQueue(request);
    }

    //Metodos para la sincronizacion con volley
    public void addToQueue(Request request) {
        if (request != null) {
            request.setTag(this);
            if (colaRequest == null)
                colaRequest = volley.getColaRequest();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            colaRequest.add(request);
        }
    }

    //Metodo para desplegar un objeto
    public void desplegarEvento(Evento evento){
        this.txtNombre.setText(evento.getNombreEvento());
        this.txtFecha.setText(evento.getFechaInicio());
        this.txtParticipantes.setText(Integer.toString(evento.getParticipantes()));
        this.txtQuiza.setText(Integer.toString(evento.getQuizas()));
        this.txtDescripcion.setText(evento.getDescripcion());
    }
}
