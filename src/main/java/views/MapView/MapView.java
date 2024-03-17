package views.MapView;
import models.Continent.Continent;
import models.Country.Country;
import models.GameContext.GameContext;
import models.Map.Map;

import java.util.ArrayList;

/**
 * The MapView class provides methods to display information about countries and continents on a map.
 */
public class MapView {
    private static Map map;
    private static final GameContext d_ctx= GameContext.getInstance();
    public static void displayMapInformation() {
        map=d_ctx.getMap();
        if(map.getContinents().isEmpty()){
            System.out.println("\nThe map is currently empty or has not been loaded yet. " +
                    "Please load a map or add elements to the map before using the 'showmap' command.");
            return;
        }

        displayCountriesAndContinents(map.getCountries(), map.getContinents());
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
            String l_continentName = getContinentName(p_continents, country.getContinentID());
            l_name = getNeighbourName(country);

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
            l_name = getNeighbourName(country);

            System.out.printf("| %-" + columnWidths[0] + "d| %-" + columnWidths[1] + "s| %-" + columnWidths[2] + "s| %-" + columnWidths[3] + "s|\n", country.getID(), country.getName(), l_continentName, l_name);
        }

        // Print bottom border
        printSeparator(totalWidth);
    }

    /**
     * Prints a separator line of specified width.
     *
     * @param width The width of the separator line.
     */
    private static void printSeparator(int width) {
        System.out.println("".format("%-" + (width) + "s", "").replace(' ', '_'));
    }

    /**
     * Calculates the total width required for formatting.
     *
     * @param columnWidths An array of column widths.
     * @return The total width.
     */
    private static int calculateTotalWidth(int[] columnWidths) {
        int totalWidth = 0;
        for (int width : columnWidths) {
            totalWidth += width;
        }
        return totalWidth ; // the total number of characters in the header line
    }

    /**
     * Retrieves the names of the neighboring countries of the provided country.
     *
     * @param country The Country object whose neighbors' names are to be retrieved.
     * @return A string containing the names of the neighboring countries.
     */
    public static String getNeighbourName(Country country){
        ArrayList<Country> neighbours = country.getNeighbours();
        String l_name = "";

        for (Country neighbour : neighbours) {
            l_name = l_name.concat(neighbour.getName());
            l_name = l_name.concat(", ");
        }

        if (!neighbours.isEmpty()) {
            l_name = l_name.substring(0, l_name.length() - 2);
        }

        return l_name;
    }

    /**
     * Retrieves the name of the continent from the provided continent list based on the continent ID.
     *
     * @param p_continents An ArrayList containing Continent objects representing continents.
     * @param p_continentID The ID of the continent whose name is to be retrieved.
     * @return The name of the continent.
     */
    private static String getContinentName(ArrayList<Continent> p_continents, int p_continentID) {
        for (Continent continent : p_continents) {
            if (continent.getID() == p_continentID) {
                return continent.getName();
            }
        }
        return "Unknown"; // If continent ID not found, return "Unknown"
    }
}
