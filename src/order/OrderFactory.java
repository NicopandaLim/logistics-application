package order;


import exceptions.DataValidationException;

public class OrderFactory {

    public static Order createOrder(String orderId, String orderDay, String destination)
        throws DataValidationException{

        return new OrderImpl(orderId, orderDay, destination);
    }
}
