package gatech.water_app.Junit;

import android.location.Address;
import android.location.Location;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
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

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;




@RunWith(PowerMockRunner.class)
@PrepareForTest({ServerConnector.class, Log.class, WaterType.class, JSONObject.class, WaterSourceReport.class})
public class WaterAppJunits {
    //Sam Gilson's Test
    @Test
    public void testAttemptLoginInUserLoginTask() throws IOException {

        mockStatic(ServerConnector.class);

        //tests a null user attempting to log in
        User nullUser = null;
        assertEquals(null, UserLoginTask.attemptLogin(nullUser));

        //tests a user not in the database attempting to log in
        when(ServerConnector.attemptLogin("yes", "yes")).thenThrow(IOException.class);
        User notInDatabase = new User("yes", "yes", "yes");
        User u = UserLoginTask.attemptLogin(notInDatabase);
        assertEquals(null, u);

        //tests a user in the database attempting to log in
        User inDatabase = new User("samgilson", "memes", "samgilson98@gmail.com");
        when(ServerConnector.attemptLogin("samgilson98@gmail.com", "memes"))
                .thenReturn(new User("samgilson", "memes", "samgilson98@gmail.com"));
        u = UserLoginTask.attemptLogin(inDatabase);
        assertEquals(inDatabase, u);

    }


    //John Ho's Test
    @Test
    public void testAddUserInUserLoginTask() throws IOException {

        mockStatic(ServerConnector.class);

        //tests a null user attempting to be added to the database
        assertEquals(false, UserLoginTask.addUser(null, null, null, null));

        //tests a user attempting to be added with no http connection
        when(ServerConnector.addUser("HTTP", "connection", "is", "broken")).thenThrow(IOException.class);
        boolean added = UserLoginTask.addUser("HTTP", "connection", "is", "broken");
        assertEquals(false, added);

        //tests a user to be properly added to the database
        when(ServerConnector.addUser("johnho", "password", "atl",  "hoboy@gmail.com"))
                .thenReturn(true);
        added = UserLoginTask.addUser("johnho", "password", "atl",  "hoboy@gmail.com");
        assertEquals(true, added);
    }

    //Alex Le's Test
    @Test
    public void testEditUserInUserLoginTask() throws IOException {

        mockStatic(ServerConnector.class);

        //tests a null user attempting to be edited within the database
        assertEquals(false, UserLoginTask.attemptEditUser(null, null, null));

        //tests a user attempting to be edited with no http connection
        when(ServerConnector.editUser("HTTP", "connection", "broke")).thenThrow(IOException.class);
        boolean added = UserLoginTask.attemptEditUser("HTTP", "connection", "broke");
        assertEquals(false, added);

        //tests a user to be properly edited within the database
        when(ServerConnector.editUser("alexLE", "fox", "alexlethien@gmail.com"))
                .thenReturn(true);
        added = UserLoginTask.attemptEditUser("alexLE", "fox", "alexlethien@gmail.com");
        assertEquals(true, added);
    }

    @Mock JSONObject waterReportReal;


    //Kaan Goksal's Test
    @Test
    public void generateWaterSourceReportfromJSONObject() throws JSONException {

        mockStatic(Log.class);
        mockStatic(WaterType.class);
        waterReportReal = mock(JSONObject.class);
        when(Log.e(anyString(), anyString())).thenReturn(0);
        when(WaterType.valueOf(null)).thenReturn(WaterType.Bottled);
        when(waterReportReal.getString("report_number")).thenReturn("129");
        when(waterReportReal.getString("date")).thenReturn("1990-03-21");
        when(waterReportReal.getString("reporter")).thenReturn("bob");
        when(waterReportReal.getString("location")).thenReturn("9827");
        when(waterReportReal.getString("data")).thenReturn("Bottled");
        JSONObject waterReportNull = new JSONObject();
        Report returnedReport = new Report(new Date(),"129","bob", new Location("9827"));

        //checks to make sure that null is returned when a faulty json object is sent in
        assertEquals(null, WaterSourceReport.fromJSONObject(waterReportNull));

        //checks to make sure that a properly filled in water report is filled from a good json object
        Report returnedFromWaterSourceReport = WaterSourceReport.fromJSONObject(waterReportReal);
        assertEquals(returnedReport, returnedFromWaterSourceReport);

        //checks to make sure that since the location string sent in was improper that the lat
        //and long are set properly to 1
        assertEquals(0, returnedFromWaterSourceReport.getLocation().getLatitude());
        assertEquals(0, returnedFromWaterSourceReport.getLocation().getLongitude());

        when(waterReportReal.getString("location")).thenReturn("98-27");

        returnedFromWaterSourceReport = WaterSourceReport.fromJSONObject(waterReportReal);

        //checks to make sure that since location string sent in was proper that the
        //lat and long are the values that they should be
        assertEquals(98, returnedFromWaterSourceReport.getLocation().getLatitude());
        assertEquals(27, returnedFromWaterSourceReport.getLocation().getLongitude());

    }

//    @Mock JSONObject data;
//    @Mock Location locate;
//    @InjectMocks WaterPurityReport waterPurityReport = new WaterPurityReport("bob");
//    @Test
//    public void generateJSONObjectFromWaterPurityReport() throws JSONException {
//        Date date = new Date();
////        Address address = new Address(new Locale("US"));
//        waterPurityReport.setContaminantPPM(100);
//        waterPurityReport.setVirusPPM(100);
//        waterPurityReport.setOverallCondition(OverallCondition.Safe);
//        waterPurityReport.setDataTime(date);
//        waterPurityReport.setLocation(locate);
//        waterPurityReport.getLocation().setLatitude(5);
//        waterPurityReport.getLocation().setLongitude(5);
//        waterPurityReport.setReporter("Bob");
//        waterPurityReport.setReportNumber("101");
////        WaterPurityReport nullReport = new WaterPurityReport(null);
////        waterPurityReport.setContaminantPPM(0);
////        waterPurityReport.setVirusPPM(0);
////        waterPurityReport.setOverallCondition(null);
//
////        when(data.put(anyString(),anyString())).thenReturn(null);
//        JSONObject x = waterPurityReport.toJSONObject();
//        assertEquals(null, waterPurityReport.toJSONObject());
//    }







}
