package models.Phase.MapEditing.Preload;

import controllers.GameEngine.GameEngine;
import models.GameContext.GameContext;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import models.Enums.GamePhase;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PreloadTest {
    private GameEngine d_gameEngine;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private GameContext mockCtx;

    @BeforeEach
    void setUp() {
        d_gameEngine=new GameEngine();
        mockCtx= GameContext.getInstance();
        mockCtx.setPhase(new Preload(d_gameEngine));
        System.setOut(new PrintStream(outContent)); // Redirect System.out to capture printed output
    }

    @AfterEach
    void tearDown() {
        d_gameEngine=null;
        mockCtx=null;
        System.setOut(System.out);
    }

    @Test
    void getPhaseName() {
        assertEquals(GamePhase.MAP_EDITING_PRELOAD, mockCtx.getPhase().getPhaseName());
    }

    @Test
    void modifyMapComponents() {
        String command = "editcontinent -add new_continent 5";
        mockCtx.getPhase().modifyMapComponents(command);
        // Verify that the appropriate feedback message is printed
        assertEquals("The 'editcontinent' command is not available in the Map Editing - Preload phase.\n" +
                "Please type 'showcommands' to see the commands you can run.",outContent.toString().trim());
    }

    @Test
    void saveMap() {
        String command = "savemap test_map.map";
          mockCtx.getPhase().saveMap(command);
        // Verify that the appropriate feedback message is printed
        assertEquals("The 'savemap' command is not available in the Map Editing - Preload phase.\n" +
                "Please type 'showcommands' to see the commands you can run.",outContent.toString().trim());
    }

    @Test
    void next_mapNotLoaded() {
        assertFalse(Preload.isMapLoaded); // Ensure map is not loaded
        mockCtx.getPhase().next();
        assertEquals("Cannot proceed to next phase. Please load the map first using the 'loadmap' command.",outContent.toString().trim());
    }
}