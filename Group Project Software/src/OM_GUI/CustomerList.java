package OM_GUI;

import Database.DBConnectivity;
import TA_GUI.SelectACustomerAlert;
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
import javafx.scene.layout.GridPane;
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
    static private TableView<Customer> table;

    // Layouts
    static BorderPane main_root_layout = new BorderPane();
    static BorderPane edit_root_layout = new BorderPane();

    // Scenes
    static Scene main_scene = new Scene(main_root_layout);
    static Scene edit_scene = new Scene(edit_root_layout);

    // Window
    static Stage window = new Stage();

    // Customers
    static Customer selectedCustomer;
    static ObservableList<Customer> customers = FXCollections.observableArrayList();

    // Labels
    static Label edit_page_info = new Label();

    // TextBoxes
    static TextField name_textField = new TextField();
    static TextField address_textField = new TextField();
    static TextField phoneNo_textField = new TextField();
    static TextField type_textField = new TextField();

    public static void display(String title)
    {
        // Window takes priority until taken care of
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
        nameColumn.setMinWidth(120);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(100);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        table = new TableView<>();
        table.setItems(getCustomers());
        table.setEditable(true);
        table.getColumns().addAll(IDColumn, nameColumn, typeColumn);

        // Buttons
        Button editDetails = new Button("Edit Status");
        editDetails.setMinSize(140,25);
        editDetails.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!(table.getSelectionModel().isEmpty()))
                {
                    selectedCustomer = table.getSelectionModel().getSelectedItem();
                    edit_page_info.setText("Editing details for " + selectedCustomer.getName());
                    name_textField.setText(selectedCustomer.getName());
                    address_textField.setText(selectedCustomer.getAddress());
                    phoneNo_textField.setText(selectedCustomer.getPhoneNumber());
                    type_textField.setText(selectedCustomer.getType());
                    window.setScene(edit_scene);
                }
                else
                {
                    SelectACustomerAlert.display();
                }
            }
        });

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
        button_layout.getChildren().addAll(editDetails, editDiscount);
        button_layout.setPadding(new Insets(0,0,20,0));

        BorderPane center_layout = new BorderPane();
        center_layout.setCenter(list_layout);
        center_layout.setBottom(button_layout);

        HBox bottom_layout = new HBox(50);
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().addAll(close);

        main_root_layout.setPadding(new Insets(10, 10, 10, 10));
        main_root_layout.setTop(top_layout);
        main_root_layout.setCenter(center_layout);
        main_root_layout.setBottom(bottom_layout);

        // Scene
        main_scene.getStylesheets().add("Stylesheet.css");
        window.setScene(main_scene);

        //*************** Edit Details ********************\\
        // Label
        edit_page_info.getStyleClass().add("label-title");

        Label name_label = new Label("Name: ");
        Label address_label = new Label("Address: ");
        Label phone_label = new Label("Phone No: ");
        Label type_label = new Label("Type: ");

        // TextBoxes
        name_textField.setMinWidth(300);
        address_textField.setMinWidth(300);
        phoneNo_textField.setMinWidth(300);
        type_textField.setMinWidth(300);

        // Buttons
        Button edit_save = new Button("Save");
        edit_save.setMinWidth(100);
        edit_save.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                updateCustomer(name_textField.getText(), address_textField.getText(), phoneNo_textField.getText(), type_textField.getText(), selectedCustomer.getID());
                table.getSelectionModel().clearSelection();
                refreshTable();
                clearFields();
                window.setScene(main_scene);
            }
        });

        Button edit_cancel = new Button("Cancel");
        edit_cancel.setMinWidth(75);
        edit_cancel.getStyleClass().add("button-exit");
        edit_cancel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                clearFields();
                refreshTable();
                window.setScene(main_scene);
            }
        });

        // Layout
        GridPane edit_fields_layout = new GridPane();
        edit_fields_layout.setAlignment(Pos.CENTER);
        edit_fields_layout.setHgap(15);
        edit_fields_layout.setVgap(12);
        // Row 1
        GridPane.setConstraints(name_label, 0, 0);
        GridPane.setConstraints(name_textField, 1, 0);
        // Row 2
        GridPane.setConstraints(address_label, 0, 1);
        GridPane.setConstraints(address_textField, 1, 1);
        // Row 3
        GridPane.setConstraints(phone_label, 0, 2);
        GridPane.setConstraints(phoneNo_textField, 1, 2);
        // Row 4
        GridPane.setConstraints(type_label, 0, 3);
        GridPane.setConstraints(type_textField,1,3);
        edit_fields_layout.getChildren().addAll(name_label, name_textField, address_label, address_textField, phone_label, phoneNo_textField, type_label, type_textField);

        HBox edit_button_layout = new HBox();
        edit_button_layout.setAlignment(Pos.CENTER);
        edit_button_layout.getChildren().add(edit_save);

        HBox edit_bottom_layout = new HBox();
        edit_bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        edit_bottom_layout.getChildren().add(edit_cancel);

        BorderPane edit_center_layout = new BorderPane();
        edit_center_layout.setCenter(edit_fields_layout);
        edit_center_layout.setBottom(edit_button_layout);

        VBox edit_top_layout = new VBox();
        edit_top_layout.getChildren().add(edit_page_info);
        edit_top_layout.setAlignment(Pos.TOP_CENTER);

        edit_root_layout.setPadding(new Insets(10,10,10,10));
        edit_root_layout.setTop(edit_top_layout);
        edit_root_layout.setCenter(edit_center_layout);
        edit_root_layout.setBottom(edit_bottom_layout);

        // Scene
        edit_scene.getStylesheets().add("Stylesheet.css");

        // Start window
        window.show();
    }

    private static void clearFields()
    {
        name_textField.clear();
        address_textField.clear();
        phoneNo_textField.clear();
        type_textField.clear();
        edit_page_info.setText("");
    }

    private static void refreshTable()
    {
        customers.clear();
        getCustomers();
    }

    private static void updateCustomer(String name, String address, String phone, String type, int id)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to update customer details
            String query = "UPDATE customer SET name = '"+name+"', address = '"+address+"', phoneNumber = '"+phone+"', type = '"+type+"' WHERE ID = '"+id+"'";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static ObservableList<Customer> getCustomers()
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to find matching customers
            String query = "SELECT * FROM customer";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                customers.add(new Customer(resultSet.getInt("ID"),
                        resultSet.getString("name"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("type")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return customers;
    }

}

