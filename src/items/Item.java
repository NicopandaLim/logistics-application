package items;


import java.text.NumberFormat;

public class Item {

    private String id;
    private double price;

    public Item(String id, double price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }
    public double getPrice() {
        return price;
    }

    public String toString(){
        //the way to represent number with comma
        return String.format( "%-9s :  $%s",id, NumberFormat.getInstance().format(price));
    }
}
