package com.example.weatherprogranv2.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "notes.db";
    private static final int DB_VERSION = 3;
    public static final String TABLE_NAME = "city";
    public static final String TABLE_ID = "id";
    public static final String TABLE_CITY = "cities";



    public DataHelper(@Nullable Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( "+
                TABLE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TABLE_CITY+" TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
