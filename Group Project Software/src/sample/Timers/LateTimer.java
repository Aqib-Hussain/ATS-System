package sample.Timers;

import Database.DBConnectivity;
import sample.AlertBox;
import sample.Staff.SystemAdmin;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LateTimer {

    String unpaidCustomer;

    DBConnectivity dbConnectivity = new DBConnectivity();
    Connection connection = dbConnectivity.getConnection();

    public Object checkIsPaid() {

        // Comparison string to check for a no value
        String customerPaid = "no";

        try {
            // Establish a database connection
            Statement statement = connection.createStatement();
            // SQL query to check if a customer has paid
            String query = "SELECT isPaid FROM sales";
            ResultSet resultSet = statement.executeQuery(query);
            if (customerPaid.equals(resultSet.getString("isPaid"))) {

                //Check if the customer has paid
                final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.scheduleAtFixedRate(this::latePayment, 10, 10, TimeUnit.SECONDS);
                unpaidCustomer = String.valueOf(resultSet);
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    public void latePayment() {
//         AlertBox alertBox = new AlertBox();
//        ResultSet unpaidCustomerID = null;
//
//        // Establish a database connection
//        try {
//            Statement statement = connection.createStatement();
//            String query = "SELECT CustomerID FROM sales WHERE isPaid = 'no'";
//            ResultSet resultSet = statement.executeQuery(query);
//            unpaidCustomerID = resultSet;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        // Alert that customer is late for payment
        JOptionPane.showMessageDialog(null, "Customer " + unpaidCustomer + " is late for payments");
    }
}
