package facility.facilityOperation.inventory;


import exceptions.DataValidationException;

import java.util.ArrayList;

public interface FacilityInventory {

    ArrayList<Inventory> addInventory(Inventory inventory);

    void decrInvetory(String itemName, int quantity) throws DataValidationException;

    void inventoryPrinter();

    void useUpPrinter();

}
