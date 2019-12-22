package com.example.weatherprogranv2.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

public class DataSource implements Closeable {

    private final DataHelper dbHelper;
    private SQLiteDatabase database;



    private DataReader reader;

    public DataSource(Context context){
        dbHelper = new DataHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        reader = new DataReader(database);
        reader.open();
    }


    @Override
    public void close() throws IOException {
        database.close();
        reader.close();
    }

    public Note add(String city, String weather){
        Note note = new Note();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataHelper.TABLE_CITY, city);
        contentValues.put(DataHelper.TABLE_WEATHER, weather);
        long id = database.insert(DataHelper.TABLE_NAME, null, contentValues);
        note.setId(id);
        note.setTemp(weather);
        note.setCity(city);
        return note;
    }

    public DataReader getReader() {
        return reader;
    }
}
