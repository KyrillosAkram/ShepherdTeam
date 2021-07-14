package com.my.shepherd_team_alpha;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    TextInputEditText locationText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_register);
        setSupportActionBar(toolbar);
        Button submit =(Button)findViewById(R.id.submit);
        Button show= (Button)findViewById(R.id.showdb);
        Button back= (Button)findViewById(R.id.register_back);
        TextInputEditText kidname=findViewById(R.id.x);
        Spinner region = (Spinner) findViewById(R.id.region_spinner);
        Spinner class_spinner = (Spinner) findViewById(R.id.class_spinner);
        TextView text= (TextView) findViewById(R.id.textview);
        SharedPreferences preferences=getSharedPreferences("setting", Context.MODE_PRIVATE);
        String dppath=preferences.getString("dpPath","");
        MyDbHandler handler = new MyDbHandler(this,dppath,null,1);
        Button getLocationbtn= (Button)findViewById(R.id.getLocationbtn);
        locationText= (TextInputEditText)findViewById(R.id.location);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(RegisterActivity.this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.addNewKid(kidname.getText().toString(),(String)class_spinner.getSelectedItem(),(String)region.getSelectedItem());
                Toast.makeText(RegisterActivity.this,kidname.getText().toString()+" Inserted !",Toast.LENGTH_LONG).show();

            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText((String) handler.getAllKids());
                kidname.setText("");
            }
        });


        getLocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(
                        ActivityCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
                        &&
                        ActivityCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED
                )
                {
                getlocation();
                }else{
                    ActivityCompat.requestPermissions(RegisterActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                                100
                            );
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            getlocation();
        } else {
            Toast.makeText(RegisterActivity.this," permission denied ",Toast.LENGTH_SHORT).show();
        }
    }

    public void on_back(View view){
        Intent i = new Intent( this ,MainActivity.class);
        startActivity(i);
    }

    @SuppressLint("MissingPermission")
    public void getlocation(){
        LocationManager locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location =task.getResult();
                    if(location != null){
                        locationText.setText(String.valueOf(location.getLatitude())+","+String.valueOf(location.getLongitude()));
                    }else {
                        LocationRequest locationRequest =new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 =locationResult.getLastLocation();
                                locationText.setText(String.valueOf(location.getLatitude())+","+String.valueOf(location.getLongitude()));
                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback , Looper.myLooper());
                    }
                }
            });
        }else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags( Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}