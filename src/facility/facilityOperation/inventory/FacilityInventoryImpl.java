package facility.facilityOperation.inventory;


import exceptions.DataValidationException;

import java.util.ArrayList;

public class FacilityInventoryImpl implements FacilityInventory {

    private ArrayList<Inventory> activeInventory = new ArrayList<>();
    private ArrayList<String> depletedInventory = new ArrayList<>();

    @Override
    public ArrayList<Inventory> addInventory(Inventory inventory){
        activeInventory.add(inventory);
        return activeInventory;
    }

    @Override
    public void inventoryPrinter(){
        System.out.println(" ");
        System.out.println("Active Inventory:");
        System.out.println("  Item ID   Quantity");
        for (Inventory facIn : activeInventory) {
            System.out.println(facIn);
        }
    }

    private void updateDepletedList(){
        for (Inventory facIn : activeInventory) {
            if (facIn.useUpCheck() != null){
                depletedInventory.add(facIn.useUpCheck());
            }
        }
    }

    @Override
    public void decrInvetory(String itemName, int quantity) throws DataValidationException {
        for(Inventory item : activeInventory){
            if(item.getItem().equals(itemName)){
                item.setQuantity(item.getQuantity() - quantity);
            }
        }
    }

    @Override
    public void useUpPrinter(){
        updateDepletedList();
        System.out.println(" ");
        System.out.print("Depleted (Used-Up) Inventory: ");
        if(depletedInventory.isEmpty()){
            System.out.println("None");
        }else{
            for (String name : depletedInventory) {
                System.out.print(name + ", ");;
            }
            System.out.println(" ");

        }
        System.out.println(" ");
    }

}
