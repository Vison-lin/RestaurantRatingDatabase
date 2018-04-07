package Model;

import java.util.Calendar;

public class Location {

    private long locationID;
    private Calendar firstOpenDate;
    private String managerName;
    private String phoneNumber;
    private String address;
    private long restaurantID;

    public Location(Calendar firstOpenDate, String managerName, String phoneNumber, String address, long restaurantID) {
        this.firstOpenDate = firstOpenDate;
        this.managerName = managerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.restaurantID = restaurantID;
    }

    public long getLocationID() {
        return locationID;
    }

    public void setLocationID(long locationID) {
        this.locationID = locationID;
    }

    public Calendar getFirstOpenDate() {
        return firstOpenDate;
    }

    public void setFirstOpenDate(Calendar firstOpenDate) {
        this.firstOpenDate = firstOpenDate;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(long restaurantID) {
        this.restaurantID = restaurantID;
    }
}
