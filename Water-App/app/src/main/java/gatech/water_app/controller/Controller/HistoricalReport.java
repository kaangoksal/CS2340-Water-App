package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.io.IOException;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import gatech.water_app.R;
import gatech.water_app.model.ServerConnector;
import gatech.water_app.model.Title;
import gatech.water_app.model.User;
import gatech.water_app.model.WaterPurityReport;

public class HistoricalReport extends AppCompatActivity {

    private XYPlot plot;
    private User loginUser;
    private Address address;
    private int year;
    private String ppm;
    private List<Number> domain = new ArrayList();
    private List<Number> range = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_report);
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
        address = searchLocation(extras.getString("address"));
        year = extras.getInt("year");
        ppm = extras.getString("PPM");


        new HTTPGetPurityReportTask().execute(loginUser);
        onGraphReady();
    }

    private Address searchLocation(String location) {
        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(this);
        try {
            addressList = geocoder.getFromLocationName(location, 1);

        } catch (IOException e) {
                e.printStackTrace();
        }
        return addressList.get(0);

    }

    private void onGraphReady() {
        plot = (XYPlot) findViewById(R.id.plot);

        //Add values to the graph
        XYSeries data = new SimpleXYSeries(domain, range, "Historical Report");

        //Line formatter
        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        //display to the graph
        plot.addSeries(data, series1Format);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round(((Number) obj).floatValue());
                return toAppendTo.append(domain.get(i));
            }
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });
    }



    /**
     * This is a class for the async HTTP Request.
     */
    private class HTTPGetPurityReportTask extends AsyncTask<Object, Integer, ArrayList<WaterPurityReport>> {
        protected ArrayList<WaterPurityReport> doInBackground(Object[] params) {
            try {
                User castedUser = (User) params[0];

                return ServerConnector.getPurityReports(castedUser);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(ArrayList<WaterPurityReport> result) {
            if (result != null) {
                populateGraph(result);
                Log.d("PurityView", "It appears that the request was successfull ");
            }
        }
    }

    /**
     * Populates the graph with WaterPurityReports
     * @param WaterPurityReportList
     */
    public void populateGraph(ArrayList<WaterPurityReport> WaterPurityReportList) {

        //Putting year from data is deprecated
        Calendar cal = Calendar.getInstance();


        int currentyear;

        WaterPurityReport current;
        for (int i = 0; i < WaterPurityReportList.size(); i++) {
            current = WaterPurityReportList.get(i);
            cal.setTime(current.getDataTime());
            currentyear = cal.get(Calendar.YEAR);

            if (current.getLocation().getProvider().equals(address.getFeatureName())
                    && currentyear == year) {
                    if (ppm.equals("Virus")) {
                        range.add(current.getVirusPPM());
                    } else if (ppm.equals("Contaminant")) {
                        range.add(current.getContaminantPPM());
                    }
                domain.add(current.getDataTime().getTime());
            }
        }
//
    }

    public void backFromGraphPage(View view) {

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
