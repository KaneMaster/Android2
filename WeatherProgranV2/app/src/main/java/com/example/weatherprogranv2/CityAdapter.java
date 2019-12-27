package com.example.weatherprogranv2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    public CityAdapter(String[] data) {
        this.data = data;
    }

    String [] data;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder((TextView)v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.textView.setText(data[position]);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = holder.textView.getText().toString();
                Intent itn = new Intent();
                itn.setClass( view.getContext(), SecondActivity.class);
                Parcel p = new Parcel();
                p.setCoord(false);
                p.setCity(city);
                p.setInx(Arrays.asList(data).indexOf(city));
                Singleton_Data sd = Singleton_Data.Create();
                sd.setCityName(city);
                sd.setCoord(false);
                itn.putExtra("INFO", p);
                view.getContext().startActivity(itn);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;

        public ViewHolder(TextView v){
            super(v);
            textView = v;
        }
    }
}
