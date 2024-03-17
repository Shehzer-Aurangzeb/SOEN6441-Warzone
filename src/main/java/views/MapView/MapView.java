package views.MapView;

import controllers.GameEngine.GameEngine;
import log.LogEntryBuffer;
import models.Continent.Continent;
import models.Country.Country;
import models.Map.Map;
import models.MapHolder.MapHolder;
import utils.ViewUtils;

import java.util.ArrayList;

/**
 * The MapView class provides methods to display information about countries and continents on a map.
 */
public class MapView {
    private static Map map;
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();
    public static void displayMapInformation() {
        map= MapHolder.getMap();
        if(map.getContinents().isEmpty()){
            System.out.println("\nThe map is currently empty or has not been loaded yet. " +
                    "Please load a map or add elements to the map before using the 'showmap' command.");
            return;
        }

        displayCountriesAndContinents(map.getCountries(), map.getContinents());
        d_logger.log("Showmap command entered.");
    }

    /**
     * Displays information about countries and continents based on the provided lists.
     *
     * @param p_countries An ArrayList containing Country objects representing countries.
     * @param p_continents An ArrayList containing Continent objects representing continents.
     */
    private static void displayCountriesAndContinents(ArrayList<Country> p_countries, ArrayList<Continent> p_continents) {
        int maxIDLength = 11;
        int maxNameLength = 0;
        int maxContinentLength = 0;
        int maxNeighboursLength = 0;

        for (Country country : p_countries) {
            String l_name = "";
            String l_continentName = ViewUtils.getContinentName(p_continents, country.getContinentID());
            l_name = ViewUtils.getNeighbourName(country);

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
        int totalWidth = ViewUtils.calculateTotalWidth(columnWidths) + 9;

        // Print header
        System.out.println("Map Information:");
        ViewUtils.printSeparator(totalWidth);

        String h1 = "Country ID";
        String h2 = "Name";
        String h3 = "Continent";
        String h4 = "Neighbours";

        System.out.printf("| %-" + columnWidths[0] + "s| %-" + columnWidths[1] + "s| %-" + columnWidths[2] + "s| %-" + columnWidths[3] + "s|\n", h1, h2, h3, h4);
        ViewUtils.printSeparator(totalWidth);

        // Print data
        for (Country country : p_countries) {
            String l_name = "";
            String l_continentName = ViewUtils.getContinentName(p_continents, country.getContinentID());
            l_name = ViewUtils.getNeighbourName(country);

            System.out.printf("| %-" + columnWidths[0] + "d| %-" + columnWidths[1] + "s| %-" + columnWidths[2] + "s| %-" + columnWidths[3] + "s|\n", country.getID(), country.getName(), l_continentName, l_name);
        }

        // Print bottom border
        ViewUtils.printSeparator(totalWidth);
    }
}
