package com.example.jenny.skincare;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jenny on 6/22/16.
 */
public class SettingTab2 extends Fragment{


    public SettingTab2() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences preferences = getActivity().getPreferences(0);
        View view = inflater.inflate(R.layout.setting_tab2, container, false);

        TextView mText = (TextView) view.findViewById(R.id.name2);
        mText.setText((preferences.getString("name", " ")));

        TextView mAge=(TextView) view.findViewById(R.id.age2);
        mAge.setText(preferences.getString("age"," "));

        TextView mGender=(TextView)view.findViewById(R.id.gender);
        mGender.setText(preferences.getString("gender"," "));


        return view;
    }

}

