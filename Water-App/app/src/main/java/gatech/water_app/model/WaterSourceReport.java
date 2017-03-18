package gatech.water_app.model;
import android.location.Location;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by vy on 2/24/17.
 * Modified by Kaan
 */

public class WaterSourceReport extends Report {

    //Not sure if dataTime should be an int


    private WaterType type;
    private WaterCondition condition;
    private static int next_Id = 1;

//    Water source report
//    A user can submit this report any time they are logged in. This report consists of:
//
//    Date and time of report (can be autogenerated by application)+
//    Report Number (must be autogenerated by application)+
//    Name of reporter (can be autogenerated from user information)+
//    Location of water (manually entered. using any kind of location services or gps is extra credit)+
//    Type of water (Bottled, Well, Stream, Lake, Spring, Other)+
//    Condition of water (Waste, Treatable-Clear, Treatable-Muddy, Potable)+


    /**
     * Constructor
     * @param condition
     * @param type
     * @param reporter
     * @param reportNumber
     * @param dataTime
     */
    public WaterSourceReport(
            Date dataTime, String reportNumber, String reporter, WaterType type, WaterCondition condition, Location location) {
        super(dataTime,reportNumber,reporter, location);
        this.condition = condition;
        this.type = type;
    }

    /**
     * Constructor
     * @param reporter
     */
    public WaterSourceReport(String reporter) {
        this(new Date(), Integer.toString(next_Id++), reporter, null, null, null);
    }



    /**
     * Getter
     * @return
     */
    public Date getDataTime() {
        return super.getDataTime();
    }

    /**
     * Setter
     * @param dataTime
     */

    public void setDataTime(Date dataTime) {
        super.dataTime = dataTime;
    }

    /**
     * Getter
     * @return
     */
    public String getReportNumber() {
        return reportNumber;
    }

    /**
     * Setter
     * @param reportNumber
     */
    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    /**
     * Setter
     * @param reporter
     */
    public void setReporter(String reporter) {
        reporter = reporter;
    }

    /**
     * Getter
     * @return
     */
    public WaterType getType() {
        return type;
    }

    /**
     * Setter
     * @param type
     */
    public void setType(WaterType type) {
        this.type = type;
    }

    /**
     * Getter
     * @return
     */
    public WaterCondition getCondition() {
        return condition;
    }

    /**
     * Setter
     * @param condition
     */

    /**
     * Setter for location
     * @param location sets the location
     */
    public void setLocation(Location location) {
        super.setLocation(location);
    }

    /**
     * Setter for location
     * @return gets the location
     */
    public Location getLocation() {
        return super.getLocation();
    }

    /**
     * Setter condition
     * @return gets the location
     */
    public void setCondition(WaterCondition condition) {
        this.condition = condition;
    }


    public JSONObject toJSONObject() {
        try {
            JSONObject data = new JSONObject();
            data.put("water_type", this.type.toString());
            data.put("water_condition", this.condition.toString());
            String dataString = data.toString();

            JSONObject returnJson = new JSONObject();
            returnJson.put("date", this.dataTime);
            returnJson.put("report_number", this.reportNumber);
            returnJson.put("reporter", this.reporter);
            returnJson.put("location", this.getLocation().toString());
            returnJson.put("data", dataString);

            return returnJson;
        } catch (JSONException E){
            return null;
        }

    }

    /**
     * formats water report into string method
     * @return String entry for JSON format
     */
    @Override
    public String toString() {
        return "WaterSourceReport{" +
                "dataTime=" + dataTime +
                ", reportNumber=" + reportNumber +
                ", Reporter='" + super.reporter + '\'' +
                ", type=" + type +
                ", condition=" + condition +
                '}';
    }

    /**
     * Gets the string for printing purposes
     * @return String entry for printing
     */
    public String toStringTemp() {
        return "Report #" + this.reportNumber + ": \n\nDate/Time: " + this.dataTime + "\nReporter: " + this.reporter + "\nLocation: " + super.getLocation().getProvider() + "\nType: " + this.type + "\nCondition: " + this.condition + "\n";
    }


}
