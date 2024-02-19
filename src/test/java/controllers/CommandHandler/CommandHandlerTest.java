package controllers.CommandHandler;

import controllers.MapEditor.MapEditor;
import models.Player.Player;
import models.PlayerHolder.PlayerHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CommandHandlerTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    @Test
    void handleLoadMapCommand() {
        // Mock MapEditor
        MapEditor mapEditor = new MapEditor();
        String command = "loadmap bigeurope.map";

        // Test loading existing map file
        CommandHandler.handleLoadMapCommand(command, mapEditor);
        assertEquals("\nMap loaded successfully. Type 'proceed' to move to the next phase of the game.\n", outputStreamCaptor.toString());

        // Test loading non-existing map file
        command = "loadmap non_existing.map";
        CommandHandler.handleLoadMapCommand(command, mapEditor);
        assertEquals("\nThe specified map file does not exist. Please make sure the file name is correct or create a new map.\n", outputStreamCaptor.toString());
    }

    @Test
    void handleExitCommand() {
        CommandHandler.handleExitCommand();
        assertEquals("Exiting the game. Goodbye!\n", outputStreamCaptor.toString());

    }

    @Test
    void handleGamePlayerCommand() {
        // Mock existing players
        ArrayList<Player> existingPlayers = new ArrayList<>();
        Player player1 = new Player("Shehzar");
        existingPlayers.add(player1);

        // Test adding a player
        String command = "gameplayer -add Tania";
        CommandHandler.handleGamePlayerCommand(command, existingPlayers);
        assertEquals("\nPlayers:\n1. Shehzar\n2. Tania\n", outputStreamCaptor.toString());

    }

    @Test
    void assignReinforcements() {
        // Create players and their owned countries
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        Player player3 = new Player("Player3");

        // Simulate players owning different numbers of countries
        // Player1 owns 9 countries, player2 owns 8, player3 owns 7
        for (int i = 0; i < 9; i++) {
            player1.addOwnedCountry(null); // Add dummy countries
        }
        for (int i = 0; i < 5; i++) {
            player2.addOwnedCountry(null);
        }
        for (int i = 0; i < 12; i++) {
            player3.addOwnedCountry(null);
        }
        // Create a list of players
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        PlayerHolder.setPlayers(players);
        // Call the method to assign reinforcements
        CommandHandler.assignReinforcements();


        // Check the number of armies assigned to each player
        // Player1: 9/3 = 3 armies,
        // Player2: 5/3 = 2 armies. 2<min_number_of_armies. so it should be 3,
        // Player3: 12/3 = 4 armies
        assertEquals(3, player1.getNoOfArmies());
        assertEquals(3, player2.getNoOfArmies());
        assertEquals(4, player3.getNoOfArmies());


    }
}