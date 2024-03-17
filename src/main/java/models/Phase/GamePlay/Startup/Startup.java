package models.Phase.GamePlay.Startup;

import controllers.GameEngine.GameEngine;
import log.LogEntryBuffer;
import models.Enums.GamePhase;
import models.Phase.GamePlay.GamePlay;
import models.Phase.GamePlay.IssueOrder.IssueOrder;
import models.Player.Player;

import static utils.Feedback.*;

public class Startup extends GamePlay {

    private static final GamePhase PHASE_NAME = GamePhase.STARTUP;
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    public Startup(GameEngine new_ge) {
        super(new_ge,PHASE_NAME);
    }

    public GamePhase getPhaseName() {
        return PHASE_NAME;
    }

    public void addOrRemovePlayer(String p_command) {
        String[] l_commandParts = p_command.split("\\s+");
        boolean l_validOption = true; // Flag to track if the option is valid
        //i=1 because 0th index contains name
        int i = 1;
        while (i < l_commandParts.length && l_validOption) {
            String l_option = l_commandParts[i];
            String l_playerName = (l_commandParts.length>i+1) ? l_commandParts[i+1]: null;
            if (l_playerName==null || l_playerName.isEmpty()) {
                System.out.println("\nNo player name provided. Please specify a name after '-add' or '-remove'.");
                break;
            }
            else {
                switch (l_option) {
                    case "-add":
                        Player l_player = new Player(l_playerName);
                        d_ctx.addPlayer(l_player);
                        System.out.println("\nPlayer '" + l_playerName + "' added successfully.");
                        d_logger.log("Player "+ l_playerName + " added." );
                        i+=2;
                        break;
                    case "-remove":
                        Player l_playerToRemove= d_ctx.removePlayer(l_playerName);
                        if (l_playerToRemove != null) {
                            System.out.println("\nPlayer '" + l_playerName + "' removed successfully.");
                            d_logger.log("Player "+ l_playerName + " removed." );
                        } else {
                            System.out.println("\nPlayer '" + l_playerName + "' not found.");
                        }
                        i+=2;
                        break;
                    default:
                        System.out.println("\nInvalid option. Please use '-add' to add a player or '-remove' to remove a player.");
                        l_validOption = false; // Set the flag to false if option is invalid
                        break;
                }
            }
        }
        displayPlayers();
    }

    public void issueOrders(){
        printInvalidCommandMessage("orders");
    }
    public void executeOrders(){};
    public void next() {
        d_ctx.setPhase(new IssueOrder(d_ge));
        printWelcomeMessageWithBanner("You have entered Game Play - Issue Order phase.");
        System.out.println("\nThe game has started! It's time to issue your orders.");
    }

}
