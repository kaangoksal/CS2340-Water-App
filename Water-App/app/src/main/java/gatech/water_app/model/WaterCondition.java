package gatech.water_app.model;

/**
 * Created by Alex Thien An Le on 2/28/2017.
 */

public enum WaterCondition {

    Waste("Waste"),
    TreatableClear("Treatable-Clear"),
    TreatableMuddy("Treatable-Muddy"),
    Potable("Potable");

    private String nameCondition;

    /**
     * Constructor
     * @param nameCondition
     */
    WaterCondition(String nameCondition) {
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
