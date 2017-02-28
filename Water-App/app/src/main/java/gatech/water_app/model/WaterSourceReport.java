package gatech.water_app.model;

/**
 * Created by vy on 2/24/17.
 */

public class WaterSourceReport {

    //Not sure if dataTime should be an int
    private int dataTime;
    private int reportNumber;
    private String Reporter;
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
    public WaterSourceReport(WaterCondition condition, WaterType type, String reporter, int reportNumber, int dataTime) {
        this.condition = condition;
        this.type = type;
        Reporter = reporter;
        this.reportNumber = reportNumber;
        this.dataTime = dataTime;
    }

    /**
     * Getter
     * @return
     */
    public int getDataTime() {
        return dataTime;
    }

    /**
     * Setter
     * @param dataTime
     */
    public void setDataTime(int dataTime) {
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
        return Reporter;
    }

    /**
     * Setter
     * @param reporter
     */
    public void setReporter(String reporter) {
        Reporter = reporter;
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
                ", Reporter='" + Reporter + '\'' +
                ", type=" + type +
                ", condition=" + condition +
                '}';
    }
}
