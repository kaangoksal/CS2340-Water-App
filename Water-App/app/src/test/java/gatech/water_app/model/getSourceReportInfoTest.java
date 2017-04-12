package gatech.water_app.model;

import android.location.Address;
import android.location.Location;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import gatech.water_app.model.OverallCondition;
import gatech.water_app.model.Report;
import gatech.water_app.model.ServerConnector;
import gatech.water_app.model.Title;
import gatech.water_app.model.User;
import gatech.water_app.model.UserLoginTask;
import gatech.water_app.model.WaterPurityReport;
import gatech.water_app.model.WaterReportTask;
import gatech.water_app.model.WaterSourceReport;
import gatech.water_app.model.WaterType;

import static org.junit.Assert.assertEquals;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


/**
 * Created by vy on 4/12/17.
 */

//Vy Huynh's Mockito/JUnit Tests
@RunWith(PowerMockRunner.class)
@PrepareForTest({ServerConnector.class, Log.class, WaterType.class, JSONObject.class})
public class getSourceReportInfoTest {

    //These Mockito Tests are aimed towards testing the getSourceReportInfo method
    //in the WaterReportTask.java file

    @Test
    public void BasicTests(){
        //When the Longitude and the Latitude of the Source Report matches up with the position
        WaterSourceReport example1 = new WaterSourceReport("Bob");
        Location newLocate = mock(Location.class);
        when(newLocate.getLatitude()).thenReturn(2.0);
        when(newLocate.getLongitude()).thenReturn(4.0);
        example1.setLocation(newLocate);
        example1.getLocation().setLatitude(2.0);
        example1.getLocation().setLongitude(4.0);
        ArrayList<WaterSourceReport> list = new ArrayList<>();
        list.add(example1);
        LatLng position = new LatLng(2.0,4.0);
        String x = WaterReportTask.getSourceReportInfo(position,list);

        //When the Longitude and the Latitude of the Source Report doesn't matches up with the position
        WaterSourceReport example2 = new WaterSourceReport("Bob");
        Location newLocate2 = mock(Location.class);
        when(newLocate2.getLatitude()).thenReturn(2.0);
        when(newLocate2.getLongitude()).thenReturn(4.0);
        example2.setLocation(newLocate2);
        example2.getLocation().setLatitude(2.0);
        example2.getLocation().setLongitude(4.0);
        ArrayList<WaterSourceReport> list2 = new ArrayList<>();
        list2.add(example2);
        LatLng position2 = new LatLng(2.0,4.0);
        String x2 = WaterReportTask.getSourceReportInfo(position2,list2);

        //When the Longitudes and the Latitudes are negative
        WaterSourceReport example3 = new WaterSourceReport("Bob");
        Location newLocate3 = mock(Location.class);
        when(newLocate3.getLatitude()).thenReturn(-2.0);
        when(newLocate3.getLongitude()).thenReturn(-4.0);
        example3.setLocation(newLocate3);
        example3.getLocation().setLatitude(-2.0);
        example3.getLocation().setLongitude(-4.0);
        ArrayList<WaterSourceReport> list3 = new ArrayList<>();
        list3.add(example3);
        LatLng position3 = new LatLng(-2.0,-4.0);
        String x3 = WaterReportTask.getSourceReportInfo(position3,list3);

        //When the Longitudes and the Latitudes are zeroes
        WaterSourceReport example4 = new WaterSourceReport("Bob");
        Location newLocate4 = mock(Location.class);
        when(newLocate4.getLatitude()).thenReturn(0.0);
        when(newLocate4.getLongitude()).thenReturn(0.0);
        example4.setLocation(newLocate4);
        example4.getLocation().setLatitude(0.0);
        example4.getLocation().setLongitude(0.0);
        ArrayList<WaterSourceReport> list4 = new ArrayList<>();
        list4.add(example4);
        LatLng position4 = new LatLng(0.0,0.0);
        String x4 = WaterReportTask.getSourceReportInfo(position4,list4);

        //When the list contains multiple longitudes and latitudes
        list.add(example2);
        list.add(example3);
        list.add(example4);
        String x5 = WaterReportTask.getSourceReportInfo(position,list);

        //when list is empty
        ArrayList<WaterSourceReport> list5 = new ArrayList<>();
        LatLng position5 = new LatLng(0.0,0.0);
        String x6 = WaterReportTask.getSourceReportInfo(position5,list5);
    }

    @Test(expected=NullPointerException.class)
    public void LocationNullTest() {
        //when Location is null
        WaterSourceReport example5 = new WaterSourceReport("Bob");
        Location newLocate5 = null;
        example5.setLocation(newLocate5);
        ArrayList<WaterSourceReport> list5 = new ArrayList<>();
        list5.add(example5);
        LatLng position5 = new LatLng(0.0,0.0);
        String x6 = WaterReportTask.getSourceReportInfo(position5,list5);
    }

    @Test(expected=NullPointerException.class)
    public void WaterSourceReportNullTest() {
        //when Water Source Report is null
        WaterSourceReport example6 = null;
        ArrayList<WaterSourceReport> list6 = new ArrayList<>();
        list6.add(example6);
        LatLng position6 = new LatLng(0.0,0.0);
        String x7 = WaterReportTask.getSourceReportInfo(position6,list6);
    }

    @Test(expected=NullPointerException.class)
    public void PositionNullTest() {
        //when the location is null
        WaterSourceReport example4 = new WaterSourceReport("Bob");
        Location newLocate4 = mock(Location.class);
        when(newLocate4.getLatitude()).thenReturn(0.0);
        when(newLocate4.getLongitude()).thenReturn(0.0);
        example4.setLocation(newLocate4);
        example4.getLocation().setLatitude(0.0);
        example4.getLocation().setLongitude(0.0);
        ArrayList<WaterSourceReport> list4 = new ArrayList<>();
        list4.add(example4);
        LatLng position4 = null;
        String x4 = WaterReportTask.getSourceReportInfo(position4,list4);
    }

    @Test(expected=NullPointerException.class)
    public void ListNullTest() {
        //when the list is null
        ArrayList<WaterSourceReport> list7 = null;
        LatLng position7 = new LatLng(0.0,0.0);
        String x8 = WaterReportTask.getSourceReportInfo(position7,list7);
    }


}