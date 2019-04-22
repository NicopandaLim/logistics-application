package order.orderItem;


import exceptions.DataValidationException;

public class OrderItem {
    private String itemId;
    private int quantity;

    public OrderItem(String id, int quantity) throws DataValidationException{
        setItemId(id);
        setQuantity(quantity);
    }

    private void setItemId(String id) throws DataValidationException{
        if (id == null || id.isEmpty())
            throw new DataValidationException((id == null? "Null" : "Empty")
            + " String passed into setItemId.(String)");
        itemId = id;
    }

    public void setQuantity(int quantity) throws DataValidationException{
        if (quantity < 0)
            throw new DataValidationException("Invalid quantity"
                    + " value passed into setQuantity.(int)");
        this.quantity = quantity;
    }

    public String getItemId(){
        return itemId;
    }

    public int getQuantity(){
        return quantity;
    }


    public String toString(){
        return String.format("%s   %d", itemId, quantity);
    }
}
