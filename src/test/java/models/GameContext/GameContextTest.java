package models.GameContext;

import controllers.GameEngine.GameEngine;
import controllers.MapEditor.MapEditor;
import models.Map.Map;
import models.Phase.MapEditing.Preload.Preload;
import models.Phase.Phase;
import models.Player.Player;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GameContextTest {

    private GameContext mockGameContext;
    private GameEngine mockGameEngine;

    @BeforeEach
    public void setUp() {
        mockGameEngine= mock(GameEngine.class);
        mockGameContext = GameContext.getInstance();
    }
    @AfterEach
    public void tearDown() {
        mockGameEngine=null;
        mockGameContext = null;
    }

    @Test
    public void testSetAndGetPhase() {
        // Set phase
       Phase phase=new Preload(mockGameEngine);
        mockGameContext.setPhase(phase);

        // Verify phase is set correctly
        assertEquals(phase, mockGameContext.getPhase());
    }

    @Test
    public void testGetAndSetMap() {
        // Create a map
        Map mockMap = mock(Map.class);

        // Set map
        mockGameContext.setMap(mockMap);

        // Verify map is set correctly
        assertEquals(mockMap, mockGameContext.getMap());
    }

    @Test
    public void testGetMapService() {
        // Get map service
        MapEditor mapEditor = mockGameContext.getMapService();

        // Verify map service is retrieved
        assertNotNull(mapEditor);
    }

    @Test
    public void testGetAndSetGamePlayers() {
        // Create players
        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        players.add(player1);
        players.add(player2);

        // Set game players
        mockGameContext.setGamePlayers(players);

        // Verify game players are set correctly
        assertEquals(players, mockGameContext.getGamePlayers());
        //remove players
        players= new ArrayList<>();
        mockGameContext.setGamePlayers(players);
        assertEquals(0,mockGameContext.getGamePlayers().size());
    }

    @Test
    public void testAddAndRemovePlayer() {
        // Create a player
        Player player = new Player("TestPlayer");

        // Add player
        mockGameContext.addPlayer(player);

        // Verify player is added
        assertTrue(mockGameContext.getGamePlayers().contains(player));

        // Remove player
        mockGameContext.removePlayer(player.getName());

        // Verify player is removed
        assertFalse(mockGameContext.getGamePlayers().contains(player));
    }


}