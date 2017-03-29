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
import org.json.JSONException;
import org.json.JSONObject;

import gatech.water_app.R;
import gatech.water_app.model.Report;
import gatech.water_app.model.ServerConnector;
import gatech.water_app.model.Title;
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
    String[] values;
    ArrayAdapter<String> listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_water_reports);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        loginUser =(User)extras.getSerializable("user");

        Log.d("SourceView", "User received Email = " + loginUser.getEmail() + " " + loginUser.getPassword() );



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.waterlist);


        values = WaterReportTask.getSourceListString();



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
            if (result != null) {
                populateList(result);
                Log.d("SourceView", "It appears that the request was successfull ");
            }
        }
    }

    public void populateList(JSONArray reportJSONArray) {
        String[] newList = new String[reportJSONArray.length()];
        try {
            for (int i = 0; i < reportJSONArray.length(); i++) {
                JSONObject reportJsonChild = reportJSONArray.getJSONObject(i);
                newList[i] = reportJsonChild.toString();
                Log.e("SourceView", "Populating the list " + reportJsonChild.toString());
            }
//            values = newList;

            // Define a new Adapter
            // First parameter - Context
            // Second parameter - Layout for the row
            // Third parameter - ID of the TextView to which the data is written
            // Forth - the Array of data
            listViewAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, newList);


            // Assign adapter to ListView
            listView.setAdapter(listViewAdapter);
        } catch (JSONException e) {
            Log.e("SourceView", "Encountered a problem while populating the list");
        }



    }
    public void backFromReportView(View view) {

        if (loginUser.getTitle().equals(Title.USER)) {
//            loginUser = dbuser;
            Intent intent = new Intent(this, LandingPage.class);
            Bundle bundle1 = new Bundle();
            intent.putExtras(bundle1);
            intent.putExtra("user", loginUser);
            startActivity(intent);
        } else if (loginUser.getTitle().equals(Title.WORKER)) {
//            loginUser = dbuser;
            Intent intent = new Intent(this, WorkerLandingPage.class);
            intent.putExtra("user", loginUser);
            startActivity(intent);
        } else if (loginUser.getTitle().equals(Title.MANAGER)) {
//            loginUser = dbuser;
            Intent intent = new Intent(this, ManagerLandingPage.class);
            intent.putExtra("user", loginUser);
            startActivity(intent);
        }
    }

}


