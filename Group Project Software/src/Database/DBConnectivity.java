package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectivity {
public  Connection connection;

    // Initialises connection to the mysql server
    public Connection getConnection(){

        String dbName="ats";
        String userName="root";
        String password="password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection= DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,userName,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
