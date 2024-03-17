package models.Country;

import models.Enums.PlayerType;

import java.util.ArrayList;

import static utils.Helpers.*;

/**
 * Represents a country in a game map.
 */
public class Country {
    private int d_id;
    private String d_name;
    private int d_continentId;
    private ArrayList<Country> d_neighbours;
    private int d_armiesDeployed;
    private PlayerType d_player;

    /**
     * Constructs a new country.
     *
     * @param new_id          The unique identifier of the country.
     * @param new_name        The name of the country.
     * @param new_continentId The ID of the continent to which the country belongs.
     */
    public Country(int new_id, String new_name, int new_continentId) {
        this.d_id = new_id;
        this.d_name = new_name;
        this.d_continentId = new_continentId;
        this.d_neighbours = new ArrayList<>();
        this.d_armiesDeployed = 0;
        this.d_player = PlayerType.NEUTRAL;
    }

    /**
     * Constructs a new country.
     *
     * @param new_name        The name of the country.
     * @param new_continentId The ID of the continent to which the country belongs.
     */
    public Country(String new_name, int new_continentId) {
        this.d_id = generateUniqueID();
        this.d_name = new_name;
        this.d_continentId = new_continentId;
        this.d_neighbours = new ArrayList<>();
        this.d_armiesDeployed = 0;
    }

    /**
     * Retrieves the ID of the country.
     *
     * @return The ID of the country.
     */
    public int getID() {
        return this.d_id;
    }

    /**
     * Retrieves the armies deployed of the country.
     *
     * @return The armies deployed of the country.
     */
    public int getArmiesDeployed() {
        return this.d_armiesDeployed;
    }

    /**
     * Retrieves the name of the country.
     *
     * @return The name of the country.
     */
    public String getName() {
        return this.d_name;
    }

    /**
     * Retrieves the ID of the continent to which the country belongs.
     *
     * @return The continent ID.
     */
    public int getContinentID() {
        return this.d_continentId;
    }

    /**
     * Retrieves the player owning the country.
     *
     * @return The player owning the country.
     */
    public PlayerType getPlayer() {
        if (this.d_player != null) {
            return this.d_player;
        } else {
            // Handle the situation when the player is null
            // For example, you can return a default player type
            return PlayerType.NEUTRAL; // Or any other default player type
        }
    }

    /**
     * Retrieves the list of neighbouring countries.
     *
     * @return The list of neighbouring countries.
     */
    public ArrayList<Country> getNeighbours() {
        return this.d_neighbours;
    }

    /**
     * Sets the ID of the country.
     *
     * @param p_id The ID to set.
     */
    public void setID(int p_id) {
        this.d_id = p_id;
    }

    /**
     * Sets the armies deployed for the country.
     *
     * @param p_armiesDeployed The name to set
     */
    public void setArmiesDeployed(int p_armiesDeployed) {
        this.d_armiesDeployed = p_armiesDeployed;
    }

    /**
     * Sets the name of the country.
     *
     * @param p_name The name to set.
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * Sets the ID of the continent to which the country belongs.
     *
     * @param p_continentID The continent ID to set.
     */
    public void setContinentID(int p_continentID) {
        this.d_continentId = p_continentID;
    }

    /**
     * Sets the player owning the country.
     *
     * @param player The player to set as the owner.
     */
    public void setPlayer(PlayerType player) {
        this.d_player = player;
    }

    /**
     * Adds a neighbouring country to the list of neighbours.
     *
     * @param p_neighbor The neighbouring country to add.
     */
    public void addNeighbor(Country p_neighbor) {
        this.d_neighbours.add(p_neighbor);
    }

    /**
     * Removes a neighbouring country from the list of neighbours.
     *
     * @param p_neighbor The neighbouring country to remove.
     */
    public void removeNeighbor(Country p_neighbor) {
        this.d_neighbours.remove(p_neighbor);
    }

    /**
     * Returns a string representation of the country.
     *
     * @return A string representation of the country.
     */
    @Override
    public String toString() {
        return "Country{name='" + this.d_name + "', id=" + this.d_id + ", Continent ID=" + this.d_continentId + "}";
    }
}
