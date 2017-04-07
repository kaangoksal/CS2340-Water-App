package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import gatech.water_app.R;
import gatech.water_app.model.User;
import gatech.water_app.model.WaterCondition;
import gatech.water_app.model.WaterReportTask;
import gatech.water_app.model.WaterSourceReport;
import gatech.water_app.model.WaterType;
/**
 *Controller class for submitting a normal water source report
 */
public class SubmitSourceReport extends AppCompatActivity {

    private EditText location;
    private Spinner typeWaterSpinner;
    private Spinner conditionSpinner;
    private Address address = new Address(new Locale("US"));



    private User loginUser;

    private WaterSourceReport newReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_source_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle extras = getIntent().getExtras();

        loginUser =(User)extras.getSerializable("user");
        Log.d("SubmitSourceReport", "User received, Email = " + loginUser.getEmail() + " " + loginUser.getPassword());

        address.setLatitude(0.0);
        address.setLongitude(0.0);

        TextView dateView = (TextView) findViewById(R.id.autogen);
        TextView reportNumView = (TextView) findViewById(R.id.autogen2);
        TextView reporterView = (TextView) findViewById(R.id.autogen3);
        location = (EditText) findViewById(R.id.autogen4);
        typeWaterSpinner = (Spinner) findViewById(R.id.spinner3);
        conditionSpinner = (Spinner) findViewById(R.id.spinner2);
        Button cancelButton = (Button) findViewById(R.id.water_report_cancel);


        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, WaterType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeWaterSpinner.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, WaterCondition.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(adapter2);

        newReport = new WaterSourceReport(loginUser.getEmail());

        newReport.setReportNumber(UUID.randomUUID().toString());
        dateView.setText(newReport.getDateString());
        reportNumView.setText(newReport.getReportNumber());
        reporterView.setText(newReport.getReporter());

        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }

    /**
     * Searches for the location base on the input string for the edit text field and sets location to the predicted address
     * @param view
     */
    public void searchLocation(View view) {
        if (location.getText().toString() != null && !location.getText().toString().equals("")) {
            String locationName = location.getText().toString();
            List<Address> addressList = null;
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(locationName, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            address = addressList.get(0);
            location.setText(address.getFeatureName());
        } else {
            Toast.makeText(getApplicationContext(), "Input Location", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Adds the new report the data base
     * @param view
     */
    public void submitSourceReport(View view) {
        if (address.getLatitude() != 0 && address.getLongitude() != 0) {



            newReport.setLocation(new Location(address.getFeatureName()));
            newReport.getLocation().setLatitude(address.getLatitude());
            newReport.getLocation().setLongitude(address.getLongitude());

            newReport.setCondition((WaterCondition) conditionSpinner.getSelectedItem());
            newReport.setType((WaterType) typeWaterSpinner.getSelectedItem());
            newReport.setReporter(loginUser.getEmail());



            JSONObject reportJson = newReport.toJSONObject();

            Log.d("SubmitSourceReport", "JSON Sending " + reportJson.toString());

            new HTTPSubmitReportTask().execute(loginUser,reportJson);


        } else {
            Toast.makeText(this, "Invalid address", Toast.LENGTH_SHORT).show();
        }
    }


    private class HTTPSubmitReportTask extends AsyncTask<Object, Integer, Boolean> {
        protected Boolean doInBackground(Object[] params) {
            try {
                User castedUser = (User) params[0];
                JSONObject castedJson = (JSONObject) params[1];

                return WaterReportTask.addWaterSourceReport(castedUser, castedJson);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                afterHTTPSuccess();
            }else {
                Log.d("[Login]", "Login failed http returned false");
            }
        }
    }

    /**
     * returns to the landing page
     */
    private void afterHTTPSuccess(){
        Intent intent = new Intent(this, SourceView.class);
        Bundle bundle1 = new Bundle();
        intent.putExtra("user", loginUser);
        intent.putExtras(bundle1);
        startActivity(intent);
    }


}
