package com.example.foodtruck;

import java.util.ArrayList;
import java.util.List;

import com.example.foodtruck.StopSelectActivity.menuAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
 
public class StopListViewMultipleSelectionActivity extends Activity implements
        OnClickListener {
    Button button;
    ListView listView;
    ArrayAdapter<String> adapter;
    //menuAdapter adapter;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_list);// activity_stop_list == main
        
        //Remove title bar
      	//this.requestWindowFeature(Window.FEATURE_NO_TITLE);

      	//Remove notification bar
      	//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

 
        findViewsById();
        ArrayList<TruckStop> stops = new ArrayList<TruckStop>();
        for (int i = 0; i < 6; i++){
        	TruckStop ts = new TruckStop();
        	ts.location = "Behind the Park";
        }
 
        String[] sports = {"Mitchell Drive & Oak Grove Rd", "1710 Mission Street, San Francisco, CA", "300 Rio Robles E, San Jose, CA", "100 Rio Lowes, San Jose, CA", "6028 Pitt Street, San Francisco, CA", "1342 Lake Drive, San Francisco, CA"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, sports);
        
        //adapter = new menuAdapter(getApplicationContext(), stops);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        
        button.setOnClickListener(this);
    }
 
    private void findViewsById() {
        listView = (ListView) findViewById(R.id.list);
        button = (Button) findViewById(R.id.testbutton);
    }
 
    public void onClick(View v) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }
 
        String[] outputStrArr = new String[selectedItems.size()];
 
        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
        }
 
        //Toast.makeText(getApplicationContext(), outputStrArr.toString(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), StopSelectActivity.class);
 
        // Create a bundle object
        //Bundle b = new Bundle();
        //b.putStringArray("selectedItems", outputStrArr);
 
        // Add the bundle to the intent.
        //intent.putExtras(b);
 
        // start the ResultActivity
        startActivity(intent);
    }
    
}