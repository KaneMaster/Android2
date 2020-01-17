package com.example.weatherprogranv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentCityInfo extends Fragment {
    public static FragmentCityInfo create(Parcel parcel){
        FragmentCityInfo fragment = new FragmentCityInfo();
        Bundle args = new Bundle();
        args.putSerializable("INFO", parcel);
        fragment.setArguments(args);
        return fragment;
    }

    public Parcel getParcel() {
        return (Parcel) getArguments().getSerializable("INFO");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.fragment_city_info, container, false);
        TextView tv = vw.findViewById(R.id.city_name);
        TextView lab = vw.findViewById(R.id.city_name_label);
        Parcel parcel = getParcel();
        if (parcel.getCoord()){
            Singleton_Data sd = Singleton_Data.Create();
            lab.setText(getResources().getString(R.string.city_label1));
            tv.setText(new StringBuilder().append("Ширина: ").append(sd.getLat()).append(" Долгота: ").append(sd.getLon()).toString());
            tv.setVisibility(View.VISIBLE);
        } else {
            lab.setText(getResources().getString(R.string.city_label2));
            tv.setVisibility(View.VISIBLE);
            tv.setText(parcel.getCity());
        }

        return vw;
    }
}
