import Model.Location;
import Model.MenuItem;
import Model.Restaurant;
import javafx.util.Pair;
import mDB.DatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class Launch {

    public static void main(String args[]) throws ClassNotFoundException, SQLException {

        String dbName = "postgres";
        String url = "jdbc:postgresql://localhost:5432/";
        String userName = "project";
        String password = "123";
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(dbName, url, userName, password);
        Pair<MenuItem, Pair<Restaurant, ArrayList<Location>>> d = databaseHelper.expensiveItemInfo(1);
        System.out.println(d.getKey() + "====");
        System.out.println((d.getValue().getKey().getName()) + "!!!!!");
        Iterator<Location> locationIterator = d.getValue().getValue().iterator();
        while (locationIterator.hasNext()) {
            Location curr = locationIterator.next();
            System.out.println(curr.getAddress());
        }


        //        HashMap<Restaurant, ArrayList<Location>> c = databaseHelper.displayRestaurantLocationMgr("Itaian");
//        Iterator<Restaurant> iterator = c.keySet().iterator();
//        while(iterator.hasNext()){
//            Restaurant curr = iterator.next();
//            ArrayList<Location> locations = c.get(curr);
//            if (locations != null) {
//                Iterator<Location> iter = locations.iterator();
//                while(iter.hasNext()){
//                    Location currLoc = iter.next();
//                    System.out.println(currLoc.getFirstOpenDate().getTime().toString() + "|"+currLoc.getManagerName());
//                }
//            }
//
//        }


//        HashMap<String, ArrayList<MenuItem>> b = databaseHelper.fullMenu(2);
//        Iterator<String> iterator = b.keySet().iterator();
//
////        ArrayList<MenuItem> starters = b.get("starter");
////        System.out.println(starters.size());
////        ArrayList<MenuItem> mains = b.get("main");
////        System.out.println(mains.size());
//        ArrayList<MenuItem> deserts = b.get("desert");
//        System.out.println(deserts.size());


//        Pair<Restaurant, ArrayList<Location>> a =  databaseHelper.displayRestaurantInfo(5);
//        Restaurant restaurant = a.getKey();
//        ArrayList<Location> locations = a.getValue();
//        System.out.println(restaurant.getName()+"   "+restaurant.getType()+"  "+restaurant.getUrl());
//        Iterator<Location>iterator = locations.iterator();
//        while(iterator.hasNext()){
//            Location location = iterator.next();
//            System.out.println(location.getAddress() + "    " + location.getManagerName() + "   " + location.getPhoneNumber()
//            +"  " + location.getFirstOpenDate().getTime().toString() + "    " + location.getHourOpen().toString() + "   " +
//            location.getHourClose().toString());
//        }

//        ArrayList<String[]> a = databaseHelper.averagePrice();
//        Iterator<String[]> iterator = a.iterator();
//        while (iterator.hasNext()) {
//            String[] temp = iterator.next();
//            System.out.println(temp[0] + "    |   " + temp[1] + "    |   " + temp[2]);
//        }

//        HashMap<Restaurant, ArrayList<Location>> r = databaseHelper.displayRestaurantLocationMgr("Italian");
//        Iterator<Restaurant> i = r.keySet().iterator();
//        while (i.hasNext()) {
//            Restaurant rr = i.next();
//            System.out.println(rr.getName() + "=================");
//            Iterator<Location> iterator = r.get(rr).iterator();
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next().getAddress());
//            }
//        }


    }

}