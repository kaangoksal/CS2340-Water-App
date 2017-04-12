package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import gatech.water_app.model.WaterPurityReport;

/**
 * Created by John on 3/1/2017.
 * Activity that contains all purity reports *needs working report database first*
 * only water purity source report
 */

public class PurityView extends AppCompatActivity {
    private ListView listView ;
    private User loginUser;

    /**
     * This is initialized when the page is started.
     * @param savedInstanceState the saved instance to be created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_purity_reports);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
//        username = extras.getString("username");
//        password = extras.getString("pass");

        loginUser =(User)extras.getSerializable("user");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar made = Snackbar.make(view,
                        "Replace with your own action", Snackbar.LENGTH_LONG);
                Snackbar action = made.setAction("Action", null);
                action.show();
            }
        });

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.puritylist);

        new HTTPGetPurityReportTask().execute(loginUser);

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
    }
    /**
     * This is a class for the async HTTP Request.
     */
    private class HTTPGetPurityReportTask extends AsyncTask<Object, Integer,
            ArrayList<WaterPurityReport>> {
        @Override
        protected ArrayList<WaterPurityReport> doInBackground(Object[] params) {
            try {
                User castedUser = (User) params[0];

                return ServerConnector.getPurityReports(castedUser);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<WaterPurityReport> result) {
            if (result != null) {
                populateList(result);
                Log.d("PurityView", "It appears that the request was successful ");
            }
        }
    }

    /**
     * Populates the list for WaterPurityReports
     * @param WaterPurityReportList the list of water purity reports to be populated
     */
    private void populateList(List<WaterPurityReport> WaterPurityReportList) {
        String[] newList = new String[WaterPurityReportList.size()];

        for (int i = 0; i < WaterPurityReportList.size(); i++) {
//                JSONObject reportJsonChild = reportJSONArray.getJSONObject(i);
            WaterPurityReport report = WaterPurityReportList.get(i);
            newList[i] = report.toString();
            Log.e("PurityView", "Populating the list " + report.toString());
        }
//            values = newList;

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, newList);


        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }

    /**
     * Goes back to the previous screen
     * @param view this is supplied by the android sdk
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