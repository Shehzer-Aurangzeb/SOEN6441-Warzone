package models.Map;


import models.Continent.Continent;
import models.Country.Country;

import java.util.ArrayList;

/**
 * The Map class represents the game map containing continents and countries.
 */
public class Map {
    /** List of continents in the map. */
    private final ArrayList<Continent> d_continents;

    /** List of countries in the map. */
    private final ArrayList<Country> d_countries;

    /**
     * Constructor to initialize an empty map.
     */
    public Map(){
        this.d_continents = new ArrayList<>();
        this.d_countries = new ArrayList<>();
    }

    /**
     * Add a country to the map.
     *
     * @param p_country The country to add.
     */
    public void addCountry(Country p_country){
        d_countries.add(p_country);
    }

    /**
     * Add a continent to the map.
     *
     * @param p_continent The continent to add.
     */
    public void addContinent(Continent p_continent){
        d_continents.add(p_continent);
    }
    /**
     * Remove a continent from the map.
     *
     * @param p_continent The continent to remove.
     */
    public void removeContinent(Continent p_continent){
        this.d_continents.remove(p_continent);
    }
    /**
     * Remove a country from the map.
     *
     * @param p_country The country to remove.
     */
    public void removeCountry(Country p_country){
        this.d_countries.remove(p_country);
    }

    /**
     * Get all countries in the map.
     *
     * @return The list of countries in the map.
     */
    public ArrayList<Country> getCountries(){
        return this.d_countries;
    }

    /**
     * Get a country in the map by its ID.
     *
     * @param p_id The ID of the country to find.
     * @return The country with the specified ID, or null if not found.
     */
    public Country getCountryByID(int p_id){
        for(Country country : this.d_countries){
            if(country.getID() == p_id) return country;
        }
        return null;
    }
    /**
     * Get a country in the map by its name.
     *
     * @param p_name The name of the country to find.
     * @return The country with the specified name, or null if not found.
     */
    public Country getCountryByName(String p_name){
        for(Country country : this.d_countries){
            if(country.getName().equals(p_name)) return country;
        }
        return null;
    }

    /**
     * Get a continent in the map by its ID.
     *
     * @param p_id The ID of the continent to find.
     * @return The continent with the specified ID, or null if not found.
     */
    public Continent getContinentByID(int p_id){
        for(Continent continent : this.d_continents){
            if(continent.getID() == p_id) return continent;
        }
        return null;
    }
    /**
     * Get a continent in the map by its name.
     *
     * @param p_name The name of the continent to find.
     * @return The continent with the specified name, or null if not found.
     */
    public Continent getContinentByName(String p_name){
        for(Continent continent : this.d_continents){
            if(continent.getName().equals(p_name)) return continent;
        }
        return null;
    }
    /**
     * Checks if two countries are adjacent.
     *
     * @param country1 The first country.
     * @param country2 The second country.
     * @return True if the countries are adjacent, false otherwise.
     */
    public boolean areAdjacent(Country country1, Country country2) {
        // Get the list of neighboring countries for country1
        ArrayList<Country> neighbors = country1.getNeighbours();

        // Check if country2 is in the list of neighboring countries
        return neighbors.contains(country2);
    }

    /**
     * Get all continents in the map.
     *
     * @return The list of continents in the map.
     */
    public ArrayList<Continent> getContinents(){
        return this.d_continents;
    }
}
