package com.my.shepherd_team_alpha;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class MyDbHandler extends SQLiteOpenHelper {
    SharedPreferences preferences;
    private static final String TAG = "MyActivity";
    public static final String db_name= /*"//data/data/com.my.shepherd_team_alpha/databases/"*/  "mydb.db";//System.getenv("EXTERNAL_STORAGE")+"/mydb.db";//Environment.getExternalStorageDirectory()+"mydb.db";// /storage/emulated/0/
    public static final String table_name = "mytable";
    public static final String studing_year= "studing_year";
    public static final int db_version=1;
    public static final String kid_name="kid_name";
    public static final String _id="_id";
    public static final String region="region";

    public MyDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, db_name, factory, db_version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //preferences= getSharedPreferences("settings", Context.MODE_PRIVATE);
        String query = "CREATE TABLE "+table_name+" ( "+_id+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+kid_name+" TEXT ,"+studing_year+" INTEGER ,"+region+" TEXT );";
//        SharedPreferences preferences= getSharedPreferences("general", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//
//        editor.putString("region",n1.getText().toString());
//        editor.putString("personName",n2.getText().toString());
//        editor.putString("dpPath",n3.getText().toString());
//        editor.apply();
//        Toast.makeText(Setting.this,"Changes saved !",Toast.LENGTH_LONG).show();
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_name);
        onCreate(db);
    }

    public  void addNewKid (String his_name,String his_studing_year,String his_region){
        Log.d(TAG,db_name);
        ContentValues details= new ContentValues();
        details.put(kid_name,his_name);
        details.put(studing_year,his_studing_year);
        details.put(region,his_region);
        SQLiteDatabase db= getWritableDatabase();
        db.insert(table_name,null,details);
        db.close();

    }

    public String getAllKids (){
        SQLiteDatabase db=getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * From "+table_name+";",null);
        String data= "" ;
        String row="";
        c.moveToFirst();
        while (!c.isAfterLast()) {
            row = c.getString(c.getColumnIndex(kid_name));
            if (row != null){
                data += (row + "\n");
            }
            c.moveToNext();
        }
        db.close();
        return data;
    }

    public String getAllRows (){ // to be continue
        SQLiteDatabase db=getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * From "+table_name+";",null);
        String data= null ;
        String row=null;
        c.moveToFirst();
        while (!c.isAfterLast()) {
            row = c.getString(c.getColumnIndex(kid_name));
            if (row != null){
                data += (row + "\n");
            }
            c.moveToNext();
        }
        db.close();
        return data;
    }
}
