package OM_GUI;

import Database.DBConnectivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerList
{
    // Database
    static DBConnectivity dbConnectivity = new DBConnectivity();
    static Connection connection = dbConnectivity.getConnection();

    // Table View
    static TableView<Customer> table;

    public static void display(String title)
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setHeight(500);
        window.setWidth(750);
        window.setResizable(false);

        // Labels
        Label page_info = new Label("Customers");
        page_info.getStyleClass().add("label-title");

        // Table
        TableColumn<Customer, String> IDColumn = new TableColumn<>("ID");
        IDColumn.setMinWidth(100);
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(100);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Customer, Double> discount = new TableColumn<>("Discount");
        discount.setMinWidth(100);
        discount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        table = new TableView<>();
        table.setItems(getCustomers());
        table.getColumns().addAll(IDColumn, nameColumn, typeColumn, discount);

        // Buttons
        Button editStatus = new Button("Edit Status");
        editStatus.setMinSize(140,25);

        Button editDiscount = new Button("Edit Discount");
        editDiscount.setMinSize(140,25);

        Button close = new Button("Close");
        close.getStyleClass().add("button-exit");
        close.setMinSize(75,25);
        close.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                window.close();
            }
        });

        // Layout
        VBox top_layout = new VBox();
        top_layout.setAlignment(Pos.CENTER);
        top_layout.getChildren().add(page_info);
        top_layout.setPadding(new Insets(0,0,20,0));

        VBox list_layout = new VBox(50);
        list_layout.setAlignment(Pos.CENTER);
        list_layout.getChildren().add(table);
        list_layout.setPadding(new Insets(0,0,20,0));

        HBox button_layout = new HBox(30);
        button_layout.setAlignment(Pos.CENTER);
        button_layout.getChildren().addAll(editStatus, editDiscount);
        button_layout.setPadding(new Insets(0,0,20,0));

        BorderPane center_layout = new BorderPane();
        center_layout.setCenter(list_layout);
        center_layout.setBottom(button_layout);

        HBox bottom_layout = new HBox(50);
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().addAll(close);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10, 10, 10, 10));
        root_layout.setTop(top_layout);
        root_layout.setCenter(center_layout);
        root_layout.setBottom(bottom_layout);

        // Scene
        Scene scene = new Scene(root_layout);
        scene.getStylesheets().add("Stylesheet.css");
        window.setScene(scene);

        // Start window
        window.showAndWait();
    }

    public static ObservableList<Customer> getCustomers()
    {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to find matching customers
            String query = "SELECT * FROM customer";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                customers.add(new Customer(resultSet.getString("ID"),
                        resultSet.getString("name"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("type"),
                        resultSet.getDouble("discount") ));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return customers;
    }

    public static void refreshTable()
    {

    }
}

