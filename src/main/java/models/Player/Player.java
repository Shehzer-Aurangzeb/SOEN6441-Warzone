package main.java.models.Player;

import main.java.models.Country.Country;
import main.java.models.Order.Order;
import java.util.ArrayList;

public class Player {
    private String d_name;
    private int d_noOfArmies;
    private ArrayList<Country> assignCountries;
    private ArrayList<Order> d_orders;
    public Player() {
        this.d_name = "";
        this.d_noOfArmies = 0;
        this.assignCountries = new ArrayList<>();
        this.d_orders = new ArrayList<>();
    }
    public String getName() { return this.d_name; }
    public int get_noOfArmies() { return this.d_noOfArmies; }
    public ArrayList<Country> getAssignCountries() { return this.assignCountries; }
    public ArrayList<Order> get_orders() { return this.d_orders; }
    public void setName(String p_name) { this.d_name = p_name; }


}
