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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Blank;
import sample.Customer;
import sample.Staff.TravelAdvisor;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Predicate;

public class SellTicket
{
    // Stage
    static Stage window = new Stage();

    // Database
    static DBConnectivity dbConnectivity = new DBConnectivity();
    static Connection connection = dbConnectivity.getConnection();

    // Table View
    static TableView<Customer> Customertable;
    static ObservableList<Customer> customers = FXCollections.observableArrayList();

    static TableView<Blank> BlankTable;
    static ObservableList<Blank> blanks = FXCollections.observableArrayList();

    // Layouts
    static BorderPane root_layout = new BorderPane();
    static BorderPane CC_root_layout = new BorderPane();
    static BorderPane EC_root_Layout = new BorderPane();
    static BorderPane payment_layout = new BorderPane();
    static BorderPane blankSelect_layout = new BorderPane();

    // Scenes
    static Scene scene = new Scene(root_layout);
    static Scene CC_scene = new Scene(CC_root_layout);
    static Scene EC_scene = new Scene(EC_root_Layout);
    static Scene payment_scene = new Scene(payment_layout);
    static Scene blankSelect_scene = new Scene(blankSelect_layout);

    // Labels
    static Label paymentMethod_label = new Label();

    // Sale
    static Blank selectedBlank = new Blank();
    static double amount = 0.0;
    static String paymentMethod = "";
    static double tax = 0.0;
    static String creditCard = "";
    static String origin = "";
    static String destination = "";
    static double commRate = 0.0;
    static Customer customer = new Customer();
    static boolean payLate = false;

    // Payment
    static TextField payment_amount_textField = new TextField();
    static TextField payment_creditCard_textField = new TextField();
    static TextField payment_tax_textField = new TextField();
    static TextField payment_origin_textField = new TextField();
    static TextField payment_destination_textField = new TextField();

    static ChoiceBox<String> payment_payMethod_choiceBox = new ChoiceBox<>();
    static ChoiceBox<String> payment_currency_choiceBox = new ChoiceBox<>();

    //***** Payment *****\\
    // Radio Buttons
    static RadioButton payment_payLateYES_radioButton = new RadioButton("Yes");
    static RadioButton payment_payLateNO_radioButton = new RadioButton("No");

    public static void display(String title)
    {
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

        Button casualCust = new Button("Casual Customer");
        casualCust.setMaxWidth(230);
        casualCust.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(blankSelect_scene);
                paymentMethod_label.setText("Select a payment method for a casual customer");
                payment_payLateNO_radioButton.setDisable(true);
                payment_payLateYES_radioButton.setDisable(true);
                customer.setName("Casual customer");
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
        center_layout.getChildren().addAll(existingCust, createCust, casualCust);

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
                createCustomer(CC_name_textfield.getText(), CC_address_text.getText(), CC_phone_textfield.getText());
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
        CC_centre_layout.setScaleY(1.2);
        CC_centre_layout.setScaleX(1.2);
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

        Customertable = new TableView<>();
        Customertable.setItems(getCustomers());
        Customertable.getColumns().addAll(IDColumn, nameColumn, phoneColumn, typeColumn, discount);

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
        sortedList.comparatorProperty().bind(Customertable.comparatorProperty());
        // Adding the sorted items to the table
        Customertable.setItems(sortedList);

        // Buttons
        Button EC_select = new Button("Select");
        EC_select.setMinWidth(150);
        EC_select.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!(Customertable.getSelectionModel().isEmpty()))
                {
                    window.setScene(blankSelect_scene);
                    paymentMethod_label.setText("Select a payment method for " + Customertable.getSelectionModel().getSelectedItem().getName());
                    customer = Customertable.getSelectionModel().getSelectedItem();
                    if(Customertable.getSelectionModel().getSelectedItem().getType().equals("regular"))
                    {
                        payment_payLateYES_radioButton.setDisable(true);
                        payment_payLateNO_radioButton.setDisable(true);
                    }
                    else
                    {
                        payment_payLateYES_radioButton.setDisable(false);
                        payment_payLateNO_radioButton.setDisable(false);
                    }
                    Customertable.getSelectionModel().clearSelection();
                }
                else
                {
                    SelectACustomerAlert.display();
                    Customertable.getSelectionModel().clearSelection();
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
        EC_list_layout.getChildren().add(Customertable);
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

        // *****************Select Blank Window****************** \\
        // Labels
        Label selectBlank_pageinfo_label = new Label("Please select a blank for the transaction");
        selectBlank_pageinfo_label.getStyleClass().add("label-title");
        selectBlank_pageinfo_label.setPadding(new Insets(0,0,10,0));

        // Table
        TableColumn<Blank, String> blankIDColumn = new TableColumn<>("Blank ID");
        blankIDColumn.setMinWidth(146);
        blankIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Blank, String> blankTypeColumn = new TableColumn<>("Blank Type");
        blankTypeColumn.setMinWidth(115);
        blankTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Blank, String> blankAssignedToColumn = new TableColumn<>("Assigned To");
        blankAssignedToColumn.setMinWidth(146);
        blankAssignedToColumn.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));

        TableColumn<Blank, String> blankReceivedDateColumn = new TableColumn<>("Received Date");
        blankReceivedDateColumn.setMinWidth(146);
        blankReceivedDateColumn.setCellValueFactory(new PropertyValueFactory<>("receivedDate"));

        TableColumn<Blank, String> blankAssignedDateColumn = new TableColumn<>("Assigned Date");
        blankAssignedDateColumn.setMinWidth(146);
        blankAssignedDateColumn.setCellValueFactory(new PropertyValueFactory<>("assignedDate"));

        BlankTable = new TableView<>();
        BlankTable.setItems(ViewBlankStock_TA.getBlanks());
        BlankTable.getColumns().addAll(blankIDColumn, blankTypeColumn, blankAssignedToColumn, blankReceivedDateColumn, blankAssignedDateColumn);

        // Buttons
        Button selectBlank_continue_button = new Button("Continue");
        selectBlank_continue_button.getStyleClass().add("button-login");
        selectBlank_continue_button.setMinWidth(125);
        selectBlank_continue_button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!(BlankTable.getSelectionModel().isEmpty()))
                {
                    selectedBlank = BlankTable.getSelectionModel().getSelectedItem();
                    window.setScene(payment_scene);
                    BlankTable.getSelectionModel().clearSelection();
                }
                else
                {
                    SelectBlankAlert.display();
                }
            }
        });

        // Layout
        VBox selectBlank_top_layout = new VBox();
        selectBlank_top_layout.setAlignment(Pos.CENTER);
        selectBlank_top_layout.getChildren().add(selectBlank_pageinfo_label);
        selectBlank_top_layout.setPadding(new Insets(0, 0, 20, 0));

        VBox selectBlank_list_layout = new VBox(50);
        selectBlank_list_layout.setAlignment(Pos.CENTER);
        selectBlank_list_layout.getChildren().add(BlankTable);
        selectBlank_list_layout.setPadding(new Insets(0, 0, 20, 0));

        HBox selectBlank_button_layout = new HBox(60);
        selectBlank_button_layout.setAlignment(Pos.CENTER);
        selectBlank_button_layout.getChildren().add(selectBlank_continue_button);
        selectBlank_button_layout.setPadding(new Insets(0, 0, 20, 0));

        BorderPane selectBlank_center_layout = new BorderPane();
        selectBlank_center_layout.setCenter(selectBlank_list_layout);
        selectBlank_center_layout.setBottom(selectBlank_button_layout);

        blankSelect_layout.setPadding(new Insets(10, 10, 10, 10));
        blankSelect_layout.setTop(selectBlank_top_layout);
        blankSelect_layout.setCenter(selectBlank_center_layout);

        // Scene
        blankSelect_scene.getStylesheets().add("Stylesheet.css");

        // *****************Payment Window****************** \\
        // Labels
        paymentMethod_label.getStyleClass().add("label-title");
        paymentMethod_label.setPadding(new Insets(0,0,13,8));

        Label payment_amount_label = new Label("Amount");
        Label payment_method_label = new Label("Credit Card");
        Label payment_tax_label = new Label("Tax");
        Label payment_origin_label = new Label("Origin");
        Label payment_destination_label = new Label("Destination");
        Label payment_payLater_label = new Label("Pay later");

        // TextFields
        payment_amount_textField.setPromptText("Amount");
        payment_creditCard_textField.setPromptText("XXXX-XXXX-XXXX");
        payment_tax_textField.setPromptText("Tax");
        payment_origin_textField.setPromptText("Origin of flight");
        payment_destination_textField.setPromptText("Destination of flight");

        // Choice Box
        payment_payMethod_choiceBox.getItems().addAll("Cash", "Card");
        payment_payMethod_choiceBox.setMinWidth(70);
        payment_payMethod_choiceBox.setValue("Cash");

        payment_currency_choiceBox.getItems().addAll("GBP", "USD");
        payment_currency_choiceBox.setMinWidth(70);
        payment_currency_choiceBox.setValue("GBP");

        // Radio buttons
        ToggleGroup payment_toggleGroup = new ToggleGroup();
        payment_payLateYES_radioButton.setToggleGroup(payment_toggleGroup);
        payment_payLateNO_radioButton.setToggleGroup(payment_toggleGroup);

        // Buttons
        Button payment_confirmPayment_button = new Button("Confirm Sale");
        payment_confirmPayment_button.getStyleClass().add("button-login");
        payment_confirmPayment_button.setMinWidth(125);
        payment_confirmPayment_button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                amount = Double.parseDouble(payment_amount_textField.getText());
                creditCard = payment_creditCard_textField.getText();
                tax = Double.parseDouble(payment_tax_textField.getText());
                origin = payment_origin_textField.getText();
                destination = payment_destination_textField.getText();
                paymentMethod = payment_payMethod_choiceBox.getSelectionModel().getSelectedItem();
                if(payment_currency_choiceBox.getSelectionModel().getSelectedItem().equals("USD"))
                {
                    amount = amount * CurrencyExchange.getCurrency();
                }
                createSale(amount, paymentMethod, tax, creditCard, origin, destination, commRate, customer.getName(), selectedBlank.getId(), payLate);

                endPayment();
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
                if(CancelPaymentAlert.display())
                {
                    endPayment();
                }
            }
        });

        // Layout
        VBox top_layout_payment = new VBox();
        top_layout_payment.setAlignment(Pos.CENTER);
        top_layout_payment.setPadding(new Insets(0,0,10,0));
        top_layout_payment.getChildren().add(paymentMethod_label);

        HBox radioButtons_layout_payment = new HBox(15);
        radioButtons_layout_payment.setAlignment(Pos.CENTER);
        radioButtons_layout_payment.getChildren().addAll(payment_payLateYES_radioButton, payment_payLateNO_radioButton);

        GridPane grid_layout_payment = new GridPane();
        grid_layout_payment.setScaleX(1.2);
        grid_layout_payment.setScaleY(1.2);
        grid_layout_payment.setAlignment(Pos.CENTER);
        grid_layout_payment.setHgap(15);
        grid_layout_payment.setVgap(12);
        // Row 1
        GridPane.setConstraints(payment_amount_label,0,0);
        GridPane.setConstraints(payment_amount_textField, 1,0);
        GridPane.setConstraints(payment_currency_choiceBox, 2, 0);
        GridPane.setConstraints(payment_payMethod_choiceBox, 3, 0);
        // Row 2
        GridPane.setConstraints(payment_method_label, 0, 1);
        GridPane.setConstraints(payment_creditCard_textField,1, 1);
        // Row 3
        GridPane.setConstraints(payment_tax_label, 0, 2);
        GridPane.setConstraints(payment_tax_textField,1, 2);
        // Row 4
        GridPane.setConstraints(payment_origin_label, 0, 3);
        GridPane.setConstraints(payment_origin_textField, 1, 3);
        // Row 5
        GridPane.setConstraints(payment_destination_label, 0, 4);
        GridPane.setConstraints(payment_destination_textField, 1, 4);
        // Row 6
        GridPane.setConstraints(payment_payLater_label, 0, 5);
        GridPane.setConstraints(radioButtons_layout_payment, 1, 5);
        grid_layout_payment.getChildren().addAll(payment_amount_label, payment_amount_textField, payment_currency_choiceBox, payment_payMethod_choiceBox, payment_method_label, payment_creditCard_textField,
                payment_tax_label, payment_tax_textField, payment_origin_label, payment_origin_textField, payment_destination_label, payment_destination_textField,
                payment_payLater_label, radioButtons_layout_payment);

        VBox center_layout_payment = new VBox(40);
        center_layout_payment.setAlignment(Pos.CENTER);
        center_layout_payment.getChildren().addAll(grid_layout_payment, payment_confirmPayment_button);

        HBox bottom_layout_payment = new HBox();
        bottom_layout_payment.getChildren().add(cancel_payment_button);
        bottom_layout_payment.setAlignment(Pos.BASELINE_RIGHT);

        payment_layout.setPadding(new Insets(10,10,10,10));
        payment_layout.setCenter(center_layout_payment);
        payment_layout.setBottom(bottom_layout_payment);
        payment_layout.setTop(top_layout_payment);

        // Scene
        payment_scene.getStylesheets().add("Stylesheet.css");

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

    public static void createSale(double amount, String paymentMeth, double tax, String creditCard, String origin, String destination, double commRate, String custName, String blankID, boolean payLate)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to find matching travel advisors
            String query = "INSERT INTO sales (BlankID, amount, paymentMethod, tax, creditcard, origin, destination, commissionRate, customer) VALUES ('"+blankID+"', '"+amount+"', '"+paymentMeth+"', '"+tax+"', '"+creditCard+"', '"+origin+"', '"+destination+"', '"+commRate+"', '"+custName+"')";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void createCustomer(String name, String address, String phone)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to create a customer
            String query = "INSERT INTO customer (name, address, phoneNumber, type, discount)VALUES ('"+name+"', '"+address+"' , '" +phone+"', 'regular', '0.00')";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void endPayment()
    {
        window.setScene(scene);
        payment_amount_textField.clear();
        payment_tax_textField.clear();
        payment_origin_textField.clear();
        payment_destination_textField.clear();
        payment_payMethod_choiceBox.setValue("Cash");
        payment_currency_choiceBox.setValue("GBP");
        if (payment_payLateYES_radioButton.isSelected())
        {
            payment_payLateYES_radioButton.setSelected(false);
        } else if (payment_payLateNO_radioButton.isSelected())
        {
            payment_payLateNO_radioButton.setSelected(false);
        }
    }
}
