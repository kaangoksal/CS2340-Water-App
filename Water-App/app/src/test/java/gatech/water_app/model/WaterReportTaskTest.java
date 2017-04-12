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

@RunWith(PowerMockRunner.class)
@PrepareForTest({ServerConnector.class, Log.class, WaterType.class, JSONObject.class})
public class WaterReportTaskTest {

    @Mock User inputUser;
    @Mock JSONObject reportJSON;
    @Test
    public void addWaterSourceReport() throws Exception {
        mockStatic(ServerConnector.class);

        //tests a null report addition
        assertEquals(false, WaterReportTask.addWaterSourceReport(null,null));
        assertEquals(false, WaterReportTask.addWaterSourceReport(inputUser,null));
        assertEquals(false, WaterReportTask.addWaterSourceReport(null,reportJSON));
        assertEquals(false, ServerConnector.addReport(null,null));
        assertEquals(false, ServerConnector.addReport(inputUser,null));
        assertEquals(false, ServerConnector.addReport(null,reportJSON));

        //tests a report being properly added to the database
        when(ServerConnector.addReport(inputUser,reportJSON)).thenReturn(true);
        boolean added = WaterReportTask.addWaterSourceReport(inputUser,reportJSON);
        assertEquals(true, added);

        //tests a report being added with no http connection
        when(ServerConnector.addReport(inputUser,reportJSON)).thenThrow(IOException.class);
        boolean test = WaterReportTask.addWaterSourceReport(inputUser,reportJSON);
        assertEquals(false, test);
    }

    @Mock ArrayList<WaterSourceReport> list;
    @Mock LatLng position;
    @Test
    public void getSourceReportInfoTest(){
        WaterReportTask.getSourceReportInfo(position,list);
    }
//    @Mock LatLng position;
//    @Mock ArrayList<WaterSourceReport> list;
//    @Test
//    public void getSourceReportInfoTest() {
//        when(element.getLocation().getLatitude())
//    }
////    public static String getSourceReportInfo(LatLng position, ArrayList<WaterSourceReport> list) {
////        String returnData = "";
////        for (WaterSourceReport element: list) {
////            if (element.getLocation().getLatitude() == position.latitude && element.getLocation().getLongitude() == position.longitude) {
////                returnData += element.toString() + "\n\n";
////            }
////        }
////        return returnData;
////    }

}