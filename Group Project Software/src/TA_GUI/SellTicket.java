package TA_GUI;

import Database.DBConnectivity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SellTicket
{
    // Database
    static DBConnectivity dbConnectivity = new DBConnectivity();
    static Connection connection = dbConnectivity.getConnection();

    // Layouts
    static BorderPane root_layout = new BorderPane();
    static BorderPane CC_root_layout = new BorderPane();
    static BorderPane EC_root_Layout = new BorderPane();

    // Scenes
    static Scene scene = new Scene(root_layout);
    static Scene CC_scene = new Scene(CC_root_layout);
    static Scene EC_scene = new Scene(EC_root_Layout);

    // sample.Customer id
    static int cust_id;

    public static void display(String title)
    {
        Stage window = new Stage();
        // **************Selection Window***************** \\
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(400);
        window.setHeight(450);
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
        window.setScene(scene);
        scene.getStylesheets().add("Stylesheet.css");


        // *************Create Customer Window************ \\
        // Label
        Label createCust_Label = new Label("Enter new customer details");
        createCust_Label.getStyleClass().add("label-title");
        createCust_Label.setFont(Font.font(16));

        Label firstName = new Label("Firstname:");
        Label surname = new Label("Surname:");
        Label address = new Label("Address:");
        Label phoneNumber = new Label("Phone No:");

        //TextFields
        TextField custFirstname = new TextField();
        custFirstname.setPromptText("Firstname");
        custFirstname.setMinWidth(200);

        TextField custSurname = new TextField();
        custSurname.setPromptText("Surname");
        custSurname.setMinWidth(200);

        TextField custAddress = new TextField();
        custAddress.setPromptText("Address");
        custAddress.setMinWidth(200);

        TextField custPhoneNum = new TextField();
        custPhoneNum.setPromptText("Phone Number");
        custPhoneNum.setMinWidth(200);

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
//                    String idQuery = "SELECT 'ID' FROM customer";
//                    ResultSet resultSet = statement.executeQuery(idQuery);
//                    cust_id = resultSet.getInt(1);
//                    cust_id++;

                    // SQL query to find matching email and password
                    String query = "INSERT INTO customer VALUES ('00003', '"+custFirstname.getText()+"', '" + custSurname.getText() + "', '" + custAddress.getText() + "', '" + custPhoneNum.getText() +"')";
                    statement.executeUpdate(query);
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
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
        GridPane.setConstraints(firstName, 0, 0);
        GridPane.setConstraints(custFirstname, 1, 0);
        GridPane.setConstraints(surname, 0, 1);
        GridPane.setConstraints(custSurname, 1, 1);
        GridPane.setConstraints(address, 0, 2);
        GridPane.setConstraints(custAddress, 1, 2);
        GridPane.setConstraints(phoneNumber, 0, 3);
        GridPane.setConstraints(custPhoneNum, 1, 3);
        CC_centre_layout.getChildren().addAll(firstName, custFirstname, surname, custSurname, address, custAddress, phoneNumber,custPhoneNum);

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
//        CC_root_layout.setLeft(CC_left_layout);

        // Scene
        CC_scene.getStylesheets().add("Stylesheet.css");

        // *****************Existing Customer Window****************** \\
        // Label
        Label EC_details_label = new Label("Enter Customer Details");
        EC_details_label.getStyleClass().add("label-title");
        EC_details_label.setFont(Font.font(16));

        Label EC_firstName = new Label("Firstname:");
        Label EC_surname = new Label("Surname:");
        Label EC_address = new Label("Address:");

        // Buttons
        Button EC_search = new Button("Search");
        EC_search.setMinWidth(150);

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

        //TextFields
        TextField EC_custFirstname = new TextField();
        EC_custFirstname.setPromptText("Firstname");
        EC_custFirstname.setMinWidth(200);

        TextField EC_custSurname = new TextField();
        EC_custSurname.setPromptText("Surname");
        EC_custSurname.setMinWidth(200);

        TextField EC_custAddress = new TextField();
        EC_custAddress.setPromptText("Address");
        EC_custAddress.setMinWidth(200);

        //---Layout---\\
//        VBox EC_centre_layout = new VBox(10);
//        EC_centre_layout.getChildren().addAll(EC_custFirstname, EC_custSurname, EC_custAddress);
//        EC_centre_layout.setAlignment(Pos.CENTER);
//
//        VBox EC_left_layout = new VBox(21);
//        EC_left_layout.getChildren().addAll(EC_firstName, EC_surname, EC_address);
//        EC_left_layout.setAlignment(Pos.CENTER_LEFT);
        GridPane EC_centre_layout = new GridPane();
        EC_centre_layout.setAlignment(Pos.CENTER);
        EC_centre_layout.setHgap(15);
        EC_centre_layout.setVgap(12);
        GridPane.setConstraints(EC_firstName, 0, 0);
        GridPane.setConstraints(EC_custFirstname, 1, 0);
        GridPane.setConstraints(EC_surname, 0, 1);
        GridPane.setConstraints(EC_custSurname, 1, 1);
        GridPane.setConstraints(EC_address, 0, 2);
        GridPane.setConstraints(EC_custAddress, 1, 2);
        EC_centre_layout.getChildren().addAll(EC_firstName, EC_custFirstname, EC_surname, EC_custSurname, EC_address, EC_custAddress);



        HBox EC_bottom_layout = new HBox(139);
        EC_bottom_layout.getChildren().addAll(EC_search, EC_cancel);
        EC_bottom_layout.setAlignment(Pos.CENTER);

        VBox EC_top_layout = new VBox();
        EC_top_layout.getChildren().add(EC_details_label);
        EC_top_layout.setAlignment(Pos.TOP_CENTER);

        // Root Layout
        EC_root_Layout.setPadding(new Insets(10,10,10,10));
        EC_root_Layout.setTop(EC_top_layout);
        EC_root_Layout.setCenter(EC_centre_layout);
        EC_root_Layout.setBottom(EC_bottom_layout);

        // Scene
        EC_scene.getStylesheets().add("Stylesheet.css");

        // Start window
        window.showAndWait();
    }
}
