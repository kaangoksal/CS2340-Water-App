package gatech.water_app.model;


import android.location.Location;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
/**
 * Created by Alex Thien An Le on 2/28/2017.
 * Modified by Khan Goksal on 3/2/2017
 */

public class WaterPurityReport extends Report {

    //Not sure if dataTime should be an in
    //Auto generated from existing Water Source(Report)
    private OverallCondition overallCondition;
    private double virusPPM;
    private double  contaminantPPM;


//    Water Purity Report
//    A real water report would need to address multiple
// bacteria/virus and chemical contaminants. For our basic application,
// we assume that parts-per-million (PPM) is our measure and we will lump
// everything into two categories: Viruses and Contaminants.
//
//    Date and time of the report (can be auto generated by application)+
//    Report Number (must be auto generated by application)+
//    Name of Worker (can be auto generated from worker information)+
//    Location of water (manually entered. using any kind of location
// services or gps is extra credit)+
//    Overall Condition (Safe / Treatable / Unsafe) --
//    Virus PPM--
//    Contaminant PPM--


    /**
     * Constructor
     * @param dataTime date published
     * @param reportNumber the number of the report, auto generated
     * @param Reporter who reported this
     * @param overallCondition the overall condition of the water
     * @param virusPPM the ppm of viral substances in the water
     * @param contaminantPPM the ppm of contaminants in the water
     */
    private WaterPurityReport(Date dataTime, String reportNumber,
                              String Reporter, OverallCondition overallCondition,
                              double virusPPM, double contaminantPPM, Location location) {
       super(dataTime, reportNumber, Reporter, location);
        this.overallCondition = overallCondition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
    }

    /**
     * Constructor
     * @param reporter who reported this
     */
    public WaterPurityReport(String reporter) {
        this(new Date(), "", reporter, null, 0, 0, null);
    }

    /**
     * Getter
     * @return the ppm of contaminant in this water source
     */
    public double getContaminantPPM() {
        return contaminantPPM;
    }

    /**
     * Setter
     * @param contaminantPPM the amount of contaminant in the water
     */
    public void setContaminantPPM(double contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
    }

    /**
     * Getter
     * @return the ppm of viral substances in the water
     */
    public double getVirusPPM() {
        return virusPPM;
    }

    /**
     * Setter
     * @param virusPPM the ppm of viral substances in the water
     */
    public void setVirusPPM(double virusPPM) {
        this.virusPPM = virusPPM;
    }

    /**
     * Getter
     * @return the condition of the water
     */
    public OverallCondition getOverallCondition() {
        return overallCondition;
    }

    /**
     * Setter
     * @param overallCondition the condition of the water
     */
    public void setOverallCondition(OverallCondition overallCondition) {
        this.overallCondition = overallCondition;
    }

    /**
     * makes this object into a json object
     * @return json object representing the report
     */
    public JSONObject toJSONObject(){
        try {
            JSONObject data = new JSONObject();
            data.put("virus_ppm", this.virusPPM);
            data.put("contamination_ppm", this.contaminantPPM);
            data.put("overall_condition", this.overallCondition.toString());
            String dataString = data.toString();

            JSONObject locationJson = new JSONObject();
            Location loc = this.getLocation();
            locationJson.put("latitude", loc.getLatitude());
            locationJson.put("longitude", loc.getLongitude());
            String locationJSONstring = locationJson.toString();


            JSONObject returnJson = new JSONObject();
            returnJson.put("date", this.getDateString());
            returnJson.put("report_number", this.reportNumber);
            returnJson.put("reporter", this.reporter);
            returnJson.put("location", locationJSONstring);
            returnJson.put("data", dataString);
            returnJson.put("type", "WaterPurityReport");
            return returnJson;
        } catch (JSONException E) {
            return null;
        }



    }

    /**
     * This method is used to parse JSON object to WaterPurityReport
     * @param jsonObject the JSON object to be parsed into a Water purity report
     * @return the created water purity report from the json object
     */
    public static WaterPurityReport fromJSONObject(JSONObject jsonObject) {
        WaterPurityReport return_report;
        try {
            String report_number = jsonObject.getString("report_number");
            String dateString = jsonObject.getString("date");
            String reporter = jsonObject.getString("reporter");
            String locationString = jsonObject.getString("location");
            String dataString = jsonObject.getString("data");

            JSONObject dataJSON = new JSONObject(dataString);
            Double virus_ppm = dataJSON.getDouble("virus_ppm");
            Double contaminant_ppm = dataJSON.getDouble("contamination_ppm");
            OverallCondition overallCondition = OverallCondition
                    .valueOf(dataJSON.getString("overall_condition"));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date reportDate = sdf.parse(dateString);
            Location location = new Location("");
            JSONObject locationJSON = new JSONObject(locationString);
            location.setLatitude(locationJSON.getDouble("latitude"));
            location.setLongitude(locationJSON.getDouble("longitude"));

            return_report = new WaterPurityReport(reportDate,report_number,
                    reporter, overallCondition,virus_ppm,contaminant_ppm,location);

        } catch (Exception E) {
            Log.e("WaterSourceReport", "Something bad happened " + E.toString() + " " +
                    E.getMessage() + " " + Arrays.toString(E.getStackTrace()));
            return null;
        }
        return return_report;

    }


    @Override
    public String toString() {
        return "WaterPurityReport{" +
                "dataTime=" + dataTime +
                ", reportNumber=" + reportNumber +
                ", Reporter='" + super.reporter + '\'' +
                ", OverAll Condition=" + overallCondition +
                '}';
    }

}
