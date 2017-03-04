package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import gatech.water_app.R;
import gatech.water_app.model.Title;
import gatech.water_app.model.UserLoginTask;
import gatech.water_app.model.WaterCondition;
import gatech.water_app.model.WaterReportTask;
import gatech.water_app.model.WaterSourceReport;
import gatech.water_app.model.WaterType;

public class SubmitSourceReport extends AppCompatActivity {

    private TextView date;
    private TextView reportNum;
    private TextView reporter;
    private EditText location;
    private Button submit;
    private Button cancelButton;
    private Spinner typeWater;
    private Spinner condition;

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

        newReport = new WaterSourceReport(getIntent().getExtras().getString("username"));

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

        date.setText(newReport.getDataTime().toString());
        reportNum.setText(newReport.getReportNumber());
        reporter.setText(newReport.getReporter());

//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                newReport.incReportNum();
//            }
//        });

        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }

    public void submitSourceReport(View view) {
        newReport.setLocation(new Location(location.getText().toString()));
        newReport.getLocation().setLatitude(0.0);
        newReport.getLocation().setLongitude(0.0);
        newReport.setCondition((WaterCondition) condition.getSelectedItem());
        newReport.setType((WaterType) typeWater.getSelectedItem());
        WaterReportTask.addWaterSourceReport(newReport);

        Intent intent = new Intent(this, ReportView.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("pass", password);
        bundle1.putString("username", username);
        intent.putExtras(bundle1);
        startActivity(intent);
    }


}
