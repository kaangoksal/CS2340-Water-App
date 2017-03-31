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
import android.text.Editable;
import android.text.TextWatcher;
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
import gatech.water_app.model.OverallCondition;
import gatech.water_app.model.User;
import gatech.water_app.model.Title;
import gatech.water_app.model.UserLoginTask;
import gatech.water_app.model.WaterCondition;
import gatech.water_app.model.WaterPurityReport;
import gatech.water_app.model.WaterReportTask;
import gatech.water_app.model.WaterSourceReport;
import gatech.water_app.model.WaterType;

/**
 * Controller class for submitting a purity report for workers
 * Created by John on 3/10/2017.
 */

public class SubmitPurityReport extends AppCompatActivity {

    private TextView dateView;
    private TextView reportNumView;
    private TextView reporterTextView;
    private EditText location;
    private Button submit;
    private Button cancelButton;
    private Spinner condition;
    private TextView virus;
    private TextView contaminant;
    private Address address = new Address(new Locale("US"));

    User loginUser;

    private WaterPurityReport newReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purity_source_report);

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

        address.setLatitude(0.0);
        address.setLongitude(0.0);

        submit = (Button) findViewById(R.id.submitreg);
        dateView = (TextView) findViewById(R.id.autogen);
        reportNumView = (TextView) findViewById(R.id.autogen2);
        reporterTextView = (TextView) findViewById(R.id.autogen3);
        location = (EditText) findViewById(R.id.autogen4);
        condition = (Spinner) findViewById(R.id.spinner3);
        virus = (EditText) findViewById(R.id.virus);
        contaminant = (EditText) findViewById(R.id.editText4);
        cancelButton = (Button) findViewById(R.id.water_report_cancel);


        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, OverallCondition.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        condition.setAdapter(adapter);

        newReport = new WaterPurityReport(loginUser.getEmail());

        newReport.setReportNumber(UUID.randomUUID().toString());
        dateView.setText(newReport.getDateString().toString());
        reportNumView.setText(newReport.getReportNumber());
        reporterTextView.setText(newReport.getReporter());

        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }

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

    public void submitPurityReport(View view) {
        if (address.getLatitude() != 0 && address.getLongitude() != 0) {
//            newReport = new WaterPurityReport((getIntent().getExtras().getString("username")));
//            date.setText(newReport.getDataTime().toString());
//            reportNum.setText(newReport.getReportNumber());
//            reporter.setText(newReport.getReporter());

            newReport.setLocation(new Location(address.getFeatureName()));
            newReport.getLocation().setLatitude(address.getLatitude());
            newReport.getLocation().setLongitude(address.getLongitude());
            newReport.setOverallCondition((OverallCondition) condition.getSelectedItem());
            newReport.setVirusPPM(Double.parseDouble(virus.getText().toString()));
            newReport.setContaminantPPM(Double.parseDouble(contaminant.getText().toString()));

            JSONObject reportJson = newReport.toJSONObject();



//            WaterReportTask.addWaterPurityReport(newReport);
            new HTTPSubmitReportTask().execute(loginUser, reportJson);


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
                afterSuccess();
            }else {
                Log.d("[Login]", "Task failed http returned false");
            }
        }
    }


    private void afterSuccess(){

        Intent intent = new Intent(this, PurityView.class);
        Bundle bundle1 = new Bundle();
        intent.putExtra("user", loginUser);
        intent.putExtras(bundle1);
        startActivity(intent);
    }



}
