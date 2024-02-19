package models.PlayerHolder;

import models.Map.Map;
import models.MapHolder.MapHolder;
import models.Player.Player;

import java.util.ArrayList;

/**
 * The PlayerHolder class manages the collection of players in the game.
 * It provides methods to retrieve and set the list of players.
 */

public class PlayerHolder {


    private static ArrayList<Player> d_players= new ArrayList<>();

    /**
     * Retrieves the list of players.
     *
     * @return The list of players.
     */
    public static ArrayList<Player> getPlayers() {
        return d_players;
    }

    /**
     * Sets the list of players.
     *
     * @param p_players The list of players to set.
     */

    public static void setPlayers(ArrayList<Player> p_players) {
        PlayerHolder.d_players = p_players;
    }
}
