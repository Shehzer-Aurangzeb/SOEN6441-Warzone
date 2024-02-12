package main.java.models.Map;

import main.java.models.Continent.Continent;
import main.java.models.Country.Country;

import java.util.ArrayList;

public class Map {
    private ArrayList<Continent> d_continents;
    private ArrayList<Country> d_countries;

    public Map(){
        this.d_continents= new ArrayList<>();
        this.d_countries= new ArrayList<>();
    }
    public void addCountry(Country p_country){
        d_countries.add(p_country);
    }
    public void addContinent(Continent p_continent){
        d_continents.add(p_continent);
    }
    /**
     * getters
     */
    public ArrayList<Country> getCountries(){
        return this.d_countries;
    }
    public Country getCountryByID(int p_id){
        for(Country country:this.d_countries){
            if(country.getID()==p_id) return country;
        }
        return null;
    }
    public Continent getContinentByID(int p_id){
        for(Continent continent:this.d_continents){
            if(continent.getID()==p_id) return continent;
        }
        return null;
    }
    public ArrayList<Continent> getContinents(){
        return this.d_continents;
    }
}
