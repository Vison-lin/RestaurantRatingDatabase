package mDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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

                "CREATE TABLE " + RATER_TABLE_NAME +
                        "(" + RATER_PRIM_KEY_USERID + " SERIAL PRIMARY KEY, " +
                        RATER_EMAIL + " char(100), " +
                        RATER_NAME + " char(50), " +
                        RATER_JOIN_DATE + " date, " +
                        RATER_TYPE + " char(11) check ( " + RATER_TYPE + " = 'blog' OR " + RATER_TYPE + " = 'online' OR " + RATER_TYPE + " = 'food critic')," +
                        RATER_REPUTATION + " numeric check ( " + RATER_REPUTATION + " <=5 AND " + RATER_REPUTATION + " >= 1));" +

                        //RESTAURANT TABLE

                        "CREATE TABLE " + RESTAURANT_TABLE_NAME +
                        "(" + RESTAURANT_PRIM_KEY_RESTAURANTID + " char(5) PRIMARY KEY, " +
                        RESTAURANT_NAME + " char(50), " +
                        RESTAURANT_TYPE + " char(50), " +
                        RESTAURANT_URL + " char(100));" +

                        //RATING TABLE

                        "CREATE TABLE " + RATING_TABLE_NAME +
                        "(" + RATING_PRIM_KEY_USERID_FOREIGN_KEY + " SERIAL references " + RATER_TABLE_NAME + " ( " + RATER_PRIM_KEY_USERID + "), " +
                        RATING_PRIM_KEY_DATE_ADDED + " date, " +
                        RATING_PRICE + " numeric check ( " + RATING_PRICE + " <=5 AND " + RATING_PRICE + " >= 1), " +
                        RATING_FOOD + " numeric check (food <=5 AND food >= 1), " +
                        RATING_MOOD + " numeric check (mood <=5 AND mood >= 1), " +
                        RATING_STAFF + " numeric check ( " + RATING_STAFF + " <=5 AND " + RATING_STAFF + " >= 1)," +
                        RATING_COMMENTS + " char(500), " +
                        RATING_RESTAURANTID_FOREIGN_KEY + " char(5) references Restaurant(" + RESTAURANT_PRIM_KEY_RESTAURANTID + ")," +
                        "PRIMARY KEY ( " + RATING_PRIM_KEY_USERID_FOREIGN_KEY + ", " + RATING_PRIM_KEY_DATE_ADDED + "), " +
                        "UNIQUE( " + RATING_PRIM_KEY_USERID_FOREIGN_KEY + ", " + RATING_PRIM_KEY_DATE_ADDED + " ));" +

                        //LOCATION TABLE

                        "CREATE TABLE " + LOCATION_TABLE_NAME +
                        "(" + LOCATION_PRIM_KEY_LOCATIONID + " char(5) PRIMARY KEY, " +
                        LOCATION_FIRST_OPEN_DATE + " date, " +
                        LOCATION_MANAGER_NAME + " char(20), " +
                        LOCATION_PHONE_NUMBER + " char(10), " +
                        LOCATION_ADDRESS + " char(30), " +
                        LOCATION_RESTAURANTID_FOREIGN_KEY + " char(5) references " + RESTAURANT_TABLE_NAME + " ( " + RESTAURANT_PRIM_KEY_RESTAURANTID + " ));" +

                        //MENUITEM TABLE

                        "CREATE TABLE " + MENUITEM_TABLE_NAME +
                        "(" + MENUITEM_PRIM_KEY_ITEMID + " char(5) PRIMARY KEY, " +
                        MENUITEM_NAME + " char(20), " +
                        MENUITEM_TYPE + " char(8) check (type = 'food' OR type = 'beverage'), " +
                        MENUITEM_CATEGORY + " char(7) check ( " + MENUITEM_CATEGORY + " = 'starter' OR " + MENUITEM_CATEGORY + " = 'main' OR " + MENUITEM_CATEGORY + " = 'desert'), " +
                        MENUITEM_DESCRIPTION + " char(50), " +
                        MENUITEM_PRICE + " numeric(2), " +
                        MENUITEM_RESTAURANTID_FOREIGN_KEY + " char(5) references " + RESTAURANT_TABLE_NAME + "( " + RESTAURANT_PRIM_KEY_RESTAURANTID + " ));" +

                        //RATINGITEM TABLE

                        "CREATE TABLE " + RATINGITEM_TABLE_NAME +
                        "(" + RATINGITEM_PRIM_KEY_USERID + " char(5), " +
                        RATINGITEM_PRIM_KEY_JOIN_DATE + " date, " +
                        RATINGITEM_PRIM_KEY_ITEMID + " char(5) references " + MENUITEM_TABLE_NAME + " ( " + MENUITEM_PRIM_KEY_ITEMID + " ), " +
                        RATINGITEM_RATING + " numeric check ( " + RATINGITEM_RATING + " <=5 AND " + RATINGITEM_RATING + " >= 1)," +
                        RATINGITEM_COMMENTS + " char(500), " +
                        "PRIMARY KEY( " + RATINGITEM_PRIM_KEY_USERID + ", " + RATINGITEM_PRIM_KEY_JOIN_DATE + ", " + RATINGITEM_PRIM_KEY_ITEMID + "));");
        st.close();
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


}

