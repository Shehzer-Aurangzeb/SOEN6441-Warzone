package main.java.models.Continent;

/**
 * Represents a continent in a game map.
 */
public class Continent {
    private int d_id;
    private String d_name;
    private int d_armyBonus;
    private static int lastAssignedID = 0;

    /**
     * Constructs a new continent.
     *
     * @param new_name     The name of the continent.
     * @param new_armyBonus The army bonus provided by controlling the entire continent.
     */
    public Continent(String new_name, int new_armyBonus) {
        d_id = ++lastAssignedID;
        d_name = new_name;
        d_armyBonus = new_armyBonus;
    }

    /**
     * Retrieves the ID of the continent.
     *
     * @return The ID of the continent.
     */
    public int getID() {
        return this.d_id;
    }

    /**
     * Retrieves the name of the continent.
     *
     * @return The name of the continent.
     */
    public String getName() {
        return this.d_name;
    }

    /**
     * Retrieves the army bonus provided by controlling the entire continent.
     *
     * @return The army bonus.
     */
    public int getArmyBonus() {
        return this.d_armyBonus;
    }

    /**
     * Sets the ID of the continent.
     *
     * @param p_id The ID to set.
     */
    public void setID(int p_id) {
        this.d_id = p_id;
    }

    /**
     * Sets the name of the continent.
     *
     * @param p_name The name to set.
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * Sets the army bonus provided by controlling the entire continent.
     *
     * @param p_armyBonus The army bonus to set.
     */
    public void setArmyBonus(int p_armyBonus) {
        this.d_armyBonus = p_armyBonus;
    }

    /**
     * Returns a string representation of the continent.
     *
     * @return A string representation of the continent.
     */
    @Override
    public String toString() {
        return "Continent{name='" + this.d_name + "', armyBonus=" + this.d_armyBonus + "}";
    }
}
