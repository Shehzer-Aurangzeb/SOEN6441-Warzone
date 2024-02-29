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
    private static int currentPlayerIndex = 0;
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

    /**
     * Retrieves the current player.
     *
     * @return The current player.
     */
    public static Player getCurrentPlayer() {
        if (d_players.isEmpty()) {
            return null;
        }
        return d_players.get(currentPlayerIndex);
    }

    /**
     * Moves to the next player.
     */
    public static void moveToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % d_players.size();
    }
}

