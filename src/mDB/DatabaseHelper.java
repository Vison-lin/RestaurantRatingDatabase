package mDB;

import Model.Location;
import Model.MenuItem;
import Model.Rater;
import Model.Restaurant;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
    final static String LOCATION_HOUR_OPEN = "hour_open";
    final static String LOCATION_HOUR_CLOSE = "hour_close";
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
                        LOCATION_HOUR_OPEN + " TIME, " +
                        LOCATION_HOUR_CLOSE + " TIME, " +
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
     * @return Pair<Restaurant , ArrayList < Location>> where the key is that restaurant and the value is an ArrayList that contains that restaurant's all location
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
            Time hourOpen = rs.getTime(10);
            Time hourClose = rs.getTime(11);

            restaurant = new Restaurant(restName, restType, restUrl);
            restaurant.setRestaurantID(restID);
            Location location = new Location(locationOpenDate, locationMgrName, locationPhone, locationAddr, restID, hourOpen, hourClose);
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
            Time hourOpen = rs.getTime(10);
            Time hourClose = rs.getTime(11);
            Location location = new Location(locationOpenDate, locationMgrName, locationPhone, locationAddr, restaurantID, hourOpen, hourClose);
            location.setLocationID(locationID);
            locations.add(location);
        }
        return new Pair<>(restaurant, locations);

    }

    /**
     * b
     *
     * @param restaurantID the id of the restaurant of the menu
     * @return HashMap<String , ArrayList < MenuItem>>, where key is the type of the items, can only be: main, starter, or Fires.
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

    /**
     * c
     *
     * @param category the prim key of that restaurant
     * @return Pair<Restaurant , ArrayList < Location>> where the key is that restaurant and the value is an ArrayList that contains that restaurant's all location
     * @throws SQLException
     */
    public HashMap<Restaurant, ArrayList<Location>> displayRestaurantLocationMgr(String category) throws SQLException {
        ResultSet rs = st.executeQuery("SELECT * FROM " + RESTAURANT_TABLE_NAME + " AS R JOIN " + LOCATION_TABLE_NAME + " AS L USING (" + RESTAURANT_PRIM_KEY_RESTAURANTID + ") WHERE R." + RESTAURANT_TYPE + " = '" + category + "' ORDER BY R." + RESTAURANT_PRIM_KEY_RESTAURANTID + ";");
        HashMap<Restaurant, ArrayList<Location>> result = new HashMap<>();
        ArrayList<Location> locations = null;
        Restaurant currResturant = null;
        while (rs.next()) {
            //restaurant
            Long restID = Long.valueOf(rs.getString(1));
            String restName = rs.getString(2);
            String restType = rs.getString(3);
            String restUrl = rs.getString(4);
            Restaurant restaurant = new Restaurant(restName, restType, restUrl);
            restaurant.setRestaurantID(restID);

            //location
            Long locationID = Long.valueOf(rs.getString(5));
            Calendar locationOpenDate = Calendar.getInstance();
            locationOpenDate.setTime(rs.getDate(6));
            String locationMgrName = rs.getString(7);
            String locationPhone = rs.getString(8);
            String locationAddr = rs.getString(9);
            Time hourOpen = rs.getTime(10);
            Time hourClose = rs.getTime(11);

            Location location = new Location(locationOpenDate, locationMgrName, locationPhone, locationAddr, restID, hourOpen, hourClose);
            location.setLocationID(locationID);


            if (currResturant == null) {//init
                currResturant = restaurant;

                locations = new ArrayList<>();
            }
            if (restID == currResturant.getRestaurantID()) {
                locations.add(location);
            } else {//new restaurant
                result.put(currResturant, locations);
                currResturant = restaurant;

                locations = new ArrayList<>();
                locations.add(location);
            }


        }
        result.put(currResturant, locations);
        return result;

    }

    /**
     * d
     *
     * @param restaurantID the id of the restaurant that the user searches for
     * @return Pair<MenuItem , Pair < Restaurant , ArrayList < Location>>>
     * @throws SQLException
     */
    public Pair<MenuItem, Pair<Restaurant, ArrayList<Location>>> expensiveItemInfo(long restaurantID) throws SQLException {
        ResultSet rs = st.executeQuery("SELECT * FROM ((" + MENUITEM_TABLE_NAME + " AS M JOIN " + RESTAURANT_TABLE_NAME + " AS R USING (" + RESTAURANT_PRIM_KEY_RESTAURANTID + ")) JOIN " + LOCATION_TABLE_NAME + " AS L USING (" + RESTAURANT_PRIM_KEY_RESTAURANTID + ")) WHERE M." + MENUITEM_RESTAURANTID_FOREIGN_KEY + " = " + restaurantID + " AND M." + MENUITEM_PRICE + " = (SELECT MAX(" + MENUITEM_PRICE + ") FROM " + MENUITEM_TABLE_NAME + ");");
        Restaurant restaurant = null;
        MenuItem menuItem = null;
        ArrayList<Location> locations = null;
        if (rs.next()) {
            long restID = rs.getLong(1);
            long itemID = rs.getLong(2);
            String manuItemName = rs.getString(3);
            String itemType = rs.getString(4);
            String category = rs.getString(5);
            String description = rs.getString(6);
            float price = rs.getFloat(7);
            String restName = rs.getString(8);
            String restType = rs.getString(9);
            String resURL = rs.getString(10);
            long locationID = rs.getLong(11);
            Calendar firstOpen = Calendar.getInstance();
            firstOpen.setTime(rs.getDate(12));
            String manager = rs.getString(13);
            String phone = rs.getString(14);
            String addr = rs.getString(15);
            Time hourOpen = rs.getTime(16);
            Time hourClose = rs.getTime(17);
            restaurant = new Restaurant(restName, restType, resURL);
            restaurant.setRestaurantID(restID);
            menuItem = new MenuItem(manuItemName, itemType, category, price, restID, description);
            menuItem.setItemID(itemID);
            locations = new ArrayList<>();
            Location location = new Location(firstOpen, manager, phone, addr, restID, hourOpen, hourClose);
            location.setLocationID(locationID);
            locations.add(location);
        }
        while (rs.next()) {
            long locationID = rs.getLong(11);
            Calendar firstOpen = Calendar.getInstance();
            firstOpen.setTime(rs.getDate(12));
            String manager = rs.getString(13);
            String phone = rs.getString(14);
            String addr = rs.getString(15);
            Time hourOpen = rs.getTime(16);
            Time hourClose = rs.getTime(17);
            Location location = new Location(firstOpen, manager, phone, addr, restaurantID, hourOpen, hourClose);
            location.setLocationID(locationID);
            locations.add(location);
        }
        return new Pair<MenuItem, Pair<Restaurant, ArrayList<Location>>>(menuItem, new Pair<>(restaurant, locations));
    }


    /**
     * e
     *
     * @return ArrayList<String [ ]>, where result[0] = restaurantType, result[1] = category, result[2] = averagePriceOfEachCombination;
     * @throws SQLException
     */
    public ArrayList<String[]> averagePrice() throws SQLException {
        ResultSet rs = st.executeQuery("SELECT RESTTYPE, " + MENUITEM_CATEGORY + ", AVG(" + MENUITEM_PRICE + ") FROM (SELECT R." + RESTAURANT_TYPE + " AS RESTTYPE, M.* FROM " + RESTAURANT_TABLE_NAME + " AS R, " + MENUITEM_TABLE_NAME + " AS M WHERE R." + RESTAURANT_PRIM_KEY_RESTAURANTID + " = M." + MENUITEM_RESTAURANTID_FOREIGN_KEY + " ORDER BY RESTTYPE, " + MENUITEM_CATEGORY + ") AS TEMP GROUP BY RESTTYPE, " + MENUITEM_CATEGORY + ";");
        ArrayList<String[]> result = new ArrayList<>();
        while (rs.next()) {
            String restType = rs.getString(1);
            String category = rs.getString(2);
            Float avgPriceInFloat = rs.getFloat(3);
            BigDecimal bd = new BigDecimal(Float.toString(avgPriceInFloat));
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            avgPriceInFloat = bd.floatValue();
            String avgPrice = String.valueOf(avgPriceInFloat);
            String[] set = new String[3];
            set[0] = restType;
            set[1] = category;
            set[2] = avgPrice;
            result.add(set);
        }
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
        } catch (Exception SQLException) {

        }
        return result;
    }

    //Question g
    public ArrayList<ArrayList<String>> getRestaurantsWithNoRatingInJan() {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        int i = 0;
        try {
            ResultSet rs = st.executeQuery("create view inJan as\n" +
                    "select * from rating where dateadded > '2014-12-31' and dateadded < '2015-02-01';\n" +
                    "\n" +
                    "create view restInfoInJan as\n" +
                    "select j.restaurantID, r.name, r.type from inJan j\n" +
                    "left join Restaurant r on r.restaurantID = j.restaurantID;\n" +
                    "\n" +
                    "select name, phoneNumber, type from restInfoInJan j\n" +
                    "left join Location l on l.restaurantID = j.restaurantID;\n");
            while (rs.next()) {
                result.get(i).add(rs.getString(1));
                result.get(i).add(rs.getString(2));
                result.get(i).add(rs.getString(3));
                i += 1;
            }
        } catch (Exception SQLException) {

        }
        try {
            st.execute("drop view inJan");
        } catch (Exception SQLException) {

        }

        try {
            st.execute("drop view restInfoInJan");
        } catch (Exception SQLException) {

        }
        return result;
    }



//Question k

    /**
     * Find the names, join‐date and reputations of the raters that give the highest overall rating, in
     * terms of the Food and the Mood of restaurants. Display this information together with the
     * names of the restaurant and the dates the ratings were done
     *
     * @return [name,joindate,reputation,name,dateadded]
     */

 public ArrayList<ArrayList<String>> getK(){
     ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
     int i =0;
     try {
         ResultSet rs = st.executeQuery("select r.name, r.joindate, r.reputation, z.name, x.dateadded\n" +
                 "from rater r, restaurant z, rating x\n" +
                 "where r.userid = x.userid and x.restaurantid = z.restaurantid and x.food=(select max(xx.food)\n" +
                 "from rating xx) and x.mood =(select max(xx.mood)\n" +
                 " from rating xx)\n" +
                 "order by x.dateadded");
         while (rs.next()) {
             result.get(i).add(rs.getString(1));
             result.get(i).add(rs.getString(2));
             result.get(i).add(rs.getString(3));
             result.get(i).add(rs.getString(4));
             result.get(i).add(rs.getString(5));
             i += 1;
         }
     } catch (Exception SQLException) {

     }

    return result;


 }


    //Question l

    /**
     * Find the names and reputations of the raters that give the highest overall rating, in terms of the
     * Food or the Mood of restaurants. Display this information together with the names of the
     * restaurant and the dates the ratings were done.
     *
     * @return [name,reputation,name,dateadded]
     */

    public ArrayList<ArrayList<String>> getL(){
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        int i =0;
        try {
            ResultSet rs = st.executeQuery("select r.name, r.reputation, z.name, x.dateadded\n" +
                    "from rater r, restaurant z, rating x\n" +
                    "where r.userid = x.userid and x.restaurantid = z.restaurantid and (x.food=(select max(xx.food)\n" +
                    "from rating xx) or x.mood =(select max(xx.mood)\n" +
                    "from rating xx))\n" +
                    "order by x.dateadded");
            while (rs.next()) {
                result.get(i).add(rs.getString(1));
                result.get(i).add(rs.getString(2));
                result.get(i).add(rs.getString(3));
                result.get(i).add(rs.getString(4));

                i += 1;
            }
        } catch (Exception SQLException) {

        }

        return result;


    }
    //Question m

    /**
     * Find the names and reputations of the raters that rated a specific restaurant (say Restaurant Z)
     * the most frequently. Display this information together with their comments and the names and
     * prices of the menu items they discuss.
     *
     * @return [name,reputation,name,price,comments]
     */

    public ArrayList<ArrayList<String>> getM(String name){
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        int i =0;
        try {
            ResultSet rs = st.executeQuery("create view rater_rating\n" +
                    "as select  r.userid,count(r.restaurantid)cc\n" +
                    "from rating r\n" +
                    "where r.restaurantid ="+name+"\n" +
                    "group by r.userid;\n" +
                    "\n" +
                    "select r.name, r.reputation,x.name,x.price,xx.comments\n" +
                    "from rater r,menuitem x, ratingitem xx\n" +
                    "where exists(select rr.userid \n" +
                    "from rater_rating rr\n" +
                    "where r.userid= rr.userid and rr.cc=(select max(rrr.cc)\n" +
                    "from rater_rating rrr))\n" +
                    "and r.userid=xx.userid and xx.itemid=x.itemid and x.restaurantid="+name+";\n");
            while (rs.next()) {
                result.get(i).add(rs.getString(1));
                result.get(i).add(rs.getString(2));
                result.get(i).add(rs.getString(3));
                result.get(i).add(rs.getString(4));
                result.get(i).add(rs.getString(5));

                i += 1;
            }
        } catch (Exception SQLException) {

        }

        try {
            st.execute("drop view rater_rating;");
        } catch (Exception SQLException) {

        }

        return result;


    }

    //Question n

    /**
     * Find the names and emails of all raters who gave ratings that are lower than that of a rater with
     * a name called John, in terms of the combined rating of Price, Food, Mood and Staff. (Note that
     * there may be more than one rater with this name).
     *
     * @return [name,email]
     */

    public ArrayList<ArrayList<String>> getN(){
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        int i =0;
        try {
            ResultSet rs = st.executeQuery("create view john\n" +
                    "as select (sum(x.price)+ sum(x.food)+sum(x.mood)+sum(x.staff))as total\n" +
                    "from rating x,rater r\n" +
                    "where x.userid=r.userid and r.name = 'John Smith';\n" +
                    "\n" +
                    "\n" +
                    "select  r.name,r.email\n" +
                    "from rater r, rating x,john j\n" +
                    "where r.userid=x.userid and j.total > ( select (sum(xx.price)+sum(xx.food)+sum(xx.mood)+sum(xx.staff)) as total\n" +
                    "from rating xx\n" +
                    "where xx.userid = x.userid);\n");
            while (rs.next()) {
                result.get(i).add(rs.getString(1));
                result.get(i).add(rs.getString(2));

                i += 1;
            }
        } catch (Exception SQLException) {

        }
        try {
            st.execute( "drop view john;");
        } catch (Exception SQLException) {

        }


        return result;


    }






}