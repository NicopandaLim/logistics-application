package facility.facilityOperation;

import exceptions.*;
import facility.facilityOperation.inventory.*;
import facility.facilityOperation.neighbors.*;
import facility.facilityOperation.schedule.*;

import java.util.*;

public class FacilityImpl implements Facility {
    private String location;
    private int ratePerDay;
    private int costPerDay;
    private ArrayList<FacilityEdge> neighbors = new ArrayList<>();
    private ArrayList<Inventory> activeInventory = new ArrayList<>();
    HashMap<Integer, Long> scheduleList = new HashMap<>();

    FacilitySchedule  facSchedule =  new FacilityScheduleImpl();
    FacilityInventory facilityInventory = new FacilityInventoryImpl();
    FacilityNeighbors facilityNeighbors = new FacilityNeighborsImpl();


    public FacilityImpl(String location, int ratePerDay, int costPerDay)
            throws DataValidationException{
        setLocation(location);
        setRatePerDay(ratePerDay);
        setCostPerDay(costPerDay);
    }

    public String getLocation() {
        return location;
    }


    public long getCostPerDay() {
        return costPerDay;
    }

    private void setLocation(String location) throws DataValidationException{
        if (location == null || location.isEmpty())
            throw new DataValidationException((location == null? "Null" : "Empty")
                    + " location value passed into FacilityImpl.setLocation()");
        this.location = location;
    }

    private void setRatePerDay(int rate) throws DataValidationException{
        if (rate < 0)
            throw new DataValidationException(" Invalid rate value passed into"
                    + " FacilityImpl.setRatePerDay()");
        this.ratePerDay = rate;
    }

    private void setCostPerDay(int cost) throws DataValidationException{
        if (cost < 0)
            throw new DataValidationException(" Invalid cost value passed into"
                    + " FacilityImpl.setCostPerDay()");
        this.costPerDay = cost;
    }

    public ArrayList<FacilityEdge> getNeighborList() throws NeighborsValidationException{
        if (neighbors == null || neighbors.isEmpty())
            throw new NeighborsValidationException((neighbors == null? "Null" : "Empty")
                    + " neighborList passed into FacilityImpl.getNeighborList()");
        return neighbors;
    }

    // Neighbor Methods
    @Override
    public void addNeighbor(FacilityEdge facilityEdge){
        neighbors = facilityNeighbors.addNeighbor(facilityEdge);
    }

    private void printNeighbors(){
        facilityNeighbors.neighborsPrinter();
    }

    //Inventory Methods
    @Override
    public void addInventory(Inventory inventory){
        activeInventory = facilityInventory.addInventory(inventory);
    }

    @Override
    public ArrayList<Inventory> getInventory(){
        return activeInventory;
    }

    @Override
    public void decInventory(String itemName, int quantity) throws DataValidationException{
        facilityInventory.decrInvetory(itemName, quantity);

    }

    private void printInventory(){
        facilityInventory.inventoryPrinter();
    }

    private void printUseUp(){
        facilityInventory.useUpPrinter();
    }

    //Schedule Methods
    @Override
    public double checkProcessDay(int processQuantity, String orderDay){
        double processDay = facSchedule.processCheck(processQuantity, orderDay, ratePerDay);
        return (processDay);
    }

    @Override
    public double getDays(int itemNum, String ordDay){
        double days = facSchedule.getProcDays(itemNum, ordDay, ratePerDay);
        return days;
    }

    @Override
    public void updateSchedule(int itemQuantity, String orderDay){
        facSchedule.updateSche(itemQuantity, orderDay, ratePerDay);
    }

    private void printSchedule(){
        facSchedule.schedulePrinter(ratePerDay);
    }

    @Override
    public void print(){
        System.out.println(" ");
        System.out.println(" " + location + " ");
        System.out.println("--------");
        System.out.println("Rate per Day: " + ratePerDay);
        System.out.println("Cost per Day: $" + costPerDay);
        System.out.println(" ");

        printNeighbors();
        printInventory();
        printUseUp();
        printSchedule();
    }
}
