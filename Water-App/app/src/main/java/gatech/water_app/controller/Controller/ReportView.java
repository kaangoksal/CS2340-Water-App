package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import gatech.water_app.R;
import gatech.water_app.model.Report;
import gatech.water_app.model.WaterReportTask;

/**
 * Created by John on 3/1/2017.
 * Activity that contains all water reports *needs working report database first*
 * This is just filled with examples to see if list view works
 */

public class ReportView extends AppCompatActivity {
    ListView listView ;
    String username;
    String password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_water_reports);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        password = extras.getString("pass");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.waterlist);
//        assert listView != null;
//        //Step 2.  Hook up the adapter to the view
//        setupRecyclerView((RecyclerView) recyclerView);

//        // Defined Array values to show in ListView
//        String[] values = new String[] { "Android List View",
//                "Adapter implementation",
//                "Simple List View In Android",
//                "Create List View Android",
//                "Android Example",
//                "List View Source Code",
//                "List View Array Adapter",
//                "Android Example List View"
//        };

        String[] values = WaterReportTask.getListString();

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });
    }

    public void backFromReportView(View view) {
        Intent intent = new Intent(this, LandingPage.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("pass", password);
        bundle1.putString("username", username);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

}


