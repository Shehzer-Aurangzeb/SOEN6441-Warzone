package main.java.models.Player;

import main.java.models.Country.Country;
import main.java.models.Order.Order;

import java.util.ArrayList;

/**
 * Represents a player in the game.
 */
public class Player {
    private final String d_playerName;
    private ArrayList<Country> d_ownedCountries;
    private ArrayList<Order> d_orders;

    /**
     * Initializes a player with the given name.
     *
     * @param new_name The name of the player.
     */
    public Player(String new_name){
        this.d_playerName= new_name;
        this.d_ownedCountries = new ArrayList<>();
        this.d_orders = new ArrayList<>();
    }
    //getters
    /**
     * Retrieves the name of the player.
     *
     * @return The name of the player.
     */
    public String getName(){
        return this.d_playerName;
    }

    /**
     * Retrieves the list of orders issued by the player.
     *
     * @return The list of orders.
     */
    public ArrayList<Order> getOrders(){
        return this.d_orders;
    }

    /**
     * Retrieves the list of countries owned by the player.
     *
     * @return The list of owned countries.
     */
    public ArrayList<Country> getOwnedCountries(){
        return this.d_ownedCountries;
    }

    //setters
    /**
     * Adds a country to the list of countries owned by the player.
     *
     * @param p_country The country to be added.
     */
    public void addOwnedCountry(Country p_country){
        this.d_ownedCountries.add(p_country);
    }

    /**
     * Issues an order from the list of orders for the player and removes it from the list.
     */
    public void issue_order(){
//        Order l_order= new Order();
//        this.d_orders.add(l_order);
    }
    /**
     * Retrieves the next order to be executed by the player.
     *
     * @return The next order to be executed.
     */
    public Order next_order(){
        if(this.d_orders.isEmpty()) return null;
        Order l_order=this.d_orders.get(0);
        this.d_orders.remove(0);
        return l_order;
    }
}
