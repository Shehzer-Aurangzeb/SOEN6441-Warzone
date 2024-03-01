package models.Player;

import models.Country.Country;
import models.Map.Map;
import models.MapHolder.MapHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    private Player player;
    private String[] command;
    private int countryID;
    private int noOfArmies;
    private ByteArrayOutputStream outContent1 = new ByteArrayOutputStream();
    private Map map;

    @BeforeEach
    void setUp() {
        map = new Map();
        player = new Player("Player A");
        MapHolder.setMap(map);
        // Set up test data
        command = new String[]{"deploy", "1", "5"};
        countryID = Integer.parseInt(command[1]);
        noOfArmies = Integer.parseInt(command[2]);
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
        Country country = new Country(1, "Country A", 1);
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

        // Trim both expected and actual outputs
        expectedOutput = expectedOutput.trim();
        actualOutput = actualOutput.trim();

        // Print the trimmed expected and actual outputs for debugging
        System.out.println("Expected output:");
        System.out.println(expectedOutput);
        System.out.println("Actual output:");
        System.out.println(actualOutput);

        // Check if the trimmed expected output matches the trimmed actual output
        assertEquals(expectedOutput, actualOutput, "Expected output and actual output are not identical");
    }


    @Test
    void issueOrder_playerDoesNotOwnTheCountry() {
        // Redirect System.out to a ByteArrayOutputStream for capturing the output
        System.setOut(new PrintStream(outContent1));

        // Create a country that the player does not own
        Country country = new Country(countryID, "Country A", 1);
        map.addCountry(country);

        // Modify the command to use a country ID that the player does not own
        String[] command = new String[]{"deploy", "1", "5"};

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
        Country country = new Country(countryID, "Country A", 1);
        map.addCountry(country);

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
    }
}
