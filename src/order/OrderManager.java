package order;


import exceptions.DataValidationException;
import order.orderItem.OrderItem;

import java.util.ArrayList;

public final class OrderManager {

    private static OrderManager instance;
    private ArrayList<Order> orderList =  new ArrayList<>();
    private OrderXMLLoader orderLoader = new OrderXMLLoader();

    public OrderManager() throws DataValidationException{
        orderLoader();
    }

    public static OrderManager getInstance() throws DataValidationException{
        if(instance == null){
            instance = new OrderManager();
        }
        return instance;
    }

   private void orderLoader() throws DataValidationException{
       orderLoader.parse(orderList);
   }

   public ArrayList<Order> getOrders() throws DataValidationException{
       ArrayList<Order> ordersCopy = new ArrayList<>();

       for(Order order : orderList) {
           // strip double quote of destination string
           String dest = order.getDestination().substring(1, order.getDestination().length() - 1);
           Order ordCopy = OrderFactory.createOrder(order.getOrderId(), order.getOrderDay(), dest);
           //HashMap<String, OrderItem> orderItems = new HashMap<>();
           for (OrderItem ordItem : order.getOrderItems()) {
               OrderItem itemCopy = new OrderItem(ordItem.getItemId(), ordItem.getQuantity());
               //orderItems.put(orditem.getItemId(), itemCopy);
               ordCopy.addOrderItem(ordItem.getItemId(), itemCopy);
           }
           ordersCopy.add(ordCopy);
       }
       return ordersCopy;


   }

   public void printReport(){
       System.out.println("orders:");
       for (Order order : orderList){
           order.print();
           System.out.println();

       }

   }
}
