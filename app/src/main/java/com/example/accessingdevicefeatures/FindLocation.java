package com.example.accessingdevicefeatures;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

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
}