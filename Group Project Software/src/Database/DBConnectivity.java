package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectivity {
public  Connection connection;

    public Connection getConnection(){

        String dbName="ats";
        String userName="root";
        String password="AB+J#bveL3Sm35j9KXmg_@mE^xny7e";

        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,userName,password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
