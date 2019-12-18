package com.example.weatherprogranv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatherprogranv2.bd.DataReader;
import com.example.weatherprogranv2.bd.DataSource;
import com.example.weatherprogranv2.bd.Note;

import java.io.IOException;
import java.sql.SQLException;

public class FragmentCityHistory extends Fragment {

    DataSource datasource;

    public static FragmentCityHistory create() {
        FragmentCityHistory fragment = new FragmentCityHistory();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.fragment_weather_history, container, false);
        datasource = new DataSource(getActivity());
        try {
            datasource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataReader reader = datasource.getReader();

        int n = (reader.getCount() > 3)?3:reader.getCount();
        Note note;
        TextView tw;
        for (int i = 0; i < n; i++){
           note = reader.getPosition(i);
           switch (i){
               case 0:
                   tw = vw.findViewById(R.id.city1);
                   tw.setText(note.getCity());
                   tw.setVisibility(View.VISIBLE);
                   tw = vw.findViewById(R.id.temp1);
                   tw.setText(note.getTemp());
                   tw.setVisibility(View.VISIBLE);
                   vw.findViewById(R.id.title2).setVisibility(View.VISIBLE);
                   break;
               case 1:
                   tw = vw.findViewById(R.id.city2);
                   tw.setText(note.getCity());
                   tw.setVisibility(View.VISIBLE);
                   tw = vw.findViewById(R.id.temp2);
                   tw.setText(note.getTemp());
                   tw.setVisibility(View.VISIBLE);
                   break;
               case 2:
                   tw = vw.findViewById(R.id.city3);
                   tw.setText(note.getCity());
                   tw.setVisibility(View.VISIBLE);
                   tw = vw.findViewById(R.id.temp3);
                   tw.setText(note.getTemp());
                   tw.setVisibility(View.VISIBLE);
                   break;           }
        }
        try {
            datasource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vw;
    }
}
