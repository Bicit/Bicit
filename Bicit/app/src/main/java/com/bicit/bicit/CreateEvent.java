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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
    private DbController dbController;
    private boolean isPrivate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        this.dbController = new DbController();
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
        EventosActivity.eventos.add(evento);
        Intent i = new Intent(this, EventosActivity.class);
        this.startActivity(i);
        finish();
    }

    public String fechaHoraActual(){
        return new SimpleDateFormat( "yyyy/MM/dd", java.util.Locale.getDefault()).format(Calendar.getInstance() .getTime());
        //return "Test";
    }
}
