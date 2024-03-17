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
            d_command = d_sc.nextLine().trim();
            handleCommand();
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
    private void handleCommand() {
        String l_commandName = d_command.split(" ")[0];
        String l_filename;
        switch (l_commandName) {
            case "loadmap":
                l_filename = d_command.split(" ")[1];
                d_ctx.getPhase().loadMap(l_filename);
                break;
            case "editmap":
                l_filename = d_command.split(" ")[1];
                d_ctx.getPhase().editMap(l_filename);
                break;
            case "editcontinent", "editcountry", "editneighbor":
                d_ctx.getPhase().modifyMapComponents(d_command);
                break;
            case "savemap":
                l_filename = d_command.split(" ")[1];
                d_ctx.getPhase().saveMap(l_filename);
                break;
            case "showcommands":
                d_ctx.getPhase().showCommands();
                break;
            case "showmap":
                d_ctx.getPhase().showMap();
                d_ctx.updateLog("Entered showmap command.");
                break;
            case "gameplayer":
                d_ctx.getPhase().addOrRemovePlayer(d_command);
                checkStartGamePrompt();
                break;
            case "startgame":
                if (d_ctx.getGamePlayers().size() < 2) {
                    System.out.println("\nMinimum two players required to start the game.");
                } else {
//                    PlayerHolder.setPlayers(d_players);
                    assignCountries();
                    assignReinforcements();
                    System.out.println("\nReinforcements have been assigned to players.");
                    d_ctx.getPhase().next();
                }
                break;
            case "proceed":
                d_ctx.updateLog("\n============== Startup Phase ==============\n");
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

}
