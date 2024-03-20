package models.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import models.Card.Card;

import models.Country.Country;
import models.Enums.CardType;
import models.Enums.GamePhase;
import models.GameContext.GameContext;
import models.Order.Deploy.DeployOrder;
import models.Order.Order;
import models.Order.Advance.AdvanceOrder;
import models.Order.Bomb.BombOrder;
import models.Order.Blockade.BlockadeOrder;
import models.Order.Airlift.AirliftOrder;
import models.Order.Diplomacy.DiplomacyOrder;

import static utils.Feedback.displayCommandUnavailableMessage;
import static views.MapView.PlayerView.displayMyMap;
import static views.MapView.PlayerView.displayPlayerList;

/**
 * Represents a player in the game.
 */
public class Player {
    private final String d_playerName;
    private ArrayList<Country> d_ownedCountries;
    private final ArrayList<Order> d_orders;
    private int d_noOfArmies;
    private boolean hasOrders;
    private boolean lastCommandValidForOrders;
    private final GameContext d_ctx = GameContext.getInstance();
    private final Scanner sc = new Scanner(System.in);
    private boolean conqueredThisTurn = false;
    private ArrayList<Card> d_cards = new ArrayList<>();



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
        this.hasOrders = true;
        this.lastCommandValidForOrders = true;
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
     * Removed a country from the list of countries owned by the player.
     *
     * @param p_country The country to be removed.
     */
    public void removeOwnedCountry(Country p_country) {
        this.d_ownedCountries.remove(p_country);
    }

    /**
     * Sets the flag indicating whether the player has orders for the current turn.
     *
     * @param p_hasOrders A boolean value indicating whether the player has orders.
     */
    public void setHasOrders(boolean p_hasOrders) {
        this.hasOrders = p_hasOrders;
    }

    /**
     * Sets the flag indicating whether the player last command was valid.
     *
     * @param p_lastCommandValidForOrders A boolean value indicating whether the player last command was valid.
     */
    public void setLastCommandValidForOrders(boolean p_lastCommandValidForOrders) {
        this.lastCommandValidForOrders = p_lastCommandValidForOrders;
    }

    /**
     * Sets the number of armies owned by the player.
     *
     * @param p_noOfArmies The number of armies to set for the player.
     */
    public void setNoOfArmies(int p_noOfArmies) {
        this.d_noOfArmies = p_noOfArmies;
    }

    /**
     * Issues an order from the list of orders for the player and removes it from the list.
     */
    public void issue_order() {
        System.out.print("\nPlayer " + this.getName() + " please enter your next order: ");
        String[] l_commandParts = sc.nextLine().split(" ");
        String l_commandName = l_commandParts[0];
        switch (l_commandName) {
            case "deploy":
                createDeployOrder(l_commandParts);
                break;
            case "advance":
                createAdvanceOrder(l_commandParts);
                break;
            case "bomb":
                createBombOrder(l_commandParts);
                break;
            case "showarmies":
                this.lastCommandValidForOrders = false;
                System.out.print("\nArmies left to deploy for " + this.getName() + ": " + this.d_noOfArmies);
                break;
            case "showcommands":
                this.lastCommandValidForOrders = false;
                d_ctx.getPhase().showCommands();
                break;

            case "blockade":
                handleBlockadeOrder(l_commandParts);
                break;
            case "airlift":
                handleAirliftOrder(l_commandParts);
                break;
            case "negotiate":
                    handleDiplomacyOrder(l_commandParts);
                break;
            case "showmap":
                this.lastCommandValidForOrders = false;
                displayPlayerList();
                d_ctx.updateLog("showmap command entered");
                break;
            case "mymap":
                this.lastCommandValidForOrders = false;
                displayMyMap(this);
                d_ctx.updateLog("mymap command entered");
                break;
            case "endturn":
                this.hasOrders = false;
                break;
            case "exit":
                d_ctx.getPhase().exit();
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
     * Creates a deployed order based on the provided command array.
     *
     * @param p_command An array containing the command arguments.
     *                  The first element is the command name.
     *                  The second element is the country ID.
     *                  The third element is the number of armies to deploy.
     */
    public final void createDeployOrder(String[] p_command) {
        try {
            int countryID = Integer.parseInt(p_command[1]);
            int noOfArmies = Integer.parseInt(p_command[2]);
            if (this.d_noOfArmies < noOfArmies) {
                this.lastCommandValidForOrders = false;
                System.out.println("\nYou do not have enough armies.");
                return;
            }
            Country country = d_ctx.getMap().getCountryByID(countryID);
            if (country == null) {
                this.lastCommandValidForOrders = false;
                System.out.println("\nInvalid country ID. Country does not exist.");
                return;
            }
            if (!this.getOwnedCountries().contains(country)) {
                this.lastCommandValidForOrders = false;
                System.out.println("\nCannot deploy armies to country " + countryID + ". You do not own this country. Please select a country that you own to deploy your armies");
                return;
            }
            // Modify the line below to pass the current player
            this.d_orders.add(new DeployOrder(countryID, noOfArmies));
            this.d_noOfArmies -= noOfArmies;
            this.lastCommandValidForOrders = true;
            System.out.println("\nDeploy order created.");
            d_ctx.updateLog("\nDeploy order created.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid country ID or number of armies. Please provide an integer.");
        }
    }

    /**
     * Handles the advance order command.
     *
     * @param commandParts The command parts containing the details of the advance order.
     */
    private void createAdvanceOrder(String[] commandParts) {
        if (commandParts.length != 4) {
            System.out.println("Invalid advance order command format. Usage: advance countryFrom countryTo numArmies");
            return;
        }
        try {
            String countryFrom = commandParts[1];
            String countryTo = commandParts[2];
            int numArmies = Integer.parseInt(commandParts[3]);
            // Get the source and target countries
            Country sourceCountry = d_ctx.getMap().getCountryByName(countryFrom);
            Country targetCountry = d_ctx.getMap().getCountryByName(countryTo);

            // Check if both countries exist
            if (sourceCountry == null || targetCountry == null) {
                System.out.println("\nInvalid country names. One or both countries do not exist.");
                return;
            }

            // Check if source country has enough armies
            if (sourceCountry.getArmiesDeployed() < numArmies) {
                System.out.println("\nThe source country does not have enough armies to advance.");
                d_ctx.updateLog("Not enough armies in " + sourceCountry.getName() + " to advance.");
                return;
            }

            // Check if the countries are adjacent
            if (!d_ctx.getMap().areAdjacent(sourceCountry, targetCountry)) {
                System.out.println("\nThe source and target countries are not adjacent.");
                return;
            }

            // Check if the source country belongs to the current player
            if (!this.d_ownedCountries.contains(sourceCountry)) {
                System.out.println("\nYou do not own the source country.");
                return;
            }
            AdvanceOrder advanceOrder = new AdvanceOrder(countryFrom, countryTo, numArmies);
            this.d_orders.add(advanceOrder); // Add AdvanceOrder to the list of orders
            this.lastCommandValidForOrders = true;
            System.out.println("\nAdvance order created.");
            d_ctx.updateLog("\nAdvance order created for " + this.d_playerName + ".");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number of armies. Please provide an integer.");
        }
    }

    /**
     * Handles the bomb order command.
     *
     * @param commandParts The command parts containing the details of the bomb order.
     */
    private void createBombOrder(String[] commandParts) {
        if (commandParts.length != 2) {
            System.out.println("Invalid bomb order command format. Usage: bomb countryID");
            return;
        }
        try {
            int countryID = Integer.parseInt(commandParts[1]);
            Country targetCountry = d_ctx.getMap().getCountryByID(countryID);
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
            d_ctx.updateLog("\nBomb order created for " + this.d_playerName + ".");
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
            Country targetCountry = d_ctx.getMap().getCountryByID(countryID);
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
            d_ctx.updateLog("\nBlockade order created for " + this.d_playerName + ".");
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

            // Check if the player has enough armies in the source country
            Country sourceCountry = d_ctx.getMap().getCountryByID(sourceCountryID);
            if (sourceCountry == null) {
                System.out.println("\nInvalid source country ID. Country does not exist.");
                return;
            }
            if (numArmies > sourceCountry.getArmiesDeployed()) {
                System.out.println("\nNot enough armies in country " + sourceCountryID + " to airlift.");
                return;
            }

            // Check if the player has the airlift card (implement this logic)
            // If the player has the airlift card, create and add an AirliftOrder to the list of orders
            // Otherwise, print a message indicating that the player does not have the required card
            AirliftOrder airliftOrder = new AirliftOrder(sourceCountryID, targetCountryID, numArmies);
            this.d_orders.add(airliftOrder);
            this.lastCommandValidForOrders = true;
            System.out.println("\nAirlift order created.");
            d_ctx.updateLog("\nAirlift order created for " + this.d_playerName + ".");
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
            System.out.println("Invalid diplomacy order command format. Usage: diplomacy playerName");
            return;
        }
        String playerName = commandParts[1]; // Extract playerName from commandParts
        DiplomacyOrder diplomacyOrder = new DiplomacyOrder(playerName); // Pass playerName to the constructor
        this.d_orders.add(diplomacyOrder);
        this.lastCommandValidForOrders = true;
        System.out.println("\nDiplomacy order created.");
        d_ctx.updateLog("\nDiplomacy order created for " + this.d_playerName + ".");
    }




    public void addRandomCard() {
        CardType[] cardTypes = CardType.values();
        CardType randomType = cardTypes[new Random().nextInt(cardTypes.length)];
        Card newCard = new Card(randomType);
        this.d_cards.add(newCard);
        System.out.println(this.d_playerName + " received a " + randomType + " card.");
        d_ctx.updateLog(this.d_playerName + " received a " + randomType + " card.");
    }


    public void setConqueredThisTurn(boolean conquered) {
        this.conqueredThisTurn = conquered;
    }

    public boolean hasConqueredThisTurn() {
        return this.conqueredThisTurn;
    }


}