package TA_GUI;

import Database.DBConnectivity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Blank;
import sample.Customer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class SellTicket
{
    // Stage
    private static Stage window = new Stage();

    // Database
    private static DBConnectivity dbConnectivity = new DBConnectivity();
    private static Connection connection = dbConnectivity.getConnection();

    // Table View
    private static TableView<Customer> Customertable;
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();

    private static TableView<Blank> BlankTable;
    private static ObservableList<Blank> blanks = FXCollections.observableArrayList();

    private static ObservableList<Double> commissionRates = FXCollections.observableArrayList();

    // Layouts
    private static BorderPane root_layout = new BorderPane();
    private static BorderPane CC_root_layout = new BorderPane();
    private static BorderPane EC_root_Layout = new BorderPane();
    private static BorderPane payment_layout = new BorderPane();
    private static BorderPane blankSelect_layout = new BorderPane();

    // Scenes
    private static Scene scene = new Scene(root_layout);
    private static Scene CC_scene = new Scene(CC_root_layout);
    private static Scene EC_scene = new Scene(EC_root_Layout);
    private static Scene payment_scene = new Scene(payment_layout);
    private static Scene blankSelect_scene = new Scene(blankSelect_layout);

    // Labels
    private static Label paymentMethod_label = new Label();

    // Sale
    private static Blank selectedBlank = new Blank();
    private static double amount = 0.0;
    private static String currency = "";
    private static String paymentMethod = "";
    private static double tax = 0.0;
    private static double otherTax = 0.0;
    private static String creditCard = "";
    private static String ticketType = "";
    private static String origin = "";
    private static String destination = "";
    private static double commRate = 0.0;
    private static Customer customer = new Customer();
    private static String saleDate = "";
    private static String payByDate = "";
    private static String isPaid = "";

    // Payment
    private static TextField payment_amount_textField = new TextField();
    private static TextField payment_creditCard_textField = new TextField();
    private static TextField payment_tax_textField = new TextField();
    private static TextField payment_otherTax_textField = new TextField();
    private static TextField payment_origin_textField = new TextField();
    private static TextField payment_destination_textField = new TextField();

    private static ChoiceBox<String> payment_payMethod_choiceBox = new ChoiceBox<>();
    private static ChoiceBox<String> payment_currency_choiceBox = new ChoiceBox<>();
    private static ChoiceBox<Double> payment_commission_choiceBox = new ChoiceBox<>();

    //***** Payment *****\\
    // Radio Buttons
    private static RadioButton payment_interline_radioButton = new RadioButton("Interline");
    private static RadioButton payment_domestic_radioButton = new RadioButton("Domestic");

    private static RadioButton payment_payLateYES_radioButton = new RadioButton("Yes");
    private static RadioButton payment_payLateNO_radioButton = new RadioButton("No");

    public static void display(String title)
    {
        // **************Selection Window***************** \\
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setHeight(700);
        window.setWidth(750);
        window.setResizable(false);

        // Labels
        Label selectCust = new Label("Please Select a customer");
        selectCust.getStyleClass().add("label-title");
        selectCust.setPadding(new Insets(0,0,13,8));

        // Buttons
        Button existingCust = new Button("Select from existing customers");
        existingCust.setMaxWidth(250);
        existingCust.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(EC_scene);
            }
        });

        Button createCust = new Button("Create customer account");
        createCust.setMaxWidth(250);
        createCust.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(CC_scene);
            }
        });

        Button casualCust = new Button("Casual Customer");
        casualCust.setMaxWidth(250);
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
                isPaid = "";
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

        Label EC_search_label = new Label("Search name:  ");

        // Table
        TableColumn<Customer, Integer> IDColumn = new TableColumn<>("ID");
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
        SortedList<Customer> sortedCustomers = new SortedList<>(customerFilteredList);
        // Binding the sorted list comparator to the table view comparator
        sortedCustomers.comparatorProperty().bind(Customertable.comparatorProperty());
        // Adding the sorted items to the table
        Customertable.setItems(sortedCustomers);

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

        Label selectBLank_search_label = new Label("Search by blank:  ");

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
        BlankTable.setItems(getBlanks());
        BlankTable.getColumns().addAll(blankIDColumn, blankTypeColumn, blankAssignedToColumn, blankReceivedDateColumn, blankAssignedDateColumn);

        // TextFields
        FilteredList<Blank> blankFilteredList = new FilteredList<>(blanks, b -> true);

        TextField selectBlank_search_textField = new TextField();
        selectBlank_search_textField.setPromptText("Search by blank...");
        selectBlank_search_textField.setMinWidth(100);
        // Creating a listener for a search function
        selectBlank_search_textField.setOnKeyReleased(e ->
        {
            selectBlank_search_textField.textProperty().addListener((observable, oldValue, newValue) ->
            {
                blankFilteredList.setPredicate(blank ->
                {
                    if(newValue == null || newValue.isEmpty())
                    {
                        return true;
                    }


                    if(blank.getId().toLowerCase().contains(newValue.toLowerCase()))
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
        SortedList<Blank> sortedBlanks = new SortedList<>(blankFilteredList);
        // Binding the sorted list comparator to the table view comparator
        sortedBlanks.comparatorProperty().bind(BlankTable.comparatorProperty());
        // Adding the sorted items to the table
        BlankTable.setItems(sortedBlanks);

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
                    payment_commission_choiceBox.getItems().addAll(getCommissionRates(selectedBlank.getType()));
                    payment_commission_choiceBox.setValue(getCommissionRates(selectedBlank.getType()).get(commissionRates.size()-1));
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

        HBox selectBlank_searchBar_layout = new HBox(10);
        selectBlank_searchBar_layout.setAlignment(Pos.CENTER_RIGHT);
        selectBlank_searchBar_layout.getChildren().addAll(selectBLank_search_label, selectBlank_search_textField);
        selectBlank_searchBar_layout.setPadding(new Insets(0,0,5,0));

        VBox selectBlank_list_layout = new VBox(50);
        selectBlank_list_layout.setAlignment(Pos.CENTER);
        selectBlank_list_layout.getChildren().add(BlankTable);
        selectBlank_list_layout.setPadding(new Insets(0, 0, 20, 0));

        HBox selectBlank_button_layout = new HBox(60);
        selectBlank_button_layout.setAlignment(Pos.CENTER);
        selectBlank_button_layout.getChildren().add(selectBlank_continue_button);
        selectBlank_button_layout.setPadding(new Insets(0, 0, 20, 0));

        BorderPane selectBlank_center_layout = new BorderPane();
        selectBlank_center_layout.setTop(selectBlank_searchBar_layout);
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
        Label payment_tax_label = new Label("Local Tax");
        Label payment_otherTax_label = new Label("Other Tax");
        Label payment_commissionRate_label = new Label("Commission Rate");
        Label payment_interlineORdomestic_label = new Label("Interline/Domestic");
        Label payment_origin_label = new Label("Origin");
        Label payment_destination_label = new Label("Destination");
        Label payment_payLater_label = new Label("Pay later");

        // TextFields
        payment_amount_textField.setPromptText("Amount");
        payment_creditCard_textField.setPromptText("XXXX-XXXX-XXXX");
        payment_creditCard_textField.setDisable(true);
        payment_tax_textField.setPromptText("Tax");
        payment_otherTax_textField.setPromptText("Other tax");
        payment_origin_textField.setPromptText("Origin of flight");
        payment_destination_textField.setPromptText("Destination of flight");

        // Choice Box
        payment_payMethod_choiceBox.getItems().addAll("Cash", "Card", "TBD");
        payment_payMethod_choiceBox.setMinWidth(70);
        payment_payMethod_choiceBox.setValue("Cash");
        payment_payMethod_choiceBox.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(payment_payMethod_choiceBox.getSelectionModel().getSelectedItem().equals("Cash") || payment_payMethod_choiceBox.getSelectionModel().getSelectedItem().equals("TBD"))
                {
                    payment_creditCard_textField.setDisable(true);
                }
                else
                {
                    payment_creditCard_textField.setDisable(false);
                }
            }
        });

        payment_currency_choiceBox.getItems().addAll("GBP", "USD");
        payment_currency_choiceBox.setMinWidth(70);
        payment_currency_choiceBox.setValue("GBP");

        // ComboBox
        payment_commission_choiceBox.setMinWidth(70);

        // Radio buttons
        ToggleGroup payment_toggleGroup_interORDom = new ToggleGroup();
        payment_interline_radioButton.setToggleGroup(payment_toggleGroup_interORDom);
        payment_interline_radioButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                if(payment_interline_radioButton.isSelected())
                {
                    payment_otherTax_textField.setDisable(false);
                }
            }
        });
        payment_domestic_radioButton.setToggleGroup(payment_toggleGroup_interORDom);
        payment_domestic_radioButton.selectedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                if(payment_domestic_radioButton.isSelected())
                {
                    payment_otherTax_textField.setDisable(true);
                }
            }
        });

        ToggleGroup payment_toggleGroup_payLate = new ToggleGroup();
        payment_payLateYES_radioButton.setToggleGroup(payment_toggleGroup_payLate);
        payment_payLateNO_radioButton.setToggleGroup(payment_toggleGroup_payLate);

        // Buttons
        Button payment_confirmPayment_button = new Button("Confirm Sale");
        payment_confirmPayment_button.getStyleClass().add("button-login");
        payment_confirmPayment_button.setMinWidth(125);
        payment_confirmPayment_button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                getCurrentDate();
                amount = Double.parseDouble(payment_amount_textField.getText());
                currency = payment_currency_choiceBox.getSelectionModel().getSelectedItem();
                creditCard = payment_creditCard_textField.getText();
                tax = Double.parseDouble(payment_tax_textField.getText());
                origin = payment_origin_textField.getText();
                destination = payment_destination_textField.getText();
                paymentMethod = payment_payMethod_choiceBox.getSelectionModel().getSelectedItem();
                if(payment_currency_choiceBox.getSelectionModel().getSelectedItem().equals("USD"))
                {
                    amount = amount * CurrencyExchange.getCurrency();
                }

                if(payment_interline_radioButton.isSelected())
                {
                    ticketType = "Interline";
                    otherTax = Double.parseDouble(payment_otherTax_textField.getText());
                }
                else
                {
                    ticketType = "Domestic";
                    otherTax = 0.0;
                }

                if (!(payment_payLateNO_radioButton.isDisabled() && payment_payLateYES_radioButton.isDisabled()))
                {
                    if(payment_payLateYES_radioButton.isSelected())
                    {
                        DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("dd");
                        DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MM");
                        DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyy");
                        LocalDateTime now = LocalDateTime.now();

                        String day = dayFormat.format(now);
                        String month = monthFormat.format(now);
                        String year = yearFormat.format(now);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        Calendar calendar = new GregorianCalendar(Integer.parseInt(year),Integer.parseInt(month)-1,Integer.parseInt(day));
                        calendar.add(Calendar.DAY_OF_MONTH, 30);
                        payByDate = sdf.format(calendar.getTime());

                        isPaid = "NO";

                        increaseDueAmount(customer.getName(), getCustomerDueBalance(customer.getName()),amount);
                    }
                }

                createSale(amount, currency,paymentMethod, tax, otherTax,creditCard, ticketType, origin, destination, commRate, customer.getName(), payByDate,selectedBlank.getId(), getTAname(),saleDate, isPaid);
                setBlankState(selectedBlank.getId());
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

        HBox interORDom_layout_payment = new HBox(15);
        interORDom_layout_payment.setAlignment(Pos.CENTER);
        interORDom_layout_payment.getChildren().addAll(payment_interline_radioButton, payment_domestic_radioButton);

        HBox payLater_layout_payment = new HBox(15);
        payLater_layout_payment.setAlignment(Pos.CENTER_LEFT);
        payLater_layout_payment.getChildren().addAll(payment_payLateYES_radioButton, payment_payLateNO_radioButton);

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
        GridPane.setConstraints(payment_interlineORdomestic_label,0,3);
        GridPane.setConstraints(interORDom_layout_payment, 1, 3);
        // Row 5
        GridPane.setConstraints(payment_otherTax_label, 0, 4);
        GridPane.setConstraints(payment_otherTax_textField,1, 4);
        // Row 6
        GridPane.setConstraints(payment_commissionRate_label, 0, 5);
        GridPane.setConstraints(payment_commission_choiceBox, 1, 5);
        // Row 7
        GridPane.setConstraints(payment_origin_label, 0, 6);
        GridPane.setConstraints(payment_origin_textField, 1, 6);
        // Row 8
        GridPane.setConstraints(payment_destination_label, 0, 7);
        GridPane.setConstraints(payment_destination_textField, 1, 7);
        // Row 9
        GridPane.setConstraints(payment_payLater_label, 0, 8);
        GridPane.setConstraints(payLater_layout_payment, 1, 8);
        grid_layout_payment.getChildren().addAll(payment_amount_label, payment_amount_textField, payment_currency_choiceBox, payment_payMethod_choiceBox, payment_method_label, payment_creditCard_textField,
                payment_tax_label, payment_tax_textField, payment_otherTax_label, payment_otherTax_textField, payment_commissionRate_label, payment_commission_choiceBox, payment_interlineORdomestic_label, interORDom_layout_payment, payment_origin_label, payment_origin_textField, payment_destination_label, payment_destination_textField,
                payment_payLater_label, payLater_layout_payment);

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

    private static ObservableList<Customer> getCustomers()
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to find matching customers
            String query = "SELECT * FROM customer ORDER BY ID DESC";
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

    private static ObservableList<Blank> getBlanks()
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to find matching travel advisors
            String query = "SELECT * FROM blank INNER JOIN staff ON blank.assignedTo = staff.name WHERE (staff.name = 'Penelope Pitstop' OR staff.name = 'Dennis Menace') AND staff.status = 'loggedIn' AND blank.state = 'Assigned'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                blanks.add(new Blank(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return blanks;
    }

    private static ObservableList<Double> getCommissionRates(String blankType)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to find commission Rates
            String query = "SELECT CommissionRate FROM commissionrate WHERE BlankType = '"+blankType+"'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                commissionRates.add(resultSet.getDouble("CommissionRate"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return commissionRates;
    }

    private static void refreshTable()
    {
        customers.clear();
        getCustomers();
    }

    private static void createSale(double amount, String currency, String paymentMeth, double tax, double otherTax, String creditCard, String ticketType, String origin, String destination, double commRate, String custName, String payBy, String blankID, String soldBy, String saleDate, String isPaid)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to find matching travel advisors
            String query = "INSERT INTO sales (BlankID, amount, currency,paymentMethod, localTax, otherTax, creditcard, ticketType, origin, destination, commissionRate, customer, payBy, isPaid, soldBy,saleDate, state, refundDate, refundAmount) VALUES ('"+blankID+"', '"+amount+"', '"+currency+"','"+paymentMeth+"', '"+tax+"', '"+otherTax+"','"+creditCard+"', '"+ticketType+"','"+origin+"', '"+destination+"', '"+commRate+"', '"+custName+"', '"+payBy+"','"+isPaid+"','"+soldBy+"', '"+saleDate+"','Valid', '0', '0')";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    private static double getCustomerDueBalance(String custName)
    {
        double amount = 0.0;
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to find customer
            String query = "SELECT dueBalance FROM customer WHERE name = '"+custName+"'";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next())
            {
                amount = resultSet.getDouble(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return amount;
    }

    private static void increaseDueAmount(String custName, double dueBalance, double amount)
    {
        double balance = dueBalance+=amount;
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to find customer
            String query = "UPDATE customer SET dueBalance = '"+balance+"' WHERE name = '"+custName+"'";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static void getCurrentDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        saleDate = dtf.format(now);
    }

    private static void setBlankState(String blankID)
    {
        try
        {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to find matching travel advisors
            String query = "UPDATE blank SET state = 'Sold/Used' WHERE ID = '"+blankID+"'";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static void createCustomer(String name, String address, String phone)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to create a customer
            String query = "INSERT INTO customer (name, address, phoneNumber, type, discount, dueBalance)VALUES ('"+name+"', '"+address+"' , '" +phone+"', 'Regular', 'None', '0.0')";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static void endPayment()
    {
        window.setScene(scene);
        payment_amount_textField.clear();
        payment_tax_textField.clear();
        payment_origin_textField.clear();
        payment_destination_textField.clear();
        payment_payMethod_choiceBox.setValue("Cash");
        payment_currency_choiceBox.setValue("GBP");
        saleDate = "";
        payByDate = "";
        isPaid = "";
        if (payment_payLateYES_radioButton.isSelected())
        {
            payment_payLateYES_radioButton.setSelected(false);
        }
        else if (payment_payLateNO_radioButton.isSelected())
        {
            payment_payLateNO_radioButton.setSelected(false);
        }

        if (payment_domestic_radioButton.isSelected())
        {
            payment_domestic_radioButton.setSelected(false);
        }
        else if (payment_interline_radioButton.isSelected())
        {
            payment_interline_radioButton.setSelected(false);
        }
    }

    private static String getTAname()
    {
        String name = "";
        try
        {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to get the current logged in TA
            String query = "SELECT name FROM staff WHERE StaffType = 'Travel Advisor' AND status = 'loggedIn'";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next())
            {
                name = resultSet.getString(1);
            }
            else
            {
                System.out.println("Something Wronmg");
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return name;
    }
}
