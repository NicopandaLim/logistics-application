package order.orderItem;


import exceptions.DataValidationException;

import java.util.ArrayList;

public class OrderItemOfOrderImpl implements OrderItemOfOrder {
    private ArrayList<OrderItem> orderItemList = new ArrayList<>();

    @Override
    public ArrayList<OrderItem> addOrderItem(OrderItem ordItem){
        orderItemList.add(ordItem);
        return orderItemList;
    }

    @Override
    public void decrQuantity(String itemName, int quantity) throws DataValidationException{
        for(OrderItem ordItem : orderItemList) {
            if(ordItem.getItemId().equals(itemName)) {
                int oriNum = ordItem.getQuantity();
                ordItem.setQuantity(oriNum - quantity);
            }
        }
    }

}
