package sample.Timers;

import Database.DBConnectivity;
import javax.swing.*;
import java.sql.*;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;


public class LateTimer {

    String unpaidCustomer;

    DBConnectivity dbConnectivity = new DBConnectivity();
    Connection connection = dbConnectivity.getConnection();

    public void checkIsPaid() {

        try {
            int n = 0;
            String[] dates = new String[10];
            String date0 = null;
            DateTime currentDate = new org.joda.time.DateTime();
            Statement statement = connection.createStatement();

            String saleDateQuery = ("SELECT saleDate FROM sales WHERE isPaid = 'No'");
            ResultSet resultSet = statement.executeQuery(saleDateQuery);

            while (resultSet.next()) {

                date0 = dates[n];
                date0 = resultSet.getString(1);

                // Convert the string date0 to a joda date
                DateTime saleDate = DateTime.parse(date0, DateTimeFormat.forPattern("dd/MM/yy"));

                // Check the days between the sale date and current date
                int days = (Days.daysBetween(saleDate, currentDate).getDays());

                // If more than 30 days since sale date have passed
                if (days > 30) {
                    String customerQuery = ("SELECT customer FROM sales WHERE isPaid = 'No'");
                    ResultSet resultSet1 = statement.executeQuery(customerQuery);
                    resultSet1.next();
                    unpaidCustomer = resultSet1.getString(1);
                    JOptionPane.showMessageDialog(null, "Customer " + unpaidCustomer + " is late for payments");
                }
                n++;
            }


        } catch (SQLException e) {
            System.out.println("Late Payments have been checked");
        }


    }

}
