package com.example.weatherprogranv2.bd;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Closeable;
import java.io.IOException;

public class DataReader implements Closeable {
    private final SQLiteDatabase database;
    private Cursor cursor;
    private String[] allColumns = {
            DataHelper.TABLE_ID,
            DataHelper.TABLE_CITY,
            DataHelper.TABLE_WEATHER
    };


    public DataReader (SQLiteDatabase database) {
        this.database = database;
    }

    public void open(){
        query();
        cursor.moveToFirst();
    }

    private void query(){
       cursor = database.query(DataHelper.TABLE_NAME, allColumns, null,null, null, null, DataHelper.TABLE_ID + " desc");
    }

    @Override
    public void close() throws IOException {
        cursor.close();
    }

    private Note cursorToNote(){
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setCity(cursor.getString(1));
        note.setTemp(cursor.getString(2));
        return note;
    }

    public Note getPosition(int position){
        cursor.moveToPosition(position);
        return cursorToNote();
    }

    public int getCount(){
        return cursor.getCount();
    }
}
