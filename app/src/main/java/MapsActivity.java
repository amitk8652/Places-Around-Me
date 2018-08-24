
package com.example.maurya.searchmap;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.LocationManager;
import android.location.Criteria;
import android.location.Location;
import android.content.Intent;
import android.provider.Settings;

import java.io.IOException;
import java.util.List;
import static android.location.LocationManager.*;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


import static com.example.maurya.searchmap.R.styleable.AlertDialog;
import static com.example.maurya.searchmap.R.styleable.BottomNavigationView;





public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private Location location1;

    private TextView mTextMessage;
    //private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentLocationmMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 810;
    double latitude,longitude;
    boolean canLocation = false;
    GoogleApiClient mGoogleApiClient;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Object dataTransfer[] = new Object[2];
            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
            MarkerOptions markerOptions = new MarkerOptions();
            LatLng latLng = new LatLng(latitude,longitude);
            switch (item.getItemId()) {
                    case R.id.navigation_ATM:
                        mMap.clear();

                        String resturant = "atm";
                          markerOptions.position(latLng).title("Current Position").
                                 icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                          currentLocationmMarker = mMap.addMarker(markerOptions);

                        String url = getUrl(latitude, longitude, resturant);
                        dataTransfer[0] = mMap;
                        dataTransfer[1] = url;
                        Log.d("onClick", url);
                        getNearbyPlacesData.execute(dataTransfer);
                        Toast.makeText(MapsActivity.this, "Showing Nearby ATM", Toast.LENGTH_SHORT).show();

                        return true;
                    case R.id.navigation_hospital:
                        mMap.clear();

                        String hospital = "hospital";
                         markerOptions.position(latLng).title("Current Position").
                                   icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                          currentLocationmMarker = mMap.addMarker(markerOptions);
                        url = getUrl(latitude, longitude, hospital);
                        dataTransfer[0] = mMap;
                        dataTransfer[1] = url;
                        Log.d("onClick", url);
                        getNearbyPlacesData.execute(dataTransfer);
                        Toast.makeText(MapsActivity.this, "Showing Nearby Hospitals", Toast.LENGTH_SHORT).show();


                        return true;
                    case R.id.navigation_hotel:
                        mMap.clear();

                        String hotel = "hotel||restaurant";
                          markerOptions.position(latLng).title("Current Position").
                                  icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                         currentLocationmMarker = mMap.addMarker(markerOptions);
                        url = getUrl(latitude, longitude, hotel);
                        dataTransfer[0] = mMap;
                        dataTransfer[1] = url;
                        Log.d("onClick", url);
                        getNearbyPlacesData.execute(dataTransfer);
                        Toast.makeText(MapsActivity.this, "Showing Nearby Restaurant", Toast.LENGTH_SHORT).show();

                        return true;
                    case R.id.nav_school:
                        mMap.clear();

                        String school = "school";
                          markerOptions.position(latLng).title("Current Position").
                                  icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                           currentLocationmMarker = mMap.addMarker(markerOptions);
                        url = getUrl(latitude, longitude, school);

                        dataTransfer[0] = mMap;
                        dataTransfer[1] = url;

                        Log.d("onClick", url);
                        getNearbyPlacesData.execute(dataTransfer);
                        Toast.makeText(MapsActivity.this, "Showing Nearby Schools", Toast.LENGTH_SHORT).show();


                        return true;
                    case R.id.nav_flim:
                        mMap.clear();

                        String flim = "flims";
                        markerOptions.position(latLng).title("Current Position").
                                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        currentLocationmMarker = mMap.addMarker(markerOptions);

                        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                        googlePlaceUrl.append("location=" + latitude + "," + longitude);
                        googlePlaceUrl.append("&radius=" + PROXIMITY_RADIUS);
                        googlePlaceUrl.append("&type=movie");
                        googlePlaceUrl.append("&keyword=theater");
                        //googlePlaceUrl.append("&sensor=true");
                        googlePlaceUrl.append("&key=" + "AIzaSyBi7Lrmcel1REWB_APN5egdgw24IJcNDqk");
                        url = googlePlaceUrl.toString();
                        Log.d("MapsActivity", "url = " + googlePlaceUrl.toString());
                        dataTransfer[0] = mMap;
                        dataTransfer[1] = url;
                        Log.d("onClick", url);
                        getNearbyPlacesData.execute(dataTransfer);
                        Toast.makeText(MapsActivity.this, "Showing Nearby Theaters", Toast.LENGTH_SHORT).show();

                        return true;
                }
                return false;
            }

    };

    public void onClick(@NonNull MenuItem item)
    {
        switch(item.getItemId()) {
            case R.id.navigation_normal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                Toast.makeText(MapsActivity.this, "Showing Normal View", Toast.LENGTH_SHORT).show();
              //  R.id.navigation_normal.setBackgroundColor("BLUE");

                //  return true;
                break;
            case R.id.navigation_satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                Toast.makeText(MapsActivity.this, "Showing Satellite View", Toast.LENGTH_SHORT).show();
             //   return true;
                break;
            case R.id.navigation_terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                Toast.makeText(MapsActivity.this, "Showing Terrain View", Toast.LENGTH_SHORT).show();
             //   return true;
                break;
        }
      //  return false;
    }


            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();

        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            bulidGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show();
                }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //for setting on the location
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        // boolean isGPSEnabled = locationManager
        //.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!isGPSEnabled)
        {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }
    protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location;
        if(currentLocationmMarker != null)
        {
            currentLocationmMarker.remove();

        }
        Log.d("lat = ",""+latitude);
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng).title("Current Position").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentLocationmMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        Toast.makeText(MapsActivity.this, "Your Current Location", Toast.LENGTH_LONG).show();

        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }
    }
    private String getUrl(double latitude , double longitude , String nearbyPlace)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&keyword="+nearbyPlace);
        //googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyBi7Lrmcel1REWB_APN5egdgw24IJcNDqk");

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return (googlePlaceUrl.toString());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }
    /* private boolean isGooglePlayServicesAvailable()
     {
         int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
         if (ConnectionResult.SUCCESS == status)
         {
             return true;
         }
         else
         {
             GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
             return false;
         }
     }*/
    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

   public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MapsActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

  }
