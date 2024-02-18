package models.Order;

import models.Enums.OrderType;

public interface Order {
    /**
     * Executes the order.
     */
    OrderType getName();
    void execute();
}
