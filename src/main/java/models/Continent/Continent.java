package models.Continent;

import models.Country.Country;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a continent in a game map.
 */
public class Continent {
    private int d_id;
    public static int lastAssignedID=1;
    private String d_name;
    private int d_armyBonus;
    private List<Country> d_countries;
    /**
     * Constructs a new continent.
     *
     * @param new_name     The name of the continent.
     * @param new_armyBonus The army bonus provided by controlling the entire continent.
     */
    public Continent(String new_name, int new_armyBonus) {
        this.d_id=lastAssignedID;
        this.d_name = new_name;
        this.d_armyBonus = new_armyBonus;
        lastAssignedID++;
        this.d_countries= new ArrayList<>();
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
     * Retrieves the list of countries of the continent.
     *
     * @return The countries of the continent.
     */
    public List<Country> getCountries() {
        return this.d_countries;
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
     * Add the country to the list of countries of the continent.
     *
     */
    public void addCountry(Country p_country) {
        this.d_countries.add(p_country);
    }
    /**
     * Remove the country from the list of countries of the continent.
     *
     */
    public void removeCountry(Country p_country) {
        this.d_countries.remove(p_country);
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
