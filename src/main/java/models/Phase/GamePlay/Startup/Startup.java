package models.Phase.GamePlay.Startup;

import controllers.GameEngine.GameEngine;
import log.LogEntryBuffer;
import models.Enums.GamePhase;
import models.Phase.GamePlay.GamePlay;
import models.Phase.GamePlay.IssueOrder.IssueOrder;
import models.Player.Player;

import static utils.Feedback.*;

/**
 * The {@code Startup} class extends {@code GamePlay} to manage the startup phase of the game.
 * This phase is responsible for setting up the game by adding or removing players based on user commands.
 */
public class Startup extends GamePlay {

    private static final GamePhase PHASE_NAME = GamePhase.STARTUP;
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * Constructs a new Startup phase associated with the specified game engine.
     *
     * @param new_ge The game engine this phase is part of.
     */
    public Startup(GameEngine new_ge) {
        super(new_ge,PHASE_NAME);
    }

    /**
     * Returns the name of this game phase.
     *
     * @return The specific GamePhase enum constant representing the startup phase.
     */
    public GamePhase getPhaseName() {
        return PHASE_NAME;
    }

    /**
     * Processes the addition or removal of players based on the given command.
     * The command should specify the action ('-add' or '-remove') followed by the player name.
     *
     * @param p_command The command to process, containing the action and player name.
     */
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

    /**
     * Transitions the game to the next phase, specifically to the issue orders phase, and displays welcome messages.
     */
    public void next() {
        d_ctx.setPhase(new IssueOrder(d_ge));
        printWelcomeMessageWithBanner("You have entered Game Play - Issue Order phase.");
        System.out.println("\nThe game has started! It's time to issue your orders.");
    }

}
