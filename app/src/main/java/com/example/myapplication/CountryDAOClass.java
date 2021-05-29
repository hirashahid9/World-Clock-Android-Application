package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class CountryDAOClass implements CountryDAO{
    private Context context;
    public CountryDAOClass(Context ctx){
        context = ctx;
    }

    public void save(Hashtable<String, String> attributes,DBHelper dbHelper)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues content = new ContentValues();
        Enumeration<String> keys = attributes.keys();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            Log.d("key",attributes.get(key));
            content.put(key,attributes.get(key));
        }
        long result=db.insert("Country",null,content);
        if(result==-1)
        {
            Log.d("d","Unable to save");
        }
        else
        {
            Log.d("d","saved");
        }
    }
    @Override
    public void save(Hashtable<String, String> attributes) {
        DBHelper dbHelper = new DBHelper(context);
         SQLiteDatabase db = dbHelper.getWritableDatabase();
         save(attributes,dbHelper);
    }

    public void deleteAll() {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Country",null,null);
    }

    @Override
    public void delete(String attribute) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String where="name=?";
        String arg[]={attribute};
        db.delete("Country",where,arg);
    }


    public void save(ArrayList<Hashtable<String, String>> objects) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Country",null,null);
        Log.d("ds","deleted");
        for(Hashtable<String,String> obj : objects){
            save(obj,dbHelper);
        }
    }


    public ArrayList<Hashtable<String, String>> load() {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM Country";
        Cursor cursor = db.rawQuery(query,null);
        ArrayList<Hashtable<String,String>> objects = new ArrayList<Hashtable<String, String>>();

        while(cursor.moveToNext()){
            Hashtable<String,String> obj = new Hashtable<String, String>();
            String [] columns = cursor.getColumnNames();
            for(String col : columns){
                Log.d("load", String.valueOf(cursor.getString(cursor.getColumnIndex(col))));
                obj.put(col.toLowerCase(),
                cursor.getString(cursor.getColumnIndex(col)));

            }
            objects.add(obj);
        }
        return objects;
    }

    public Hashtable<String, String> load(String id) {
        return null;
    }


}
