package orderProcessor;

import exceptions.DataValidationException;
import exceptions.NullFacilityException;
import facility.FacilityManager;
import items.ItemManager;

import java.util.ArrayList;
import java.util.HashMap;

public class LogisticsRecord {

    String itemId;
    int quantity;
    double itemTotalCost;
    FacilityManager facilityMgr;
    ItemManager itemMgr = ItemManager.getInstance();
    ArrayList<FacilityRecord> facRecSolutions = new ArrayList<>();
    HashMap<FacilityRecord, Double> facCostList = new HashMap<>();

    public LogisticsRecord(String id, int itemNum ){
        itemId = id;
        quantity = itemNum;
    }

    public String getItemId(){
        return itemId;
    }

    public void addFacRecord(FacilityRecord facRec){
        facRecSolutions.add(facRec);
    }

    // total process cost for this item
    private double calProcessCost() throws DataValidationException, NullFacilityException{
        int dayCost;
        double  procCost= 0.0;
        facilityMgr = FacilityManager.getInstance();

        for(FacilityRecord facRec : facRecSolutions){
            dayCost = facilityMgr.getFacilityCost(facRec.getFacHasItem());
            procCost = procCost + facRec.getDaysToProc() * dayCost;
        }
        return procCost;
    }

    //total travel cost for this item
    private double calTravelCost(){
        double traCost = 0.0;
        for(FacilityRecord facRec : facRecSolutions){
            traCost = traCost + facRec.getTravelDay() * 500;
        }
        return traCost;
    }

    private void calculateCost() throws DataValidationException, NullFacilityException{
        eachFacCost();
        double itemCost = 0.0;
        for(double cost : facCostList.values()){
            itemCost = itemCost + cost;
        }

        itemTotalCost = itemCost;
    }

    public double getItemCost() throws DataValidationException, NullFacilityException{
        calculateCost();
        return itemTotalCost;
    }

    //list of cost for each facility
    private void eachFacCost()throws DataValidationException, NullFacilityException{
        facilityMgr = FacilityManager.getInstance();
        int dayCost;
        double itemPrice = itemMgr.getPrice(itemId);
        for(FacilityRecord facRec : facRecSolutions){
            dayCost = facilityMgr.getFacilityCost(facRec.getFacHasItem());
            double cost = itemPrice * facRec.getItemsNumber() + dayCost * facRec.getDaysToProc() + facRec.getTravelDay() * 500;
            facCostList.put(facRec, Math.round(cost*100.0)/100.0);
        }
    }

    private int getSourceNum(){
        return facRecSolutions.size();
    }

    public int getFstArrDay(){
        int fstDay = Integer.MAX_VALUE;
        for(FacilityRecord facRec : facRecSolutions){
            int day = Integer.parseInt(facRec.getArrivalDay().substring(4));
            if(day < fstDay){fstDay = day;}
        }
        return fstDay;
    }

    public int getLstArrDay(){
        int lstDay = 0;
        for(FacilityRecord facRec : facRecSolutions){
            int day = Integer.parseInt(facRec.getArrivalDay().substring(4));
            if(day > lstDay){lstDay = day;}
        }
        return lstDay;
    }

    public void orderItemPrinter(){
        System.out.print(String.format("%-10s", itemId));
        System.out.print(String.format("%-10d", quantity));
        System.out.print(String.format("%-12.2f", itemTotalCost));
        System.out.print(String.format("%-15d", getSourceNum()));
        System.out.print(String.format("%-10d", getFstArrDay()));
        System.out.println(String.format("%-10d", getLstArrDay()));

    }

    public void facCostPrinter(){
        for(FacilityRecord facRec : facCostList.keySet()){
            System.out.print(String.format("%-20s", facRec.getFacHasItem()));
            System.out.print("  " + String.format("%-10d", facRec.getItemsNumber()));
            System.out.print("  $" + String.format("%8.2f", facCostList.get(facRec)));
            System.out.println("      " + facRec.getArrivalDay());
        }
    }

}
