package gatech.water_app.model;

/**
 * Enum for water type
 * Created by Alex Thien An Le on 2/28/2017.
 */

public enum WaterType {

    Bottled("Bottled"),
    Well("Well"),
    Stream("Stream"),
    Lake("Lake"),
    Spring("Spring"),
    Other("Other");

    private String nameType;

    WaterType(String name) {
        nameType = name;
    }

    /**
     * Getter
     * @return
     */
    public String getNameType() {
        return nameType;
    }

    /**
     * Setter
     * @param nameType
     */
    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    @Override
    public String toString() {
        return nameType;
    }
}
