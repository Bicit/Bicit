package com.bicit.bicit;

import android.content.Intent;
import android.provider.MediaStore;
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
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CreateEvent extends AppCompatActivity {

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
        String fechaPublicacion=fechaHoraActual();
        String descripcion=this.description.getText().toString();
        String privacidad;
        //Se evalua el check de privacidad
        if(this.isPrivate) privacidad = "Privado";
        else privacidad = "Publico";
        int duracion=Integer.parseInt(this.duracion.getText().toString());
        int distancia=Integer.parseInt(this.distacia.getText().toString());

        Evento evento = new Evento(name,fechaInicio,fechaPublicacion,descripcion,privacidad,duracion,distancia);
        agregarEvento(evento);
    }

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

    public void agregarEvento(Evento evento){
        String url = "https://bicits.herokuapp.com/events.json";
        HashMap<String, String> parametros = new HashMap<>();
        parametros.put("nombre", evento.getNombreEvento());
        parametros.put("autor", "bicit");
        parametros.put("privacidad", evento.getPrivacidad());
        parametros.put("fecha_publicacion", evento.getFechaPublicacion());
        parametros.put("fecha_evento", evento.getFechaInicio());
        parametros.put("distancia", Integer.toString(evento.getDistancia()));
        parametros.put("duracion", Integer.toString(evento.getDuracion()));
        parametros.put("asistentes", Integer.toString(0));
        parametros.put("tal_ves", Integer.toString(0));
        parametros.put("descripcion", evento.getDescripcion());
        System.out.println("Se agrega");
        JSONObject eventoJson = new JSONObject(parametros);
        JsonObjectRequest request = new JsonObjectRequest(url, eventoJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent resultado = new Intent();
                setResult(EventosListar.CREAR_EVENTO, resultado);
                finish();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                Toast.makeText(CreateEvent.this, "Error, no se ha crear el evento", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        addToQueue(request);

    }

    public String fechaHoraActual(){
        return new SimpleDateFormat( "yyyy-MM-dd", java.util.Locale.getDefault()).format(Calendar.getInstance() .getTime());
        //return "Test";
    }
}
