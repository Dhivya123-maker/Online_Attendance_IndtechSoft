package com.example.online_attendance_indtechsoft;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Dummy extends AppCompatActivity {


    Button showaddress;
    TextView textView;
    public LocationManager locationManager;
    public LocationListener locationListener = new MyLocationListener();
    String lat, longi;
    private boolean gps_enable = false;
    private boolean network_enable = false;
    Geocoder geocoder;
    List<Address> myaddress;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        showaddress = findViewById(R.id.btnShowAddress);
        textView = findViewById(R.id.textView);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        showaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getlocation();
            }
        });

        checklocationpermission();


    }

    class MyLocationListener implements LocationListener {


        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (location != null) {
                locationManager.removeUpdates(locationListener);

                lat = "" + location.getLatitude();
                longi = "" + location.getLongitude();

                geocoder = new Geocoder(Dummy.this,Locale.getDefault());
                try {
                    myaddress = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String address = myaddress.get(0).getAddressLine(0);
                textView.setText(address);

            }
        }

        @Override
        public void onLocationChanged(@NonNull List<Location> locations) {
            LocationListener.super.onLocationChanged(locations);
        }

        @Override
        public void onFlushComplete(int requestCode) {
            LocationListener.super.onFlushComplete(requestCode);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LocationListener.super.onStatusChanged(provider, status, extras);
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            LocationListener.super.onProviderEnabled(provider);
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }
    }

    public void getlocation() {

        try {
            gps_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {

        }
        try {
            network_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {

        }

        if (!gps_enable && !network_enable) {

            AlertDialog.Builder builder = new AlertDialog.Builder(Dummy.this);
            builder.setTitle("Attention");
            builder.setMessage("please turn on your location");

            builder.create().show();


        }

        if (gps_enable) {
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
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }

        if(network_enable){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        }

    }

    private boolean checklocationpermission(){
        int location = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        int location2 = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION);

      List<String> listpermission = new ArrayList<>();

      if(location!=PackageManager.PERMISSION_GRANTED){
          listpermission.add(Manifest.permission.ACCESS_FINE_LOCATION);

      }
        if(location2!=PackageManager.PERMISSION_GRANTED){
            listpermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        }
        if(!listpermission.isEmpty()){

            ActivityCompat.requestPermissions(this,listpermission.toArray(new String[listpermission.size()]),
                    1);

        }

        return  true;

    }

}
//package com.example.onlineattendance;
//
//import static android.Manifest.permission.ACCESS_FINE_LOCATION;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import android.Manifest;
//import android.content.IntentSender;
//import android.content.pm.PackageManager;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.google.android.gms.common.api.ResolvableApiException;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResponse;
//import com.google.android.gms.location.SettingsClient;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//
//public class Dummy extends AppCompatActivity {
//
//    private TextView textView;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dummy);
//
//
//        textView = findViewById(R.id.lat);
//
//        String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
//        textView.setText(currentTime);

//        "yyyy.MM.dd G 'at' HH:mm:ss z" ---- 2001.07.04 AD at 12:08:56 PDT
//        "hh 'o''clock' a, zzzz" ----------- 12 o'clock PM, Pacific Daylight Time
//        "EEE, d MMM yyyy HH:mm:ss Z"------- Wed, 4 Jul 2001 12:08:56 -0700
//        "yyyy-MM-dd'T'HH:mm:ss.SSSZ"------- 2001-07-04T12:08:56.235-0700
//        "yyMMddHHmmssZ"-------------------- 010704120856-0700
//        "K:mm a, z" ----------------------- 0:08 PM, PDT
//        "h:mm a" -------------------------- 12:08 PM
//        "EEE, MMM d, ''yy" ---------------- Wed, Jul 4, '01


//    }
//
//
//
//
//}

