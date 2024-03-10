package models.Player;

import java.util.ArrayList;
import java.util.Scanner;
import log.LogEntryBuffer;
import models.Country.Country;
import models.Enums.GamePhase;
import models.Order.Deploy.DeployOrder;
import models.Order.Order;
import models.Order.Advance.AdvanceOrder;
import models.Order.Bomb.BombOrder;
import models.Order.Blockade.BlockadeOrder;
import models.Order.Airlift.AirliftOrder;
import models.Order.Diplomacy.DiplomacyOrder;
import models.MapHolder.MapHolder;
import static controllers.CommandHandler.CommandHandler.handleDisplayCommands;
import static controllers.CommandHandler.CommandHandler.handleExitCommand;
import static utils.Feedback.displayCommandUnavailableMessage;
import static views.MapView.PlayerView.displayPlayerList;

/**
 * Represents a player in the game.
 */
public class Player {
    private final String d_playerName;
    private ArrayList<Country> d_ownedCountries;
    private ArrayList<Order> d_orders;
    private int d_noOfArmies;
    private boolean hasOrders;
    private boolean lastCommandValidForOrders;

    private Scanner sc = new Scanner(System.in);
    private GamePhase currentPhase;
    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();
    /**
     * Initializes a player with the given name.
     *
     * @param new_name The name of the player.
     */
    public Player(String new_name) {
        this.d_playerName = new_name;
        this.d_ownedCountries = new ArrayList<>();
        this.d_orders = new ArrayList<>();
        this.d_noOfArmies = 0;
        this.hasOrders=true;
        this.lastCommandValidForOrders= true;
    }
    //getters

    /**
     * Retrieves the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return this.d_playerName;
    }

    /**
     * Retrieves the list of orders issued by the player.
     *
     * @return The list of orders.
     */
    public ArrayList<Order> getOrders() {
        return this.d_orders;
    }

    /**
     * Checks if the player has any pending orders.
     *
     * @return True if the player has pending orders, false otherwise.
     */
    public boolean hasOrders() {
        return this.hasOrders;
    }

    /**
     * Retrieves the number of armies owned by the player.
     *
     * @return The number of armies owned by the player.
     */
    public int getNoOfArmies() {
        return this.d_noOfArmies;
    }

    /**
     * Checks if the player last command was valid for orders.
     *
     * @return True if the command was valid.
     */
    public boolean lastCommandValidForOrders() {
        return this.lastCommandValidForOrders;
    }

    /**
     * Retrieves the list of countries owned by the player.
     *
     * @return The list of owned countries.
     */
    public ArrayList<Country> getOwnedCountries() {
        return this.d_ownedCountries;
    }

    //setters

    /**
     * Adds a country to the list of countries owned by the player.
     *
     * @param p_country The country to be added.
     */
    public void addOwnedCountry(Country p_country) {
        this.d_ownedCountries.add(p_country);
    }

    /**
     * Sets the flag indicating whether the player has orders for the current turn.
     *
     * @param p_hasOrders A boolean value indicating whether the player has orders.
     */
    public void setHasOrders(boolean p_hasOrders){
        this.hasOrders= p_hasOrders;
    }
    /**
     * Sets the flag indicating whether the player last command was valid.
     *
     * @param p_lastCommandValidForOrders A boolean value indicating whether the player last command was valid.
     */
    public void setLastCommandValidForOrders(boolean p_lastCommandValidForOrders) {
        this.lastCommandValidForOrders=p_lastCommandValidForOrders;
    }
    /**
     * Sets the number of armies owned by the player.
     *
     * @param p_noOfArmies The number of armies to set for the player.
     */
    public void setNoOfArmies(int p_noOfArmies){
        this.d_noOfArmies=p_noOfArmies;
    }

    /**
     * Issues an order from the list of orders for the player and removes it from the list.
     */
    /**
     * Issues an order from the list of orders for the player and removes it from the list.
     */
    /**
     * Issues an order from the list of orders for the player and removes it from the list.
     */
    public void IssueOrder() {
        System.out.print("\nPlayer " + this.getName() + " please enter your next order: ");
        String[] l_commandParts = sc.nextLine().split(" ");
        String l_commandName = l_commandParts[0];
        switch (l_commandName) {
            case "deploy":
                createDeployOrder(l_commandParts);
                break;
            case "showarmies":
                this.lastCommandValidForOrders = false;
                System.out.print("\nArmies left to deploy for " + this.getName() + ": " + this.d_noOfArmies);
                break;
            case "showcommands":
                this.lastCommandValidForOrders = false;
                handleDisplayCommands(GamePhase.ISSUE_ORDERS);
                break;
            case "advance":
                handleAdvanceOrder(l_commandParts);
                break;
            case "bomb":
                handleBombOrder(l_commandParts);
                break;
            case "blockade":
                handleBlockadeOrder(l_commandParts);
                break;
            case "airlift":
                handleAirliftOrder(l_commandParts);
                break;
            case "negotiate":
                if (currentPhase == GamePhase.ISSUE_ORDERS) {
                    System.out.println("\nThe 'negotiate' command is not available in the ISSUE ORDERS phase.");
                    System.out.println("Please type 'showcommands' to see the commands you can run.");
                } else {
                    handleDiplomacyOrder(l_commandParts);
                }
                break;
            case "showmap":
                this.lastCommandValidForOrders = false;
                displayPlayerList();
                break;
            case "endturn":
                this.hasOrders = false;
                break;
            case "exit":
                handleExitCommand();
                break;
            default:
                this.lastCommandValidForOrders = false;
                displayCommandUnavailableMessage(l_commandName, GamePhase.ISSUE_ORDERS);
                break;
        }
    }



    /**
     * Retrieves the next order to be executed by the player.
     *
     * @return The next order to be executed.
     */
    public Order next_order() {
        if (this.d_orders.isEmpty()) return null;
        Order l_order = this.d_orders.get(0);
        this.d_orders.remove(0);
        return l_order;
    }

    /**
     * Creates a deploy order based on the provided command array.
     *
     * @param p_command An array containing the command arguments.
     *                  The first element is the command name.
     *                  The second element is the country ID.
     *                  The third element is the number of armies to deploy.
     */
    public final void createDeployOrder(String[] p_command){
        int countryID = Integer.parseInt(p_command[1]);
        int noOfArmies= Integer.parseInt(p_command[2]);
        if(this.d_noOfArmies<noOfArmies){
            this.lastCommandValidForOrders=false;
            System.out.println("\nYou do not have enough armies.");
            return;
        }
        Country country = MapHolder.getMap().getCountryByID(countryID);
        if (country == null) {
            this.lastCommandValidForOrders=false;
            System.out.println("\nInvalid country ID. Country does not exist.");
            return;
        }
        if(!this.getOwnedCountries().contains(country)){
            this.lastCommandValidForOrders=false;
            System.out.println("\nCannot deploy armies to country " +countryID+". You do not own this country. Please select a country that you own to deploy your armies");
            return;
        }
        // Modify the line below to pass the current player
        this.d_orders.add(new DeployOrder(countryID, noOfArmies, this));
        this.d_noOfArmies-=noOfArmies;
        this.lastCommandValidForOrders=true;
        System.out.println("\nDeploy order created.");
        d_logger.log("Player "+this.getName()+" deployed "+ noOfArmies+" armies to country "+countryID);

    }

    /**
     * Handles the advance order command.
     *
     * @param commandParts The command parts containing the details of the advance order.
     */
    /**
     * Handles the advance order command.
     *
     * @param commandParts The command parts containing the details of the advance order.
     */
    private void handleAdvanceOrder(String[] commandParts) {
        if (commandParts.length != 4) {
            System.out.println("Invalid advance order command format. Usage: advance countryFrom countryTo numArmies");
            return;
        }
        try {
            String countryFrom = commandParts[1];
            String countryTo = commandParts[2];
            int numArmies = Integer.parseInt(commandParts[3]);
            if (this.d_noOfArmies < numArmies) {
                this.lastCommandValidForOrders = false;
                System.out.println("\nYou do not have enough armies to advance.");
                return;
            }
            Country sourceCountry = MapHolder.getMap().getCountryByName(countryFrom);
            Country targetCountry = MapHolder.getMap().getCountryByName(countryTo);
            if (sourceCountry == null || targetCountry == null) {
                this.lastCommandValidForOrders = false;
                System.out.println("\nInvalid country names. One or both countries do not exist.");
                return;
            }
            if (!this.d_ownedCountries.contains(sourceCountry)) {
                this.lastCommandValidForOrders = false;
                System.out.println("\nYou do not own the source country.");
                return;
            }
            AdvanceOrder advanceOrder = new AdvanceOrder(countryFrom, countryTo, numArmies);
            this.d_orders.add(advanceOrder); // Add AdvanceOrder to the list of orders
            this.d_noOfArmies -= numArmies;
            this.lastCommandValidForOrders = true;
            System.out.println("\nAdvance order created.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of armies. Please provide an integer.");
        }
    }

    /**
     * Handles the bomb order command.
     *
     * @param commandParts The command parts containing the details of the bomb order.
     */
    private void handleBombOrder(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("Invalid bomb order command format. Usage: bomb countryID");
            return;
        }
        try {
            int countryID = Integer.parseInt(commandParts[1]);
            Country targetCountry = MapHolder.getMap().getCountryByID(countryID);
            if (targetCountry == null) {
                this.lastCommandValidForOrders = false;
                System.out.println("\nInvalid country ID. Country does not exist.");
                return;
            }
            // Check if the player has the bomb card (implement this logic)
            // If the player has the bomb card, create and add a BombOrder to the list of orders
            // Otherwise, print a message indicating that the player does not have the required card
            BombOrder bombOrder = new BombOrder(countryID);
            this.d_orders.add(bombOrder);
            this.lastCommandValidForOrders = true;
            System.out.println("\nBomb order created.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid country ID. Please provide a valid integer.");
        }
    }

    /**
     * Handles the blockade order command.
     *
     * @param commandParts The command parts containing the details of the blockade order.
     */
    private void handleBlockadeOrder(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("Invalid blockade order command format. Usage: blockade countryID");
            return;
        }
        try {
            int countryID = Integer.parseInt(commandParts[1]);
            Country targetCountry = MapHolder.getMap().getCountryByID(countryID);
            if (targetCountry == null) {
                this.lastCommandValidForOrders = false;
                System.out.println("\nInvalid country ID. Country does not exist.");
                return;
            }
            // Check if the player has the blockade card (implement this logic)
            // If the player has the blockade card, create and add a BlockadeOrder to the list of orders
            // Otherwise, print a message indicating that the player does not have the required card
            BlockadeOrder blockadeOrder = new BlockadeOrder(countryID);
            this.d_orders.add(blockadeOrder);
            this.lastCommandValidForOrders = true;
            System.out.println("\nBlockade order created.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid country ID. Please provide a valid integer.");
        }
    }
    /**
     * Handles the airlift order command.
     *
     * @param commandParts The command parts containing the details of the airlift order.
     */
    private void handleAirliftOrder(String[] commandParts) {
        if (commandParts.length != 4) {
            System.out.println("Invalid airlift order command format. Usage: airlift sourcecountryID targetcountryID numarmies");
            return;
        }
        try {
            int sourceCountryID = Integer.parseInt(commandParts[1]);
            int targetCountryID = Integer.parseInt(commandParts[2]);
            int numArmies = Integer.parseInt(commandParts[3]);

            // Check if the player has the airlift card (implement this logic)
            // If the player has the airlift card, create and add an AirliftOrder to the list of orders
            // Otherwise, print a message indicating that the player does not have the required card
            AirliftOrder airliftOrder = new AirliftOrder(sourceCountryID, targetCountryID, numArmies);
            this.d_orders.add(airliftOrder);
            this.lastCommandValidForOrders = true;
            System.out.println("\nAirlift order created.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid country ID or number of armies. Please provide valid integers.");
        }
    }

    /**
     * Handles the diplomacy order command.
     *
     * @param commandParts The command parts containing the details of the diplomacy order.
     */
    private void handleDiplomacyOrder(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("Invalid diplomacy order command format. Usage: diplomacy playerID");
            return;
        }
        try {
            int playerID = Integer.parseInt(commandParts[1]);
            // Check if the player has the diplomacy card (implement this logic)
            // If the player has the diplomacy card, create and add a DiplomacyOrder to the list of orders
            // Otherwise, print a message indicating that the player does not have the required card
            DiplomacyOrder diplomacyOrder = new DiplomacyOrder(playerID);
            this.d_orders.add(diplomacyOrder);
            this.lastCommandValidForOrders = true;
            System.out.println("\nDiplomacy order created.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid player ID. Please provide a valid integer.");
        }
    }

    /**
     * Issues an order from the list of orders for the player and removes it from the list.
     */


    public static Player getCurrentPlayer() {
        // You might need to implement this based on your game logic
        // For example, you could keep track of the current player in the PlayerHolder class
        return null;
    }
    public void issueOrder(Order order) {
        d_orders.add(order);
    }
}