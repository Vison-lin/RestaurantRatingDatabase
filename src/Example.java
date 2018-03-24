import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Example {

    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        //connectoin
        String dbName = "db1";//todo your dB name
        String url = "jdbc:postgresql://localhost:5432/";//todo your postresql url

        //authentication
        Properties props = new Properties();
        props.setProperty("user", "#####");//todo your username
        props.setProperty("password", "######");//todo your password
        Connection db = DriverManager.getConnection(url + dbName, props);

        Statement st = db.createStatement();
        //todo Write the sql below using st:

        st.close();


    }

}