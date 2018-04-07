package Model;

public class MenuItem {
    private long itemID;
    private String name;
    private String type;
    private String category;
    private String description;
    private float price;
    private long restaurantID;

    public MenuItem(String name, String type, String category, float price, long restaurantID, String description) {

        this.name = name;
        this.type = type;
        this.category = category;
        this.description = description;
        this.price = price;
        this.restaurantID = restaurantID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(long restaurantID) {
        this.restaurantID = restaurantID;
    }
}
