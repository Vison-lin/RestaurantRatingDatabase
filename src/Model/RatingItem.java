package Model;

import java.util.Calendar;

public class RatingItem {
    private long userID;
    private Calendar joinDate;
    private long itemID;
    private int rating;
    private String comments;

    public RatingItem(Calendar joinDate, long itemID, int rating, String comments) {
        this.userID = userID;
        this.joinDate = joinDate;
        this.itemID = itemID;
        this.rating = rating;
        this.comments = comments;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public Calendar getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Calendar joinDate) {
        this.joinDate = joinDate;
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
