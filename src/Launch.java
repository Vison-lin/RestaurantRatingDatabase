import Model.Location;
import Model.Restaurant;
import mDB.DatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Launch {

    public static void main(String args[]) throws ClassNotFoundException, SQLException {

        String dbName = "postgres";
        String url = "jdbc:postgresql://localhost:5432/";
        String userName = "project";
        String password = "123";
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(dbName, url, userName, password);
        HashMap<Restaurant, ArrayList<Location>> r = databaseHelper.displayRestaurantLocationMgr("Italian");
        Iterator<Restaurant> i = r.keySet().iterator();
        while (i.hasNext()) {
            Restaurant rr = i.next();
            System.out.println(rr.getName() + "=================");
            Iterator<Location> iterator = r.get(rr).iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next().getAddress());
            }
        }


    }

}