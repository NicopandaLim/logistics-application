package items;

import java.util.HashMap;

public final class ItemManager {

    private static ItemManager instance;
    private HashMap<String, Item> itemList = new HashMap<>();
    ItemCatalogXMLLoader itemLoader;

    public ItemManager(){
        itemLoader();

    }

    public static ItemManager getInstance(){
        if (instance == null) {
            instance = new ItemManager();
        }
        return instance;
    }

    public HashMap<String, Item> itemLoader(){
       itemLoader = new ItemCatalogXMLLoader();
       itemLoader.parse(itemList);
        return itemList;
    }

    public double getPrice(String itemName){
        double price = itemList.get(itemName).getPrice();
        return price;
    }

    public Boolean IsRealItem(String id){
        return itemList.containsKey(id);
    }

    //for test
    public void printReport(){
        System.out.println(" ");
        System.out.println("Item Catalog Content Output:");
        System.out.println();
        for(Item item : itemList.values()){
            System.out.println(item);
        }
        System.out.println(" ");
    }

}
