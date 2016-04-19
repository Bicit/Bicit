package com.bicit.bicit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class CreateEvent extends AppCompatActivity {
    PlantillaEvento plantillaEvento;
    RadioButton rbPrivate;
    RadioButton rbPublic;
    EditText eventName;
    Button importKml;
    Button createEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        this.rbPrivate = (RadioButton)findViewById(R.id.rbPrivate);
        this.rbPublic = (RadioButton)findViewById(R.id.rbPublic);
        this.eventName = (EditText)findViewById(R.id.eventName);
        this.importKml = (Button)findViewById(R.id.btnMap);
        this.createEvent = (Button)findViewById(R.id.btnCreate);


    }
}
