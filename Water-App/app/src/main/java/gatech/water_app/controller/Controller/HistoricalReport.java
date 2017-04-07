package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import gatech.water_app.R;
import gatech.water_app.model.ServerConnector;
import gatech.water_app.model.Title;
import gatech.water_app.model.User;
import gatech.water_app.model.WaterPurityReport;

public class HistoricalReport extends AppCompatActivity {

    private XYPlot plot1;
    private User loginUser;
    private Address address;
    private int year;
    private String ppm;
    private final List<Number> range = new ArrayList();

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

        plot1 = (XYPlot) findViewById(R.id.plot);

        new HTTPGetPurityReportTask().execute(loginUser);
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

        // create our series from our array of nums:
        SimpleXYSeries series = new SimpleXYSeries(Arrays.asList(range.toArray(new Number[range.size()])),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, ppm);

        LineAndPointFormatter formatter =
                new LineAndPointFormatter(Color.rgb(0, 0, 0), Color.RED, Color.RED, null);
        formatter.getVertexPaint().setStrokeWidth(PixelUtils.dpToPix(10));
        formatter.getLinePaint().setStrokeWidth(PixelUtils.dpToPix(5));

        // setup our line fill paint to be a slightly transparent gradient:
        Paint lineFill = new Paint();
        lineFill.setAlpha(200);

        formatter.setFillPaint(lineFill);

        plot1.addSeries(series, formatter);

        final Date[] months = {
                new GregorianCalendar(year, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(year, Calendar.FEBRUARY, 1).getTime(),
                new GregorianCalendar(year, Calendar.MARCH, 1).getTime(),
                new GregorianCalendar(year, Calendar.APRIL, 1).getTime(),
                new GregorianCalendar(year, Calendar.MAY, 1).getTime(),
                new GregorianCalendar(year, Calendar.JUNE, 1).getTime(),
                new GregorianCalendar(year, Calendar.JULY, 1).getTime(),
                new GregorianCalendar(year, Calendar.AUGUST, 1).getTime(),
                new GregorianCalendar(year, Calendar.SEPTEMBER, 1).getTime(),
                new GregorianCalendar(year, Calendar.OCTOBER, 1).getTime(),
                new GregorianCalendar(year, Calendar.NOVEMBER, 1).getTime(),
                new GregorianCalendar(year, Calendar.DECEMBER, 1).getTime()
        };

        plot1.setRangeBoundaries(0, 200, BoundaryMode.AUTO);

        plot1.getGraph().getGridBackgroundPaint().setColor(Color.WHITE);
        plot1.getGraph().getDomainGridLinePaint().setColor(Color.BLACK);
        plot1.getGraph().getDomainGridLinePaint().
                setPathEffect(new DashPathEffect(new float[]{1, 1}, 1));
        plot1.getGraph().getRangeGridLinePaint().setColor(Color.BLACK);
        plot1.getGraph().getRangeGridLinePaint().
                setPathEffect(new DashPathEffect(new float[]{1, 1}, 1));
        plot1.getGraph().getDomainOriginLinePaint().setColor(Color.BLACK);
        plot1.getGraph().getRangeOriginLinePaint().setColor(Color.BLACK);

        plot1.getGraph().setPaddingRight(2);

        // draw a domain tick for each year:
        plot1.setDomainStep(StepMode.SUBDIVIDE, months.length);

        // customize our domain/range labels
        plot1.setDomainLabel("Year");
        plot1.setRangeLabel("PPM");

        // get rid of decimal points in our range labels:
        plot1.getGraph().getLineLabelStyle(XYGraphWidget.Edge.LEFT).
                setFormat(new DecimalFormat("0.0"));

        plot1.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).
                setFormat(new Format() {

                    // create a simple date format that draws on the year portion of our timestamp.
                    // see http://download.oracle.com/javase/1.4.2/docs/api/java/text/SimpleDateFormat.html
                    // for a full description of SimpleDateFormat.
                    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM");

                    @Override
                    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {

                        // this rounding is necessary to avoid precision loss when converting from
                        // double back to int:
                        int monthIndex = (int) Math.round(((Number) obj).doubleValue());
                        return dateFormat.format(months[monthIndex], toAppendTo, pos);
                    }

                    @Override
                    public Object parseObject(String source, ParsePosition pos) {
                        return null;

                    }
                });
        plot1.redraw();
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
                onGraphReady();
                Log.d("PurityView", "It appears that the request was successfull ");
            }
        }
    }

    /**
     * Populates the graph with WaterPurityReports
     * @param WaterPurityReportList
     */
    private void populateGraph(ArrayList<WaterPurityReport> WaterPurityReportList) {

        //Putting year from data is deprecated
        Calendar cal = Calendar.getInstance();

        int currentyear;
        int currentmonth;
        double currentValue;
        int perMonth;
        WaterPurityReport current;

        //Searches for current month
        for (int m = 0; m < 12; m++) {
            currentValue = 0;
            perMonth = 0;

            //Looks through water reports
            for (int i = 0; i < WaterPurityReportList.size(); i++) {

                //Checks for correct location, year, and PPM type
                current = WaterPurityReportList.get(i);
                cal.setTime(current.getDataTime());
                currentyear = cal.get(Calendar.YEAR);
                currentmonth = cal.get(Calendar.MONTH);

                if (current.getLocation().getLatitude() == address.getLatitude()
                        && current.getLocation().getLongitude()  == address.getLongitude()
                        && currentyear == year
                        && currentmonth == m) {

                    if (ppm.equals("Virus")) {
                        currentValue += current.getVirusPPM();
                    } else if (ppm.equals("Contaminant")) {
                        currentValue += current.getContaminantPPM();
                    }
                    perMonth++;
                }
            }
            if (perMonth == 0) {
                range.add(0);
            } else {
                range.add(currentValue/perMonth);
            }
        }
    }

    /**
     * Returns to the Manager Landing Page
     * @param view
     */
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
