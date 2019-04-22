package items;


public class ItemFactory {
    public static Item createItem(String id, double price){

      return new Item(id, price);
    }
}
