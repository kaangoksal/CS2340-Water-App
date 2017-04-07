package gatech.water_app.model;

/**
 * Created by Alex Thien An Le on 2/28/2017.
 * Definies overall condition of water.
 */

public enum OverallCondition {

    Safe("Safe"),
    Treatable("Treatable"),
    Unsafe("Unsafe");

    private String nameCondition;

    /**
     * Constructor
     * @param nameCondition
     */
    OverallCondition(String nameCondition) {
        this.nameCondition = nameCondition;
    }

    /**
     * Getter
     * @return
     */
    public String getNameCondition() {
        return nameCondition;
    }

    /**
     * Setter
     * @param nameCondition
     */
    public void setNameCondition(String nameCondition) {
        this.nameCondition = nameCondition;
    }

    @Override
    public String toString() {
        return nameCondition;
    }
}
