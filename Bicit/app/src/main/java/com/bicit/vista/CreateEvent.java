package com.bicit.vista;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bicit.adaptador.AdaptadorRequest;
import com.bicit.adaptador.VolleyS;
import com.bicit.manejadorArchivos.DespliegueArchivos;
import com.bicit.manejadorArchivos.UploaderArchivo;
import com.bicit.modelo.Evento;
import com.bicit.bicit.R;
import com.facebook.Profile;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CreateEvent extends AppCompatActivity {

    public static final int CODIGO_ABRIR_ARCHIVO = 2;

    //Contiene los diferentes atributos que tiene la plantilla para crear el evento
    private RadioButton rbPrivate;
    private RadioButton rbPublic;
    private EditText eventName;
    private Button importKml;
    private Button createEvent;
    private RadioGroup group;
    private EditText startDate;
    private EditText description;
    private EditText distacia;
    private EditText duracion;
    private boolean isPrivate = false;
    private String urlKml = "";

    private ProgressDialog carga;

    //Elementos para la comunicacion con el servidor
    private VolleyS volley;
    private RequestQueue colaRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        this.description = (EditText)findViewById(R.id.description);
        this.startDate = (EditText)findViewById(R.id.startDate);
        this.rbPrivate = (RadioButton)findViewById(R.id.rbPrivate);
        this.rbPublic = (RadioButton)findViewById(R.id.rbPublic);
        this.eventName = (EditText)findViewById(R.id.eventName);
        this.importKml = (Button)findViewById(R.id.btnMap);
        this.createEvent = (Button)findViewById(R.id.btnCreate);
        this.group = (RadioGroup)findViewById(R.id.group);
        this.distacia = (EditText)findViewById(R.id.txtDistancia);
        this.duracion = (EditText) findViewById(R.id.txtDuracion);

        //Metodo donde se evalua que RadioButton esta seleccionado
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbPrivate){
                    isPrivate = true;
                }else if (checkedId == R.id.rbPublic){
                    isPrivate = false;
                }

            }
        });


        volley = VolleyS.getInstance(this.getApplicationContext());
        colaRequest = volley.getColaRequest();
    }

    public void createEvent(View v ){

        //Se reciben los datos de los campos
        String name=this.eventName.getText().toString();

        String fechaInicio=this.startDate.getText().toString();
        String fechaPublicacion=AdaptadorRequest.fechaActual();
        String descripcion=this.description.getText().toString();
        String privacidad;
        //Se evalua el check de privacidad
        if(this.isPrivate) privacidad = "Privado";
        else privacidad = "Publico";
        int duracion=Integer.parseInt(this.duracion.getText().toString());
        int distancia=Integer.parseInt(this.distacia.getText().toString());

        Profile perfiActual = Profile.getCurrentProfile();
        String autor = perfiActual.getName();
        String imagen = perfiActual.getProfilePictureUri(120,120).toString();
        String tipo = "persona";
        String imagenGrande = perfiActual.getProfilePictureUri(400,400).toString();

        Evento evento = new Evento(name,fechaInicio,fechaPublicacion,descripcion,privacidad,duracion,distancia, 0, 0, "", autor, imagen, tipo, imagenGrande);
        agregarEvento(evento);

    }

    public void agregarEvento(Evento evento){
        final boolean[] continuar = {true};
        if(this.urlKml == ""){
            continuar[0] = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea crear evento sin la ruta kml?")
                    .setTitle("Atención")
                    .setCancelable(false)
                    .setNegativeButton("Cancelar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                    .setPositiveButton("Aceptar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    continuar[0] = true;
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        if(continuar[0]){
            String url = "https://bicits.herokuapp.com/events.json";
            this.carga = ProgressDialog.show(this, "Cargando", "Espere unos segundos por favor", true, false);
            JSONObject eventoJson = AdaptadorRequest.crearRequestEvento(evento);

            JsonObjectRequest request = new JsonObjectRequest(url, eventoJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Intent resultado = new Intent();
                    setResult(EventosListar.CREAR_EVENTO, resultado);
                    carga.dismiss();
                    finish();
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                    mostrarError("No se ha podido crear el evento");
                    finish();
                }
            });

            volley.agregarPila(request);
        }
    }

    public void abrirArchivo(View view){
        Intent i = new Intent(CreateEvent.this, DespliegueArchivos.class);
        startActivityForResult(i,CreateEvent.CODIGO_ABRIR_ARCHIVO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == CreateEvent.CODIGO_ABRIR_ARCHIVO){
            if(resultCode == DespliegueArchivos.CODIGO_RUTA_ARCHIVO){
                String rutaArchivo = data.getStringExtra("Ruta");
                new UploaderArchivo(rutaArchivo, getApplicationContext()).execute();
                this.urlKml = "http://bicit.eu5.org/images/"+data.getStringExtra("Nombre");
            }
        }
    }

    public void mostrarError(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje)
                .setTitle("Atención!!")
                .setCancelable(false)
                .setNeutralButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
