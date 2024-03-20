package models.Order;

import models.Enums.OrderType;
import models.Player.Player;

public interface Order {
    /**
     * get the order name.
     */

    OrderType getName();
    /**
     * Executes the order.
     * @param p_player the player who issues the order.
     */
    void execute(Player p_player);
}
