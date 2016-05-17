package com.bicit.bicit;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;

public class DespliegueEvento extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despliegue_evento);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
}
