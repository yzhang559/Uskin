package com.example.jenny.skincare;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Jenny on 6/22/16.
 */
public class SettingTab1 extends Fragment {

    private RadioButton r;
    private View view;


    public SettingTab1() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.setting_tab,container, false);
        Button bsave = (Button) view.findViewById(R.id.save);
        final EditText mEdit = (EditText) view.findViewById(R.id.name);
        final EditText mAge=(EditText)view.findViewById(R.id.age);
        final RadioGroup Gender=(RadioGroup)view.findViewById(R.id.Gender);


        bsave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int selectedId = Gender.getCheckedRadioButtonId();
                r=(RadioButton) view.findViewById (selectedId);

                SharedPreferences preferences = getActivity().getPreferences(0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("name", mEdit.getText().toString());
                editor.putString("age",mAge.getText().toString());
                editor.remove("gender");
                editor.putString("gender", r.getText().toString());
                editor.commit();

                Toast.makeText(getContext(),"quit to save the information",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }



}
