package sample.Timers;

import Database.DBConnectivity;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.joda.time.*;

public class LateTimer {

    String unpaidCustomer;

    DBConnectivity dbConnectivity = new DBConnectivity();
    Connection connection = dbConnectivity.getConnection();

    public void checkIsPaid() {

        try {
            DateTime currentDate = new org.joda.time.DateTime();
            Statement statement = connection.createStatement();

            String saleDateQuery = ("SELECT saleDate FROM sales WHERE isPaid = 'No'");
            ResultSet resultSet = statement.executeQuery(saleDateQuery);
            System.out.println(resultSet.next());
           // java.sql.Date saleDate = (Date) resultSet;

            DateTime saleDateJoda = new DateTime(resultSet); // Convert to a JODA object.

            if ((Days.daysBetween(currentDate, saleDateJoda)).isGreaterThan(Days.days(30))) {
                String customerQuery = ("SELECT customer FROM sales WHERE isPaid = 'No'");
                ResultSet resultSet1 = statement.executeQuery(customerQuery);
                unpaidCustomer = customerQuery;
                JOptionPane.showMessageDialog(null, "Customer " + unpaidCustomer + " is late for payments");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
