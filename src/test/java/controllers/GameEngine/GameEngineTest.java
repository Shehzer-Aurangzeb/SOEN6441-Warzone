package controllers.GameEngine;

import models.GameContext.GameContext;
import models.Phase.MapEditing.Preload.Preload;
import org.junit.jupiter.api.*;

import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
class GameEngineTest {
    private GameEngine gameEngine;
    private final ByteArrayOutputStream outContent= new ByteArrayOutputStream();
    private GameContext gameContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gameEngine = new GameEngine();
        gameContext= GameContext.getInstance();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
    }

    @Test
    public void testHandleLoadMapCommand() {
        String testMapName = "canada";
        gameContext.setPhase(new Preload(gameEngine));
        gameEngine.setCommandForTesting("loadmap " + testMapName);
        gameEngine.handleCommand();

       assertEquals("Map loaded successfully. Type 'proceed' to move to the next phase of the game.",outContent.toString().trim());
    }

    @Disabled
    @Test
    public void testHandleEditMapCommand() {
        String testMapName = "canada";
        gameEngine.setCommandForTesting("editmap " + testMapName);
        gameEngine.handleCommand();

        // Verify the interaction with the mock object
        verify(gameContext.getPhase()).editMap(eq(testMapName));
    }

    @Disabled
    @Test
    public void testHandleModifyMapComponentsCommand() {
        String command = "editcontinent -add Asia 5";
        gameEngine.setCommandForTesting(command);
        gameEngine.handleCommand();

        verify(gameContext.getPhase()).modifyMapComponents(eq(command));
    }

    @Disabled
    @Test
    public void testHandleSaveMapCommand() {
        String filename = "exampleMap";
        gameEngine.setCommandForTesting("savemap " + filename);
        gameEngine.handleCommand();

        verify(gameContext.getPhase()).saveMap(eq(filename));
    }

    @Disabled
    @Test
    public void testHandleShowMapCommand() {
        gameEngine.setCommandForTesting("showmap");
        gameEngine.handleCommand();

        verify(gameContext.getPhase()).showMap();
    }

    @Disabled
    @Test
    public void testHandleAddOrRemovePlayerCommand() {
        String command = "gameplayer -add Alice";
        gameEngine.setCommandForTesting(command);
        gameEngine.handleCommand();

        // This line causes the error due to type mismatch
        // verify(gameEngine.getPhase()).addOrRemovePlayer(eq(command), anyList());

        // Corrected line with explicit type argument to match the expected method signature
        verify(gameContext.getPhase()).addOrRemovePlayer(eq(command));
    }

    @Disabled
    @Test
    public void testHandleProceedCommand() {
        gameEngine.setCommandForTesting("proceed");
        gameEngine.handleCommand();

        verify(gameContext.getPhase()).next();
    }

    @Disabled
    @Test
    public void testHandleExitCommand() {
        gameEngine.setCommandForTesting("exit");
        gameEngine.handleCommand();

        verify(gameContext.getPhase()).exit();
    }

}
