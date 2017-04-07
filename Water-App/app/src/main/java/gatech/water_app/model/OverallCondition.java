package gatech.water_app.model;

/**
 * Created by Alex Thien An Le on 2/28/2017.
 * Defines overall condition of water.
 */

public enum OverallCondition {

    Safe("Safe"),
    Treatable("Treatable"),
    Unsafe("Unsafe");

    private String nameCondition;

    /**
     * Constructor
     * @param nameCondition the name of the condition of water
     */
    OverallCondition(String nameCondition) {
        this.nameCondition = nameCondition;
    }

    /**
     * Getter
     * @return the name of the water condition
     */
    public String getNameCondition() {
        return nameCondition;
    }

    /**
     * Setter
     * @param nameCondition the name of the condition of water
     */
    public void setNameCondition(String nameCondition) {
        this.nameCondition = nameCondition;
    }

    @Override
    public String toString() {
        return nameCondition;
    }
}
