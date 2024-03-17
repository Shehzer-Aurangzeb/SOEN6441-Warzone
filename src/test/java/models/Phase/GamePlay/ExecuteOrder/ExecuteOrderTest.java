package models.Phase.GamePlay.ExecuteOrder;

import controllers.GameEngine.GameEngine;
import models.Enums.GamePhase;
import models.GameContext.GameContext;
import models.Phase.GamePlay.IssueOrder.IssueOrder;
import models.Player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExecuteOrderTest {
    private GameEngine gameEngine;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private GameContext gameContext;

    @BeforeEach
    void setUp() {
        gameContext = GameContext.getInstance();
        gameEngine = new GameEngine();
        gameContext.setPhase(new ExecuteOrder(gameEngine));
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        gameEngine = null;
        gameContext = null;
        System.setOut(System.out);
    }
    @Test
    void getPhaseName() {
        assertEquals(GamePhase.EXECUTE_ORDERS, gameContext.getPhase().getPhaseName());
    }

    @Test
    void assignReinforcements() {
        // Prepare test data
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        // Set players in the context
        gameContext.setGamePlayers(players);

        // Assign reinforcements
        gameContext.getPhase().assignReinforcements();

        // Verify reinforcements are assigned correctly
        assertEquals(3, player1.getNoOfArmies()); // Assuming player1 owns 9 countries
        assertEquals(3, player2.getNoOfArmies()); // Assuming player2 owns 9 countries
    }
    @Test
    void next(){
        gameContext.getPhase().next();
        assertEquals(GamePhase.ISSUE_ORDERS,gameContext.getPhase().getPhaseName());
    }
}