package com.example.jenny.skincare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jenny on 6/21/16.
 */
public class HistoryActivity extends Activity  {
    public final static String SHARED_PREFS_FILE = "Records";
    public HistoryAdapter adapter;
    ArrayList<Record> arrayOfUsers;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    ListView listView;

    Button clear,compare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        clear=(Button)findViewById(R.id.clear_his);
        compare=(Button) findViewById(R.id.compare);
        prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        editor =prefs.edit();
        populateUsersList();

        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, CompareActivity.class);
                startActivity(intent);
            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                adapter=new HistoryAdapter(getBaseContext(),new ArrayList<Record>());
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //populate();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //populate();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //populate();
    }



    private void populateUsersList() {

        try {
            arrayOfUsers = (ArrayList<Record>) ObjectSerializer.deserialize(prefs.getString("re", ObjectSerializer.serialize(new ArrayList<Record>())));
        } catch (IOException e) {
            e.printStackTrace();
        }catch(ClassCastException e){
            e.printStackTrace();
        }
        // Create the adapter to convert the array to views
        adapter = new HistoryAdapter(this,arrayOfUsers);
        
        // Attach the adapter to a ListView
        listView = (ListView)findViewById(R.id.history);
        listView.setAdapter(adapter);
    }
}
