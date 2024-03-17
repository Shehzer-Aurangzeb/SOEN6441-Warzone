package models.Phase.GamePlay;

import controllers.GameEngine.GameEngine;
import models.GameContext.GameContext;
import models.Phase.GamePlay.ExecuteOrder.ExecuteOrder;
import models.Phase.GamePlay.IssueOrder.IssueOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class GamePlayTest {
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
    void loadMap() {
        gameContext.getPhase().loadMap("filename");
        assertEquals("The 'loadmap' command is not available in the "+
                gameContext.getPhase().getPhaseName().getName() +
                " phase.\nPlease type 'showcommands' to see the commands you can run.",outContent.toString().trim());
    }

    @Test
    void editMap() {
        gameContext.getPhase().editMap("editmap");
        assertEquals("The 'editmap' command is not available in the "+
                gameContext.getPhase().getPhaseName().getName() +
                " phase.\nPlease type 'showcommands' to see the commands you can run.",outContent.toString().trim());
    }

    @Test
    void modifyMapComponents() {
        String command= "editcontinent new_continent 5";
        gameContext.getPhase().modifyMapComponents(command);
        assertEquals("The '"+command.split(" ")[0]+"' command is not available in the "+
                gameContext.getPhase().getPhaseName().getName() +
                " phase.\nPlease type 'showcommands' to see the commands you can run.",outContent.toString().trim());
    }

    @Test
    void saveMap() {
        String command= "savemap filename";
        gameContext.getPhase().saveMap(command);
        assertEquals("The '"+command.split(" ")[0]+"' command is not available in the "+
                gameContext.getPhase().getPhaseName().getName() +
                " phase.\nPlease type 'showcommands' to see the commands you can run.",outContent.toString().trim());
    }
}