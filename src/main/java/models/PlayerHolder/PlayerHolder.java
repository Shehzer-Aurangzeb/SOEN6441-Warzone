package models.PlayerHolder;

import models.Map.Map;
import models.MapHolder.MapHolder;
import models.Player.Player;

import java.util.ArrayList;

public class PlayerHolder {
    private static ArrayList<Player> d_players= new ArrayList<>();

    /**
     * Retrieves the existing players.
     *
     * @return The current map object.
     */
    public static ArrayList<Player> getPlayers() {
        return d_players;
    }
    /**
     * Sets the map object to the specified value.
     *
     * @param p_players The map object to set.
     */
    public static void setPlayers(ArrayList<Player> p_players) {
        PlayerHolder.d_players = p_players;
    }
}
