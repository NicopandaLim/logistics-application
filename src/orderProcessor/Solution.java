package orderProcessor;

import exceptions.DataValidationException;
import exceptions.NullFacilityException;
import order.orderItem.OrderItem;
import java.util.ArrayList;

public class Solution {
    String orderId;
    String orderTime;
    String destination;
    ArrayList<OrderItem> itemList = new ArrayList<>();
    ArrayList<String> stringList = new ArrayList<>();
    ArrayList<LogisticsRecord> logRecList = new ArrayList<>();

    public Solution(String ordId, String ordTime, String dest){
        orderId = ordId;
        orderTime = ordTime;
        destination = dest;
    }

    public double getTotalCost() throws DataValidationException, NullFacilityException {
        return calTotalCost();
    }

    public void addItem(OrderItem ordItem){
        itemList.add(ordItem);
    }

    public void addString(String str){
        stringList.add(str);
    }

    public void addLogRecord(LogisticsRecord logRec){
        logRecList.add(logRec);
    }

    private double calTotalCost() throws DataValidationException, NullFacilityException {
        double total = 0.0;
        for(LogisticsRecord logRec : logRecList){
            total = total + logRec.getItemCost();
        }
        return total;
    }

    private int getFstDeliDay(){
        int FstDeliDay = Integer.MAX_VALUE;
        for(LogisticsRecord logRec : logRecList){
            int day = logRec.getFstArrDay();
            if(day < FstDeliDay){FstDeliDay = day;}
        }
        return FstDeliDay;
    }

    private int getLstDeliDay(){
        int LstDeliDay = 0;
        for(LogisticsRecord logRec : logRecList){
            int day = logRec.getLstArrDay();
            if(day > LstDeliDay){LstDeliDay = day;}
        }
        return LstDeliDay;
    }

    private void printOrderItem(){
        for(OrderItem item : itemList){
            System.out.println("Item ID:   " + item.getItemId() + ",    Quantity:" + item.getQuantity());
        }
    }

    private void ordItemInfoPrinter(){
        System.out.println("   Item ID   Quantity  Cost       # Source Used   First Day  Last Day");
        for(int i=1; i<=logRecList.size(); i++){
            System.out.print(i+") ");
            logRecList.get(i-1).orderItemPrinter();
        }
    }

    private void printItemsFacCost(){
        for(LogisticsRecord logRec : logRecList){
            System.out.println(logRec.getItemId() + ":");
            logRec.facCostPrinter();
            System.out.println(" ");
        }
    }

    public void solutionPrinter() throws DataValidationException, NullFacilityException{
        System.out.println("Order Id:   " + orderId);
        System.out.println("Order Time:  " + orderTime);
        System.out.println("Destination: " + destination);
        System.out.println("List of Order Items:");
        printOrderItem();
        System.out.println(" ");
        System.out.println("Processing Solution:");
        System.out.println("Total Cost:         $" + getTotalCost());
        System.out.println("1st Delivery Day:   " + getFstDeliDay());
        System.out.println("Last Delivery Day:  " + getLstDeliDay());
        System.out.println("Order Items:");
        ordItemInfoPrinter();
        System.out.println(" ");
        printItemsFacCost();
    }


}
