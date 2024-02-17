package models.Player;

import models.Country.Country;
import models.MapHolder.MapHolder;
import models.Order.Deploy.DeployOrder;
import models.Order.Order;
import utils.Command;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a player in the game.
 */
public class Player {
    private final String d_playerName;
    private ArrayList<Country> d_ownedCountries;
    private ArrayList<Order> d_orders;
    private int d_noOfArmies;
    private Scanner sc;
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
     * Issues an order from the list of orders for the player and removes it from the list.
     */
    public void issue_order() {
        String[] l_command= sc.nextLine().split(" ");
        String l_commandName=l_command[0];
        switch(l_commandName){
            case "deploy":
                createDeployOrder(l_command);
                break;
            case "advance":
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
    private void createDeployOrder(String[] p_command){
        int countryID = Integer.parseInt(p_command[1]);
        int noOfArmies= Integer.parseInt(p_command[2]);
        if(this.d_noOfArmies<noOfArmies){
            System.out.println("You do not have enough armies.");
            return;
        }
        Country country = MapHolder.getMap().getCountryByID(countryID);
        if (country == null) {
            System.out.println("Invalid country ID. Country does not exist.");
            return;
        }
        this.d_orders.add(new DeployOrder(countryID,noOfArmies));
        this.d_noOfArmies-=noOfArmies;
    }
}
