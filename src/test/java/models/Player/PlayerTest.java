package models.Player;

import models.Country.Country;
import models.GameContext.GameContext;
import models.Map.Map;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;


public class PlayerTest {

    private Player player;
    private int countryID;
    private final ByteArrayOutputStream outContent1 = new ByteArrayOutputStream();
    private GameContext d_ctx;
    private Country country;

    @BeforeEach
    void setUp() {
        d_ctx = GameContext.getInstance();
        country = mock(Country.class);
        player = new Player("Player A");
        // Set up test data
        String[] command = new String[]{"deploy", "1", "5"};
        countryID = Integer.parseInt(command[1]);
    }

    @Test
    void getName() {
        assertEquals("Player A", player.getName());
    }

    @Test
    void getOrders() {
        assertEquals(0, player.getOrders().size()); // Initially, the list should be empty
    }

    @Test
    void getOwnedCountries() {
        assertEquals(0, player.getOwnedCountries().size()); // Initially, the list should be empty
    }

    @Test
    void addOwnedCountry() {
        assertEquals(0, player.getOwnedCountries().size());
        // Add the country and check if it is added
        player.addOwnedCountry(country);
        ArrayList<Country> ownedCountries = player.getOwnedCountries();
        assertEquals(1, ownedCountries.size());
        assertEquals(country, ownedCountries.get(0));
    }

    @Test
    void testIssueOrder_countryDoesNotExist() {
        // Redirect System.out to a ByteArrayOutputStream for capturing the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Set up test data
        String[] invalidCommand = new String[]{"deploy", "999", "5"}; // Using an invalid country ID
        player.setNoOfArmies(5);

        // Invoke the method
        player.createDeployOrder(invalidCommand);

        // Capture the expected and actual outputs
        String expectedOutput = "\nInvalid country ID. Country does not exist.\n";
        String actualOutput = outContent.toString();


        // Check if the trimmed expected output matches the trimmed actual output
        assertEquals(expectedOutput.trim(), actualOutput.trim(), "Expected output and actual output are not identical");
    }


    @Test
    void issueOrder_playerDoesNotOwnTheCountry() {
        // Redirect System.out to a ByteArrayOutputStream for capturing the output
        System.setOut(new PrintStream(outContent1));
        Map map = new Map();
        d_ctx.setMap(map);
        Country country = new Country(1, "country A", 5);
        map.addCountry(country);
        assertEquals(1,map.getCountries().size());
        // Modify the command to use a country ID that the player does not own
        String[] command = new String[]{"deploy", "1", "5"};
        assertEquals(country,map.getCountryByID(1));

        // Set the player's armies
        player.setNoOfArmies(5);

        // Invoke the method
        player.createDeployOrder(command);

        // Get the expected output message
        String expectedOutput = "\nCannot deploy armies to country 1. You do not own this country. Please select a country that you own to deploy your armies\n";

        // Get the actual output message
        String actualOutput = outContent1.toString();

        // Trim both expected and actual outputs and ignore case sensitivity
        expectedOutput = expectedOutput.trim().toLowerCase();
        actualOutput = actualOutput.trim().toLowerCase();

        // Assert the output message
        assertEquals(expectedOutput, actualOutput);


    }


    @Test
    void issueOrder_playerDoesNotHaveEnoughArmies() {
        // Redirect System.out to a ByteArrayOutputStream for capturing the output
        System.setOut(new PrintStream(outContent1));
        // Create a country
        d_ctx.getMap().addCountry(country);

        // Modify the command to deploy more armies than the player has
        String[] command = new String[]{"deploy", String.valueOf(countryID), "10"};

        // Invoke the method
        player.createDeployOrder(command);

        // Trim and normalize the expected and actual output strings
        String expectedOutput = "\nYou do not have enough armies.\n".trim();
        String actualOutput = outContent1.toString().trim();

        // Assert the output message ignoring case sensitivity
        assertEquals(expectedOutput.toLowerCase(), actualOutput.toLowerCase(), "Expected output and actual output do not match.");

        // Print the expected and actual output strings for debugging
        System.out.println("Expected output: [" + expectedOutput + "] (Length: " + expectedOutput.length() + ")");
        System.out.println("Actual output: [" + actualOutput + "] (Length: " + actualOutput.length() + ")");
    }

    @Test
    void nextOrder() {
        // Test for nextOrder() method can be implemented here
    }

    @AfterEach
    void tearDown() {
        // Reset System.out
        System.setOut(System.out);
        player = null;
        d_ctx = null;
    }
}
