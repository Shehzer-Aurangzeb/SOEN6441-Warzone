package main.java.models.Continent;

public class Continent {
    private int d_id;
    private String d_name;
    private int d_armyBonus;
    private static int lastAssignedID=0;

    public Continent(String new_name, int new_armyBonus) {
        d_id = lastAssignedID++;
        d_name = new_name;
        d_armyBonus = new_armyBonus;
    }

    /**
     * getters
     */
    public int getID() {
        return this.d_id;
    }

    public String getName() {
        return this.d_name;
    }

    public int getArmyBonus() {
        return this.d_armyBonus;
    }

    /**
     * setter
     */
    public void setID(int p_id) {
        this.d_id = p_id;
    }

    public void setName(String p_name) {
        this.d_name = p_name;
    }

    public void setArmyBonus(int p_armyBonus) {
        this.d_armyBonus = p_armyBonus;
    }

    @Override
    public String toString() {
        return "Continent{name='" + this.d_name + "', armyBonus=" + this.d_armyBonus + "\n}";
    }

}
