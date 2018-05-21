package com.example.dexter.tourguideapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by dexter on 3/10/2018.
 */

public class MapPolylineActivity  extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener
        ,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,
        DirectionCallback {

    private Button btnRequestDirection;
    private GoogleMap googleMap;
    private String serverKey = "AIzaSyCpg0kMnFXBelMQTomeQ5Ielt9__bezkZg";


    private LatLng origin ;//32.22111 35.25444
    private LatLng destination;// = new LatLng(32.22111 , 35.25444);////32.22111 35.25444
    private LocationManager locationManager;
    MarkerOptions mo;
    Marker marker;
    private String Latitude,Longitude;
    double dLatitude,dLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplayout);



        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Latitude=null;
            } else {
                Latitude=getIntent().getStringExtra("Latitude");
                Longitude=getIntent().getStringExtra("Longitude");
            }
        } else {
            Latitude=getIntent().getStringExtra("Latitude");
            Longitude=getIntent().getStringExtra("Longitude");

        }

          dLatitude  = Double.parseDouble(Latitude);
         dLongitude= Double.parseDouble(Longitude);

        destination=new LatLng(dLatitude,dLongitude);


        btnRequestDirection = findViewById(R.id.btn_request_direction);
        btnRequestDirection.setOnClickListener(this);

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mo = new MarkerOptions().position(new LatLng(0, 0)).title("My Current LocationN");
        requestLocation();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_request_direction) {
            requestDirection();
        }
    }


    public void requestDirection() {

        if(origin==null || destination==null)
        {
            Toast.makeText(this,origin+""+destination+"",Toast.LENGTH_SHORT).show();
            return;
        }

        Snackbar.make(btnRequestDirection, "Direction Requesting...", Snackbar.LENGTH_SHORT).show();
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .optimizeWaypoints(true)
              //  .avoid(AvoidType.HIGHWAYS)
                //.avoid(AvoidType.FERRIES)
                .execute(this);
    }


    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        Snackbar.make(btnRequestDirection, "Success with status : " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            googleMap.addMarker(new MarkerOptions().position(origin));

            googleMap.addMarker(new MarkerOptions().position(destination));

            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
            googleMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.RED));
            setCameraWithCoordinationBounds(route);

            btnRequestDirection.setVisibility(View.GONE);
        } else {
            Snackbar.make(btnRequestDirection, direction.getStatus(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Snackbar.make(btnRequestDirection, t.getMessage(), Snackbar.LENGTH_SHORT).show();

    }


    @Override
    public void onMapReady(GoogleMap _googleMap) {

        googleMap = _googleMap;
        marker =  googleMap.addMarker(mo);//32.22111 35.25444

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);




    }

    private void setCameraWithCoordinationBounds(Route route) {
       LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
       LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
       LatLngBounds bounds = new LatLngBounds(southwest, northeast);
       googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        /*
        final Context context = getApplicationContext();
        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) == false) {
            Toast.makeText(this, "Please make sure that Gps is open", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, "onConnected", Toast.LENGTH_SHORT).show();
            double lat, lng;
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);

            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }


            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

//            lat = mLastLocation.getLatitude();
            //   lng = mLastLocation.getLongitude();

            //  MYPlace = new LatLng(lat, lng);

            Toast.makeText(this, mLastLocation+""+" My Place", Toast.LENGTH_SHORT).show();
            //    requestDirection();
            //  LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }
*/

    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
         origin = new LatLng(location.getLatitude(), location.getLongitude());
        // Toast.makeText(this,origin+"",Toast.LENGTH_SHORT).show();
         marker.setPosition(origin);
         googleMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    private void requestLocation() {

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Toast.makeText(this,"Check",Toast.LENGTH_SHORT).show();

        locationManager.requestLocationUpdates(provider, 10000, 10, this);


    }
    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
