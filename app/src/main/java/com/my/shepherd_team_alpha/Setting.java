package com.my.shepherd_team_alpha;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
//import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Setting extends AppCompatActivity  {
    TextInputEditText n1 ;
    TextInputEditText n2 ;
    TextInputEditText n3;
    private TextView tvItemPath;
    private Uri fileUri;
    public static final int PICKFILE_RESULT_CODE = 1;
    private Button btnChooseFile;
    private String filePath;
    ActivityResultLauncher<Intent> someActivityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        n1=(TextInputEditText)findViewById(R.id.n1);
        n2=(TextInputEditText)findViewById(R.id.n2);
        n3=(TextInputEditText)findViewById(R.id.n3);
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            // doSomeOperations();
                            fileUri = data.getData();
                            filePath = fileUri.getPath();
                            n3.setText(filePath);
                        }


                    }
                });
    }

    public void onSave(View v){
        SharedPreferences preferences= getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("region",n1.getText().toString());
        editor.putString("personName",n2.getText().toString());
        editor.putString("dpPath",n3.getText().toString());

        editor.apply();
        Toast.makeText(Setting.this,"Changes saved !",Toast.LENGTH_LONG).show();

    }
    public void onCancel(View v){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

//    public void openSomeActivityForResult() {
//        Intent intent = new Intent(this, SomeActivity.class);
//        someActivityResultLauncher.launch(intent);
//    }

    public void onBrowse(View v){
        //some code here
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        someActivityResultLauncher.launch(chooseFile);

    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//
//            case PICKFILE_RESULT_CODE:
//
//                if (resultCode == -1) {
//
//                    fileUri = data.getData();
//
//                    filePath = fileUri.getPath();
//
//                    //tvItemPath.setText(filePath);
//
//                }
//
//
//                break;
//
//        }
//
//    }

}