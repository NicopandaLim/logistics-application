package order;

import exceptions.DataValidationException;
import order.orderItem.*;

import java.util.ArrayList;

public class OrderImpl implements Order{
    String orderId;
    String orderDay;
    String destination;
    private ArrayList<OrderItem> orderItemList = new ArrayList<>();

    OrderItemOfOrder ordItemOfOrder = new OrderItemOfOrderImpl();

    public OrderImpl(String orderId, String orderDay, String destination)
            throws DataValidationException {
        setOrderId(orderId);
        setOrderDay(orderDay);
        setDestination(destination);
    }

    public void setOrderId(String id) throws DataValidationException{
        if (id == null || id.isEmpty())
            throw new DataValidationException((id == null? "Null" : "Empty")
            + " string passed into OrderImpl.setOrderId(String)");
        orderId = id;
    }

    public void setOrderDay(String day) throws DataValidationException{
        if (day == null || day.isEmpty())
            throw new DataValidationException((day == null? "Null" : "Empty")
                    + " string passed into OrderImpl.setOrderDay(String)");
        orderDay = day;
    }

    public void setDestination(String destination) throws DataValidationException{
        if (destination == null || destination.isEmpty())
            throw new DataValidationException((destination == null? "Null" : "Empty")
                    + " string passed into OrderImpl.setDestination(String)");
        this.destination = destination;
    }

    @Override
    public String getOrderId(){
        return orderId;
    }

    @Override
    public String getOrderDay(){
        return orderDay;
    }

    @Override
    public String getDestination(){
        return destination;
    }

    @Override
    public void addOrderItem(String id, OrderItem orderItem){
        orderItemList = ordItemOfOrder.addOrderItem(orderItem);
    }

    @Override
    public ArrayList<OrderItem> getOrderItems(){
        return orderItemList;
    }

    @Override
    public void decrQuantity(String itemName, int quantity) throws DataValidationException{
        ordItemOfOrder.decrQuantity(itemName, quantity);
    }

    @Override
    public void print(){
        System.out.println("OrderId: " + orderId);
        System.out.println("OrderDay: " + orderDay);
        System.out.println("Destination: " + destination);
        for (OrderItem ordItem : orderItemList){
            System.out.println(ordItem);
        }
    }

}
