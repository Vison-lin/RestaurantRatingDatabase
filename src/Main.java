import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {

    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        //url
        String url = "jdbc:postgresql://localhost:5432/postgres";//todo your dB url
        Properties props = new Properties();
        props.setProperty("user", "postgres");//todo your username
        props.setProperty("password", "");//todo your password
        Connection conn = DriverManager.getConnection(url, props);
    }

}
