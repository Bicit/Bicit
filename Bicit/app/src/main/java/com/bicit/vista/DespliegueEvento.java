package com.bicit.vista;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bicit.adaptador.AdaptadorImagen;
import com.bicit.adaptador.AdaptadorRequest;
import com.bicit.adaptador.VolleyS;
import com.bicit.modelo.Evento;
import com.bicit.modelo.Mapa;
import com.bicit.bicit.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.json.JSONObject;

import java.util.HashMap;
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
    private TextView txtAutor;
    private ImageView fotoPerfil;
    private String urlEvento;
    private ProgressDialog carga;
    private Evento eventoMostrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despliegue_evento);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
**/
        //Se intancian los elementos de la interfaz de usuario
        txtNombre = (TextView) findViewById(R.id.nombreDespliegue);
        txtFecha = (TextView) findViewById(R.id.fechaTV);
        txtQuiza = (TextView) findViewById(R.id.quizaTV);
        txtParticipantes = (TextView) findViewById(R.id.participantesTV);
        txtDescripcion = (TextView) findViewById(R.id.descripcionTV);
        txtAutor = (TextView) findViewById(R.id.nombreAutor);
        fotoPerfil = (ImageView) findViewById(R.id.fotoPerfilMiniatura);

        volley = VolleyS.getInstance(this.getApplicationContext());
        colaRequest = volley.getColaRequest();

        this.carga = ProgressDialog.show(this, "Cargando", "Espere unos segundos por favor", true, false);

        //Capturar la url enviada
        urlEvento = this.getIntent().getStringExtra("url");
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
        carga.onStart();
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                carga.dismiss();
                eventoMostrado = new Evento(response);
                desplegarEvento(eventoMostrado);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
        volley.agregarPila(request);
    }

    public void asistirEvento(final View view){
        eventoMostrado.agregarParticipante();

        JSONObject requestObject = AdaptadorRequest.crearRequestEvento(eventoMostrado);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, this.urlEvento, requestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                crearEvento(urlEvento);
                Button btnAsistir = (Button) view;
                btnAsistir.setClickable(false);
                btnAsistir.setText("Ya asistes");

                Button btnQuizas = (Button) findViewById(R.id.btnQuiza);
                btnQuizas.setClickable(false);

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en conexion", Toast.LENGTH_SHORT).show();
            }
        });
        volley.agregarPila(request);
    }

    public void quizasEvento(final View view){
        eventoMostrado.agregarQuizas();

        JSONObject requestObject = AdaptadorRequest.crearRequestEvento(eventoMostrado);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,this.urlEvento, requestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                crearEvento(urlEvento);
                Button btn = (Button) view;
                btn.setClickable(false);
                btn.setText("Quizas iras");

                Button btnAsistir = (Button) findViewById(R.id.btnAsistir);
                btnAsistir.setClickable(false);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en conexion", Toast.LENGTH_SHORT).show();
            }
        });
        volley.agregarPila(request);
    }

    //Metodo para desplegar un objeto
    public void desplegarEvento(Evento evento){
        this.txtNombre.setText(evento.getNombreEvento());
        this.txtFecha.setText(evento.getFechaInicio());
        this.txtParticipantes.setText(Integer.toString(evento.getParticipantes()));
        this.txtQuiza.setText(Integer.toString(evento.getQuizas()));
        this.txtDescripcion.setText(evento.getDescripcion());
        this.txtAutor.setText(evento.getAutor());

        //Se descarga la imagen del perfil
        new AdaptadorImagen(this.fotoPerfil).execute(evento.getImagenGrande());
    }

    public void desplegarMapa(View view){
        Intent i = new Intent(DespliegueEvento.this, despliegueMapa.class);
        startActivity(i);
    }
}
