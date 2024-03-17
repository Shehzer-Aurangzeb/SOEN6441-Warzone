package views.MapView;

import models.Continent.Continent;
import models.Country.Country;
import models.GameContext.GameContext;
import models.Map.Map;

import models.Player.Player;

import utils.ViewUtils;


import java.util.ArrayList;
/**
 * The PlayerView class provides methods to display information about players and their owned countries on a map.
 */
public class PlayerView {

    /**
     * Displays the list of players along with their owned countries on the provided map..
     */
    private static final GameContext d_ctx= GameContext.getInstance();

    public static void displayPlayerList() {
        displayPlayerCountries(d_ctx.getGamePlayers(), d_ctx.getMap());

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
                String l_continentName = ViewUtils.getContinentName(p_continents, c.getContinentID());
                String l_name = ViewUtils.getNeighbourName(c);

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

        int[] columnWidths = {maxNameLength + 2, maxCountryNameLength + 2, maxCountryNameLength + 2, maxNoOfArmyLength + 2, maxContinentLength + 2, maxNeighboursLength + 2}; // Widths for Country ID, Name, Continent, Neighbours
        int totalWidth = ViewUtils.calculateTotalWidth(columnWidths) + 13;

        // Print header
        System.out.println("Map Information:");
        ViewUtils.printSeparator(totalWidth);

        String h1 = "Player";
        String h2 = "Country Owned";
        String h3 = "Country Name";
        String h4 = "Armies Deployed";
        String h5 = "Continent";
        String h6 = "Neighbours";

        System.out.printf("| %-" + columnWidths[0] + "s| %-" + columnWidths[1] + "s| %-" + columnWidths[2] + "s| %-" + columnWidths[3] + "s| %-" + columnWidths[4] + "s| %-" + columnWidths[5] + "s|\n", h1, h2, h3, h4, h5, h6);
        ViewUtils.printSeparator(totalWidth);
        for (Player p : playersList) {
            for (Country c : p.getOwnedCountries()) {
                String l_continentName = ViewUtils.getContinentName(p_continents, c.getContinentID());
                String l_name = ViewUtils.getNeighbourName(c);
                int no_of_armies_in_country = c.getArmiesDeployed();
                System.out.printf("| %-" + columnWidths[0] + "s| %-" + columnWidths[1] + "d| %-" + columnWidths[2] + "s| %-" + columnWidths[3] + "d| %-" + columnWidths[4] + "s| %-" + columnWidths[5] + "s|\n", p.getName(), c.getID(), c.getName(), no_of_armies_in_country, l_continentName, l_name);
            }
        }
        ViewUtils.printSeparator(totalWidth);
        for (Player p : playersList) {
            int l_countryCount =0;
            for (Country c : p.getOwnedCountries()) {
                l_countryCount++;
            }
            System.out.println("Player "+p.getName()+" owns "+l_countryCount+" countries.");
        }
    }
    public static void displayMyMap(Player player) {
        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(player);
        displayPlayerCountries(playerList, d_ctx.getMap());
    }

}
