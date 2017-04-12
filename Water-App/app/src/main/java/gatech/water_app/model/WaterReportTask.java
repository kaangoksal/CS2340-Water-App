package gatech.water_app.model;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Handles and stores water reports
 * Created by Alex Thien An Le on 2/23/2017.
 */

public class WaterReportTask {

    //temp

    //temp


//    /**
//     * Adds Water Source Report to database
//     * @param sourceReport
//     */
//    public static void addWaterSourceReport(WaterSourceReport sourceReport) {
//        //temp debugging code
//        sourceList.add(sourceReport);
//    }

    /**
     * attempt to add a water source report
     * @param user user
     * @param reportJson the json to be added
     * @return whether it was added or not
     */
    public static boolean addWaterSourceReport(User user, JSONObject reportJson)  {
        try {
            return ServerConnector.addReport(user, reportJson);
        } catch (IOException E){
            return false;
        }
    }



    /**
     * Search for water source report
     * @param position the latitude longitude position of the water source
     * @param list list of data
     * @return a string representation of the position
     */
    public static String getSourceReportInfo(LatLng position, Iterable<WaterSourceReport> list) {
        String returnData = "";
        for (WaterSourceReport element: list) {

            if ((element.getLocation().getLatitude() == position.latitude)
                    && (element.getLocation().getLongitude() == position.longitude)) {
                returnData += element.toString() + "\n\n";
            }
        }
        return returnData;
    }

    /**
     * List of the water sources reports
     * @param user user to log in
     * @return the list
     */
    public static List<WaterSourceReport> waterSourceReportList(User user) {
        try {
            return ServerConnector.getSourceReports(user);
        } catch (IOException E) {
            throw new RuntimeException("Failed to get purity report form database");
        }
    }


    /*
      Adds Water Source Report to database
      @param sourceReport the source report to be added to the list.
     */
    /*
    public static void addWaterPurityReport(WaterPurityReport sourceReport) {
        //temp debugging code
        purityList.add(sourceReport);
    }
    */



    /*
    public static String getPurityReportInfo(LatLng position) {
        String returnData = "";
        for (WaterPurityReport element: purityList) {
            if ((element.getLocation().getLatitude() == position.latitude)
                    && (element.getLocation().getLongitude() == position.longitude)) {
                returnData += element.toString() + "\n\n";
            }
        }
        return returnData;
    }
    */

    /**
     * List of the water sources reports
     * @param user user to log in
     * @return the list
     */
    public static List<WaterPurityReport> waterPurityReportList(User user) {
        try {
            return ServerConnector.getPurityReports(user);
        } catch (IOException E) {
            throw new RuntimeException("Failed to get purity report form database");
        }
    }

}
