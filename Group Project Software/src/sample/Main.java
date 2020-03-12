package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.OM_GUI.*;

public class Main extends Application
{
    // Stage
    Stage window;

    // Log-in Scenes
    Scene login;
    // Office Manager Scenes
    Scene OM_mainMenu, OM_reportType, OM_customerList, OM_editDiscount, OM_editStatus, OM_blankStock,
            OM_assignBlank, OM_reAssignBlank, OM_setCommissionRates, OM_refundLog;
    // System Admin Scenes
    Scene SA_mainMenu;
    // Travel Advisor Scenes
    Scene TA_mainMenu;

    //------------------------------------User-------------------------------\\
    User user = new User();
    OfficeManager officeManager = new OfficeManager("Office Manager");

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        window = primaryStage;
        primaryStage.setResizable(false);

        //--------------------------Log-in Menu------------------------------\\
        // Layout
        VBox login_layout = new VBox(20);
        login_layout.setAlignment(Pos.CENTER);
        // Labels
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");
        // TextBoxes
        TextField emailText = new TextField();
        emailText.setPromptText("Username");
        emailText.setMaxWidth(200);
        emailText.setMaxHeight(25);
        emailText.setMinHeight(25);
        PasswordField passwordText = new PasswordField();
        passwordText.setPromptText("Password");
        passwordText.setMaxWidth(200);
        passwordText.setMaxHeight(25);
        passwordText.setMinHeight(25);
        // Buttons
        Button loginButton = new Button("Log-In");
        loginButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(emailText.getText().equals(user.getEmail()) && passwordText.getText().equals(user.getPassword()))
                {
                    window.setScene(OM_mainMenu);
                    emailText.clear();
                    passwordText.clear();
                }
                else
                {
                    AlertBox.display("ALERT!", "Please provide correct details");
                    emailText.clear();
                    passwordText.clear();
                }
            }
        });
        // Add Elements
        login_layout.getChildren().addAll(emailLabel, emailText, passwordLabel, passwordText, loginButton);
        // Create the Scene
        login = new Scene(login_layout, 850, 600);

        //******************************************//
        //*****        Office Manager GUI     ******//
        //******************************************//

        //----------------------------1.0 Main menu-----------------------------\\
        // Labels
        Label userTypeLabel = new Label("Hello " + officeManager.getUserType());
        // TextBoxes
        // Buttons
        Button generateReport = new Button("Generate a Global Report");
        generateReport.setMaxWidth(250);
        generateReport.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                ReportType.display("Report Type", "Please select a Report Type");
            }
        });
        Button viewCustomers = new Button("View Customers");
        viewCustomers.setMaxWidth(250);
        viewCustomers.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                CustomerList.display("Customer List");
            }
        });
        Button viewBlankStock = new Button("Blank Stock");
        viewBlankStock.setMaxWidth(250);
        viewBlankStock.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                ViewBlankStock.display("Blank Stock");
            }
        });
        Button setCommissionRates = new Button("Commission Rates");
        setCommissionRates.setMaxWidth(250);
        setCommissionRates.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                CommissionRates.display("Commission Rate");
            }
        });
        Button viewRefundLog = new Button("Refund Log");
        viewRefundLog.setMaxWidth(250);
        viewRefundLog.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                RefundLog.display("Refund Log");
            }
        });
        Button logOutButton = new Button("Log-Out!");
        logOutButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                ConfirmLogOut.display();
                if(ConfirmLogOut.isIsLoogedOut())
                {
                    window.setScene(login);
                }
            }
        });
        // Layout
        // Button Layout
        VBox OM_MainMenu = new VBox(10);
        OM_MainMenu.setAlignment(Pos.CENTER);
        // Root Layout
        BorderPane OM_rootLayout = new BorderPane();
        OM_rootLayout.setPadding(new Insets(10,10,10,10));
        OM_rootLayout.setCenter(OM_MainMenu);
        OM_rootLayout.setBottom(logOutButton);
        // Add Elements
        OM_MainMenu.getChildren().addAll(userTypeLabel,generateReport,viewCustomers, viewBlankStock,
                setCommissionRates, viewRefundLog);
        // Create Scene
        OM_mainMenu = new Scene(OM_rootLayout, 850, 600);

        // 1.2 Customer List
        // Labels
        // TextBoxes
        // Buttons
        // Add Elements
        // Create Scene

        // 1.2.1 Edit Discount
        // Labels
        // TextBoxes
        // Buttons
        // Add Elements
        // Create Scene

        // 1.2.2 Edit Status
        // Labels
        // TextBoxes
        // Buttons
        // Add Elements
        // Create Scene

        // 1.3 Blank Stock List
        // Labels
        // TextBoxes
        // Buttons
        // Add Elements
        // Create Scene

        // 1.3.1 Re-assign Blanks
        // Labels
        // TextBoxes
        // Buttons
        // Add Elements
        // Create Scene

        // 1.3.2 Assign Blanks
        // Labels
        // TextBoxes
        // Buttons
        // Add Elements
        // Create Scene

        // 1.4 Set Commission Rates Menu
        // Labels
        // TextBoxes
        // Buttons
        // Add Elements
        // Create Scene

        // 1.5 Refund Log
        // Labels
        // TextBoxes
        // Buttons
        // Add Elements
        // Create Scene
        //******************************************//
        //*****   System Administrator GUI    ******//
        //******************************************//



        //******************************************//
        //*****    Travel Advisor GUI GUI     ******//
        //******************************************//


        // Start-up
        window.setScene(login);
        window.setTitle("Hello");
        window.show();
    }

    public void goToLogin()
    {
        window.setScene(login);
    }

}
