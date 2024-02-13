package main.java.models.Map;

import main.java.models.Continent.Continent;
import main.java.models.Country.Country;

import java.util.ArrayList;

/**
 * The Map class represents the game map containing continents and countries.
 */
public class Map {

    /** List of continents in the map. */
    private ArrayList<Continent> d_continents;

    /** List of countries in the map. */
    private ArrayList<Country> d_countries;

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
     * Get all continents in the map.
     *
     * @return The list of continents in the map.
     */
    public ArrayList<Continent> getContinents(){
        return this.d_continents;
    }
}
