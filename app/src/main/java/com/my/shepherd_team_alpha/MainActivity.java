package com.my.shepherd_team_alpha;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.mainTitle);
        //toolbar.setTitleAlignment(View.TEXT_ALIGNMENT_CENTER);      // bug here where trying to align title
        setSupportActionBar(toolbar);
        Button goto_new_day         =(Button) findViewById(R.id.new_day);
        Button goto_last_day        =(Button) findViewById(R.id.last_day);
        Button goto_new_kid         =(Button) findViewById(R.id.new_kid);
        Button goto_edit_kid        =(Button) findViewById(R.id.edit_kid);
        Button goto_searchForKid    =(Button) findViewById(R.id.searchForKid);
        Button goto_searchUsingQuery=(Button) findViewById(R.id.searchUsingQuery);
        Button goto_setting         =(Button) findViewById(R.id.setting);


    }

    public void on_goto_new_kid(View view ){
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    public void on_goto_setting(View view ){
        Intent i = new Intent(this,Setting.class);
        startActivity(i);
    }

}