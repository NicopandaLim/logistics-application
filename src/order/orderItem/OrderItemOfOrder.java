package order.orderItem;


import exceptions.DataValidationException;

import java.util.ArrayList;

public interface OrderItemOfOrder {

    ArrayList<OrderItem> addOrderItem(OrderItem ordItem);

    void decrQuantity(String itemName, int quantity) throws DataValidationException;

}
