package com.bicit.vista;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bicit.bicit.R;
import com.bicit.modelo.Mapa;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class despliegueMapa extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String kmlurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despliegue_mapa);

        kmlurl = getIntent().getStringExtra("kmlurl");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapDespliegue);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setPadding(0, 130, 0, 0);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mMap.setMyLocationEnabled(true);

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
        try {
            mMap = controladorMapa.cargarInputStream(new URL(this.kmlurl));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
