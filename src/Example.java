import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Example {

    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        //connectoin
        String dbName = "postgres";//todo your dB name
        String url = "jdbc:postgresql://localhost:5432/";//todo your postresql url

        //authentication
        Properties props = new Properties();
        props.setProperty("user", "project");//todo your username
        props.setProperty("password", "123");//todo your password
        Connection db = DriverManager.getConnection(url + dbName, props);

        Statement st = db.createStatement();
        //todo Write the sql below using st:
        st.execute("CREATE TABLE RATER\n" +
                "(userID char(5) PRIMARY KEY,\n" +
                "email char(100),\n" +
                "name char(50),\n" +
                "joinDate date,\n" +
                "type char(11) check (type = 'blog' OR type = 'online' OR type = 'food critic'),\n" +
                "reputation numeric check (reputation <=5 AND reputation >= 1));\n" +
                "\n" +
                "CREATE TABLE RESTAURANT\n" +
                "(restaurantID char(5) PRIMARY KEY,\n" +
                "name char(50),\n" +
                "type char(50),\n" +
                "url char(100));\n" +
                "\n" +
                "CREATE TABLE RATING\n" +
                "(userID char(5) references RATER(userID) ,\n" +
                "dateAdded date,\n" +
                "price numeric check (price <=5 AND price >= 1),\n" +
                "food numeric check (food <=5 AND food >= 1),\n" +
                "mood numeric check (mood <=5 AND mood >= 1),\n" +
                "staff numeric check (staff <=5 AND staff >= 1),\n" +
                "comments char(500),\n" +
                "restaurantID char(5) references Restaurant(restaurantID),\n" +
                "PRIMARY KEY (userID, dateAdded),\n" +
                "UNIQUE(userID, dateAdded));\n" +
                "\n" +
                "\n" +
                "CREATE TABLE LOCATION\n" +
                "(locationID char(5) PRIMARY KEY,\n" +
                "firstOpenDate date, \n" +
                "managerName char(20),\n" +
                "phoneNumber char(10),\n" +
                "address char(30),\n" +
                "restaurantID char(5) references RESTAURANT(restaurantID));\n" +
                "\n" +
                "CREATE TABLE MENUITEM\n" +
                "(itemID char(5) PRIMARY KEY,\n" +
                "name char(20),\n" +
                "type char(8) check (type = 'food' OR type = 'beverage'),\n" +
                "category char(7) check (category = 'starter' OR category = 'main' OR category = 'desert'),\n" +
                "description char(50),\n" +
                "price numeric(2),\n" +
                "restaurantID char(5) references RESTAURANT(restaurantID));\n" +
                "\n" +
                "CREATE TABLE RATINGITEM\n" +
                "(userID char(5),\n" +
                "joinDate date,\n" +
                "itemID char(5) references MENUITEM(itemID),\n" +
                "rating numeric check (rating <=5 AND rating >= 1),\n" +
                "comments char(500),\n" +
                "PRIMARY KEY(userId, joinDate, itemID));");
        st.close();


    }

}