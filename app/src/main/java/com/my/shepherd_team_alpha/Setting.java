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
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;


import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


public class Setting extends AppCompatActivity  {
    private static final String TAG ="setting";
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
        SharedPreferences preferences= getSharedPreferences("general", Context.MODE_PRIVATE);
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
        String real_path = filePath;//getPath(fileUri);

        File source = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +real_path);
        Log.i(TAG, "onBrowse: "+real_path);
//        String filename = uri.getLastPathSegment();
        //File destination = new File("//data/data/com.my.shepherd_team_alpha/databases/mydb.db");//(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CustomFolder/" + filename);
        File destination = new File(this.getDatabasePath("mydb.db").getAbsolutePath());
        try {
            copy(source,destination);
        }
        catch(Exception err){
            // post to log
            Log.e(TAG, "copy failed " );
        }

    }


    public boolean importDatabase(String dbPath,String DB_FILEPATH) throws IOException {

        // Close the SQLiteOpenHelper so it will commit the created empty
        // database to internal storage.
        MyDbHandler dbHandler= new MyDbHandler(null,null,null,1);
        dbHandler.close();
        File newDb = new File(dbPath);
        File oldDb = new File(DB_FILEPATH);
        if (newDb.exists()) {
            FileUtils.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
            // Access the copied database so SQLiteHelper will cache it and mark
            // it as created.
            dbHandler.getWritableDatabase().close();
            return true;
        }
        return false;
    }

    public String getPath(Uri uri) {

        String path = null;
        String[] projection = { MediaStore.Files.FileColumns.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if(cursor == null){
            path = uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(projection[0]);
            path = cursor.getString(column_index);
            cursor.close();
        }

        return ((path == null || path.isEmpty()) ? (uri.getPath()) : path);
    }

    private void copy(File source, File destination) throws IOException {

        FileChannel in = new FileInputStream(source).getChannel();
        FileChannel out = new FileOutputStream(destination).getChannel();

        try {
            in.transferTo(0, in.size(), out);
        } catch(Exception err){
            // post to log
            Log.e(TAG, "copy failed " );
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
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