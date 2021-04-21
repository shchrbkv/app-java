package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    private static final String URL = "jdbc:postgresql://hattie.db.elephantsql.com:5432/";
    private static final String USERNAME = "xxepmrym";
    private static final String PASSWORD = "vp0n6VUDf0K0h2u3HP85oRRwRiS_tK8Q";

    public synchronized Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
