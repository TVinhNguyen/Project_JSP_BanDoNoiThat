package DB;

import java.sql.Connection;
import java.sql.DriverManager;
public class Database {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlch_noi_that", "root", "");
            return conn;
        }
        catch (Exception e) {e.printStackTrace();}
        return null;
    }
}
