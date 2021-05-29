package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final int version=1;
    public static final String name="SelectedCountry.db";

    public DBHelper(Context context)
    {
        super(context,name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE Country (Name TEXT PRIMARY KEY)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("d","on upgarde");
        db.execSQL("DROP TABLE IF EXISTS Country");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("d","on downgarde");
        onUpgrade(db,newVersion,oldVersion);
    }
}
