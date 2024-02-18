package models.Map;

import models.Continent.Continent;
import models.Country.Country;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MapValidator {

    /**
     * Validates the integrity of a map represented by continents and countries.
     * The validation includes checking for a connected graph, if continents are connected sub-graphs,
     * and unique country names.
     *
     * @param p_map The map to be validated, represented as an object of type Map.
     * @return true if the map is valid; false otherwise.
     */
    public static boolean validateMap(Map p_map) {
        // Retrieve continents and countries from the given map
        ArrayList<Continent> l_continents = p_map.getContinents();
        ArrayList<Country> l_countries = p_map.getCountries();

        // Check if the map is a connected graph
        if (!isGraphConnected(l_countries)) {
            System.out.println("The map is a disconnected graph.");
            return false;
        }

        // Check if all continents are connected sub-graphs
        if (!areContinentsConnected(l_continents, l_countries)) {
            System.out.println("One or more continents are disconnected subgraphs.");
            return false;
        }

        // Check if country names are unique
        if (!areCountryNamesUnique(l_countries)) {
            System.out.println("Duplicate country names found.");
            return false;
        }

        // If all validation checks pass, the map is considered valid
        return true;
    }

    /**
     * Checks whether the graph formed by countries is connected.
     *
     * @param p_countries List of countries to check for graph connectivity.
     * @return true if the graph is connected; false otherwise.
     */
    private static boolean isGraphConnected(ArrayList<Country> p_countries) {
        if (p_countries.isEmpty()) return true;

        Set<Country> l_visited = new HashSet<>();
        dfsCountries(p_countries.get(0), l_visited);

        return l_visited.size() == p_countries.size();
    }

    /**
     * Performs a depth-first search (DFS) traversal of the graph starting from a specific country.
     *
     * @param p_country      The starting country for DFS traversal.
     * @param p_visited      Set of visited countries during DFS traversal.
     */
    private static void dfsCountries(Country p_country, Set<Country> p_visited) {
        p_visited.add(p_country);
        for (Country neighbor : p_country.getNeighbours()) {
            if (!p_visited.contains(neighbor)) {
                dfsCountries(neighbor, p_visited);
            }
        }
    }

    /**
     * Checks whether all countries within each continent are connected.
     *
     * @param p_continents          List of continents to check for connectivity.
     * @param p_countries           List of all countries in the map.
     * @return true if all continents are connected sub-graphs; false otherwise.
     */
    private static boolean areContinentsConnected(ArrayList<Continent> p_continents, ArrayList<Country> p_countries) {
        for (Continent continent : p_continents) {
            Set<Country> l_visited = new HashSet<>();
            ArrayList<Country> l_continentCountries = new ArrayList<>();

            // Collect countries belonging to the current continent
            for (Country country : p_countries) {
                if (country.getContinentID() == continent.getID()) {
                    l_continentCountries.add(country);
                }
            }

            // Perform DFS traversal for the continent
            if (!l_continentCountries.isEmpty()) {
                dfsContinent(l_continentCountries.get(0), l_visited, continent.getID());
            }

            // Check if all countries in the continent are visited
            if (l_visited.size() != l_continentCountries.size()) {
                System.out.println("Disconnected continent found: " + continent.getName());
                System.out.println("Visited countries: " + l_visited.size() + ", Total countries: " + l_continentCountries.size());
                return false;
            }
        }
        return true;
    }

    /**
     * Performs a depth-first search (DFS) traversal of countries within a specific continent.
     * Marks visited countries in the provided set and ensures traversal only within the given continent.
     *
     * @param p_country      The starting country for DFS traversal.
     * @param p_visited      Set of visited countries during DFS traversal.
     * @param p_continentID  ID of the continent being traversed.
     */
    private static void dfsContinent(Country p_country, Set<Country> p_visited, int p_continentID) {
        p_visited.add(p_country);
        for (Country neighbor : p_country.getNeighbours()) {
            if (!p_visited.contains(neighbor) && neighbor.getContinentID() == p_continentID) {
                dfsContinent(neighbor, p_visited, p_continentID);
            }
        }
    }

    /**
     * Checks whether country names in the list are unique.
     *
     * @param p_countries List of countries to check for unique names.
     * @return true if all country names are unique; false otherwise.
     */
    private static boolean areCountryNamesUnique(ArrayList<Country> p_countries) {
        Set<String> l_countryNames = new HashSet<>();
        for (Country country : p_countries) {
            if (!l_countryNames.add(country.getName())) {
                return false;
            }
        }
        return true;
    }
}
