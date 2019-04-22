package facility.facilityOperation.inventory;

import exceptions.DataValidationException;

public class Inventory {
    private String item;
    private int quantity;

    public Inventory(String item, int quantity) throws DataValidationException{
        setItem(item);
        setQuantity(quantity);
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    private void setItem(String item) throws DataValidationException {
        if (item == null )
            throw new DataValidationException("Null item passed into Inventory.getItem(Item)");
        this.item = item;
    }

    public void setQuantity(int quantity) throws DataValidationException{
        if (quantity < 0)
            throw new DataValidationException("Invalid quantity value passed into Inventory.setQuantity(int)");
        this.quantity = quantity;
    }

    public String useUpCheck(){
        if (this.quantity == 0) {
            return this.item;
        }else{
            return null;
        }
    }

    @Override
    public String toString(){
        return String.format("  %-9s %d", item, quantity);
    }
}
