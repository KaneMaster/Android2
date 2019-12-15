package com.example.weatherprogranv2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentCharacter extends Fragment {
    public static FragmentCharacter create(Parcel parcel){
        FragmentCharacter fragment = new FragmentCharacter();
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
        View vw = inflater.inflate(R.layout.fragment_char, container, false);
        Parcel parcel = getParcel();
        if (parcel.isWet()) {
            vw.findViewById(R.id.label_wet).setVisibility(View.VISIBLE);
            vw.findViewById(R.id.val_wet).setVisibility(View.VISIBLE);
        } else  {
            vw.findViewById(R.id.label_wet).setVisibility(View.INVISIBLE);
            vw.findViewById(R.id.val_wet).setVisibility(View.INVISIBLE);
        }
        if (parcel.isPressure()) {
            vw.findViewById(R.id.label_pressure).setVisibility(View.VISIBLE);
            vw.findViewById(R.id.val_pressure).setVisibility(View.VISIBLE);
        } else  {
            vw.findViewById(R.id.label_pressure).setVisibility(View.INVISIBLE);
            vw.findViewById(R.id.val_pressure).setVisibility(View.INVISIBLE);
        }
        if (parcel.isWind()) {
            vw.findViewById(R.id.label_wind).setVisibility(View.VISIBLE);
            vw.findViewById(R.id.val_wind).setVisibility(View.VISIBLE);
        } else  {
            vw.findViewById(R.id.label_wind).setVisibility(View.INVISIBLE);
            vw.findViewById(R.id.val_wind).setVisibility(View.INVISIBLE);
        }
        Singleton_Data sd = Singleton_Data.Create();
        TextView tv = vw.findViewById(R.id.val_wind);
        tv.setText(String.valueOf( Math.round(sd.getSpeed()*100) / 100.0) + " " + getString(R.string.val_wind));
        tv = vw.findViewById(R.id.val_pressure);
        tv.setText(String.valueOf( Math.round( sd.getPressure() * 75.0062)/100.0) + " " + getString(R.string.val_pressure));
        tv = vw.findViewById(R.id.val_wet);
        tv.setText(String.valueOf(sd.getWet()) + " " + getString(R.string.val_wet));

        return vw;
    }
}
