package controllers.GameEngine;

import controllers.MapEditor.MapEditor;
import models.Phase.Phase;
import models.Player.Player;
import models.PlayerHolder.PlayerHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {
    private GameEngine gameEngine;
    private ByteArrayOutputStream outputStreamCaptor;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gameEngine = new GameEngine();
        outputStreamCaptor = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStreamCaptor));

        Phase mockPhase = mock(Phase.class);
        gameEngine.setPhase(mockPhase);
        doNothing().when(mockPhase).loadMap(anyString(), any(MapEditor.class));
        doNothing().when(mockPhase).editMap(anyString(), any(MapEditor.class));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testHandleLoadMapCommand() {
        String testMapName = "canada";
        gameEngine.setCommandForTesting("loadmap " + testMapName);
        gameEngine.handleCommand();

        // Instead of verifying console output, verify the interaction with the mock object
        verify(gameEngine.getPhase()).loadMap(eq(testMapName), any(MapEditor.class));
    }

    @Test
    public void testHandleEditMapCommand() {
        String testMapName = "canada";
        gameEngine.setCommandForTesting("editmap " + testMapName);
        gameEngine.handleCommand();

        // Verify the interaction with the mock object
        verify(gameEngine.getPhase()).editMap(eq(testMapName), any(MapEditor.class));
    }

    @Test
    public void testHandleModifyMapComponentsCommand() {
        String command = "editcontinent -add Asia 5";
        gameEngine.setCommandForTesting(command);
        gameEngine.handleCommand();

        verify(gameEngine.getPhase()).modifyMapComponents(eq(command), any(MapEditor.class));
    }

    @Test
    public void testHandleSaveMapCommand() {
        String filename = "exampleMap";
        gameEngine.setCommandForTesting("savemap " + filename);
        gameEngine.handleCommand();

        verify(gameEngine.getPhase()).saveMap(eq(filename), any(MapEditor.class));
    }

    @Test
    public void testHandleShowMapCommand() {
        gameEngine.setCommandForTesting("showmap");
        gameEngine.handleCommand();

        verify(gameEngine.getPhase()).showMap();
    }

    @Test
    public void testHandleAddOrRemovePlayerCommand() {
        String command = "gameplayer -add Alice";
        gameEngine.setCommandForTesting(command);
        gameEngine.handleCommand();

        // This line causes the error due to type mismatch
        // verify(gameEngine.getPhase()).addOrRemovePlayer(eq(command), anyList());

        // Corrected line with explicit type argument to match the expected method signature
        verify(gameEngine.getPhase()).addOrRemovePlayer(eq(command), any(ArrayList.class));
    }

    @Test
    public void testHandleProceedCommand() {
        gameEngine.setCommandForTesting("proceed");
        gameEngine.handleCommand();

        verify(gameEngine.getPhase()).next();
    }

    @Test
    public void testHandleExitCommand() {
        gameEngine.setCommandForTesting("exit");
        gameEngine.handleCommand();

        verify(gameEngine.getPhase()).exit();
    }

}
