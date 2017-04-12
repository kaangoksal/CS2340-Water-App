package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.androidplot.xy.XYSeries;

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

/**
 * creates the graphs for historical report
 */
public class HistoricalReport extends AppCompatActivity {

    private XYPlot plot1;
    private User loginUser;
    private Address address;
    private int year;
    private String ppm;
    private final List<Number> range = new ArrayList<>();

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
                Snackbar made = Snackbar.make(view,
                        "Replace with your own action", Snackbar.LENGTH_LONG);
                Snackbar action = made.setAction("Action", null);
                action.show();
            }
        });

        Intent extrasIntent = getIntent();
        Bundle extras = extrasIntent.getExtras();
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
        assert addressList != null;
        return addressList.get(0);

    }

    private void onGraphReady() {

        // create our series from our array of numbers:
        XYSeries series = new SimpleXYSeries(Arrays.asList(range.toArray(new Number[range.size()])),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, ppm);

        LineAndPointFormatter formatter =
                new LineAndPointFormatter(Color.rgb(0, 0, 0), Color.RED, Color.RED, null);
        Paint vertex = formatter.getVertexPaint();
        vertex.setStrokeWidth(PixelUtils.dpToPix(10));
        Paint linePaint = formatter.getLinePaint();
        linePaint.setStrokeWidth(PixelUtils.dpToPix(5));

        // setup our line fill paint to be a slightly transparent gradient:
        Paint lineFill = new Paint();
        lineFill.setAlpha(200);

        formatter.setFillPaint(lineFill);

        plot1.addSeries(series, formatter);

        GregorianCalendar jan = new GregorianCalendar(year, Calendar.JANUARY, 1);
        GregorianCalendar feb = new GregorianCalendar(year, Calendar.FEBRUARY, 1);
        GregorianCalendar march = new GregorianCalendar(year, Calendar.MARCH, 1);
        GregorianCalendar apr = new GregorianCalendar(year, Calendar.APRIL, 1);
        GregorianCalendar may = new GregorianCalendar(year, Calendar.MAY, 1);
        GregorianCalendar june = new GregorianCalendar(year, Calendar.JUNE, 1);
        GregorianCalendar july = new GregorianCalendar(year, Calendar.JULY, 1);
        GregorianCalendar august = new GregorianCalendar(year, Calendar.AUGUST, 1);
        GregorianCalendar september = new GregorianCalendar(year, Calendar.SEPTEMBER, 1);
        GregorianCalendar october = new GregorianCalendar(year, Calendar.OCTOBER, 1);
        GregorianCalendar november = new GregorianCalendar(year, Calendar.NOVEMBER, 1);
        GregorianCalendar december = new GregorianCalendar(year, Calendar.DECEMBER, 1);


        final Date[] months = {
                jan.getTime(),
                feb.getTime(),
                march.getTime(),
                apr.getTime(),
                may.getTime(),
                june.getTime(),
                july.getTime(),
                august.getTime(),
                september.getTime(),
                october.getTime(),
                november.getTime(),
                december.getTime()
        };

        plot1.setRangeBoundaries(0, 200, BoundaryMode.AUTO);

        XYGraphWidget graph = plot1.getGraph();
        Paint grid = graph.getGridBackgroundPaint();
        Paint domain = graph.getDomainGridLinePaint();
        Paint range = graph.getRangeGridLinePaint();
        Paint domainOrigin = graph.getDomainOriginLinePaint();
        Paint rangeOrigin = graph.getRangeOriginLinePaint();
        grid.setColor(Color.WHITE);
        domain.setColor(Color.BLACK);
        domain.setPathEffect(new DashPathEffect(new float[]{1, 1}, 1));
        range.setColor(Color.BLACK);
        range.setPathEffect(new DashPathEffect(new float[]{1, 1}, 1));
        domainOrigin.setColor(Color.BLACK);
        rangeOrigin.setColor(Color.BLACK);

        graph.setPaddingRight(2);

        // draw a domain tick for each year:
        plot1.setDomainStep(StepMode.SUBDIVIDE, months.length);

        // customize our domain/range labels
        plot1.setDomainLabel("Year");
        plot1.setRangeLabel("PPM");

        // get rid of decimal points in our range labels:
        XYGraphWidget.LineLabelStyle edgeLeft = graph.getLineLabelStyle(XYGraphWidget.Edge.LEFT);
        XYGraphWidget.LineLabelStyle edgeBottom = graph
                .getLineLabelStyle(XYGraphWidget.Edge.BOTTOM);
        edgeLeft.setFormat(new DecimalFormat("0.0"));

        edgeBottom.setFormat(new Format() {

                    // create a simple date format that draws on the year portion of our timestamp.
                    // see http://download.oracle.com/javase
                    // /1.4.2/docs/api/java/text/SimpleDateFormat.html
                    // for a full description of SimpleDateFormat.
                    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM");

                    @Override
                    public StringBuffer format(Object obj, @NonNull StringBuffer toAppendTo,
                                               @NonNull FieldPosition pos) {

                        // this rounding is necessary to avoid precision loss when converting from
                        // double back to int:
                        int monthIndex = (int) Math.round(((Number) obj).doubleValue());
                        return dateFormat.format(months[monthIndex], toAppendTo, pos);
                    }

                    @Override
                    public Object parseObject(String source, @NonNull ParsePosition pos) {
                        return null;

                    }
                });
        plot1.redraw();
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
                populateGraph(result);
                onGraphReady();
                Log.d("PurityView", "It appears that the request was successful ");
            }
        }
    }

    /**
     * Populates the graph with WaterPurityReports
     * @param WaterPurityReportList list of water purity reports
     */
    private void populateGraph(List<WaterPurityReport> WaterPurityReportList) {

        //Putting year from data is deprecated
        Calendar cal = Calendar.getInstance();

        int current_year;
        int current_month;
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
                current_year = cal.get(Calendar.YEAR);
                current_month = cal.get(Calendar.MONTH);
                Location cur = current.getLocation();

                if ((cur.getLatitude() == address.getLatitude())
                        && (cur.getLongitude() == address.getLongitude())
                        && (current_year == year)
                        && (current_month == m)) {

                    if ("Virus".equals(ppm)) {
                        currentValue += current.getVirusPPM();
                    } else if ("Contaminant".equals(ppm)) {
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
     * @param view the view you are attempting to reach
     */
    public void backFromGraphPage(View view) {

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
