package mDB;

import Model.Location;
import Model.MenuItem;
import Model.Restaurant;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import Model.Rater;
import Model.Restaurant;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

public class DatabaseHelper {

    //RATER TABLE
    final static String RATER_TABLE_NAME = "RATER";
    final static String RATER_PRIM_KEY_USERID = "userID";
    final static String RATER_NAME = "name";
    final static String RATER_EMAIL = "email";
    final static String RATER_JOIN_DATE = "joinDate";
    final static String RATER_TYPE = "type";
    final static String RATER_REPUTATION = "reputation";
    //RESTAURANT TABLE
    final static String RESTAURANT_TABLE_NAME = "RESTAURANT";
    final static String RESTAURANT_PRIM_KEY_RESTAURANTID = "restaurantID";
    final static String RESTAURANT_NAME = "name";
    final static String RESTAURANT_TYPE = "type";
    final static String RESTAURANT_URL = "url";
    //RATING TABLE
    final static String RATING_TABLE_NAME = "RATING";
    final static String RATING_PRIM_KEY_USERID_FOREIGN_KEY = "userID";
    final static String RATING_PRIM_KEY_DATE_ADDED = "dateAdded";
    final static String RATING_PRICE = "price";
    final static String RATING_FOOD = "food";
    final static String RATING_MOOD = "mood";
    final static String RATING_STAFF = "staff";
    final static String RATING_COMMENTS = "comments";
    final static String RATING_RESTAURANTID_FOREIGN_KEY = "restaurantID";
    //LOCATION TABLE
    final static String LOCATION_TABLE_NAME = "LOCATION";
    final static String LOCATION_PRIM_KEY_LOCATIONID = "locationID";
    final static String LOCATION_FIRST_OPEN_DATE = "firstOpenDate";
    final static String LOCATION_MANAGER_NAME = "managerName";
    final static String LOCATION_PHONE_NUMBER = "phoneNumber";
    final static String LOCATION_ADDRESS = "address";
    final static String LOCATION_RESTAURANTID_FOREIGN_KEY = "restaurantID";
    //MENUITEM TABLE
    final static String MENUITEM_TABLE_NAME = "MENUITEM";
    final static String MENUITEM_PRIM_KEY_ITEMID = "itemID";
    final static String MENUITEM_NAME = "name";
    final static String MENUITEM_TYPE = "type";
    final static String MENUITEM_CATEGORY = "category";
    final static String MENUITEM_DESCRIPTION = "description";
    final static String MENUITEM_PRICE = "price";
    final static String MENUITEM_RESTAURANTID_FOREIGN_KEY = "restaurantID";
    //RATINGITEM TABLE
    final static String RATINGITEM_TABLE_NAME = "RATINGITEM";
    final static String RATINGITEM_PRIM_KEY_USERID = "userID";
    final static String RATINGITEM_PRIM_KEY_JOIN_DATE = "joinDate";
    final static String RATINGITEM_PRIM_KEY_ITEMID = "itemID";
    final static String RATINGITEM_RATING = "rating";
    final static String RATINGITEM_COMMENTS = "comments";
    static DatabaseHelper sInstance;
    /**
     * dB connection
     */
    final Connection db;
    /**
     * Used to run query
     */
    final Statement st;


    private DatabaseHelper(String dbName, String url, String userName, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        //authentication
        Properties props = new Properties();
        props.setProperty("user", userName);
        props.setProperty("password", password);
        db = DriverManager.getConnection(url + dbName, props);

        st = db.createStatement();

        //init: create table
        st.execute(
                //RATER TABLE

                "CREATE TABLE IF NOT EXISTS " + RATER_TABLE_NAME +
                        "(" + RATER_PRIM_KEY_USERID + " SERIAL PRIMARY KEY, " +
                        RATER_EMAIL + " char(100), " +
                        RATER_NAME + " char(50), " +
                        RATER_JOIN_DATE + " date, " +
                        RATER_TYPE + " char(11) check ( " + RATER_TYPE + " = 'blog' OR " + RATER_TYPE + " = 'online' OR " + RATER_TYPE + " = 'food critic')," +
                        RATER_REPUTATION + " numeric check ( " + RATER_REPUTATION + " <=5 AND " + RATER_REPUTATION + " >= 1));" +

                        //RESTAURANT TABLE

                        "CREATE TABLE IF NOT EXISTS " + RESTAURANT_TABLE_NAME +
                        "(" + RESTAURANT_PRIM_KEY_RESTAURANTID + " SERIAL PRIMARY KEY, " +
                        RESTAURANT_NAME + " char(50), " +
                        RESTAURANT_TYPE + " char(50), " +
                        RESTAURANT_URL + " char(100));" +

                        //RATING TABLE

                        "CREATE TABLE IF NOT EXISTS " + RATING_TABLE_NAME +
                        "(" + RATING_PRIM_KEY_USERID_FOREIGN_KEY + " INTEGER references " + RATER_TABLE_NAME + " ( " + RATER_PRIM_KEY_USERID + "), " +
                        RATING_PRIM_KEY_DATE_ADDED + " date, " +
                        RATING_PRICE + " numeric check ( " + RATING_PRICE + " <=5 AND " + RATING_PRICE + " >= 1), " +
                        RATING_FOOD + " numeric check (food <=5 AND food >= 1), " +
                        RATING_MOOD + " numeric check (mood <=5 AND mood >= 1), " +
                        RATING_STAFF + " numeric check ( " + RATING_STAFF + " <=5 AND " + RATING_STAFF + " >= 1)," +
                        RATING_COMMENTS + " char(500), " +
                        RATING_RESTAURANTID_FOREIGN_KEY + " INTEGER references Restaurant(" + RESTAURANT_PRIM_KEY_RESTAURANTID + ")," +
                        "PRIMARY KEY ( " + RATING_PRIM_KEY_USERID_FOREIGN_KEY + ", " + RATING_PRIM_KEY_DATE_ADDED + "), " +
                        "UNIQUE( " + RATING_PRIM_KEY_USERID_FOREIGN_KEY + ", " + RATING_PRIM_KEY_DATE_ADDED + " ));" +

                        //LOCATION TABLE

                        "CREATE TABLE IF NOT EXISTS " + LOCATION_TABLE_NAME +
                        "(" + LOCATION_PRIM_KEY_LOCATIONID + " SERIAL PRIMARY KEY, " +
                        LOCATION_FIRST_OPEN_DATE + " date, " +
                        LOCATION_MANAGER_NAME + " char(20), " +
                        LOCATION_PHONE_NUMBER + " char(10), " +
                        LOCATION_ADDRESS + " char(30), " +
                        LOCATION_RESTAURANTID_FOREIGN_KEY + " INTEGER references " + RESTAURANT_TABLE_NAME + " ( " + RESTAURANT_PRIM_KEY_RESTAURANTID + " ));" +

                        //MENUITEM TABLE

                        "CREATE TABLE IF NOT EXISTS " + MENUITEM_TABLE_NAME +
                        "(" + MENUITEM_PRIM_KEY_ITEMID + " SERIAL PRIMARY KEY, " +
                        MENUITEM_NAME + " char(50), " +
                        MENUITEM_TYPE + " char(8) check (type = 'food' OR type = 'beverage'), " +
                        MENUITEM_CATEGORY + " char(7) check ( " + MENUITEM_CATEGORY + " = 'starter' OR " + MENUITEM_CATEGORY + " = 'main' OR " + MENUITEM_CATEGORY + " = 'desert'), " +
                        MENUITEM_DESCRIPTION + " char(100), " +
                        MENUITEM_PRICE + " numeric(2), " +
                        MENUITEM_RESTAURANTID_FOREIGN_KEY + " INTEGER references " + RESTAURANT_TABLE_NAME + "( " + RESTAURANT_PRIM_KEY_RESTAURANTID + " ));" +

                        //RATINGITEM TABLE

                        "CREATE TABLE IF NOT EXISTS " + RATINGITEM_TABLE_NAME +
                        "(" + RATINGITEM_PRIM_KEY_USERID + " SERIAL, " +
                        RATINGITEM_PRIM_KEY_JOIN_DATE + " date, " +
                        RATINGITEM_PRIM_KEY_ITEMID + " INTEGER references " + MENUITEM_TABLE_NAME + " ( " + MENUITEM_PRIM_KEY_ITEMID + " ), " +
                        RATINGITEM_RATING + " numeric check ( " + RATINGITEM_RATING + " <=5 AND " + RATINGITEM_RATING + " >= 1)," +
                        RATINGITEM_COMMENTS + " char(500), " +
                        "PRIMARY KEY( " + RATINGITEM_PRIM_KEY_USERID + ", " + RATINGITEM_PRIM_KEY_JOIN_DATE + ", " + RATINGITEM_PRIM_KEY_ITEMID + "));");
        //st.close();
    }

    public static synchronized DatabaseHelper getInstance(String dbName, String url, String userName, String password) throws SQLException, ClassNotFoundException {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHelper(dbName, url, userName, password);
        }
        return sInstance;
    }

    public Connection getdBConnection() {
        if (db == null) {
            throw new NullPointerException();
        }
        return db;
    }

    public Statement getdBStatement() throws IllegalAccessException {
        if (st == null) {
            throw new IllegalAccessException();
        }
        return st;
    }


    /**
     * a
     *
     * @param restaurantID the prim key of that restaurant
     * @return Pair<Restaurant   ,       ArrayList   <   Location>> where the key is that restaurant and the value is an ArrayList that contains that restaurant's all location
     * @throws SQLException
     */
    public Pair<Restaurant, ArrayList<Location>> displayRestaurantInfo(long restaurantID) throws SQLException {
        ResultSet rs = st.executeQuery("SELECT * FROM " + RESTAURANT_TABLE_NAME + " AS R JOIN " + LOCATION_TABLE_NAME + " AS L USING (" + RESTAURANT_PRIM_KEY_RESTAURANTID + ") WHERE R." + RESTAURANT_PRIM_KEY_RESTAURANTID + " = " + restaurantID + ";");
        Restaurant restaurant = null;
        ArrayList<Location> locations = new ArrayList<>();
        if (rs.next()) {
            //restaurant
            Long restID = Long.valueOf(rs.getString(1));
            String restName = rs.getString(2);
            String restType = rs.getString(3);
            String restUrl = rs.getString(4);
            //location
            Long locationID = Long.valueOf(rs.getString(5));
            Calendar locationOpenDate = Calendar.getInstance();
            locationOpenDate.setTime(rs.getDate(6));
            String locationMgrName = rs.getString(7);
            String locationPhone = rs.getString(8);
            String locationAddr = rs.getString(9);

            restaurant = new Restaurant(restName, restType, restUrl);
            restaurant.setRestaurantID(restID);
            Location location = new Location(locationOpenDate, locationMgrName, locationPhone, locationAddr, restID);
            location.setLocationID(locationID);
            locations.add(location);
        }
        while (rs.next()) {
            Long locationID = Long.valueOf(rs.getString(5));
            Calendar locationOpenDate = Calendar.getInstance();
            locationOpenDate.setTime(rs.getDate(6));
            String locationMgrName = rs.getString(7);
            String locationPhone = rs.getString(8);
            String locationAddr = rs.getString(9);
            Location location = new Location(locationOpenDate, locationMgrName, locationPhone, locationAddr, restaurantID);
            location.setLocationID(locationID);
            locations.add(location);
        }
        return new Pair<>(restaurant, locations);

    }

    /**
     * b
     *
     * @param restaurantID the id of the restaurant of the menu
     * @return HashMap<String   ,       ArrayList   <   MenuItem>>, where key is the type of the items, can only be: main, starter, or Fires.
     * @throws SQLException
     */
    public HashMap<String, ArrayList<MenuItem>> fullMenu(long restaurantID) throws SQLException {
        ResultSet rs = st.executeQuery("SELECT * FROM " + MENUITEM_TABLE_NAME + " AS M WHERE M." + MENUITEM_RESTAURANTID_FOREIGN_KEY + " = " + restaurantID + " ORDER BY M." + MENUITEM_CATEGORY + ";");
        HashMap<String, ArrayList<MenuItem>> result = new HashMap<>();
        String currCategory = "";
        ArrayList<MenuItem> menuItems = null;
        while (rs.next()) {
            Long itemID = rs.getLong(1);
            String name = rs.getString(2);
            String type = rs.getString(3);
            String category = rs.getString(4);
            String description = rs.getString(5);
            Float price = rs.getFloat(6);
            MenuItem menuItem = new MenuItem(name, type, category, price, restaurantID, description);
            menuItem.setItemID(itemID);
            if (currCategory.equals("")) {
                currCategory = category;
                menuItems = new ArrayList<>();
            }
            if (!category.equals(currCategory)) {//if goes to the new category

                if (menuItems == null) {
                    throw new NullPointerException();
                }
                result.put(currCategory, menuItems);
                menuItems = new ArrayList<>();
                menuItems.add(menuItem);
                currCategory = category;

            } else {
                if (menuItems == null) {
                    throw new NullPointerException();
                }
                menuItems.add(menuItem);
            }
        }
        result.put(currCategory, menuItems);
        return result;
    }

    public boolean addRestaurant(String name, String type, String url) {
        try {
            st.execute("INSERT INTO RESTAURANT VALUES (DEFAULT, '" + name + "','" + type + "','" + url + "');");
        } catch (Exception SQLException) {
            return false;
        }
        return true;
    }

    public boolean addRater(String email, String name, String joinDate, String type, int reputation) {
        try {
            st.execute("INSERT INTO RATER VALUES (DEFAULT, '" + email + "','" + name + "', '" + joinDate + "','" + type + "'," + Integer.toString(reputation) + ");");
        } catch (Exception SQLException) {
            return false;
        }
        return true;
    }


    public boolean addMenuItem(String name, String type, String category, String description, double price, int restaurantID) {
        try {
            st.execute("INSERT INTO MENUITEM VALUES (DEFAULT, '" + name + "','" + type + "','" + category + "','" + description + "'," + Double.toString(price) + "," + Integer.toString(restaurantID) + ");");
        } catch (Exception SQLException) {
            return false;
        }
        return true;
    }

    public boolean deleteFromMenuItem(int id) {
        try {
            st.execute("DELETE FROM MENUITEM WHERE itemID =  " + Integer.toString(id));
        } catch (Exception SQLException) {
            return false;
        }
        return true;
    }

    //Question f
    public ArrayList<Pair<Restaurant, Integer>> getTotalRestaurantReviews() {
        ArrayList<Pair<Restaurant, Integer>> result = new ArrayList<Pair<Restaurant, Integer>>();
        Restaurant tempRest = null;
        Pair tempPair = null;
        try {
            ResultSet rs = st.executeQuery("select rest.restaurantID, rest.name, rest.type, rest.url, count(rat.restaurantID) from restaurant rest left join rating rat on rat.restaurantID = rest.restaurantID group by rest.restaurantID order by rest.restaurantID");
            while (rs.next()) {
                tempRest = new Restaurant(rs.getString(2), rs.getString(3), rs.getString(4));
                tempRest.setRestaurantID(rs.getLong(1));
                tempPair = new Pair<Restaurant, Integer>(tempRest, rs.getInt(5));
                result.add(tempPair);
            }
        } catch (Exception SQLEXception) {

        }
        return result;
    }

    public ArrayList<Pair<Rater, Integer>> getTotalRaterReviews() {
        ArrayList<Pair<Rater, Integer>> result = new ArrayList<Pair<Rater, Integer>>();
        Rater tempRater = null;
        Pair tempPair = null;
        try {
            ResultSet rs = st.executeQuery("select rater.userID, rater.email, rater.name, rater.joinDate, rater.type, rater.reputation, count(rating.userID) from RATER rater \n" +
                    "left join Rating rating on rater.userID = rating.userID \n" +
                    "group by rater.userID order by rater.userID");

            while (rs.next()) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(rs.getDate(4));
                tempRater = new Rater(rs.getString(2), rs.getString(3), cal, rs.getString(4), rs.getInt(5));
                tempRater.setUserID(rs.getLong(1));
                tempPair = new Pair<Rater, Integer>(tempRater, rs.getInt(5));
                result.add(tempPair);
            }
        } catch (Exception SQLEXception) {

        }
        return result;
    }


}

