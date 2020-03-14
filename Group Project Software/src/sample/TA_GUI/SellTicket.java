package sample.TA_GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.ConfirmLogOut;
import sample.OM_GUI.GloReportType;

public class SellTicket
{
    // Layouts
    static BorderPane root_layout = new BorderPane();
    static BorderPane CC_root_layout = new BorderPane();
    static BorderPane EC_root_Layout = new BorderPane();

    // Scenes
    static Scene scene = new Scene(root_layout);
    static Scene CC_scene = new Scene(CC_root_layout);
    static Scene EC_scene = new Scene(EC_root_Layout);

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
        selectCust.setPadding(new Insets(0,0,13,8));
        // Buttons
        Button existingCust = new Button("Select from existing customers");
        existingCust.setMaxWidth(200);
        existingCust.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(EC_scene);
            }
        });

        Button createCust = new Button("Create customer account");
        createCust.setMaxWidth(200);
        createCust.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(CC_scene);
            }
        });

        Button close = new Button("Close");
        close.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.close();
            }
        });
        // Layout
        VBox button_layout = new VBox(10);
        button_layout.setAlignment(Pos.CENTER);
        button_layout.setSpacing(10);
        button_layout.getChildren().addAll(existingCust, createCust);

        HBox bottom_layout = new HBox();
        bottom_layout.getChildren().add(close);
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);


        root_layout.setPadding(new Insets(11,11,11,11));
        root_layout.setCenter(button_layout);
        root_layout.setBottom(bottom_layout);
        root_layout.setTop(selectCust);


        window.setScene(scene);


        // *************Create Customer Window************ \\
        // Label
        Label createCust_Label = new Label("Enter new customer details");

        Label firstName = new Label("Firstname:");
        Label surname = new Label("Surname:");
        Label address = new Label("Address:");
        Label phoneNumber = new Label("Phone No:");

        // Buttons
        Button create = new Button("Create customer");
        create.setMinSize(125,25);

        Button cancel = new Button("Cancel");
        cancel.setMinSize(75,25);
        cancel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(scene);
            }
        });

        //TextFields
        TextField custFirstname = new TextField();
        custFirstname.setPromptText("Firstname");
        custFirstname.setMaxWidth(200);

        TextField custSurname = new TextField();
        custSurname.setPromptText("Surname");
        custSurname.setMaxWidth(200);

        TextField custAddress = new TextField();
        custAddress.setPromptText("Address");
        custAddress.setMaxWidth(200);

        TextField custPhoneNum = new TextField();
        custPhoneNum.setPromptText("Phone Number");
        custPhoneNum.setMaxWidth(200);

        //---Layout---\\
        // Button Layout
        VBox CC_centre_layout = new VBox(10);
        CC_centre_layout.getChildren().addAll(custFirstname, custSurname, custAddress, custPhoneNum);
        CC_centre_layout.setAlignment(Pos.CENTER);

        VBox CC_left_layout = new VBox(21);
        CC_left_layout.getChildren().addAll(firstName, surname, address, phoneNumber);
        CC_left_layout.setAlignment(Pos.CENTER_LEFT);

        HBox CC_bottom_layout = new HBox(160);
        CC_bottom_layout.getChildren().addAll(create, cancel);
        CC_bottom_layout.setAlignment(Pos.BASELINE_LEFT);

        VBox CC_top_layout = new VBox();
        CC_top_layout.getChildren().add(createCust_Label);
        CC_top_layout.setAlignment(Pos.TOP_CENTER);

        // Root Layout
        CC_root_layout.setPadding(new Insets(10,10,10,10));
        CC_root_layout.setTop(CC_top_layout);
        CC_root_layout.setCenter(CC_centre_layout);
        CC_root_layout.setBottom(CC_bottom_layout);
        CC_root_layout.setLeft(CC_left_layout);

        // *****************Existing Customer Window****************** \\
        // Label
        Label EC_details_label = new Label("Enter Customer Details");

        Label EC_firstName = new Label("Firstname:");
        Label EC_surname = new Label("Surname:");
        Label EC_address = new Label("Address:");

        // Buttons
        Button EC_search = new Button("Search");
        EC_search.setMinSize(125,25);

        Button EC_cancel = new Button("Cancel");
        EC_cancel.setMinSize(75,25);
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
        EC_custFirstname.setMaxWidth(200);

        TextField EC_custSurname = new TextField();
        EC_custSurname.setPromptText("Surname");
        EC_custSurname.setMaxWidth(200);

        TextField EC_custAddress = new TextField();
        EC_custAddress.setPromptText("Address");
        EC_custAddress.setMaxWidth(200);

        //---Layout---\\
        VBox EC_centre_layout = new VBox(10);
        EC_centre_layout.getChildren().addAll(EC_custFirstname, EC_custSurname, EC_custAddress);
        EC_centre_layout.setAlignment(Pos.CENTER);

        VBox EC_left_layout = new VBox(21);
        EC_left_layout.getChildren().addAll(EC_firstName, EC_surname, EC_address);
        EC_left_layout.setAlignment(Pos.CENTER_LEFT);

        HBox EC_bottom_layout = new HBox(160);
        EC_bottom_layout.getChildren().addAll(EC_search, EC_cancel);
        EC_bottom_layout.setAlignment(Pos.BASELINE_LEFT);

        VBox EC_top_layout = new VBox();
        EC_top_layout.getChildren().add(EC_details_label);
        EC_top_layout.setAlignment(Pos.TOP_CENTER);

        // Root Layout
        EC_root_Layout.setPadding(new Insets(10,10,10,10));
        EC_root_Layout.setTop(EC_top_layout);
        EC_root_Layout.setCenter(EC_centre_layout);
        EC_root_Layout.setBottom(EC_bottom_layout);
        EC_root_Layout.setLeft(EC_left_layout);

        window.showAndWait();
    }
}
