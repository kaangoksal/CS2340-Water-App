package gatech.water_app.controller.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gatech.water_app.R;
import gatech.water_app.model.User;

/**
 * landing page managers come to
 */
public class ManagerLandingPage extends WorkerLandingPage {

    private Spinner PPMSpinner;
    private EditText historicalLocation;
    private EditText historicalYear;
    private final Address address = new Address(new Locale("US"));
    private User loginUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent extrasIntent = getIntent();
        Bundle extras = extrasIntent.getExtras();
        loginUser =(User)extras.getSerializable("user");

        setContentView(R.layout.activity_manager_landing_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        address.setLatitude(0.0);
        address.setLongitude(0.0);
    }

    /**
     * Creates a dialog box that requires the user to
     * input setup information before going to the historical report page
     * @param view the view you are attempting to reach
     */
    public void setupHistoricalReport(final View view) {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.historical_report_setup, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Setup");

        // set prompts.xml to alert dialog builder
        alertDialogBuilder.setView(promptsView);

        historicalLocation = (EditText) promptsView.findViewById(R.id.historicalLocation);
        historicalYear = (EditText) promptsView.findViewById(R.id.historicalYear);
        PPMSpinner = (Spinner) promptsView.findViewById(R.id.historicalPPM);
        List<String> PPMList = new ArrayList<>();
        PPMList.add("Virus");
        PPMList.add("Contaminant");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, PPMList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PPMSpinner.setAdapter(adapter);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                Object ppmSpinnerGet = PPMSpinner.getSelectedItem();
                                Editable historyYear = historicalYear.getText();
                                Editable historyLocation = historicalLocation.getText();
                                if ((ppmSpinnerGet.toString() != null) &&
                                        !"".equals(historyYear.toString()) &&
                                        !"".equals(historyLocation.toString())) {
                                    if (Integer.parseInt(historyYear.toString()) > 0) {

                                        Bundle bundle = new Bundle();
                                        int historyYearIntInteger = Integer.parseInt(historyYear
                                                .toString());
                                        bundle.putInt("year", historyYearIntInteger);
                                        bundle.putString("PPM", ppmSpinnerGet.toString());
                                        bundle.putString("address", historyLocation.toString());
                                        startHistoricalReport(bundle);

                                    } else {
                                        Toast toast = Toast.makeText(ManagerLandingPage.this,
                                                "Invalid year",
                                                Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                } else {
                                    Toast toast = Toast.makeText(ManagerLandingPage.this,
                                            "Incomplete fields",
                                            Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void startHistoricalReport(Bundle bundle) {
        Intent intent = new Intent(this, HistoricalReport.class);
        intent.putExtras(bundle);
        intent.putExtra("user", loginUser);
        startActivity(intent);
    }

}
