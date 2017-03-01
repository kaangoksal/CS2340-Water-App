package gatech.water_app.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by vy on 2/24/17.
 */

public class WaterSourceReport {

    //Not sure if dataTime should be an int
    private String dataTime;
    private int reportNumber;
    private String reporter;
    private WaterType type;
    private WaterCondition condition;

    /**
     * Constructor
     * @param condition
     * @param type
     * @param reporter
     * @param reportNumber
     * @param dataTime
     */
    public WaterSourceReport(
            String dataTime, int reportNumber, String reporter, WaterType type, WaterCondition condition) {
        this.condition = condition;
        this.type = type;
        reporter = reporter;
        this.reportNumber = reportNumber;
        this.dataTime = dataTime;
    }

    /**
     * Constructor (default)
     */
    public WaterSourceReport(String reporter) {
        dataTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        reportNumber = 1;
        this.reporter = reporter;
        type = WaterType.Lake;
        condition = WaterCondition.Potable;

    }

    /**
     * Getter
     * @return
     */
    public void incReportNum() {
        reportNumber++;
    }

    /**
     * Getter
     * @return dataTime the date and time
     */
    public String getDataTime() {
        return dataTime;
    }

    /**
     * Setter
     * @param dataTime
     */
    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    /**
     * Getter
     * @return
     */
    public int getReportNumber() {
        return reportNumber;
    }

    /**
     * Setter
     * @param reportNumber
     */
    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    /**
     * Getter
     * @return
     */
    public String getReporter() {
        return reporter;
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
    public void setCondition(WaterCondition condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "WaterSourceReport{" +
                "dataTime=" + dataTime +
                ", reportNumber=" + reportNumber +
                ", Reporter='" + reporter + '\'' +
                ", type=" + type +
                ", condition=" + condition +
                '}';
    }
}
