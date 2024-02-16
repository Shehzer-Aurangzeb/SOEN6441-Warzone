package views.MapView;

import models.Continent.Continent;
import models.Country.Country;
import models.Map.Map;

import java.util.ArrayList;

public class MapView {
    public static void displayMapInformation(Map map) {
        displayCountriesAndContinents(map.getCountries(), map.getContinents());
    }

    private static void displayCountriesAndContinents(ArrayList<Country> p_countries, ArrayList<Continent> p_continents) {
        System.out.println("Map Information:");
        for (Country country : p_countries) {
            String l_name = "";
            String l_continentName = getContinentName(p_continents, country.getContinentID());
            ArrayList<Country> neighbours = country.getNeighbours();
            for(Country neighbour: neighbours){
                l_name = l_name.concat(neighbour.getName());
                l_name = l_name.concat(", ");
            }
            l_name = l_name.substring(0, l_name.length() - 2);
            System.out.println("Country ID: " + country.getID() + ", Name: " + country.getName() + ", Continent: " + l_continentName + ", Neighbours: " + l_name);
        }
    }

    private static String getContinentName(ArrayList<Continent> p_continents, int p_continentID) {
        for (Continent continent : p_continents) {
            if (continent.getID() == p_continentID) {
                return continent.getName();
            }
        }
        return "Unknown"; // If continent ID not found, return "Unknown"
    }
}
