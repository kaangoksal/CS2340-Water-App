package gatech.water_app.model;

/**
 * Created by Alex Thien An Le on 2/28/2017.
 */

public class WaterPurityReport extends WaterSourceReport {

    //Not sure if dataTime should be an int
    private int dataTime;
    private int reportNumber;
    private String Reporter;
    private WaterType type;                         //Auto generated from existing Water Source(Report)
    private WaterCondition condition;
    private OverallCondition overallCondition;
    private double virusPPM;
    private double  contaminantPPM;

    /**
     * Constructor
     * @param dataTime
     * @param reportNumber
     * @param Reporter
     * @param type
     * @param overallCondition
     * @param virusPPM
     * @param contaminantPPM
     */
    public WaterPurityReport(String dataTime, int reportNumber, String Reporter, WaterType type, OverallCondition overallCondition, double virusPPM, double contaminantPPM) {
       super(dataTime, reportNumber, Reporter, type, WaterCondition.NA);
        this.overallCondition = overallCondition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
    }

    /**
     * Getter
     * @return
     */
    public double getContaminantPPM() {
        return contaminantPPM;
    }

    /**
     * Setter
     * @param contaminantPPM
     */
    public void setContaminantPPM(double contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
    }

    /**
     * Getter
     * @return
     */
    public double getVirusPPM() {
        return virusPPM;
    }

    /**
     * Setter
     * @param virusPPM
     */
    public void setVirusPPM(double virusPPM) {
        this.virusPPM = virusPPM;
    }

    /**
     * Getter
     * @return
     */
    public OverallCondition getOverallCondition() {
        return overallCondition;
    }

    /**
     * Setter
     * @param overallCondition
     */
    public void setOverallCondition(OverallCondition overallCondition) {
        this.overallCondition = overallCondition;
    }
}
