package models.Map;

import models.Continent.Continent;
import models.Country.Country;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MapValidator {

    public static boolean validateMap(Map map) {
        ArrayList<Continent> continents = map.getContinents();
        ArrayList<Country> countries = map.getCountries();

        if (!isGraphConnected(countries)) {
            System.out.println("The map is a disconnected graph.");
            return false;
        }

        if (!areContinentsConnected(continents, countries)) {
            System.out.println("One or more continents are disconnected subgraphs.");
            return false;
        }

        if (!areCountryNamesUnique(countries)) {
            System.out.println("Duplicate country names found.");
            return false;
        }

        return true;
    }

    private static boolean isGraphConnected(ArrayList<Country> countries) {
        if (countries.isEmpty()) return true;

        Set<Country> visited = new HashSet<>();
        dfsGlobal(countries.get(0), visited, countries);

        return visited.size() == countries.size();
    }

    private static void dfsGlobal(Country country, Set<Country> visited, ArrayList<Country> countries) {
        visited.add(country);
        for (Country neighbor : country.getNeighbours()) {
            if (!visited.contains(neighbor)) {
                dfsGlobal(neighbor, visited, countries);
            }
        }
    }

    private static boolean areContinentsConnected(ArrayList<Continent> continents, ArrayList<Country> countries) {
        for (Continent continent : continents) {
            Set<Country> visited = new HashSet<>();
            ArrayList<Country> continentCountries = new ArrayList<>();

            for (Country country : countries) {
                if (country.getContinentID() == continent.getID()) {
                    continentCountries.add(country);
                }
            }

            if (!continentCountries.isEmpty()) {
                dfsContinent(continentCountries.get(0), visited, continentCountries, continent.getID());
            }

            if (visited.size() != continentCountries.size()) {
                System.out.println("Disconnected continent found: " + continent.getName());
                System.out.println("Visited countries: " + visited.size() + ", Total countries: " + continentCountries.size());
                return false;
            }
        }
        return true;
    }

    private static void dfsContinent(Country country, Set<Country> visited, ArrayList<Country> continentCountries, int continentID) {
        visited.add(country);
        for (Country neighbor : country.getNeighbours()) {
            if (!visited.contains(neighbor) && neighbor.getContinentID() == continentID) {
                dfsContinent(neighbor, visited, continentCountries, continentID);
            }
        }
    }

    private static boolean areCountryNamesUnique(ArrayList<Country> countries) {
        Set<String> countryNames = new HashSet<>();
        for (Country country : countries) {
            if (!countryNames.add(country.getName())) {
                return false;
            }
        }
        return true;
    }
}
