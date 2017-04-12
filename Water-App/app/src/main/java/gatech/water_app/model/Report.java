package gatech.water_app.model;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kaan Goksal on 3/2/17.
 * Defines the parameters of a report and what can be done with it.
 */

public class Report {

    String reportNumber;
    String reporter;
    Date dataTime;
    private Location location;


    /**
     * constructor for a report
     * @param dataTime date
     * @param reportNumber report number
     * @param reporter who reported it
     * @param location where this is
     */
    public Report(Date dataTime, String reportNumber, String reporter, Location location) {
        this.dataTime = new Date();
        this.reporter = reporter;
        this.reportNumber = reportNumber;
        this.dataTime = dataTime;
        this.location = location;
    }

    /**
     * Getter
     * @return the date published
     */
    public Date getDataTime() {
        return dataTime;
    }
    /**
     * Getter SQL compliant
     * @return string of the date which is compatible with SQL
     */
    public CharSequence getDateString() {
        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return iso8601Format.format(this.dataTime);
    }
    /**
     * Setter
     * @param dataTime this is the date time that the report is created
     */
    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    /**
     * Getter
     * @return returns the report number
     */
    public String getReportNumber() {
        return reportNumber;
    }

    /**
     * Setter
     * @param reportNumber sets the report number
     */
    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    /**
     * Getter
     * @return returns the reporter
     */
    public CharSequence getReporter() {
        return reporter;
    }

    /**
     * Setter
     * @param reporter sets the reporter
     */
    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    /**
     * Setter for location
     * @param location sets the location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Setter for location
     * @return gets the location
     */
    public Location getLocation() {
        return this.location;
    }



}
