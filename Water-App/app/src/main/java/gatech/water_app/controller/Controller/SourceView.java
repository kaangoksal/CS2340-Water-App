package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gatech.water_app.R;
import gatech.water_app.model.ServerConnector;
import gatech.water_app.model.Title;
import gatech.water_app.model.User;
import gatech.water_app.model.WaterSourceReport;

/**
 * Created by John on 3/1/2017.
 * Activity that contains all water reports *needs working report database first*
 * This views water source reports.
 */

public class SourceView extends AppCompatActivity {
    private ListView listView ;
    private User loginUser;

    /**
     * This is initialized when the page starts
     * @param savedInstanceState supplied by android
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_water_reports);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        loginUser =(User)extras.getSerializable("user");

        assert loginUser != null;
        Log.d("SourceView", "User received Email = " + loginUser.getEmail() + " "
                + loginUser.getPassword() );



        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.waterlist);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Position :"+ position +"  ListItem : " +itemValue , Toast.LENGTH_LONG);
                toast.show();

            }

        });

        new HTTPGetReportTask().execute(loginUser);
    }

    /**
     * This class is used for the async HTTP request.
     */
    private class HTTPGetReportTask extends
            AsyncTask<Object, Integer, ArrayList<WaterSourceReport>> {
        @Override
        protected ArrayList<WaterSourceReport> doInBackground(Object[] params) {
            try {
                User castedUser = (User) params[0];

                return ServerConnector.getSourceReports(castedUser);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<WaterSourceReport> result) {
            if (result != null) {
                populateList(result);
                Log.d("SourceView", "It appears that the request was successful ");
            }
        }
    }

    /**
     * This populates the list of water reports on the GUI
     * @param WaterSourceReportList the list of water reports
     */
    private void populateList(List<WaterSourceReport> WaterSourceReportList) {
        String[] newList = new String[WaterSourceReportList.size()];

            for (int i = 0; i < WaterSourceReportList.size(); i++) {
//                JSONObject reportJsonChild = reportJSONArray.getJSONObject(i);
                WaterSourceReport report = WaterSourceReportList.get(i);
                newList[i] = report.toString();
                Log.e("SourceView", "Populating the list " + report
                        .toStringTemp());
            }

            // Define a new Adapter
            // First parameter - Context
            // Second parameter - Layout for the row
            // Third parameter - ID of the TextView to which the data is written
            // Forth - the Array of data
        ListAdapter listViewAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, newList);


            // Assign adapter to ListView
            listView.setAdapter(listViewAdapter);
    }

    /**
     * This is used to go to the other screens.
     * @param view supplied by android
     */
    public void backFromReportView(View view) {
        Title userTitle = loginUser.getTitle();

        if (userTitle.equals(Title.USER)) {

            Intent intent = new Intent(this, LandingPage.class);
            Bundle bundle1 = new Bundle();
            intent.putExtras(bundle1);
            intent.putExtra("user", loginUser);
            startActivity(intent);
        } else if (userTitle.equals(Title.WORKER)) {

            Intent intent = new Intent(this, WorkerLandingPage.class);
            intent.putExtra("user", loginUser);
            startActivity(intent);
        } else if (userTitle.equals(Title.MANAGER)) {

            Intent intent = new Intent(this, ManagerLandingPage.class);
            intent.putExtra("user", loginUser);
            startActivity(intent);
        }
    }

}


