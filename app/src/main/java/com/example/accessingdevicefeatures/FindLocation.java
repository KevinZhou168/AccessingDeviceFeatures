package com.example.accessingdevicefeatures;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FindLocation extends AppCompatActivity {
    //Initialize variables
    Button btLocation;
    TextView textViewLat, textViewLong, textViewCount, textViewLoc, textViewAdd;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location);
        //Assign variable
        btLocation = findViewById(R.id.bt_location);
        textViewLat = findViewById(R.id.text_view_lat);
        textViewLong = findViewById(R.id.text_view_long);
        textViewCount = findViewById(R.id.text_view_count);
        textViewLoc = findViewById(R.id.text_view_loc);
        textViewAdd = findViewById(R.id.text_view_add);

        //Initialize fusedLocationProvider
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check permission
                if (ActivityCompat.checkSelfPermission(FindLocation.this,
                         Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }
                else{
                    //When permission denied
                    ActivityCompat.requestPermissions(FindLocation.this
                            ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });

    }
    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null){
                    try{
                        //Initialize geoCoder
                        Geocoder geocoder = new Geocoder(FindLocation.this, Locale.getDefault());
                        //Initialize address list
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);
                        //Set Latitude on TextView
                        textViewLat.setText(Html.fromHtml(
                                "<font color = '#6200EE'><b>Latitude: </b><br></font>"
                                        + addresses.get(0).getLatitude()
                        ));
                        textViewLong.setText(Html.fromHtml(
                                "<font color = '#6200EE'><b>Longitude: </b><br></font>"
                                        + addresses.get(0).getLongitude()
                        ));
                        //Set country name
                        textViewCount.setText(Html.fromHtml(
                                "<font color = '#6200EE'><b>Country Name: </b><br></font>"
                                        + addresses.get(0).getCountryName()
                        ));
                        textViewLoc.setText(Html.fromHtml(
                                "<font color = '#6200EE'><b>Locality: </b><br></font>"
                                        + addresses.get(0).getLocality()
                        ));
                        //Set address
                        textViewAdd.setText(Html.fromHtml(
                                "<font color = '#6200EE'><b>Address: </b><br></font>"
                                        + addresses.get(0).getAddressLine(0)
                        ));

                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}