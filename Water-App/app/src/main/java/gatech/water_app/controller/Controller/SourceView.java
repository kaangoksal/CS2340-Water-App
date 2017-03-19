package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import gatech.water_app.R;
import gatech.water_app.model.Report;
import gatech.water_app.model.ServerConnector;
import gatech.water_app.model.User;
import gatech.water_app.model.WaterReportTask;

/**
 * Created by John on 3/1/2017.
 * Activity that contains all water reports *needs working report database first*
 * This views water source reports.
 */

public class SourceView extends AppCompatActivity {
    ListView listView ;
    User loginUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_water_reports);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        loginUser =(User)extras.getSerializable("user");

        Log.d("SourceView", "User received Email = " + loginUser.getEmail() + " " + loginUser.getPassword() );
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


        String[] values = WaterReportTask.getSourceListString();

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

        new HTTPGetReportTask().execute(loginUser);
    }

    private class HTTPGetReportTask extends AsyncTask<Object, Integer, JSONArray> {
        protected JSONArray doInBackground(Object[] params) {
            try {
                User castedUser = (User) params[0];

                return ServerConnector.getReports(castedUser);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(JSONArray result) {
            Log.d("SourceView", "It appears that the request was successfull ");
        }
    }


    public void backFromReportView(View view) {
        Intent intent = new Intent(this, LandingPage.class);
        intent.putExtra("user", loginUser);
        startActivity(intent);
    }

}


