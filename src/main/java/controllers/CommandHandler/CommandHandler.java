/**
 * The CommandHandler class handles different commands and operations in the game.
 * It includes methods for loading a map, exiting the game, displaying commands, managing players,
 * issuing orders, and executing orders.
 */
package controllers.CommandHandler;


import controllers.MapEditor.MapEditor;
import models.Enums.GamePhase;
import models.Player.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static adapters.FileAdapter.FileAdapter.isFileExists;
import static utils.Feedback.displayPhaseInstructions;
import static utils.Feedback.displayPlayers;

public class CommandHandler {

    /**
     * Handles the loadmap command by loading the specified map file.
     * If the file exists, it loads the map using the provided MapEditor instance.
     * If the file does not exist, it displays an error message.
     *
     * @param p_command   The loadmap command.
     * @param p_mapEditor The MapEditor instance.
     */
    public static void handleLoadMapCommand(String p_command,MapEditor p_mapEditor){
        String l_fileName = p_command.split(" ")[1];
        File l_file = isFileExists(l_fileName);
        if (l_file != null) {
            try {
                p_mapEditor.loadMap(l_file);
                // \n to skip one line
                System.out.println("\nMap loaded successfully. Type 'proceed' to move to the next phase of the game.");
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
                System.exit(0);
            } catch (IOException e) {
                System.out.println("IO exception.");
                System.exit(0);
            }
        }else{
            System.out.println("\nThe specified map file does not exist." +
                    " Please make sure the file name is correct or create a new map.");
        }
    }
    /**
     * Handles the exit command by gracefully exiting the game.
     */
    public static void handleExitCommand(){
        System.out.println("Exiting the game. Goodbye!");
        System.exit(0);
    }
    /**
     * Displays the available commands based on the current game phase.
     *
     * @param p_gamePhase The current game phase.
     */
    public static void handleDisplayCommands(GamePhase p_gamePhase){
        System.out.println(""); //to skip one line.
        displayPhaseInstructions(p_gamePhase);

    }
    /**
     * Handles the gameplayer command by adding or removing players from the game.
     *
     * @param p_command         The gameplayer command.
     * @param p_existingPlayers The list of existing players.
     */
    public static void handleGamePlayerCommand(String p_command, ArrayList<Player> p_existingPlayers){
        String[] l_commandParts = p_command.trim().split(" ");
        String l_option="";
        if(l_commandParts[1].equals("-add") || l_commandParts[1].equals("-remove")) l_option=l_commandParts[1];
        //there are no playerNames
        if(l_commandParts.length==2){
            System.out.println("\nNo player name provided. Please specify a name after '-add' or '-remove'.");
            return;
        }
       switch (l_option){
           case "-add":
               for(int i=2; i<l_commandParts.length;i++){
                        String l_playerName= l_commandParts[i];
                        Player player= new Player(l_playerName);
                        p_existingPlayers.add(player);
               }
               displayPlayers(p_existingPlayers);
               break;
           case "-remove":
               for(int i=2; i<l_commandParts.length;i++){
                   String l_playerName= l_commandParts[i];
                   Player l_playerToRemove=null;
                   for(Player player:p_existingPlayers){
                       if(player.getName().equals(l_playerName)){
                           l_playerToRemove=player;
                           break;
                       }
                   }
                   if(l_playerToRemove!=null){
                       p_existingPlayers.remove(l_playerToRemove);
                       System.out.println("\nPlayer '"+l_playerName+"' removed successfully.");
                   }else{
                       System.out.println("\nPlayer '"+l_playerName+"' not found.");

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
     *
     * @param p_existingPlayers The list of existing players.
     */

    public static void handleIssueOrder(ArrayList<Player> p_existingPlayers){
        int l_currentPlayerIndex = 0;
        while(true){
            if(allPlayersDoneWithOrders(p_existingPlayers)) break;
            Player l_currentPlayer= p_existingPlayers.get(l_currentPlayerIndex);
            if(!l_currentPlayer.hasOrders())
            {
                l_currentPlayerIndex= (l_currentPlayerIndex+1)% p_existingPlayers.size();
                continue;
            }
            l_currentPlayer.issue_order();
            l_currentPlayer.setHasOrders(l_currentPlayer.getNoOfArmies()>0);
            l_currentPlayerIndex= (l_currentPlayerIndex+1)% p_existingPlayers.size();
        }
        System.out.println("\nAll players have finished issuing orders. The game is now proceeding to execute orders.");
    }
    /**
     * Checks if all players are done issuing orders.
     *
     * @param p_existingPlayers The list of existing players.
     * @return True if all players are done issuing orders, false otherwise.
     */
    private static boolean allPlayersDoneWithOrders(ArrayList<Player> p_existingPlayers){
        boolean l_allPlayersDone= true;
        for(Player player:p_existingPlayers){
            if(player.hasOrders()){
                l_allPlayersDone=false;
                break;
            }
        }
        return l_allPlayersDone;
    }
    /**
     * Placeholder method for handling the execution of orders.
     */
    public static void handleExecuteOrder(){

    }


}
