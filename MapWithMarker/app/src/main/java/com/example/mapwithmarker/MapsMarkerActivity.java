package com.example.mapwithmarker;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.*;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapsMarkerActivity extends AppCompatActivity
        implements OnMapReadyCallback, LocationListener {
    private LocationManager locationManager;
    private LocationListener locationListener;


    GoogleMap mMap;
    protected double lat = 50.446727;
    protected double lng = 30.454385;

    Marker m1;
    Marker m2;
    Marker m3;
    Marker m4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();
                mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)), 2000, null);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET
            }, 10);
            return;
        } else {
            locationManager.requestLocationUpdates("gps", 5000, 4, locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }locationManager.requestLocationUpdates("gps", 5000, 4, locationListener);
                return;
        }
    }

    private  void  configure()
    {
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(mMap != googleMap)
        mMap = googleMap;
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(aromaJava));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 17.5f), 2000, null);
        LatLng aromaJava = new LatLng(50.446883, 30.453212);
        LatLng styleJava = new LatLng(50.446920, 30.453123);
        LatLng myJava = new LatLng(50.446957, 30.452957);
        LatLng kosherniy = new LatLng(50.447006, 30.453118);


        m1 = googleMap.addMarker(new MarkerOptions().position(aromaJava)
                .title("AromaJava").snippet("Aroma Java").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        m2 = googleMap.addMarker(new MarkerOptions().position(styleJava)
                .title("StyleJava").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        m3 = googleMap.addMarker(new MarkerOptions().position(myJava)
                .title("MyJava").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        m4 = googleMap.addMarker(new MarkerOptions().position(kosherniy)
                .title("Kosherniy").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));


        final LayoutInflater layouter = this.getLayoutInflater();
        final Context c = this;

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                AlertDialog.Builder adb = new AlertDialog.Builder(c);
                final View dialogView = layouter.inflate(R.layout.details,null);
                final TextView Address = (TextView)dialogView.findViewById(R.id.Address);
                final TextView Description = (TextView)dialogView.findViewById(R.id.Description);
                final TextView Rate = (TextView)dialogView.findViewById(R.id.Rate);
                adb.setView(dialogView);
                adb.setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {} });



                if(marker.equals(m1)){
                    Address.setText("m1.Address");
                    Description.setText("m1.Description");
                    Rate.setText("☆☆☆");
                    adb.setTitle(m1.getTitle());
                    AlertDialog toShow = adb.create();
                    toShow.show();/////////////////droping here
                    return true;
                } else if(marker.equals(m2)){
                    Address.setText("m2.Address");
                    Description.setText("m2.Description");
                    Rate.setText("☆☆☆☆");
                    adb.setTitle(m2.getTitle());
                    adb.create().show();//or here

                    return true;
                } else if(marker.equals(m3)){
                    Address.setText("m3.Address");
                    Description.setText("m3.Description");
                    Rate.setText("☆☆☆☆☆");
                    adb.setTitle(m3.getTitle());
                    adb.create().show();//or here
                    return true;
                } else if(marker.equals(m4)){
                    Address.setText("m4.Address");
                    Description.setText("m4.Description");
                    Rate.setText("☆☆");
                    adb.setTitle(m4.getTitle());
                    adb.create().show();// or here
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
