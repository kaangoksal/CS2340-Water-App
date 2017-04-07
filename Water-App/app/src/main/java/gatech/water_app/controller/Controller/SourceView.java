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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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

        Bundle extras = getIntent().getExtras();
        loginUser =(User)extras.getSerializable("user");

        Log.d("SourceView", "User received Email = " + loginUser.getEmail() + " " + loginUser.getPassword() );



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
                Toast.makeText(getApplicationContext(),
                        "Position :"+ position +"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });

        new HTTPGetReportTask().execute(loginUser);
    }

    /**
     * This class is used for the async HTTP request.
     */
    private class HTTPGetReportTask extends AsyncTask<Object, Integer, ArrayList<WaterSourceReport>> {
        protected ArrayList<WaterSourceReport> doInBackground(Object[] params) {
            try {
                User castedUser = (User) params[0];

                return ServerConnector.getSourceReports(castedUser);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(ArrayList<WaterSourceReport> result) {
            if (result != null) {
                populateList(result);
                Log.d("SourceView", "It appears that the request was successfull ");
            }
        }
    }

    /**
     * This populates the list of water reports on the GUI
     * @param WaterSourceReportList the list of water reports
     */
    private void populateList(ArrayList<WaterSourceReport> WaterSourceReportList) {
        String[] newList = new String[WaterSourceReportList.size()];

            for (int i = 0; i < WaterSourceReportList.size(); i++) {
//                JSONObject reportJsonChild = reportJSONArray.getJSONObject(i);
                newList[i] = WaterSourceReportList.get(i).toString();
                Log.e("SourceView", "Populating the list " + WaterSourceReportList.get(i).toStringTemp());
            }

            // Define a new Adapter
            // First parameter - Context
            // Second parameter - Layout for the row
            // Third parameter - ID of the TextView to which the data is written
            // Forth - the Array of data
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, newList);


            // Assign adapter to ListView
            listView.setAdapter(listViewAdapter);
    }

    /**
     * This is used to go to the other screens.
     * @param view supplied by android
     */
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


