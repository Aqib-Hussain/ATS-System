package TA_GUI;

import Database.DBConnectivity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

public class ResetDueAmount
{
    // Database
    static private final DBConnectivity dbConnectivity = new DBConnectivity();
    static private final Connection connection = dbConnectivity.getConnection();

    // Table
    static private TableView<Customer> table;
    static private ObservableList<Customer> customers = FXCollections.observableArrayList();

    // Amount
    static private double amountToTake = 0;
    static private double currentAmount = 0;

    // Customer
    static private Customer selectedCustomer;

    public static void display(String title)
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(600);
        window.setHeight(500);
        window.setResizable(false);

        // Labels
        Label page_info = new Label("Customers with due balance");
        page_info.getStyleClass().add("label-title");

        Label specificAmount_label = new Label("Specific amount ");
        Label or_label = new Label(" OR ");
        Label fullAmount = new Label ("Full amount ");

        // TextFields
        TextField amount = new TextField();
        amount.setPrefWidth(50);
        amount.setPromptText("e.g 500");

        // CheckBox
        CheckBox fullAmount_checkBox = new CheckBox();
        fullAmount_checkBox.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                if(fullAmount_checkBox.isSelected())
                {
                    amount.setDisable(true);
                }
                else
                {
                    amount.setDisable(false);
                }
            }
        });

        // Table
        TableColumn<Customer, Integer> ID_column = new TableColumn<>("ID");
        ID_column.setMinWidth(50);
        ID_column.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Customer, String> name_column = new TableColumn<>("Name");
        name_column.setMinWidth(100);
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> address_column = new TableColumn<>("Address");
        address_column.setMinWidth(120);
        address_column.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Customer, String> phone_column = new TableColumn<>("Phone No.");
        phone_column.setMinWidth(100);
        phone_column.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Customer, Double> amount_column = new TableColumn<>("Amount Due");
        amount_column.setPrefWidth(100);
        amount_column.setCellValueFactory(new PropertyValueFactory<>("dueBalance"));

        table = new TableView<>();
        table.setItems(getCustomers());
        table.getColumns().addAll(ID_column, name_column, address_column, phone_column, amount_column);

        // Buttons
        Button confirm = new Button("Confirm");
        confirm.setPrefWidth(100);
        confirm.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!(table.getSelectionModel().isEmpty()))
                {
                    selectedCustomer = table.getSelectionModel().getSelectedItem();
                    if((!(amount.getText().isEmpty()) || fullAmount_checkBox.isSelected()))
                    {
                        getDueAmount(selectedCustomer.getID());
                        if (amount.isDisabled())
                        {
                            amountToTake = currentAmount;
                        } else
                        {
                            amountToTake = Double.parseDouble(amount.getText());
                        }
                        double newAmount = currentAmount - amountToTake;
                        reduceDueBalance(newAmount, selectedCustomer.getID());
                        refreshTable();
                    }
                    else
                    {
                        SelectAmountAlert.display();
                    }
                }
                else
                {
                    SelectACustomerAlert.display();
                }
            }
        });

        Button close = new Button("Close");
        close.getStyleClass().add("button-exit");
        close.setMinWidth(75);
        close.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.close();
            }
        });

        // Layout
        HBox top_layout = new HBox();
        top_layout.setAlignment(Pos.CENTER);
        top_layout.getChildren().add(page_info);

        VBox list_layout = new VBox();
        list_layout.setAlignment(Pos.CENTER);
        list_layout.getChildren().add(table);

        VBox checkBoc = new VBox();
        checkBoc.setAlignment(Pos.CENTER);
        checkBoc.getChildren().add(fullAmount_checkBox);

        GridPane fields_layout = new GridPane();
        fields_layout.setAlignment(Pos.CENTER);
        fields_layout.setHgap(15);
        fields_layout.setVgap(12);
        // Row 1
        GridPane.setConstraints(specificAmount_label, 0, 0);
        GridPane.setConstraints(fullAmount, 2, 0);
        // Row 2
        GridPane.setConstraints(amount, 0, 1);
        GridPane.setConstraints(or_label, 1, 1);
        GridPane.setConstraints(checkBoc, 2, 1);
        fields_layout.getChildren().addAll(specificAmount_label, fullAmount, amount, or_label, checkBoc);

        VBox button_layout = new VBox(10);
        button_layout.setPadding(new Insets(10,0,0,0));
        button_layout.setAlignment(Pos.CENTER);
        button_layout.getChildren().addAll(fields_layout,confirm);

        BorderPane center_layout = new BorderPane();
        center_layout.setPadding(new Insets(10,0,0,0));
        center_layout.setCenter(list_layout);
        center_layout.setBottom(button_layout);

        HBox bottom_layout = new HBox();
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().add(close);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10,10,10,10));
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

    private static ObservableList<Customer> getCustomers()
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to find matching customers
            String query = "SELECT * FROM customer WHERE dueBalance > '0';";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                customers.add(new Customer(resultSet.getInt("ID"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getDouble("dueBalance")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return customers;
    }

    private static void getDueAmount(int id)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to select the current due amount
            String query = "SELECT dueBalance FROM customer WHERE ID = '"+id+"'";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next())
            {
                currentAmount = resultSet.getDouble(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static void refreshTable()
    {
        customers.clear();
        getCustomers();
    }

    private static void reduceDueBalance(double newAmount, int id)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to update the due balance
            String query = "UPDATE customer SET dueBalance = '"+newAmount+"' WHERE ID = '"+id+"'";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
