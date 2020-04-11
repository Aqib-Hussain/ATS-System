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
import javafx.util.StringConverter;
import sample.Blank;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GenerateReport_TA
{
    // Database
    static DBConnectivity dbConnectivity = new DBConnectivity();
    static Connection connection = dbConnectivity.getConnection();

    // ResultSet
    static ResultSet calculateReportResultSet;

    // Radio buttons
    static private RadioButton interline_radioButton = new RadioButton("Interline");
    static private RadioButton domestic_radioButton = new RadioButton("Domestic");

    public static void display(String title) {


        DatePicker datePicker1 = new DatePicker();
        datePicker1.setConverter(new StringConverter<LocalDate>()
        {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            {
                datePicker1.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date)
            {
                if(date != null)
                {
                    return dateFormatter.format(date);
                }
                else
                {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string)
            {
                if(string != null && !string.isEmpty())
                {
                    return LocalDate.parse(string, dateFormatter);
                }
                else
                {
                    return null;
                }
            }
        });

        DatePicker datePicker2 = new DatePicker();
        datePicker2.setConverter(new StringConverter<LocalDate>()
        {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            {
                datePicker2.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date)
            {
                if(date != null)
                {
                    return dateFormatter.format(date);
                }
                else
                {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string)
            {
                if(string != null && !string.isEmpty())
                {
                    return LocalDate.parse(string, dateFormatter);
                }
                else
                {
                    return null;
                }
            }
        });

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

        Label ticketType_label = new Label("Ticket Type");

        // Radio buttons
        ToggleGroup toggleGroup = new ToggleGroup();
        interline_radioButton.setToggleGroup(toggleGroup);
        domestic_radioButton.setToggleGroup(toggleGroup);

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
            public void handle(ActionEvent actionEvent)
            {
                if(datePicker1.getValue() != null && datePicker2.getValue() != null)
                {
                    String ticketType = "";
                    if(domestic_radioButton.isSelected())
                    {
                        ticketType = "Domestic";
                    }
                    else
                    {
                        ticketType = "Interline";
                    }
                    GenerateReport_TA.calculateReport(datePicker1.getValue(), datePicker2.getValue(), getCurrentTA(), ticketType);
                    ViewReports_TA.display(title);

                }
                else
                {
                    System.out.println("Error bruh");
                }
            }
        });

        // Layout
        HBox radio_layout = new HBox(10);
        radio_layout.setAlignment(Pos.CENTER);
        radio_layout.getChildren().addAll(domestic_radioButton, interline_radioButton);

        VBox top_layout = new VBox();
        top_layout.setAlignment(Pos.CENTER);
        top_layout.setPadding(new Insets(0, 0, 10, 0));
        top_layout.getChildren().addAll(selectTimeFrame, radio_layout);

        VBox center_layout = new VBox(15);
        center_layout.setAlignment(Pos.CENTER);
        center_layout.getChildren().addAll(datePicker1, to, datePicker2);

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
    public static void calculateReport(LocalDate date1, LocalDate date2, String name, String ticketType)
    {
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
            String query = "SELECT * FROM sales WHERE saleDate BETWEEN CAST('"+ date1 + "' AS DATE) AND CAST('" + date2 + "' AS DATE) AND soldBy = '"+name+"' AND ticketType = '"+ticketType+"'";
            ResultSet resultSet = statement.executeQuery(query);
            calculateReportResultSet = resultSet;

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ResultSet getCalculateReportResultSet() {
        return calculateReportResultSet;
    }
}
