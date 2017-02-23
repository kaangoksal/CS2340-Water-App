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
     * @param pcode   letter code / abbreviation for the course
     */
    Title (String pcode) {
        code = pcode;
    }

    private String code;

    /**
     *
     * @return   the title name
     */
    public String getCode(){return code;}

    /**
     *  Sets the title name
     *  @param pcode letter code
     */
    public void setName(String pcode){ code = pcode;}

    /**
     *
     * @return the display string representation of user's title
     */
    public String toString() { return code; }


}
