package com.my.shepherd_team_alpha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.my.shepherd_team_alpha.MyDbHandler;

public class RegisterActivity extends AppCompatActivity {

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
    }

    public void on_back(View view){
        Intent i = new Intent( this ,MainActivity.class);
        startActivity(i);
    }
}