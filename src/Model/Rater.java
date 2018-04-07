package Model;

import java.util.Calendar;

public class Rater {
    private long userID;
    private String email;
    private String name;
    private Calendar joinDate;
    private String type;
    private int reputation;

    public Rater(String email, String name, Calendar joinDate, String type, int reputation) {
        this.userID = userID;
        this.email = email;
        this.name = name;
        this.joinDate = joinDate;
        this.type = type;
        this.reputation = reputation;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Calendar joinDate) {
        this.joinDate = joinDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }
}
