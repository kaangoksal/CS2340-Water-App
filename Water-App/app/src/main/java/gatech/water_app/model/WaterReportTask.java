package gatech.water_app.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

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

    /**
     * Adds Water Source Report to database
     * @param sourceReport
     */
    public static void addWaterSourceReport(WaterSourceReport sourceReport) {
        //temp debugging code
        sourceList.add(sourceReport);
    }

    //temp
    /**
     * returns the static sourceList
     */
    public static List<WaterSourceReport> getSourceList() {
        return sourceList;
    }

    //temp
    /**
     * returns the static sourceList
     */
    public static String[] getSourceListString() {
        String[] temp = new String[sourceList.size()];
        for (int i = 0; i < sourceList.size(); i++){
            temp[i] = sourceList.get(i).toStringTemp();
        }
        return temp;
    }

    /**
     * Search for water source report
     * @param position
     * @return
     */
    public static String getSourceReportInfo(LatLng position) {
        String returnData = "";
        for (WaterSourceReport element: sourceList) {
            if (element.getLocation().getLatitude() == position.latitude && element.getLocation().getLongitude() == position.longitude) {
                returnData += element.toString() + "\n\n";
            }
        }
        return returnData;
    }

    /**
     * List of the water sources reports
     */
    public static List<WaterSourceReport> waterSourceReportList() {
        return sourceList;
    }

    /**
     * Adds Water Source Report to database
     * @param sourceReport
     */
    public static void addWaterPurityReport(WaterPurityReport sourceReport) {
        //temp debugging code
        purityList.add(sourceReport);
    }

    //temp
    /**
     * returns the static purityList
     */
    public static List<WaterPurityReport> getPurityList() {
        return purityList;
    }

    //temp
    /**
     * returns the static purityList
     */
    public static String[] getPurityListString() {
        String[] temp = new String[purityList.size()];
        for (int i = 0; i < purityList.size(); i++){
            temp[i] = purityList.get(i).toString();
        }
        return temp;
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
    public static List<WaterPurityReport> waterPurityReportList() {
        return purityList;
    }

}
