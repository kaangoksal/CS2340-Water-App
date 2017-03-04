package gatech.water_app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Thien An Le on 2/23/2017.
 */

public class WaterReportTask {

    //temp
    private static List<WaterSourceReport> list = new ArrayList<>();

    /**
     * Adds Water Source Report to database
     * @param sourceReport
     */
    public static void addWaterSourceReport(WaterSourceReport sourceReport) {
        //temp debugging code
        list.add(sourceReport);
    }

    //temp
    /**
     * returns the static list
     */
    public static List<WaterSourceReport> getList() {
        return list;
    }

    //temp
    /**
     * returns the static list
     */
    public static String[] getListString() {
        String[] temp = new String[list.size()];
        for (int i = 0; i < list.size(); i++){
            temp[i] = list.get(i).toStringTemp();
        }
        return temp;
    }

}
