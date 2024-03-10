/**
 * The CommandHandler class handles different commands and operations in the game.
 * It includes methods for loading a map, exiting the game, displaying commands, managing players,
 * issuing orders, and executing orders.
 */
package controllers.CommandHandler;


import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;
import models.Map.Map;
import models.MapHolder.MapHolder;
import models.Order.Order;
import models.Player.Player;
import models.PlayerHolder.PlayerHolder;

import java.io.*;
import java.util.ArrayList;

import static adapters.FileAdapter.FileAdapter.*;
import static utils.Feedback.*;

public class CommandHandler {

    final static int MIN_ARMIES_PER_PLAYER = 3;

    /**
     * Handles the loadmap command by loading the specified map file.
     * If the file exists, it loads the map using the provided MapEditor instance.
     * If the file does not exist, it displays an error message.
     *
     * @param p_command   The loadmap command.
     * @param p_mapEditor The MapEditor instance.
     */
    public static void handleLoadMapCommand(String p_command, MapEditor p_mapEditor) {
        String l_fileName = p_command.split(" ")[1];
        File l_file = isFileExists(l_fileName);
        if (l_file != null) {
            try {
                p_mapEditor.loadMap(l_file);
                // \n to skip one line
                System.out.println("\nMap loaded successfully. Type 'proceed' to move to the next phase of the game.");
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            } catch (IOException e) {
                System.out.println("IO exception.");
                System.exit(0);
            }
        } else {
            System.out.println("\nThe specified map file does not exist." +
                    " Please make sure the file name is correct or create a new map.");
        }
    }

    public static void handleEditMapCommand(String p_command, MapEditor p_mapEditor) {
        String l_fileName = p_command.split(" ")[1];
        File l_file = isFileExists(l_fileName);
        //load map if file exists.
        if (l_file != null) {
            try {
                //it will load the map that is in MapHolder.
                p_mapEditor.loadMap(l_file);
                p_mapEditor.setMapInRegistry(l_fileName, MapHolder.getMap());
                p_mapEditor.setCurrentEditingFilename(l_fileName);
                System.out.println("\nMap loaded successfully. Ready for editing.");
                System.out.println("Use 'showcommands' to see to see how you can edit the map.");
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            } catch (IOException e) {
                System.out.println("IO exception.");
                System.exit(0);
            }
        } else {
            //check if map is not in registry
            if (p_mapEditor.getMapRegistry().containsKey(l_fileName)) {
                p_mapEditor.setCurrentEditingFilename(l_fileName);
                System.out.println("\nMap '" + l_fileName + "' is selected for edit.");
            } else {
                //creating a new map and linking it to filename.
                p_mapEditor.setMapInRegistry(l_fileName, new Map());
                p_mapEditor.setCurrentEditingFilename(l_fileName);
                System.out.println("\nNew map '" + l_fileName + "' created. Ready to edit.");
            }
            System.out.println("Use 'showcommands' to see to see how you can edit the map.");
        }

    }

    public static void handleSaveMapCommand(String p_command, MapEditor p_mapEditor) {
        String l_fileName = p_command.split(" ")[1];
        File l_file;
        if (!p_mapEditor.getMapRegistry().containsKey(l_fileName)) {
            System.out.println("\nPlease specify the same filename used for editing when saving the map.");
            return;
        }
        l_file = isFileExists(l_fileName);
        if (l_file == null) {
            l_file = createFile(l_fileName);
            p_mapEditor.saveMap(l_file, p_mapEditor.getMapRegistry().get(l_fileName));
        } else {
            p_mapEditor.saveMap(l_file, p_mapEditor.getMapRegistry().get(l_fileName));
        }
    }

    public static void handleEditMapElementsCommand(String p_command, MapEditor p_mapEditor) {
        String[] l_commandParts = p_command.split("\\s+");
        boolean l_validOption = true; // Flag to track if the option is valid
        //i=1 because 0th index contains name
        int i = 1;
        while (i < l_commandParts.length && l_validOption) {
            String l_option = l_commandParts[i];
            String l_entityID;
            String l_entityValue;
            switch (l_option) {
                case "-add":
                    l_entityID = l_commandParts[i + 1];
                    l_entityValue = l_commandParts[i + 2];
                    if (l_commandParts[0].equals("editcontinent")) {
                        p_mapEditor.addContinent(l_entityID, l_entityValue);
                    } else if (l_commandParts[0].equals("editcountry")) {
                        p_mapEditor.addCountry(l_entityID, l_entityValue);
                    } else if (l_commandParts[0].equals("editneighbor")) {
                        p_mapEditor.addNeighbor(l_entityID, l_entityValue);
                    }
                    i += 3;
                    break;
                case "-remove":
                    l_entityID = l_commandParts[i + 1];
                    if (l_commandParts[0].equals("editcontinent")) {
                        p_mapEditor.removeContinent(l_entityID);
                        i += 2;
                    } else if (l_commandParts[0].equals("editcountry")) {
                        p_mapEditor.removeCountry(l_entityID);
                        i += 2;
                    } else if (l_commandParts[0].equals("editneighbor")) {
                        l_entityValue = l_commandParts[i + 2];
                        p_mapEditor.removeNeighbor(l_entityID, l_entityValue);
                        i += 3;
                    }

                    break;
                default:
                    System.out.println("\nInvalid option: " + l_option);
                    l_validOption = false; // Set the flag to false if option is invalid
                    break;
            }
        }
    }

    /**
     * Handles the exit command by gracefully exiting the game.
     */
    public static void handleExitCommand() {
        System.out.println("Exiting the game. Goodbye!");
        System.exit(0);
    }

    /**
     * Displays the available commands based on the current game phase.
     *
     * @param p_gamePhase The current game phase.
     */
    public static void handleDisplayCommands(GamePhase p_gamePhase) {
        System.out.println(""); //to skip one line.
        displayPhaseInstructions(p_gamePhase);

    }

    /**
     * Handles the gameplayer command by adding or removing players from the game.
     *
     * @param p_command         The gameplayer command.
     * @param p_existingPlayers The list of existing players.
     */
    public static void handleGamePlayerCommand(String p_command, ArrayList<Player> p_existingPlayers) {
        String[] l_commandParts = p_command.trim().split(" ");
        String l_option = "";
        if (l_commandParts[1].equals("-add") || l_commandParts[1].equals("-remove")) l_option = l_commandParts[1];
        //there are no playerNames
        if (l_commandParts.length == 2) {
            System.out.println("\nNo player name provided. Please specify a name after '-add' or '-remove'.");
            return;
        }
        switch (l_option) {
            case "-add":
                for (int i = 2; i < l_commandParts.length; i++) {
                    String l_playerName = l_commandParts[i];
                    Player player = new Player(l_playerName);
                    p_existingPlayers.add(player);
                }
                displayPlayers(p_existingPlayers);
                break;
            case "-remove":
                for (int i = 2; i < l_commandParts.length; i++) {
                    String l_playerName = l_commandParts[i];
                    Player l_playerToRemove = null;
                    for (Player player : p_existingPlayers) {
                        if (player.getName().equals(l_playerName)) {
                            l_playerToRemove = player;
                            break;
                        }
                    }
                    if (l_playerToRemove != null) {
                        p_existingPlayers.remove(l_playerToRemove);
                        System.out.println("\nPlayer '" + l_playerName + "' removed successfully.");
                    } else {
                        System.out.println("\nPlayer '" + l_playerName + "' not found.");

                    }

                }
                displayPlayers(p_existingPlayers);
                break;
            default:
                System.out.println("\nInvalid option. Please use '-add' to add a player or '-remove' to remove a player.");
                break;
        }
    }

    /**
     * Handles the issue order phase by allowing players to issue orders one by one until all players have finished.
     */

    public static void handleIssueOrder() {
        int l_currentPlayerIndex = 0;
        ArrayList<Player> l_existingPlayers = PlayerHolder.getPlayers();
        while (true) {
            if (allPlayersDoneWithOrders()) break;
            Player l_currentPlayer = l_existingPlayers.get(l_currentPlayerIndex);
            if (!l_currentPlayer.hasOrders()) {
                l_currentPlayerIndex = (l_currentPlayerIndex + 1) % l_existingPlayers.size();
                continue;
            }
            l_currentPlayer.issue_order();
            if (!l_currentPlayer.lastCommandValidForOrders()) {
                continue;
            }
            l_currentPlayer.setHasOrders(l_currentPlayer.getNoOfArmies() > 0);
            l_currentPlayerIndex = (l_currentPlayerIndex + 1) % l_existingPlayers.size();
        }
        System.out.println("\nAll players have finished issuing orders. The game is now proceeding to execute orders.");
    }

    /**
     * Checks if all players are done issuing orders.
     *
     * @return True if all players are done issuing orders, false otherwise.
     */
    private static boolean allPlayersDoneWithOrders() {
        boolean l_allPlayersDone = true;
        for (Player player : PlayerHolder.getPlayers()) {
            if (player.hasOrders()) {
                l_allPlayersDone = false;
                break;
            }
        }
        return l_allPlayersDone;
    }

    /**
     * Placeholder method for handling the execution of orders.
     */
    public static void handleExecuteOrder() {
        ArrayList<Player> l_existingPlayers = PlayerHolder.getPlayers();
        int l_totalOrders = 0;
        for (Player player : l_existingPlayers) {
            l_totalOrders += player.getOrders().size();
        }
        int l_currentOrder = 1;
        int l_currentPlayerIndex = 0;
        while (l_currentOrder <= l_totalOrders) {
            Player l_currentPlayer = l_existingPlayers.get(l_currentPlayerIndex);
            Order l_order = l_currentPlayer.next_order();
            if (l_order == null) {
                l_currentPlayerIndex = (l_currentPlayerIndex + 1) % l_existingPlayers.size();
                continue;
            }
            switch (l_order.getName()) {
                case DEPLOY:
                    System.out.println("\nExecuting order for player " + l_currentPlayer.getName() + ": " + l_order);
                    l_order.execute();
                    l_currentOrder++;
                    break;
            }
            l_currentPlayerIndex = (l_currentPlayerIndex + 1) % l_existingPlayers.size();
        }
        //after all orders are executed. assign reinforcements for next turn.
        assignReinforcements();
        resetOrdersStatus();
    }

    /**
     * Assigns reinforcements to players based on the number of countries owned.
     */
    public static void assignReinforcements() {
        ArrayList<Player> l_existingPlayer = PlayerHolder.getPlayers();
        for (Player player : l_existingPlayer) {
            int l_armyCount = player.getOwnedCountries().size() / 3;
            if (l_armyCount < MIN_ARMIES_PER_PLAYER) l_armyCount = MIN_ARMIES_PER_PLAYER;
            player.setNoOfArmies(l_armyCount);
        }
    }

    /**
     * Resets the hasOrders flag for all players after each turn.
     */
    private static void resetOrdersStatus() {
        ArrayList<Player> l_existingPlayer = PlayerHolder.getPlayers();
        for (Player player : l_existingPlayer) {
            player.setHasOrders(true);
        }
    }

}
