package com.bicit.bicit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EventosListar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Elementos volley para la comunicacion con el servidor
    private VolleyS volley;
    //Cola que usa la aplicacion para las solicitudes we
    private RequestQueue colaRequest;

    //Elementos para la lista de eventos
    private ListView listaEventos;
    private AdaptadorCustomLista adaptador;
    private ArrayList<Evento> eventos;

    //Codigo de respuesta a la creacion de eventos
    public static int CREAR_EVENTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Se crea la actividad con el navigation view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_listar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //<------------------------------------------->

        //Se cambia la accion del boton para nuevo evento
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.nuevoEvento);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventosListar.this, CreateEvent.class);
                startActivityForResult(i, EventosListar.CREAR_EVENTO);
            }
        });

        //Se instancia la lista de los eventos y su adaptador
        this.eventos = new ArrayList<>();
        this.listaEventos = (ListView) findViewById(R.id.listaEventos);
        this.adaptador = new AdaptadorCustomLista(this, eventos);
        listaEventos.setAdapter(adaptador);

        //Se cambia el comportamiento de la lista al seleccionar un item
        this.listaEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento evento = (Evento) adaptador.getItem(position);
                Intent i = new Intent(EventosListar.this, DespliegueEvento.class);
                i.putExtra("url", evento.getUrl());
                startActivity(i);
            }
        });

        //Se instancian los objetos volley para la comunicacion con el servidor
        volley = VolleyS.getInstance(this.getApplicationContext());
        colaRequest = volley.getColaRequest();
        solicitarEventos();
        Toast.makeText(getApplicationContext(),"Cargando...", Toast.LENGTH_LONG);
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

    public void solicitarEventos(){
        String url = "https://bicits.herokuapp.com/events.json";
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
            for(int i=0; i<jsonArray.length(); i++){
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Evento nuevoEvento = new Evento(jsonObject);
                    adaptador.add(nuevoEvento);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            adaptador.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
        addToQueue(request);
    }

    //Cuando vuelva de el crear evento
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == EventosListar.CREAR_EVENTO){
            this.solicitarEventos();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_maproute) {
            Intent intent = new Intent(EventosListar.this,Maproute.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_events) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
