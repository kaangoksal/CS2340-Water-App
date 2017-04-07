package gatech.water_app.model;

/**
 *
 * Enum class for user titles
 * Created by John on 2/22/2017.
 */

public enum Title {

    USER ("User"),
    WORKER ("Worker"),
    MANAGER ("Manager"),
    ADMIN ("Admin");


    /**
     * Constructor for the title
     *
     * @param p_code   letter code / abbreviation for the course
     */
    Title (String p_code) {
        code = p_code;
    }

    private String code;

    /**
     *
     * @return   the title name
     */
    public String getCode(){return code;}

    /**
     *  Sets the title name
     *  @param p_code letter code
     */
    public void setName(String p_code){ code = p_code;}

    /**
     *
     * @return the display string representation of user's title
     */
    public String toString() { return code; }


}
