package models.Phase.GamePlay.IssueOrder;

import controllers.GameEngine.GameEngine;
import models.Enums.GamePhase;
import models.GameContext.GameContext;
import models.Player.Player;
import org.junit.jupiter.api.*;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IssueOrderTest {
    private GameEngine gameEngine;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private GameContext gameContext;

    @BeforeEach
    void setUp() {
        gameContext = GameContext.getInstance();
        gameEngine = new GameEngine();
        gameContext.setPhase(new IssueOrder(gameEngine));
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
        assertEquals(GamePhase.ISSUE_ORDERS, gameContext.getPhase().getPhaseName());
    }

    @Test
    void addOrRemovePlayer() {
        String command= "gameplayer -add Player1";
        gameContext.getPhase().addOrRemovePlayer(command);
        assertEquals("The '"+command.split(" ")[0]+"' command is not available in the "+
                gameContext.getPhase().getPhaseName().getName() +
                " phase.\nPlease type 'showcommands' to see the commands you can run.",outContent.toString().trim());
    }

    @Test
    void next() {
        gameContext.getPhase().next();
        assertEquals(GamePhase.EXECUTE_ORDERS, gameContext.getPhase().getPhaseName());

    }
    private void setUpIssueOrderContext(){
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        gameContext.setGamePlayers(players);
    }
}