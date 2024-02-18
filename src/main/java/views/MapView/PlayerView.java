package views.MapView;

import models.Continent.Continent;
import models.Country.Country;
import models.Map.Map;
import models.MapHolder.MapHolder;
import models.Player.Player;
import models.PlayerHolder.PlayerHolder;

import java.util.ArrayList;
/**
 * The PlayerView class provides methods to display information about players and their owned countries on a map.
 */
public class PlayerView {

    /**
     * Displays the list of players along with their owned countries on the provided map..
     */
    public static void displayPlayerList() {
        displayPlayerCountries(PlayerHolder.getPlayers(), MapHolder.getMap());

    }

    /**
     * Displays the owned countries of each player along with relevant information.
     *
     * @param playersList An ArrayList containing Player objects representing the players.
     * @param map The Map object representing the game map.
     */
    private static void displayPlayerCountries(ArrayList<Player> playersList, Map map){
        int maxNameLength = 6;
        int maxCountryNameLength = 13;
        int maxNoOfArmyLength = 15;
        int maxContinentLength = 9;
        int maxNeighboursLength = 10;

        ArrayList<Continent> p_continents;
        p_continents = map.getContinents();

        for (Player p : playersList) {

            // Calculate max name length
            int nameLength = p.getName().length();
            if (nameLength > maxNameLength) {
                maxNameLength = nameLength;
            }

            for (Country c : p.getOwnedCountries()) {
                String l_continentName = getContinentName(p_continents, c.getContinentID());
                String l_name = MapView.getNeighbourName(c);

                // Calculate max country name length
                int countryNameLength = c.getName().length();
                if (countryNameLength > maxCountryNameLength) {
                    maxCountryNameLength = countryNameLength;
                }

                // Calculate max no of armies in a country length
                // int noOfArmyLength = c.getNoOfArmies().length();
                // if (noOfArmyLength > maxNoOfArmyLength) {
                //     maxNoOfArmyLength = noOfArmyLength;
                // }

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
        }

        int[] columnWidths = {maxNameLength + 2, maxCountryNameLength + 2, maxNoOfArmyLength + 2, maxContinentLength + 2, maxNeighboursLength + 2}; // Widths for Country ID, Name, Continent, Neighbours
        int totalWidth = calculateTotalWidth(columnWidths) + 11;

        // Print header
        System.out.println("Map Information:");
        printSeparator(totalWidth);

        String h1 = "Player";
        String h2 = "Country Owned";
        String h3 = "Armies Deployed";
        String h4 = "Continent";
        String h5 = "Neighbours";

        System.out.printf("| %-" + columnWidths[0] + "s| %-" + columnWidths[1] + "s| %-" + columnWidths[2] + "s| %-" + columnWidths[3] + "s| %-" + columnWidths[4] + "s|\n", h1, h2, h3, h4, h5);
        printSeparator(totalWidth);
        for (Player p : playersList) {
            for (Country c : p.getOwnedCountries()) {
                String l_continentName = getContinentName(p_continents, c.getContinentID());
                String l_name = MapView.getNeighbourName(c);
                int no_of_armies_in_country = 0;
                System.out.printf("| %-" + columnWidths[0] + "s| %-" + columnWidths[1] + "s| %-" + columnWidths[2] + "d| %-" + columnWidths[3] + "s| %-" + columnWidths[4] + "s|\n", p.getName(), c.getName(), no_of_armies_in_country, l_continentName, l_name);
            }
        }
        printSeparator(totalWidth);
        for (Player p : playersList) {
            int l_countryCount =0;
            for (Country c : p.getOwnedCountries()) {
                l_countryCount++;
            }
            System.out.println("Player "+p.getName()+" owns "+l_countryCount+" countries.");
        }
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
