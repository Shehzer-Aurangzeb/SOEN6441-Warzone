package controllers.GameEngine;

import models.Enums.GamePhase;
import models.GameContext.GameContext;
import models.Phase.MapEditing.Preload.Preload;
import models.Player.Player;

import java.util.ArrayList;
import java.util.Scanner;

import static utils.Feedback.*;

public class GameEngine {
    private final Scanner d_sc = new Scanner(System.in);
    private final GameContext d_ctx = GameContext.getInstance();
    private String d_command;


    /**
     * Initializes the GameEngine with default settings.
     */
    public GameEngine() {
    }

    void setCommandForTesting(String command) {
        this.d_command = command;
    }

    /**
     * Starts the game and handles command processing based on the current phase.
     */
    public void startGame() {

        //initially preload phase
        d_ctx.setPhase(new Preload(this));
        displayWelcomeMessage();
        d_ctx.updateLog("Welcome to Warzone Game!");
        d_ctx.updateLog("\n============== Map Editing Phase ==============\n");
        while (d_ctx.getPhase().getPhaseName() != GamePhase.ISSUE_ORDERS) {
            System.out.print("\nEnter your command: ");
            this.d_command = d_sc.nextLine().trim(); // Ensure this line is correctly assigning the input to d_command
            if (d_command != null && !d_command.isEmpty()) {
                handleCommand();
            } else {
                System.out.println("No command entered, please try again.");
            }
        }
        startMainGameLoop();
    }

    /**
     * Starts the main game loop where players issue orders and orders are executed.
     */
    private void startMainGameLoop() {
        d_ctx.updateLog("\n============== Issue Order Phase ==============\n");
        while (true) {
            switch (d_ctx.getPhase().getPhaseName()) {
                case ISSUE_ORDERS:
                    d_ctx.getPhase().issueOrders();
                    break;
                case EXECUTE_ORDERS:
                    d_ctx.getPhase().executeOrders();
                    break;
            }
        }
    }

    /**
     * Handles the given command processing.
     */
    /**
     * Handles the given command processing.
     */
    public void handleCommand() {
        String commandType = d_command.split(" ")[0];
        switch (commandType) {
            case "loadmap":
                handleLoadMap();
                break;
            case "editmap":
                handleEditMap();
                break;
            case "editcontinent":
            case "editcountry":
            case "editneighbor":
                handleModifyMapComponents();
                break;
            case "savemap":
                handleSaveMap();
                break;
            case "showcommands":
                d_ctx.getPhase().showCommands();
                break;
            case "showmap":
                d_ctx.getPhase().showMap();
                break;
            case "gameplayer":
                handleAddOrRemovePlayer();
                break;
            case "startgame":
                handleStartGame();
                break;
            case "proceed":
                d_ctx.getPhase().next();
                break;
            case "exit":
                d_ctx.getPhase().exit();
                break;
            default:
                d_ctx.getPhase().printInvalidCommandMessage(d_command);
                d_ctx.updateLog("Invalid command entered.");
                break;
        }
    }

    public void handleLoadMap() {
        String filename = d_command.split(" ")[1];
        d_ctx.getPhase().loadMap(filename);
    }

    public void handleEditMap() {
        String filename = d_command.split(" ")[1];
        d_ctx.getPhase().editMap(filename);
    }

    public void handleModifyMapComponents() {
        d_ctx.getPhase().modifyMapComponents(d_command);
    }

    public void handleSaveMap() {
        String filename = d_command.split(" ")[1];
        d_ctx.getPhase().saveMap(filename);
    }

    public void handleAddOrRemovePlayer() {
        d_ctx.getPhase().addOrRemovePlayer(d_command);
        checkStartGamePrompt();
    }

    public void handleStartGame() {
        if (d_ctx.getGamePlayers().size() < 2) {
            System.out.println("\nMinimum two players required to start the game.");
        } else {
            assignCountries();
            assignReinforcements();
            System.out.println("\nReinforcements have been assigned to players.");
            d_ctx.getPhase().next();
        }
    }


    /**
     * Randomly assigns countries to players.
     */
    private void assignCountries() {
        int l_numPlayers = d_ctx.getGamePlayers().size();
        for (int i = 0; i < d_ctx.getMap().getCountries().size(); i++) {
            d_ctx.getGamePlayers().get(i % l_numPlayers).addOwnedCountry(d_ctx.getMap().getCountries().get(i));
        }
        System.out.println("\nCountries have been assigned to players.");
        d_ctx.updateLog("Countries assigned to players.");
    }

    /**
     * Checks if there are enough players to start the game and prompts the user accordingly.
     */

    private void checkStartGamePrompt() {
        if (d_ctx.getGamePlayers().size() >= 2) {
            System.out.println("\nYou have sufficient players to start the game. Type 'startgame' command to begin.");
        }
    }

    /**
     * Assigns reinforcements to players based on the number of countries owned.
     */

    public void assignReinforcements() {
        ArrayList<Player> l_existingPlayer = d_ctx.getGamePlayers();
        for (Player player : l_existingPlayer) {
            int l_armyCount = player.getOwnedCountries().size() / 3;
            if (l_armyCount < 3) l_armyCount = 3;
            player.setNoOfArmies(l_armyCount);
        }
    }

    private void awardCardsAndResetConquests() {
        ArrayList<Player> l_existingPlayer = d_ctx.getGamePlayers();
        for (Player player : l_existingPlayer) {
            if (player.hasConqueredThisTurn()) {
                player.addRandomCard(); // Award a random card
                player.setConqueredThisTurn(false); // Reset for the next turn
            }
        }
    }
}
