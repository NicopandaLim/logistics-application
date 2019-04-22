package order;


import exceptions.DataValidationException;
import order.orderItem.OrderItem;

import java.util.ArrayList;

public interface Order {

    String getOrderId();

    String getOrderDay();

    String getDestination();

    void decrQuantity(String itemName, int quantity) throws DataValidationException;

    void addOrderItem(String id, OrderItem orderItem);

    ArrayList<OrderItem> getOrderItems();

    void print();
}
