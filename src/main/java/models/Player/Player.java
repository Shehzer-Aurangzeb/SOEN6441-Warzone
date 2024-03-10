package models.Player;

import models.Country.Country;
import models.Enums.GamePhase;
import models.MapHolder.MapHolder;
import models.Order.Deploy.DeployOrder;
import models.Order.Order;

import java.util.ArrayList;
import java.util.Scanner;

import static controllers.CommandHandler.CommandHandler.handleDisplayCommands;
import static controllers.CommandHandler.CommandHandler.handleExitCommand;
import static utils.Feedback.displayCommandUnavailableMessage;
import static views.MapView.PlayerView.displayPlayerList;
import log.LogEntryBuffer;

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
    public void issue_order() {
        System.out.print("\nPlayer "+ this.getName() + " please enter your next order: ");
        String[] l_commandParts= sc.nextLine().split(" ");
        String l_commandName=l_commandParts[0];
        switch(l_commandName){
            case "deploy":
                createDeployOrder(l_commandParts);
                break;
            case "showarmies":
                this.lastCommandValidForOrders=false;
                System.out.print("\nArmies left to deploy for " + this.getName() + ": " + this.d_noOfArmies);
                break;
            case "showcommands":
                this.lastCommandValidForOrders=false;
                handleDisplayCommands(GamePhase.ISSUE_ORDERS);
                break;
            case "advance":
                break;
            case "showmap":
                this.lastCommandValidForOrders=false;
                displayPlayerList();
                break;
            case "endturn":
                this.hasOrders=false;
                break;
            case "exit":
                handleExitCommand();
                break;
            default:
                this.lastCommandValidForOrders=false;
                displayCommandUnavailableMessage(l_commandName,GamePhase.ISSUE_ORDERS);
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
        this.d_orders.add(new DeployOrder(countryID,noOfArmies));
        this.d_noOfArmies-=noOfArmies;
        this.lastCommandValidForOrders=true;
        System.out.println("\nDeploy order created.");
        d_logger.log("Player "+this.getName()+" deployed "+ noOfArmies+" armies to country "+countryID);

    }
}
