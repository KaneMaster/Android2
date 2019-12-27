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
            DataHelper.TABLE_CITY
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

    public boolean findCity(String city){
        Cursor cursor2 = database.query(DataHelper.TABLE_NAME, new String[]{ DataHelper.TABLE_CITY}, DataHelper.TABLE_CITY + " = ?" , new String[]{city}, null, null, DataHelper.TABLE_ID + " desc");
        int cnt = cursor2.getCount();
        cursor2.close();
        return (cnt > 0)?true: false;
    }


    @Override
    public void close() throws IOException {
        cursor.close();
    }

    private Note cursorToNote(){
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setCity(cursor.getString(1));
        return note;
    }

    public Note getPosition(int position){
        cursor.moveToPosition(position);
        return cursorToNote();
    }

    public int getCount(){
        return cursor.getCount();
    }

    public int getCountTop10(){
        return (cursor.getCount() > 10) ? 10 : cursor.getCount();
    }
}
