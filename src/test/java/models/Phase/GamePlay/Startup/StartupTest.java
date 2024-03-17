package models.Phase.GamePlay.Startup;

import controllers.GameEngine.GameEngine;
import models.Enums.GamePhase;
import models.GameContext.GameContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StartupTest {
    private GameEngine gameEngine;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private GameContext gameContext;

    @BeforeEach
    void setUp() {
        gameContext = GameContext.getInstance();
        //clean up game context
        gameContext.setGamePlayers( new ArrayList<>());
        gameEngine = new GameEngine();
        gameContext.setPhase(new Startup(gameEngine));
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
        assertEquals(GamePhase.STARTUP, gameContext.getPhase().getPhaseName());
    }

    @Test
    void addOrRemovePlayer() {
        StringBuilder  output=new StringBuilder();
        // Add player
        gameContext.getPhase().addOrRemovePlayer("gameplayer -add Player1");

        // Verify player is added
        assertEquals(1, gameContext.getGamePlayers().size());
        output.append("Player 'Player1' added successfully.\n\n").append("Players:\n").append("1. Player1");
        // Remove player
        gameContext.getPhase().addOrRemovePlayer("gameplayer -remove Player1");

        // Verify player is removed
        assertEquals(0, gameContext.getGamePlayers().size());
        output.append("\n\nPlayer 'Player1' removed successfully.\n\n").append("No players available.");
        assertEquals(output.toString(),outContent.toString().trim());
    }

    @Test
    void issueOrders() {
        gameContext.getPhase().issueOrders();
        assertEquals("The 'orders' command is not available in the "+
                gameContext.getPhase().getPhaseName().getName() +
                " phase.\nPlease type 'showcommands' to see the commands you can run.",outContent.toString().trim());
    }



    @Test
    void next() {
        gameContext.getPhase().next();
        assertEquals(GamePhase.ISSUE_ORDERS, gameContext.getPhase().getPhaseName());
    }
}