package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import gatech.water_app.R;
import gatech.water_app.model.Title;
import gatech.water_app.model.UserLoginTask;
import gatech.water_app.model.WaterCondition;
import gatech.water_app.model.WaterReportTask;
import gatech.water_app.model.WaterSourceReport;
import gatech.water_app.model.WaterType;
/**
 *Controller class for submitting a normal water source report
 */
public class SubmitSourceReport extends AppCompatActivity {

    private TextView date;
    private TextView reportNum;
    private TextView reporter;
    private EditText location;
    private Button submit;
    private Button cancelButton;
    private Spinner typeWater;
    private Spinner condition;
    private Address address = new Address(new Locale("US"));

    String afterTextChanged = "";
    String beforeTextChanged = "";
    String onTextChanged = "";

    String username;
    String password;

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
        username = extras.getString("username");
        password = extras.getString("pass");

        address.setLatitude(0.0);
        address.setLongitude(0.0);

        submit = (Button) findViewById(R.id.submitreg);
        date = (TextView) findViewById(R.id.autogen);
        reportNum = (TextView) findViewById(R.id.autogen2);
        reporter = (TextView) findViewById(R.id.autogen3);
        location = (EditText) findViewById(R.id.autogen4);
        typeWater = (Spinner) findViewById(R.id.spinner3);
        condition = (Spinner) findViewById(R.id.spinner2);
        cancelButton = (Button) findViewById(R.id.water_report_cancel);


        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, WaterType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeWater.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, WaterCondition.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        condition.setAdapter(adapter2);

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

    public void submitSourceReport(View view) {
        if (address.getLatitude() != 0 && address.getLongitude() != 0) {
            newReport = new WaterSourceReport(getIntent().getExtras().getString("username"));
            date.setText(newReport.getDataTime().toString());
            reportNum.setText(newReport.getReportNumber());
            reporter.setText(newReport.getReporter());

            newReport.setLocation(new Location(address.getFeatureName()));
            newReport.getLocation().setLatitude(address.getLatitude());
            newReport.getLocation().setLongitude(address.getLongitude());
            newReport.setCondition((WaterCondition) condition.getSelectedItem());
            newReport.setType((WaterType) typeWater.getSelectedItem());
            WaterReportTask.addWaterSourceReport(newReport);

            Intent intent = new Intent(this, ReportView.class);
            Bundle bundle1 = new Bundle();
            bundle1.putString("pass", password);
            bundle1.putString("username", username);
            intent.putExtras(bundle1);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid address", Toast.LENGTH_SHORT).show();
        }
    }


}
