package com.example.jenny.skincare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jenny on 14/7/2016.
 */
public class HistoryAdapter extends ArrayAdapter<Record> {

    public HistoryAdapter(Context context, ArrayList<Record> records) {
        super(context, 0, records);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
       Record records = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.record, parent, false);
        }
        // Lookup view for data population
        TextView time = (TextView) convertView.findViewById(R.id.firstLine);
        TextView improve = (TextView) convertView.findViewById(R.id.secondLine);
        ImageView skin=(ImageView)convertView.findViewById(R.id.color);
        // Populate the data into the template view using the data object
        skin.setBackgroundColor(records.color);
        time.setText(records.time);
        improve.setText(records.improve);
        // Return the completed view to render on screen
        return convertView;
    }
}
