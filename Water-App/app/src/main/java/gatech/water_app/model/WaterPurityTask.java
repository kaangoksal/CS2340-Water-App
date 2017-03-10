package gatech.water_app.model;

import com.google.android.gms.maps.model.LatLng;
import android.location.Location;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles and stores purity reports
 * Created by John on 3/10/2017.
 */

public class WaterPurityTask {

    //temp
    private static List<WaterPurityReport> list = new ArrayList<>();

    /**
     * Adds Water Source Report to database
     * @param sourceReport
     */
    public static void addWaterPurityReport(WaterPurityReport sourceReport) {
        //temp debugging code
        list.add(sourceReport);
    }

    //temp
    /**
     * returns the static list
     */
    public static List<WaterPurityReport> getList() {
        return list;
    }

    //temp
    /**
     * returns the static list
     */
    public static String[] getListString() {
        String[] temp = new String[list.size()];
        for (int i = 0; i < list.size(); i++){
            temp[i] = list.get(i).toString();
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
        for (WaterPurityReport element: list) {
            if (element.getLocation().getLatitude() == position.latitude && element.getLocation().getLongitude() == position.longitude) {
                returnData += element.toString() + "\n\n";
            }
        }
        return returnData;
    }

    /**
     * List of the water sources reports
     */
    public static List<WaterPurityReport> waterSourceReportList() {
        return list;
    }

}