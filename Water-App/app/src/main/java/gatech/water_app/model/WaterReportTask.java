package gatech.water_app.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles and stores water reports
 * Created by Alex Thien An Le on 2/23/2017.
 */

public class WaterReportTask {

    //temp
    private static List<WaterSourceReport> sourceList = new ArrayList<>();

    //temp
    private static List<WaterPurityReport> purityList = new ArrayList<>();

//    /**
//     * Adds Water Source Report to database
//     * @param sourceReport
//     */
//    public static void addWaterSourceReport(WaterSourceReport sourceReport) {
//        //temp debugging code
//        sourceList.add(sourceReport);
//    }

    public static boolean addWaterSourceReport(User user, JSONObject reportJson)  {
        try {
            return ServerConnector.addReport(user, reportJson);
        } catch (IOException E){
            return false;
        }
    }



    /**
     * Search for water source report
     * @param position
     * @return
     */
    public static String getSourceReportInfo(LatLng position, ArrayList<WaterSourceReport> list) {
        String returnData = "";
        for (WaterSourceReport element: list) {
            if (element.getLocation().getLatitude() == position.latitude && element.getLocation().getLongitude() == position.longitude) {
                returnData += element.toString() + "\n\n";
            }
        }
        return returnData;
    }

    /**
     * List of the water sources reports
     */
    public static List<WaterSourceReport> waterSourceReportList(User user) {
        try {
            return ServerConnector.getSourceReports(user);
        } catch (IOException E) {
            throw new RuntimeException("Failed to get purity report form database");
        }
    }

    /**
     * Adds Water Source Report to database
     * @param sourceReport
     */
    public static void addWaterPurityReport(WaterPurityReport sourceReport) {
        //temp debugging code
        purityList.add(sourceReport);
    }


    /**
     * Search for water source report
     * @param position
     * @return
     */
    public static String getPurityReportInfo(LatLng position) {
        String returnData = "";
        for (WaterPurityReport element: purityList) {
            if (element.getLocation().getLatitude() == position.latitude && element.getLocation().getLongitude() == position.longitude) {
                returnData += element.toString() + "\n\n";
            }
        }
        return returnData;
    }

    /**
     * List of the water sources reports
     */
    public static List<WaterPurityReport> waterPurityReportList(User user) {
        try {
            return ServerConnector.getPurityReports(user);
        } catch (IOException E) {
            throw new RuntimeException("Failed to get purity report form database");
        }
    }

}
