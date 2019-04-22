package orderProcessor;


import exceptions.*;
import order.Order;
import order.OrderManager;

import java.util.ArrayList;

public class OrderProcessManager {

    OrderManager orderManager;
    ArrayList<Solution> solutionList = new ArrayList<>();

    public void toProcess() throws NeighborsValidationException, DataValidationException,
                                   NullFacilityException, NullVertexException {
        orderManager = OrderManager.getInstance();
        ArrayList<Order> orderList = orderManager.getOrders();
        OrderProcessor ordProc = new OrderProcessor();
        for(Order order : orderList) {
            ordProc.startToProcess(order);
            solutionList.add(ordProc.getSolution());
        }
        printerSolution();
    }

    private void printerSolution() throws DataValidationException, NullFacilityException{
        for(int i=1; i<solutionList.size(); i++){
            System.out.println("Order #" + i);
            solutionList.get(i-1).solutionPrinter();
            System.out.println(" ");
        }
    }
}
