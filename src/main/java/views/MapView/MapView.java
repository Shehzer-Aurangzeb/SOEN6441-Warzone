package views.MapView;

import controllers.GameEngine.GameEngine;
import models.Continent.Continent;
import models.Country.Country;
import models.Map.Map;
import models.MapHolder.MapHolder;

import java.util.ArrayList;

public class MapView {
    private static Map map;
    public static void displayMapInformation() {
        map= MapHolder.getMap();
        if(map.getContinents().isEmpty()){
            System.out.println("\nThe map is currently empty or has not been loaded yet. " +
                    "Please load a map or add elements to the map before using the 'showmap' command.");
            return;
        }
        displayCountriesAndContinents(map.getCountries(), map.getContinents());
    }

    private static void displayCountriesAndContinents(ArrayList<Country> p_countries, ArrayList<Continent> p_continents) {
        int maxIDLength = 11;
        int maxNameLength = 0;
        int maxContinentLength = 0;
        int maxNeighboursLength = 0;

        for (Country country : p_countries) {
            String l_name = "";
            String l_continentName = getContinentName(p_continents, country.getContinentID());

            ArrayList<Country> neighbours = country.getNeighbours();
            for (Country neighbour : neighbours) {
                l_name = l_name.concat(neighbour.getName());
                l_name = l_name.concat(", ");
            }
            if (!neighbours.isEmpty()) {
                l_name = l_name.substring(0, l_name.length() - 2);
            }

            // Calculate max name length
            int nameLength = country.getName().length();
            if (nameLength > maxNameLength) {
                maxNameLength = nameLength;
            }

            // Calculate max continent name length
            int continentLength = l_continentName.length();
            if (continentLength > maxContinentLength) {
                maxContinentLength = continentLength;
            }

            // Calculate max neighbors length
            int neighboursLength = l_name.length();
            if (neighboursLength > maxNeighboursLength) {
                maxNeighboursLength = neighboursLength;
            }
        }

        // Calculate column widths
        int[] columnWidths = {maxIDLength, maxNameLength + 2, maxContinentLength + 2, maxNeighboursLength + 2}; // Widths for Country ID, Name, Continent, Neighbours
        int totalWidth = calculateTotalWidth(columnWidths) + 9;

        // Print header
        // System.out.println(totalWidth);
        System.out.println("Map Information:");
        printSeparator(totalWidth);
        String h1 = "Country ID";
        String h2 = "Name";
        String h3 = "Continent";
        String h4 = "Neighbours";
        System.out.printf("| %-" + columnWidths[0] + "s| %-" + columnWidths[1] + "s| %-" + columnWidths[2] + "s| %-" + columnWidths[3] + "s|\n", h1, h2, h3, h4);
        printSeparator(totalWidth);

        // Print data
        for (Country country : p_countries) {
            String l_name = "";
            String l_continentName = getContinentName(p_continents, country.getContinentID());

            ArrayList<Country> neighbours = country.getNeighbours();
            for (Country neighbour : neighbours) {
                l_name = l_name.concat(neighbour.getName());
                l_name = l_name.concat(", ");
            }
            if (!neighbours.isEmpty()) {
                l_name = l_name.substring(0, l_name.length() - 2);
            }

            System.out.printf("| %-" + columnWidths[0] + "d| %-" + columnWidths[1] + "s| %-" + columnWidths[2] + "s| %-" + columnWidths[3] + "s|\n", country.getID(), country.getName(), l_continentName, l_name);
        }

        // Print bottom border
        printSeparator(totalWidth);
    }
    private static void printSeparator(int width) {
        System.out.println("".format("%-" + (width) + "s", "").replace(' ', '_'));
    }

    private static int calculateTotalWidth(int[] columnWidths) {
        int totalWidth = 0;
        for (int width : columnWidths) {
            totalWidth += width;
        }
        return totalWidth ; // 27 is the total number of characters in the header line
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
