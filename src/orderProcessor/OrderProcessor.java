package orderProcessor;

import exceptions.*;
import facility.FacilityManager;
import items.ItemManager;
import order.Order;
import order.orderItem.OrderItem;
import shortestPath.ShortestProcessor;

import java.util.*;

public class OrderProcessor {

    Order currOrder;
    ShortestProcessor sp;
    Solution currSolution;
    FacilityManager facilityManager;

    ItemManager itemManager = ItemManager.getInstance();

    public void startToProcess(Order order)
            throws NeighborsValidationException, DataValidationException,
                   NullFacilityException, NullVertexException{
        facilityManager = FacilityManager.getInstance();
        currOrder = order;
        // Initialize current solution
        currSolution = new Solution(currOrder.getOrderId(), currOrder.getOrderDay(),
                         currOrder.getDestination());

        //currOrder.print();
        for (OrderItem ordItem : currOrder.getOrderItems()){
            if(itemManager.IsRealItem(ordItem.getItemId())) {
                //If item is real, copy and add to solution.
                OrderItem orderItem = new OrderItem(ordItem.getItemId(), ordItem.getQuantity());
                currSolution.addItem(orderItem);
                //pass the name list to createFacRecord()
                List<String> nameList = facilityManager.getIdentifiedFacs(ordItem.getItemId(), currOrder.getDestination());
                createFacRecord(nameList, ordItem);
            }

        }
    }

    public void createFacRecord(List<String> nameList, OrderItem ordItem)
            throws NeighborsValidationException, DataValidationException,
                   NullFacilityException, NullVertexException{

        LogisticsRecord currLogRecord = new LogisticsRecord(ordItem.getItemId(), ordItem.getQuantity());

        while(ordItem.getQuantity()>0) {
            ArrayList<FacilityRecord> facRecordList = new ArrayList<>();
            sp = new ShortestProcessor();
            if (!nameList.isEmpty()) {
                for (String facility : nameList) {
                    double procDay;
                    double DaysToProc;
                    //the number of item in Identified facility
                    int processQuantity = facilityManager.getItemQuantity(facility, ordItem.getItemId());
                    //calculate the shortest path
                    String Day = sp.getTravelTime(facility, currOrder.getDestination());
                    double traDay = Double.parseDouble(Day);
                    //get processing day and days to process
                    if (processQuantity > ordItem.getQuantity()) {
                        procDay = facilityManager.checkProcessDay(facility, ordItem.getQuantity(), currOrder.getOrderDay());
                        DaysToProc = facilityManager.getProcessDays(facility, ordItem.getQuantity(), currOrder.getOrderDay());
                    } else {
                        procDay = facilityManager.checkProcessDay(facility, processQuantity, currOrder.getOrderDay());
                        DaysToProc = facilityManager.getProcessDays(facility, processQuantity, currOrder.getOrderDay());
                    }
                    FacilityRecord currRecord = new FacilityRecord(facility, processQuantity, procDay, DaysToProc, traDay);
                    facRecordList.add(currRecord);
                }
            } else {
                //no facility has this item, Stored as back order in solution
                currSolution.addString(Integer.toString(ordItem.getQuantity()) + " of " + ordItem.getItemId() + " is back-order.");
                return;

            }
            //sort List to get the top facility
            facRecordList.sort(Comparator.comparing(FacilityRecord::getArrDayInt));
            FacilityRecord topRecord = facRecordList.get(0);
            processRec(topRecord, ordItem);
            currLogRecord.addFacRecord(topRecord);

            if(topRecord == facRecordList.get(facRecordList.size()-1)){
                currSolution.addString(Integer.toString(ordItem.getQuantity()) + " of "
                        + ordItem.getItemId() + " is back-order.");
                break;
            }else {
                //get the new name list
                nameList.remove(topRecord.getFacHasItem());
            }
        }
        currSolution.addLogRecord(currLogRecord);
    }


    public void processRec(FacilityRecord facRecord, OrderItem ordItem)
            throws DataValidationException{

            if(ordItem.getQuantity() > facRecord.getItemsNumber()){
                //reduce quantity in Identified facility
                facilityManager.reduceInventory(facRecord.facHasItem, ordItem.getItemId(),
                        facRecord.getItemsNumber());
                //reduce quantity in copied Order
                currOrder.decrQuantity(ordItem.getItemId(), facRecord.getItemsNumber());
                //update facility Schedule
                facilityManager.updateSchedule(facRecord.getFacHasItem(), facRecord.getItemsNumber(),
                        currOrder.getOrderDay());

            }else{
                //(ordItem.getQuantity() <= facRecord.getItemsNumber())
                facRecord.setItemsNumber(ordItem.getQuantity());
                //reduce quantity in Identified facility
                facilityManager.reduceInventory(facRecord.facHasItem, ordItem.getItemId(),
                        ordItem.getQuantity());
                //reduce quantity in copied Order
                currOrder.decrQuantity(ordItem.getItemId(), ordItem.getQuantity());
                //update facility Schedule
                facilityManager.updateSchedule(facRecord.getFacHasItem(), facRecord.getItemsNumber(),
                        currOrder.getOrderDay());
            }
    }

    public Solution getSolution(){
        return currSolution;
    }
}
