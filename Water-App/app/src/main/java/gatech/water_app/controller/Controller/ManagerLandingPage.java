package gatech.water_app.controller.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import gatech.water_app.R;
import gatech.water_app.model.User;

public class ManagerLandingPage extends LandingPage {

    private Button historicalButton;
    private String[] PPMList = {"Virus", "Contaminant"};
    private Spinner PPMSpinner;
    private EditText historicalLocation;
    private EditText historicalYear;
    User loginUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        loginUser =(User)extras.getSerializable("user");

        setContentView(R.layout.activity_manager_landing_page);
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

        historicalLocation = (EditText) findViewById(R.id.historicalLocation);
        historicalYear = (EditText) findViewById(R.id.historicalYear);

//        PPMSpinner = (Spinner) findViewById(R.id.historicalPPM);
//        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, PPMList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        PPMSpinner.setAdapter(adapter);
    }

    public void startPurityView(View view) {
        Intent intent = new Intent(this, PurityView.class);
        Bundle bundle1 = new Bundle();
//        bundle1.putString("pass", password);
//        bundle1.putString("email", email);
        intent.putExtra("user", loginUser);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    public void setupHistoricalReport(final View view) {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.historical_report_setup, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Setup");

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText location = (EditText) promptsView
                .findViewById(R.id.historicalLocation);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                if (PPMSpinner.getSelectedItem().toString() != null
                                        && historicalYear.getText().toString() != null
                                        && !historicalYear.getText().toString().equals("")
                                        && historicalLocation.getText().toString() != null
                                        && !historicalLocation.getText().toString().equals("")) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("year", Integer.parseInt(historicalYear.getText().toString()));
                                    bundle.putString("PMM", PPMSpinner.getSelectedItem().toString());
                                    bundle.putString("location", historicalLocation.getText().toString());
                                    startHistoricalReport(view, bundle);
                                } else {
                                    Toast.makeText(ManagerLandingPage.this, "Incomplete Fields", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void startHistoricalReport(View view, Bundle bundle) {
        Intent intent = new Intent(this, HistoricalReport.class);
        intent.putExtras(bundle);
        intent.putExtra("user", loginUser);
        startActivity(intent);
    }

}
