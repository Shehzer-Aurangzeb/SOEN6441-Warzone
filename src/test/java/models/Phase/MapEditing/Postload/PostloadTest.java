package models.Phase.MapEditing.Postload;

import controllers.GameEngine.GameEngine;
import models.Continent.Continent;
import models.Country.Country;
import models.Enums.GamePhase;
import models.GameContext.GameContext;

import models.Map.Map;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;


import static org.junit.jupiter.api.Assertions.*;


public class PostloadTest {
    private GameEngine gameEngine;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private GameContext gameContext;
    private Map map;

    @BeforeEach
    void setUp() {
        gameContext = GameContext.getInstance();
        gameEngine = new GameEngine();
        gameContext.setPhase(new Postload(gameEngine));
        System.setOut(new PrintStream(outContent)); // Redirect System.out to capture printed output
    }

    @AfterEach
    void tearDown() {
        gameEngine = null;
        gameContext = null;
        map = null;
        System.setOut(System.out);
    }

    @Test
    void getPhaseName() {
        assertEquals(GamePhase.MAP_EDITING_POSTLOAD, gameContext.getPhase().getPhaseName());
    }

    @Test
    void modifyMapComponents() {
        setUpModifyMapContext();
        String command = "editcontinent -add Asia 5";

        gameContext.getPhase().modifyMapComponents(command);
        Continent l_continent= gameContext.getMap().getContinentByName("Asia");
        //check if continent is added
        assertEquals("Continent 'Asia' with value '5' has been successfully added.",outContent.toString().trim());
    }

    @Test
    void saveMap() {
        // Prepare the map
        Map map = new Map();
        gameContext.getMapService().getMapRegistry().put("test-file", map);

        // Call the method
        gameContext.getPhase().saveMap("test-file");
        File file = new File("src/main/resources/maps/test-file.map");

        // Verify that the map is saved correctly
        assertTrue(file.exists()); // Check if the file exists
        if (file.exists()) {
            assertTrue(file.delete()); // Delete the file
        }
        assertFalse(file.exists()); // Check if the file is deleted
    }

    @Test
    void next_withInvalidMap() {
        map = new Map(); // Create an empty invalid map

        // Set up the game context
        gameContext.setMap(map);


        gameContext.getPhase().next();

        // Verify that the phase is not changed. and is still postload
        assertEquals(GamePhase.MAP_EDITING_POSTLOAD, gameContext.getPhase().getPhaseName());
        //verify the message printed
        assertEquals("The map is currently empty or has not been loaded yet.\n" +
                "The map is not valid. Please load a valid map.", outContent.toString().trim());
    }

    @Test
    void next_withValidMap() {
        map = createValidMap();

        // Set up the game context
        gameContext.setMap(map);


        gameContext.getPhase().next();

        // Verify that the phase is set to Startup
        assertEquals(GamePhase.STARTUP, gameContext.getPhase().getPhaseName());
    }

    private Map createValidMap() {
        Map map = new Map();
        // Add continents
        map.addContinent(new Continent("Asia", 5)); // Add continents with correct parameters
        // Add countries
        Country country1 = new Country(1, "Country1", map.getContinentByName("Asia").getID());
        Country country2 = new Country(2, "Country2", map.getContinentByName("Asia").getID());
        // Add connections
        country1.addNeighbor(country2);
        country2.addNeighbor(country1);
        map.addCountry(country1);
        map.addCountry(country2);
        return map;
    }
    private void setUpModifyMapContext(){
        map= new Map();
        gameContext.getMapService().setCurrentEditingFilename("test-file");
        gameContext.getMapService().getMapRegistry().put("test-file",map);

    }

}