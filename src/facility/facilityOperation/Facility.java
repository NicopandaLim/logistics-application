package facility.facilityOperation;


import exceptions.DataValidationException;
import exceptions.NeighborsValidationException;
import facility.facilityOperation.inventory.Inventory;
import facility.facilityOperation.neighbors.FacilityEdge;

import java.util.*;

public interface Facility {
     String getLocation();

     long getCostPerDay();

     ArrayList<FacilityEdge> getNeighborList()throws NeighborsValidationException;

     void addNeighbor(FacilityEdge facilityEdge);

     void addInventory(Inventory facInventory);

     ArrayList<Inventory> getInventory();

     void decInventory(String itemName, int quantity) throws DataValidationException;

     double checkProcessDay(int processQuantity, String orderDay);

     double getDays(int itemNum, String ordDay);

     void updateSchedule(int itemQuantity, String orderDay);

     void print();

}
