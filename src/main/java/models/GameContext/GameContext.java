package models.GameContext;

import controllers.MapEditor.MapEditor;
import log.LogEntryBuffer;
import models.Map.Map;
import models.Phase.Phase;
import models.Player.Player;

import java.util.ArrayList;

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

    public static GameContext getInstance(){
        if(d_instance==null) return d_instance=new GameContext();
        else return d_instance;
    }
    public void setPhase(Phase new_state) {
        d_currentGamePhase = new_state;
    }

    public Phase getPhase() { return d_currentGamePhase; }
    public Map getMap() {
        return d_map;
    }

    public void setMap(Map p_map) {
        this.d_map = p_map;
    }
    public MapEditor getMapService() {
        if (d_mapEditor == null) {
            d_mapEditor = new MapEditor(this);
        }
        return d_mapEditor;
    }
    public ArrayList<Player> getGamePlayers(){
        return d_players;
    }
    public void setGamePlayers(ArrayList<Player> new_players){
        this.d_players=new_players;
    }
    public void addPlayer(Player new_player){
        this.d_players.add(new_player);
    }
    public Player removePlayer(String p_playerName){
        for (Player player : d_players) {
            if (player.getName().equals(p_playerName)) {
               d_players.remove(player);
               return player;
            }
        }
        return null;
    }
    public void updateLog(String p_log){
        d_logger.log(p_log);
    }



}
