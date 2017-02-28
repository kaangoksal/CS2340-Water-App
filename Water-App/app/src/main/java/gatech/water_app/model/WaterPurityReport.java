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

    public WaterPurityReport(int dataTime, int reportNumber, String Reporter, WaterType type, OverallCondition overallCondition, double virusPPM, double contaminantPPM) {
       super(dataTime, reportNumber, Reporter, type, WaterCondition.NA);
        this.overallCondition = overallCondition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
    }
}
