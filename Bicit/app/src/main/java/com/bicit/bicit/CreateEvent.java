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

import java.util.ArrayList;

public class CreateEvent extends AppCompatActivity {
    //Contiene los diferentes atributos que tiene la plantilla para crear el evento
    public PlantillaEvento plantillaEvento;
    public RadioButton rbPrivate;
    public RadioButton rbPublic;
    public EditText eventName;
    public Button importKml;
    public Button createEvent;
    public RadioGroup group;
    public EditText startDate;
    public EditText startTime;
    public EditText description;
    public String name;
    public String sDate;
    public String sTime;
    public String descript;

    public boolean isPrivate = false;
    //Lista donde se almacena la informacion del evento.
    public ArrayList<PlantillaEvento> plantilla = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        this.plantillaEvento = new PlantillaEvento();
        this.description = (EditText)findViewById(R.id.description);
        this.startDate = (EditText)findViewById(R.id.startDate);
        this.startTime = (EditText)findViewById(R.id.startTime);
        this.rbPrivate = (RadioButton)findViewById(R.id.rbPrivate);
        this.rbPublic = (RadioButton)findViewById(R.id.rbPublic);
        this.eventName = (EditText)findViewById(R.id.eventName);
        this.importKml = (Button)findViewById(R.id.btnMap);
        this.createEvent = (Button)findViewById(R.id.btnCreate);
        this.group = (RadioGroup)findViewById(R.id.group);
        this.name = eventName.getText().toString();
        this.sTime = startTime.getText().toString();
        this.sDate = startDate.getText().toString();
        this.descript = description.getText().toString();
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

        plantillaEvento.nombreEvento = name;
        plantillaEvento.horaInicio = sTime;
        plantillaEvento.fechaInicio = sDate;
        plantillaEvento.descripcion = descript;
        if(isPrivate){
           plantillaEvento.eventoPrivado = true;
            plantillaEvento.eventoPublico = false;
        }else{
            plantillaEvento.eventoPublico = true;
            plantillaEvento.eventoPrivado = false;
        }
        plantilla.add(plantillaEvento);
        //Intent intent = new Intent(this,Maproute.class);
        //startActivity(intent);

    }
}
