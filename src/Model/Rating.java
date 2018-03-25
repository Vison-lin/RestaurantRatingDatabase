package Model;

import java.util.Calendar;

public class Rating {
    private long userID;
    private Calendar dateAdded;
    private int price;
    private int food;
    private int mood;
    private int staff;
    private String comments;
    private long restaurantID;

    public Rating(long userID, Calendar dateAdded, int price, int food, int mood, int staff, String comments, long restaurantID) {
        this.userID = userID;
        this.dateAdded = dateAdded;
        this.price = price;
        this.food = food;
        this.mood = mood;
        this.staff = staff;
        this.comments = comments;
        this.restaurantID = restaurantID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public Calendar getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Calendar dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getStaff() {
        return staff;
    }

    public void setStaff(int staff) {
        this.staff = staff;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public long getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(long restaurantID) {
        this.restaurantID = restaurantID;
    }
}
