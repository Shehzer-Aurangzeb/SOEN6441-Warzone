package models.Map;

import models.Continent.Continent;
import models.Country.Country;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MapValidator {

    // Validate the entire map
    public static boolean validateMap(Map map) {
        ArrayList<Continent> l_continents = map.getContinents(); // populate continents
        ArrayList<Country> l_countries = map.getCountries();// populate countries
        return isGraphConnected(l_countries) && areContinentsConnected(l_continents, l_countries) && areCountryNamesUnique(l_countries);
    }

    // Check if the entire map is a connected graph
    private static boolean isGraphConnected(ArrayList<Country> countries) {
        Set<Country> visited = new HashSet<>();
        dfs(countries.get(0), visited);

        return visited.size() == countries.size();
    }

    // Depth-First Search (DFS) to check connected graph
    private static void dfs(Country country, Set<Country> visited) {
        if (!visited.contains(country)) {
            visited.add(country);

            for (Country neighbor : country.getNeighbours()) {
                dfs(neighbor, visited);
            }
        }
    }

    // Check if each continent forms a connected subgraph
    private static boolean areContinentsConnected(ArrayList<Continent> p_continents, ArrayList<Country> p_countries) {
        for (Continent continent : p_continents) {
            Set<Country> l_continentCountries = getCountriesInContinent(continent, p_countries);

            if (!isGraphConnected(new ArrayList<>(l_continentCountries))) {
                return false;
            }
        }

        return true;
    }

    // Get countries in a specific continent
    private static Set<Country> getCountriesInContinent(Continent p_continent, ArrayList<Country> p_countries) {
        Set<Country> l_continentCountries = new HashSet<>();
        for (Country country : p_countries) {
            if (country.getContinentID() == p_continent.getID()) {
                l_continentCountries.add(country);
            }
        }
        return l_continentCountries;
    }

    // Check if country names are unique
    private static boolean areCountryNamesUnique(ArrayList<Country> p_countries) {
        Set<String> l_countryNames = new HashSet<>();
        for (Country country : p_countries) {
            if (!l_countryNames.add(country.getName())) {
                return false; // Duplicate country name found
            }
        }
        return true;
    }
}
