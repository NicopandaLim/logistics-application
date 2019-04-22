package facility;

import exceptions.*;
import exceptions.NeighborsValidationException;
import facility.facilityOperation.Facility;

import facility.facilityLoader.*;
import facility.facilityOperation.inventory.Inventory;
import facility.facilityOperation.neighbors.FacilityEdge;
import javafx.util.Pair;

import java.util.*;

// It's a facade
public final class FacilityManager {

    private static FacilityManager instance;
    private HashMap<String, Facility> facilityList = new HashMap<>();
    private FacilityXMLLoader facLoader = new FacilityXMLLoader();
    private FacilityNetworkXMLLoader facNetworkLoader = new FacilityNetworkXMLLoader();
    private FacilityInventoryXMLLoader facInvenLoader = new FacilityInventoryXMLLoader();

    public FacilityManager() throws DataValidationException, NullFacilityException{
      facilityLoader();
    }

    public static FacilityManager getInstance() throws DataValidationException, NullFacilityException{
        if(instance == null){
            instance = new FacilityManager();
        }
        return instance;
    }

      // a method to read xml
    public HashMap<String, Facility> facilityLoader() throws DataValidationException, NullFacilityException{
          facLoader.parse(facilityList);
          facNetworkLoader.parse(facilityList);
          facInvenLoader.parse(facilityList);
          return facilityList;
      }

      public double getDistOf2Facilities(String fac1, String fac2) throws NeighborsValidationException{
          double dist = 0;
          for(FacilityEdge edge : facilityList.get(fac1).getNeighborList()){
              if ((edge.getTarget().getLocation()).equals(fac2)){
                  dist = edge.getWeight();
              }
          }
          return dist;
      }

    public List<Pair<String, String>> createFacPairs() throws NeighborsValidationException {
        List<Pair<String, String>> pairList = new ArrayList<>();
        for(Facility facility : facilityList.values()){
            for (FacilityEdge edge : facility.getNeighborList()) {
                pairList.add(new Pair<>(facility.getLocation(), edge.getTarget().getLocation()));
            }
        }
        return pairList;
    }

    public List<String> getIdentifiedFacs(String itemName, String facilityName){
        List<String> nameList = new ArrayList<>();
        for (Facility fac : facilityList.values()){
            if(!fac.getLocation().equals(facilityName)){
                for(Inventory inventory : fac.getInventory()){
                    if((inventory.getItem().equals(itemName)) && (inventory.getQuantity() > 0)){
                        nameList.add(fac.getLocation());
                    }
                }
            }
        }
        return nameList;
    }

    public int getItemQuantity(String facility, String itemId){
        int quantity = 0;
        for (Inventory inventory : facilityList.get(facility).getInventory()){
            if (inventory.getItem().equals(itemId)){
                quantity = inventory.getQuantity();
            }
        }
        return quantity;
    }

     public double checkProcessDay(String facility, int processQuantity, String orderDay){
        return facilityList.get(facility).checkProcessDay(processQuantity, orderDay);
    }

    public double getProcessDays(String facility, int procQuantity, String ordDay){
         return facilityList.get(facility).getDays(procQuantity, ordDay);
    }

    public int getFacilityCost(String facility){
        return (int)facilityList.get(facility).getCostPerDay();
    }

    public void updateSchedule(String facility, int itemQuantity, String orderDay){
        facilityList.get(facility).updateSchedule(itemQuantity, orderDay);
    }

    public void reduceInventory(String fac, String itemName, int quantity) throws DataValidationException{
        facilityList.get(fac).decInventory(itemName, quantity);
    }


    public void printReport (){
         System.out.println("Facility Status Output:");
         for(String name : facilityList.keySet()){
            facilityList.get(name).print();
             System.out.println(" ");
        }
        System.out.println(" ");
    }

}
