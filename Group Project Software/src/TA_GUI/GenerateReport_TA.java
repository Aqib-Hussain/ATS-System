package TA_GUI;

import Database.DBConnectivity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Blank;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class GenerateReport_TA
{
    // Database
    static DBConnectivity dbConnectivity = new DBConnectivity();
    static Connection connection = dbConnectivity.getConnection();

    // ResultSet
    static ResultSet calculateReportResultSet;

    public static void display(String title) {


        DatePicker datePicker1 = new DatePicker();
        String pattern = "yyyy-MM-dd";

        DatePicker datePicker2 = new DatePicker();
        String pattern1 = "yyyy-MM-dd";

        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        window.setMinHeight(350);
        window.setResizable(false);

        // Labels
        Label selectTimeFrame = new Label("Select the time frame");
        selectTimeFrame.setPadding(new Insets(10, 0, 10, 0));
        Label to = new Label("To");
        to.setPadding(new Insets(10, 0, 10, 0));

        // Buttons
        Button close = new Button("Close");
        close.getStyleClass().add("button-exit");
        close.setMinSize(75, 25);
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.close();
            }
        });

        Button generate = new Button("Generate");
        generate.getStyleClass().add("button-login");
        generate.setMinSize(75, 25);
        generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalDate date1 = datePicker1.getValue();
                LocalDate date2 = datePicker2.getValue();
                System.out.println(date1);
                System.out.println(date2);
                GenerateReport_TA.calculateReport(date1, date2, getCurrentTA());
                ViewReports_TA.display(title);
            }
        });

        // Layout
        VBox top_layout = new VBox();
        top_layout.setAlignment(Pos.CENTER);
        top_layout.setPadding(new Insets(0, 0, 10, 0));
        top_layout.getChildren().add(selectTimeFrame);

        VBox center_layout = new VBox(15);
        center_layout.setAlignment(Pos.CENTER);
        center_layout.setSpacing(10);
        center_layout.getChildren().add(datePicker1);
        center_layout.getChildren().add(to);
        center_layout.setAlignment(Pos.CENTER);
        center_layout.setSpacing(10);
        center_layout.getChildren().add(datePicker2);

        HBox bottom_layout = new HBox();
        bottom_layout.setPadding(new Insets(10, 0, 0, 0));
        bottom_layout.setSpacing(150);
        bottom_layout.setAlignment(Pos.BASELINE_LEFT);
        bottom_layout.getChildren().addAll(generate, close);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10, 10, 10, 10));
        root_layout.setCenter(center_layout);
        root_layout.setBottom(bottom_layout);
        root_layout.setTop(top_layout);

        // Scene
        Scene scene = new Scene(root_layout);
        scene.getStylesheets().add("Stylesheet.css");
        window.setScene(scene);

        // Start window
        window.showAndWait();
    }

    private static String getCurrentTA()
    {
        String name = "";
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to get the current TA
            String query = "SELECT name FROM staff WHERE StaffType = 'Travel Advisor' AND status = 'loggedIn'";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next())
            {
                name = resultSet.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    // Calculate Report values
    public static void calculateReport(LocalDate date1, LocalDate date2, String name)
    {
        int totalUSD;
        int totalLocal = 0;
        int totalTax = 0;
        int totalOtherTax = 0;
        int totalAmount;
        int totalCommissions;

        totalAmount = totalLocal + totalTax + totalOtherTax;

        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query
            String query = "SELECT * FROM sales WHERE saleDate BETWEEN CAST('"+ date1 + "' AS DATE) AND CAST('" + date2 + "' AS DATE) AND soldBy = '"+name+"'";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next())
            {
                calculateReportResultSet = resultSet;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ResultSet getCalculateReportResultSet() {
        return calculateReportResultSet;
    }
}
