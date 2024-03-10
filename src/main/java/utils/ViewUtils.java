package utils;

import models.Continent.Continent;
import models.Country.Country;

import java.util.ArrayList;

public class ViewUtils {
    /**
     * Prints a separator line of specified width.
     *
     * @param width The width of the separator line.
     */
    public static void printSeparator(int width) {
        System.out.println("".format("%-" + (width) + "s", "").replace(' ', '_'));
    }
    /**
     * Calculates the total width required for formatting.
     *
     * @param columnWidths An array of column widths.
     * @return The total width.
     */
    public static int calculateTotalWidth(int[] columnWidths) {
        int totalWidth = 0;
        for (int width : columnWidths) {
            totalWidth += width;
        }
        return totalWidth;
    }

    /**
     * Retrieves the name of the continent from the provided continent list based on the continent ID.
     *
     * @param p_continents An ArrayList containing Continent objects representing continents.
     * @param p_continentID The ID of the continent whose name is to be retrieved.
     * @return The name of the continent.
     */
    public static String getContinentName(ArrayList<Continent> p_continents, int p_continentID) {
        for (Continent continent : p_continents) {
            if (continent.getID() == p_continentID) {
                return continent.getName();
            }
        }
        return "Unknown"; // If continent ID not found, return "Unknown"
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
}