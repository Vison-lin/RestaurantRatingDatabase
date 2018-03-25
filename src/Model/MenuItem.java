package Model;

public class MenuItem {
    private long itemID;
    private String name;
    private String type;
    private String category;
    private String description;
    private float price;
    private long restaurantID;

    public MenuItem(long itemID, String name, String type, String category, float price, long restaurantID, String description) {
        this.itemID = itemID;
        this.name = name;
        this.type = type;
        this.category = category;
        this.description = description;
        this.price = price;
        this.restaurantID = restaurantID;
    }
}
