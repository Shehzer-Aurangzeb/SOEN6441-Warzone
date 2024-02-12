package main.java.models.Country;

import java.util.ArrayList;

public class Country {
    private int d_id;
    private String d_name;
    private int d_continentId;
    private ArrayList<Country> d_neighbours;

    public Country(int new_id, String new_name, int new_continentId) {
        d_id = new_id;
        d_name = new_name;
        d_continentId = new_continentId;
        d_neighbours= new ArrayList<>();
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

    public int getContinentID() {
        return this.d_continentId;
    }
    public ArrayList<Country> getNeighbours() {
        return this.d_neighbours;
    }


    /**
     * setters
     */
    public void setID(int p_id) {
        this.d_id = p_id;
    }

    public void setName(String p_name) {
        this.d_name = p_name;
    }

    public void setContinentID(int p_continentID) {
        this.d_continentId = p_continentID;
    }
    /**
     * add neighbouring country to neighbours array
     */
    public void addNeighbor(Country p_neighbor) {
        this.d_neighbours.add(p_neighbor);
    }
    /**
     * delete neighbouring country from neighbours array
     */
    public void removeNeighbor(Country p_neighbor) {
        this.d_neighbours.remove(p_neighbor);
    }

    @Override
    public String toString() {
        return "Country{name='" +this.d_name + "', id=" + this.d_id + "Continent ID= "+this.d_continentId
                + "\n}";
    }
}
