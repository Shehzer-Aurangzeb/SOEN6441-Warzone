package models.GameContext;

import controllers.MapEditor.MapEditor;
import log.LogEntryBuffer;
import models.Map.Map;
import models.Phase.Phase;
import models.Player.Player;
import java.util.ArrayList;


/**
 * The GameContext class holds the global state of the game, including the current map, players, and game phase.
 * It serves as a centralized point of access for different components of the game to interact with and modify the game state.
 */
public class GameContext {
    private static GameContext d_instance= new GameContext();
    private Map d_map;
    private MapEditor d_mapEditor;
    private ArrayList<Player> d_players = new ArrayList<>();
    private final LogEntryBuffer d_logger= LogEntryBuffer.getInstance();
    private Phase d_currentGamePhase;
    private GameContext(){
        d_map = new Map();
        d_logger.clear();
    }

    /**
     * Provides access to the single instance of GameContext, creating it if it doesn't already exist.
     *
     * @return The single instance of GameContext.
     */
    public static GameContext getInstance(){
        if(d_instance==null) return d_instance=new GameContext();
        else return d_instance;
    }

    /**
     * Sets the current phase of the game.
     *
     * @param new_state The new game phase to set.
     */
    public void setPhase(Phase new_state) {
        d_currentGamePhase = new_state;
    }

    /**
     * Gets the current game phase.
     *
     * @return The current game phase.
     */
    public Phase getPhase() { return d_currentGamePhase; }

    /**
     * Gets the current map.
     *
     * @return The current map.
     */
    public Map getMap() {
        return d_map;
    }

    /**
     * Sets the current map to a new map.
     *
     * @param p_map The new map to set as the current map.
     */
    public void setMap(Map p_map) {
        this.d_map = p_map;
    }

    /**
     * Initializes and returns the map editor service.
     *
     * @return The map editor service.
     */
    public MapEditor getMapService() {
        if (d_mapEditor == null) {
            d_mapEditor = new MapEditor(this);
        }
        return d_mapEditor;
    }

    /**
     * Gets the list of players in the game.
     *
     * @return An ArrayList of Player objects.
     */
    public ArrayList<Player> getGamePlayers(){
        return d_players;
    }

    /**
     * Sets the list of players to a new list of players.
     *
     * @param new_players The new list of players.
     */
    public void setGamePlayers(ArrayList<Player> new_players){
        this.d_players=new_players;
    }

    /**
     * Adds a new player to the game.
     *
     * @param new_player The player to add.
     */
    public void addPlayer(Player new_player){
        this.d_players.add(new_player);
    }

    /**
     * Removes a player from the game by name.
     *
     * @param p_playerName The name of the player to remove.
     * @return The removed Player object if found, null otherwise.
     */
    public Player removePlayer(String p_playerName){
        for (Player player : d_players) {
            if (player.getName().equals(p_playerName)) {
               d_players.remove(player);
               return player;
            }
        }
        return null;
    }

    /**
     * Updates the game log with a new entry.
     *
     * @param p_log The log entry to add.
     */
    public void updateLog(String p_log){
        d_logger.log(p_log);
    }



}
