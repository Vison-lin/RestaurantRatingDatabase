import java.sql.SQLException;

public class Launch {

    public static void main(String args[]) throws ClassNotFoundException, SQLException {

        String dbName = "postgres";
        String url = "jdbc:postgresql://localhost:5432/";
        String userName = "project";
        String password = "123";
        DatabaseHelper DatabaseHelper = new DatabaseHelper(dbName, url, userName, password);

    }

}