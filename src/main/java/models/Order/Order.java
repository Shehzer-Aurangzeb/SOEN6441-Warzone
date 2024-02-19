package models.Order;

import models.Enums.OrderType;

public interface Order {
    /**
     * get the order name.
     */

    OrderType getName();
    /**
     * Executes the order.
     */
    void execute();
}
