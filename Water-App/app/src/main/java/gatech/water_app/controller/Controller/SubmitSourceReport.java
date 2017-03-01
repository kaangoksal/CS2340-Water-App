package gatech.water_app.controller.Controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import gatech.water_app.R;
import gatech.water_app.model.Title;
import gatech.water_app.model.UserLoginTask;
import gatech.water_app.model.WaterSourceReport;

public class SubmitSourceReport extends AppCompatActivity {

    TextView date;
    TextView reportNum;
    TextView reporter;
    TextView location;
    Button submit;
    Spinner typeWater;
    Spinner condition;

    WaterSourceReport newReport = new WaterSourceReport();


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

        submit = (Button) findViewById(R.id.submitreg);
        date = (TextView) findViewById(R.id.autogen);
        reportNum = (TextView) findViewById(R.id.autogen2);
        reporter = (TextView) findViewById(R.id.autogen3);
        location = (TextView) findViewById(R.id.autogen4);
        typeWater = (Spinner) findViewById(R.id.spinner3);
        condition = (Spinner) findViewById(R.id.spinner2);

        date.setText(newReport.getDataTime());
        reportNum.setText((Integer.toString(newReport.getReportNumber())));
        reporter.setText(newReport.getReporter());

//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                newReport.incReportNum();
//            }
//        });
    }


}
