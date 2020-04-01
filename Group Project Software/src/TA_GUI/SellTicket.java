package TA_GUI;

import Database.DBConnectivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Predicate;

public class SellTicket
{
    // Database
    static DBConnectivity dbConnectivity = new DBConnectivity();
    static Connection connection = dbConnectivity.getConnection();

    // Table View
    static TableView<Customer> table;
    static ObservableList<Customer> customers = FXCollections.observableArrayList();

    // Layouts
    static BorderPane root_layout = new BorderPane();
    static BorderPane CC_root_layout = new BorderPane();
    static BorderPane EC_root_Layout = new BorderPane();
    static BorderPane payment_layout = new BorderPane();
    static BorderPane cashPayment_layout = new BorderPane();
    static BorderPane cardPayment_layout = new BorderPane();

    // Scenes
    static Scene scene = new Scene(root_layout);
    static Scene CC_scene = new Scene(CC_root_layout);
    static Scene EC_scene = new Scene(EC_root_Layout);
    static Scene payment_scene = new Scene(payment_layout);
    static Scene cashPayment_scene = new Scene(cashPayment_layout);
    static Scene cardPayment_scene = new Scene(cardPayment_layout);

    // Labels
    static Label paymentMethod_label = new Label();

    public static void display(String title)
    {
        Stage window = new Stage();
        // **************Selection Window***************** \\
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setHeight(500);
        window.setWidth(750);
        window.setResizable(false);

        // Labels
        Label selectCust = new Label("Please Select a customer");
        selectCust.getStyleClass().add("label-title");
        selectCust.setPadding(new Insets(0,0,13,8));

        // Buttons
        Button existingCust = new Button("Select from existing customers");
        existingCust.setMaxWidth(230);
        existingCust.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(EC_scene);
            }
        });

        Button createCust = new Button("Create customer account");
        createCust.setMaxWidth(230);
        createCust.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(CC_scene);
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
        VBox top_layout = new VBox();
        top_layout.setAlignment(Pos.CENTER);
        top_layout.setPadding(new Insets(0,0,10,0));
        top_layout.getChildren().add(selectCust);

        VBox center_layout = new VBox(10);
        center_layout.setAlignment(Pos.CENTER);
        center_layout.setSpacing(10);
        center_layout.getChildren().addAll(existingCust, createCust);

        HBox bottom_layout = new HBox();
        bottom_layout.getChildren().add(close);
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);

        root_layout.setPadding(new Insets(10,10,10,10));
        root_layout.setCenter(center_layout);
        root_layout.setBottom(bottom_layout);
        root_layout.setTop(top_layout);

        // Scene
        scene.getStylesheets().add("Stylesheet.css");


        // *************Create Customer Window************ \\
        // Label
        Label createCust_Label = new Label("Enter new customer details");
        createCust_Label.getStyleClass().add("label-title");
        createCust_Label.setFont(Font.font(16));

        Label CC_name_label = new Label("Name:");
        Label CC_phone_label = new Label("Phone Number:");
        Label CC_address_label = new Label("Address: ");

        //TextFields
        TextField CC_name_textfield = new TextField();
        CC_name_textfield.setPromptText("Name");
        CC_name_textfield.setMinWidth(200);

        TextField CC_phone_textfield = new TextField();
        CC_phone_textfield.setPromptText("Phone Number");
        CC_phone_textfield.setMinWidth(200);

        TextField CC_address_text = new TextField();
        CC_address_text.setPromptText("Address");
        CC_address_text.setMinWidth(200);

        // Buttons
        Button create = new Button("Create customer");
        create.getStyleClass().add("button-login");
        create.setMinSize(150,25);
        create.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                try {
                    // Connect to the Database
                    Statement statement = connection.createStatement();

                    // SQL query to find matching email and password
                    String query = "INSERT INTO customer (name, address, phoneNumber, type, discount)VALUES ('"+CC_name_textfield.getText()+"', '"+CC_address_text.getText()+"' , '" + CC_phone_textfield.getText() +"', 'regular', '0.00')";
                    statement.executeUpdate(query);
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
                refreshTable();
                window.setScene(scene);
                CC_name_textfield.clear();
                CC_phone_textfield.clear();
                CC_address_text.clear();
            }
        });

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("button-exit");
        cancel.setMinSize(75,25);
        cancel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(scene);
            }
        });

        //---Layout---\\
        GridPane CC_centre_layout = new GridPane();
        CC_centre_layout.setAlignment(Pos.CENTER);
        CC_centre_layout.setHgap(15);
        CC_centre_layout.setVgap(12);
        GridPane.setConstraints(CC_name_label, 0, 0);
        GridPane.setConstraints(CC_name_textfield, 1, 0);
        GridPane.setConstraints(CC_phone_label, 0, 1);
        GridPane.setConstraints(CC_phone_textfield, 1, 1);
        GridPane.setConstraints(CC_address_label, 0, 2);
        GridPane.setConstraints(CC_address_text, 1, 2);
        CC_centre_layout.getChildren().addAll(CC_name_label, CC_name_textfield, CC_phone_label, CC_phone_textfield, CC_address_label, CC_address_text);

        HBox CC_bottom_layout = new HBox(139);
        CC_bottom_layout.getChildren().addAll(create, cancel);
        CC_bottom_layout.setAlignment(Pos.CENTER);

        VBox CC_top_layout = new VBox();
        CC_top_layout.getChildren().add(createCust_Label);
        CC_top_layout.setAlignment(Pos.TOP_CENTER);

        // Root Layout
        CC_root_layout.setPadding(new Insets(10,10,10,10));
        CC_root_layout.setTop(CC_top_layout);
        CC_root_layout.setCenter(CC_centre_layout);
        CC_root_layout.setBottom(CC_bottom_layout);

        // Scene
        CC_scene.getStylesheets().add("Stylesheet.css");

        // *****************Existing Customer Window****************** \\
        // Labels
        Label EC_page_info = new Label("Customers");
        EC_page_info.getStyleClass().add("label-title");

        Label EC_search_label = new Label("Search name:   ");

        // Table
        TableColumn<Customer, String> IDColumn = new TableColumn<>("ID");
        IDColumn.setMinWidth(144);
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(144);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> phoneColumn = new TableColumn<>("Phone Number");
        phoneColumn.setMinWidth(144);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Customer, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setMinWidth(144);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Customer, Double> discount = new TableColumn<>("Discount");
        discount.setMinWidth(136);
        discount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        table = new TableView<>();
        table.setItems(getCustomers());
        table.getColumns().addAll(IDColumn, nameColumn, phoneColumn, typeColumn, discount);

        // TextField
        FilteredList<Customer> customerFilteredList = new FilteredList<>(customers, b -> true);

        TextField EC_search_bar_textField = new TextField();
        EC_search_bar_textField.setPromptText("Search by name...");
        EC_search_bar_textField.setMinWidth(100);
        // Creating a listener for a search function
        EC_search_bar_textField.setOnKeyReleased(e ->
        {
            EC_search_bar_textField.textProperty().addListener((observable, oldValue, newValue) ->
            {
                customerFilteredList.setPredicate(customer ->
                {
                    if(newValue == null || newValue.isEmpty())
                    {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if(customer.getName().toLowerCase().contains(lowerCaseFilter))
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                });
            });
        });

        // Creating a sorted list for the new items
        SortedList<Customer> sortedList = new SortedList<>(customerFilteredList);
        // Binding the sorted list comparator to the table view comparator
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        // Adding the sorted items to the table
        table.setItems(sortedList);

        // Buttons
        Button EC_select = new Button("Select");
        EC_select.setMinWidth(150);
        EC_select.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!(table.getSelectionModel().isEmpty()))
                {
                    window.setScene(payment_scene);
                    paymentMethod_label.setText("Select a payment method for " + table.getSelectionModel().getSelectedItem().getName());
                    table.getSelectionModel().clearSelection();
                }
                else
                {
                    SelectACustomerAlert.display();
                    table.getSelectionModel().clearSelection();
                }
            }
        });

        Button EC_refreshTable_button = new Button("Refresh Table");
        EC_refreshTable_button.getStyleClass().add("button-login");
        EC_refreshTable_button.setMinWidth(150);
        EC_refreshTable_button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                refreshTable();
            }
        });

        Button EC_cancel = new Button("Cancel");
        EC_cancel.getStyleClass().add("button-exit");
        EC_cancel.setMinWidth(75);
        EC_cancel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(scene);
            }
        });

        //---Layout---\\
        VBox EC_top_layout = new VBox();
        EC_top_layout.setAlignment(Pos.CENTER);
        EC_top_layout.getChildren().add(EC_page_info);
        EC_top_layout.setPadding(new Insets(0,0,5,0));

        HBox EC_searchBar_layout = new HBox(10);
        EC_searchBar_layout.setAlignment(Pos.CENTER_RIGHT);
        EC_searchBar_layout.getChildren().addAll(EC_search_label, EC_search_bar_textField);
        EC_searchBar_layout.setPadding(new Insets(0,0,5,0));

        VBox EC_list_layout = new VBox(50);
        EC_list_layout.setAlignment(Pos.CENTER);
        EC_list_layout.getChildren().add(table);
        EC_list_layout.setPadding(new Insets(0,0,20,0));

        HBox EC_button_layout = new HBox(30);
        EC_button_layout.setAlignment(Pos.CENTER);
        EC_button_layout.getChildren().addAll(EC_select, EC_refreshTable_button);
        EC_button_layout.setPadding(new Insets(0,0,20,0));

        BorderPane EC_center_layout = new BorderPane();
        EC_center_layout.setTop(EC_searchBar_layout);
        EC_center_layout.setCenter(EC_list_layout);
        EC_center_layout.setBottom(EC_button_layout);

        HBox EC_bottom_layout = new HBox(139);
        EC_bottom_layout.getChildren().addAll(EC_cancel);
        EC_bottom_layout.setAlignment(Pos.BASELINE_RIGHT);

        // Root Layout
        EC_root_Layout.setPadding(new Insets(10,10,10,10));
        EC_root_Layout.setTop(EC_top_layout);
        EC_root_Layout.setCenter(EC_center_layout);
        EC_root_Layout.setBottom(EC_bottom_layout);

        // Scene
        EC_scene.getStylesheets().add("Stylesheet.css");


        // *****************Payment Window****************** \\
        // Labels
        paymentMethod_label.getStyleClass().add("label-title");
        paymentMethod_label.setPadding(new Insets(0,0,13,8));

        // Buttons
        Button cash_button = new Button("Cash");
        cash_button.setMaxWidth(150);
        cash_button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(cashPayment_scene);
            }
        });

        Button card_button = new Button("Card");
        card_button.setMaxWidth(150);
        card_button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(cardPayment_scene);
            }
        });

        Button cancel_payment_button = new Button("Cancel");
        cancel_payment_button.getStyleClass().add("button-exit");
        cancel_payment_button.setMinWidth(75);
        cancel_payment_button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(scene);
            }
        });

        // Layout
        VBox top_layout_payment = new VBox();
        top_layout_payment.setAlignment(Pos.CENTER);
        top_layout_payment.setPadding(new Insets(0,0,10,0));
        top_layout_payment.getChildren().add(paymentMethod_label);

        VBox center_layout_payment = new VBox(10);
        center_layout_payment.setAlignment(Pos.CENTER);
        center_layout_payment.setSpacing(10);
        center_layout_payment.getChildren().addAll(cash_button, card_button);

        HBox bottom_layout_payment = new HBox();
        bottom_layout_payment.getChildren().add(cancel_payment_button);
        bottom_layout_payment.setAlignment(Pos.BASELINE_RIGHT);

        payment_layout.setPadding(new Insets(10,10,10,10));
        payment_layout.setCenter(center_layout_payment);
        payment_layout.setBottom(bottom_layout_payment);
        payment_layout.setTop(top_layout_payment);

        // Scene
        payment_scene.getStylesheets().add("Stylesheet.css");

        // *****************CASH Payment Window****************** \\
        // Label
        Label cashPayment_pageinfo = new Label("Please enter cash amount");
        cashPayment_pageinfo.getStyleClass().add("label-title");

        Label cashAmount_label = new Label("Cash Amount: ");

        // TextFields
        TextField cashAmount = new TextField();
        cashAmount.setPromptText("Enter amount...");
        cashAmount.setMaxWidth(200);

        // Buttons
        Button submitCash = new Button("Submit");
        submitCash.setMaxWidth(150);

        Button cancelCash = new Button("Cancel");
        cancelCash.getStyleClass().add("button-exit");
        cancelCash.setMinWidth(75);
        cancelCash.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(scene);
            }
        });

        // Layout
        VBox cash_top_layout_payment = new VBox();
        cash_top_layout_payment.setAlignment(Pos.CENTER);
        cash_top_layout_payment.setPadding(new Insets(0,0,10,0));
        cash_top_layout_payment.getChildren().add(cashPayment_pageinfo);

        GridPane cash_center_layout_payment = new GridPane();
        cash_center_layout_payment.setAlignment(Pos.CENTER);
        cash_center_layout_payment.setHgap(15);
        cash_center_layout_payment.setVgap(12);
        GridPane.setConstraints(cashAmount_label, 0, 0);
        GridPane.setConstraints(cashAmount, 2, 0);
        GridPane.setConstraints(submitCash, 1, 1);
        cash_center_layout_payment.getChildren().addAll(cashAmount_label, cashAmount, submitCash);

        HBox cash_bottom_layout_payment = new HBox();
        cash_bottom_layout_payment.getChildren().add(cancelCash);
        cash_bottom_layout_payment.setAlignment(Pos.BASELINE_RIGHT);

        cashPayment_layout.setPadding(new Insets(10,10,10,10));
        cashPayment_layout.setCenter(cash_center_layout_payment);
        cashPayment_layout.setBottom(cash_bottom_layout_payment);
        cashPayment_layout.setTop(cash_top_layout_payment);

        // Scene
        cashPayment_scene.getStylesheets().add("Stylesheet.css");



        // *****************CARD Payment Window****************** \\
        // Label
        Label cardPayment_pageinfo = new Label("Please enter cash amount");
        cardPayment_pageinfo.getStyleClass().add("label-title");

        Label cardAmount_label = new Label("Payment amount: ");

        Label cardNumber_label = new Label("Card number: ");

        // TextFields
        TextField cardAmount_textField = new TextField();
        cardAmount_textField.setPromptText("Enter amount...");
        cardAmount_textField.setMaxWidth(100);

        TextField cardNumber_textField = new TextField();
        cardNumber_textField.setPromptText("XXXX-XXXX-XXXX-XXXX");
        cardNumber_textField.setMaxWidth(200);

        // Buttons
        Button submitCard = new Button("Submit");
        submitCard.setMaxWidth(150);

        Button cancelCard = new Button("Cancel");
        cancelCard.getStyleClass().add("button-exit");
        cancelCard.setMinWidth(75);
        cancelCard.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(scene);
            }
        });

        // Layout
        VBox card_top_layout_payment = new VBox();
        card_top_layout_payment.setAlignment(Pos.CENTER);
        card_top_layout_payment.setPadding(new Insets(0,0,10,0));
        card_top_layout_payment.getChildren().add(cardPayment_pageinfo);

        GridPane card_center_layout_payment = new GridPane();
        card_center_layout_payment.setAlignment(Pos.CENTER);
        card_center_layout_payment.setHgap(15);
        card_center_layout_payment.setVgap(12);
        GridPane.setConstraints(cardAmount_label, 0, 0);
        GridPane.setConstraints(cardAmount_textField, 2, 0);
        GridPane.setConstraints(cardNumber_label, 0, 1);
        GridPane.setConstraints(cardNumber_textField, 2, 1);
        GridPane.setConstraints(submitCard, 1, 2);
        card_center_layout_payment.getChildren().addAll(cardAmount_label, cardAmount_textField, cardNumber_label, cardNumber_textField, submitCard);

        HBox card_bottom_layout_payment = new HBox();
        card_bottom_layout_payment.getChildren().add(cancelCard);
        card_bottom_layout_payment.setAlignment(Pos.BASELINE_RIGHT);

        cardPayment_layout.setPadding(new Insets(10,10,10,10));
        cardPayment_layout.setCenter(card_center_layout_payment);
        cardPayment_layout.setBottom(card_bottom_layout_payment);
        cardPayment_layout.setTop(card_top_layout_payment);

        // Scene
        cardPayment_scene.getStylesheets().add("Stylesheet.css");



        // Start window
        window.setScene(scene);
        window.showAndWait();
    }

    public static ObservableList<Customer> getCustomers()
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to find matching customers
            String query = "SELECT * FROM customer ORDER BY ID DESC";
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
        customers.clear();
        getCustomers();
    }
}
