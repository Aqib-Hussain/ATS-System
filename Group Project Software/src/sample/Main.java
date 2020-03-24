package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import OM_GUI.*;
import SA_GUI.TicketTypes;
import SA_GUI.TravelAdvisors;
import SA_GUI.ViewBlankStock_SA;
import TA_GUI.CurrencyExchange;
import TA_GUI.SellTicket;
import TA_GUI.ViewReports;
import Staff.OfficeManager;
import Staff.SystemAdmin;
import Staff.TravelAdvisor;
import Staff.StaffAccount;

public class Main extends Application
{
    // Stage
    Stage window;

    // Log-in Scenes
    Scene login;
    // Office Manager Scenes
    Scene OM_mainMenu;
    // System Admin Scenes
    Scene SA_mainMenu;
    // Travel Advisor Scenes
    Scene TA_mainMenu;

    //------------------------------------User-------------------------------\\
    StaffAccount staffAccount = new StaffAccount();
    OfficeManager officeManager = new OfficeManager();
    SystemAdmin systemAdmin = new SystemAdmin();
    TravelAdvisor travelAdvisor = new TravelAdvisor();

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
        // Labels
        Label title = new Label("ATS-System Login");
        title.setFont(Font.font(27));

        Label emailLabel = new Label("Email");
        emailLabel.setPadding(new Insets(0,0,-5,0));

        Label passwordLabel = new Label("Password");
        passwordLabel.setPadding(new Insets(0,0,-5,0));

        // TextBoxes
        TextField emailText = new TextField();
        emailText.setPromptText("Username");
        emailText.setMaxWidth(200);

        PasswordField passwordText = new PasswordField();
        passwordText.setPromptText("Password");
        passwordText.setMaxWidth(200);

        // Buttons
        Button loginButton = new Button("Log-In");
        loginButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(emailText.getText().equals(officeManager.getEmail()) && passwordText.getText().equals(officeManager.getPassword()))
                {
                    window.setScene(OM_mainMenu);
                    emailText.clear();
                    passwordText.clear();
                }
                else if (emailText.getText().equals(systemAdmin.getEmail()) && passwordText.getText().equals(systemAdmin.getPassword()))
                {
                    window.setScene(SA_mainMenu);
                    emailText.clear();
                    passwordText.clear();
                }
                else if (emailText.getText().equals(travelAdvisor.getEmail()) && passwordText.getText().equals(travelAdvisor.getPassword()))
                {
                    window.setScene(TA_mainMenu);
                    emailText.clear();
                    passwordText.clear();
                }
                else
                {
                    AlertBox.display("Wrong Log-in Info", "Please provide correct details");
                    emailText.clear();
                    passwordText.clear();
                }
            }
        });

        // Layout
        VBox top_layout_login = new VBox();
        top_layout_login.setAlignment(Pos.CENTER);
        top_layout_login.setPadding(new Insets(0,0,10,0));
        top_layout_login.getChildren().add(title);

        VBox center_layout_login = new VBox(15);
        center_layout_login.setAlignment(Pos.CENTER);
        center_layout_login.getChildren().addAll(emailLabel, emailText, passwordLabel, passwordText, loginButton);

        BorderPane root_layout_login = new BorderPane();
        root_layout_login.setPadding(new Insets(10,10,10,10));
        root_layout_login.setTop(top_layout_login);
        root_layout_login.setCenter(center_layout_login);

        // Create the Scene
        login = new Scene(root_layout_login, 850, 600);

                                    //******************************************//
                                    //*****        Office Manager GUI     ******//
                                    //******************************************//

        //----------------------------Office Manager Main menu-----------------------------\\
        // Labels
        Label welcome_message_OM = new Label("Hello " + officeManager.getUserType());
        welcome_message_OM.setFont(Font.font(20));

        // Buttons
        Button generateReport = new Button("Generate a Global Report");
        generateReport.setMinWidth(250);
        generateReport.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                GloReportType.display("Report Type");
            }
        });
        Button viewCustomers = new Button("View Customers");
        viewCustomers.setMinWidth(250);
        viewCustomers.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                CustomerList.display("Customer List");
            }
        });
        Button viewBlankStock_OM = new Button("Blank Stock");
        viewBlankStock_OM.setMinWidth(250);
        viewBlankStock_OM.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                ViewBlankStock_OM.display("Blank Stock");
            }
        });
        Button setCommissionRates = new Button("Commission Rates");
        setCommissionRates.setMinWidth(250);
        setCommissionRates.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                CommissionRates.display("Commission Rate");
            }
        });
        Button viewRefundLog = new Button("Refund Log");
        viewRefundLog.setMinWidth(250);
        viewRefundLog.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                RefundLog.display("Refund Log");
            }
        });
        Button logOutButton_OM = new Button("Log-Out");
        logOutButton_OM.setOnAction(new EventHandler<ActionEvent>()
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

        //---Layout---\\
        // Button Layout
        VBox center_layout_OM = new VBox(10);
        center_layout_OM.setAlignment(Pos.CENTER);
        center_layout_OM.getChildren().addAll(generateReport,viewCustomers, viewBlankStock_OM, setCommissionRates, viewRefundLog);

        HBox bottom_layout_OM = new HBox();
        bottom_layout_OM.getChildren().add(logOutButton_OM);
        bottom_layout_OM.setAlignment(Pos.BASELINE_RIGHT);

        VBox top_layout_OM = new VBox();
        top_layout_OM.getChildren().add(welcome_message_OM);
        top_layout_OM.setAlignment(Pos.CENTER);

        // Root Layout
        BorderPane OM_rootLayout = new BorderPane();
        OM_rootLayout.setPadding(new Insets(10,10,10,10));
        OM_rootLayout.setTop(top_layout_OM);
        OM_rootLayout.setCenter(center_layout_OM);
        OM_rootLayout.setBottom(bottom_layout_OM);

        // Create Scene
        OM_mainMenu = new Scene(OM_rootLayout, 850, 600);

                                    //******************************************//
                                    //*****   System Administrator GUI    ******//
                                    //******************************************//
        //----------------------------System Admin Main menu-----------------------------\\
        // Label
        Label welcome_message_SA = new Label("Hello " + systemAdmin.getUserType());
        welcome_message_SA.setFont(Font.font(20));

        // Buttons
        Button generateStockReport = new Button("Generate Stock Over Report");
        generateStockReport.setMinWidth(250);

        Button backUp = new Button("Back-Up System");
        backUp.setMinWidth(250);

        Button restore = new Button("Restore System");
        restore.setMinWidth(250);

        Button viewTravelAdvisors = new Button("View Travel Advisors");
        viewTravelAdvisors.setMinWidth(250);
        viewTravelAdvisors.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                TravelAdvisors.display("Travel Advisors");
            }
        });

        Button viewTicketTypes = new Button("View Ticket Types");
        viewTicketTypes.setMinWidth(250);
        viewTicketTypes.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                TicketTypes.display("Ticket Types");
            }
        });

        Button viewBlankStock_SA = new Button("View Blank Stock");
        viewBlankStock_SA.setMinWidth(250);
        viewBlankStock_SA.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                ViewBlankStock_SA.display("Blank Stock");
            }
        });

        Button logOutButton_SA = new Button("Log-Out");
        logOutButton_SA.setOnAction(new EventHandler<ActionEvent>() {
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

        //---Layout---\\
        // Button Layout
        VBox center_layout_SA = new VBox(10);
        center_layout_SA.getChildren().addAll(generateStockReport, backUp, restore, viewTicketTypes, viewBlankStock_SA, viewTravelAdvisors);
        center_layout_SA.setAlignment(Pos.CENTER);

        HBox bottom_layout_SA = new HBox();
        bottom_layout_SA.getChildren().add(logOutButton_SA);
        bottom_layout_SA.setAlignment(Pos.BASELINE_RIGHT);

        VBox top_layout_SA = new VBox();
        top_layout_SA.getChildren().add(welcome_message_SA);
        top_layout_SA.setAlignment(Pos.CENTER);

        // Root Layout
        BorderPane SA_rootLayout = new BorderPane();
        SA_rootLayout.setPadding(new Insets(10,10,10,10));
        SA_rootLayout.setTop(top_layout_SA);
        SA_rootLayout.setCenter(center_layout_SA);
        SA_rootLayout.setBottom(bottom_layout_SA);

        // Create Scene
        SA_mainMenu = new Scene(SA_rootLayout, 850, 600);

                                    //******************************************//
                                    //*****    Travel Advisor GUI GUI     ******//
                                    //******************************************//
        //----------------------------System Admin Main menu-----------------------------\\
        // Label
        Label welcome_message_TA = new Label("Hello " + travelAdvisor.getUserType());
        welcome_message_TA.setFont(Font.font(20));

        // Buttons
        Button generateIndividualReport = new Button("Generate Individual Report");
        generateIndividualReport.setMinWidth(250);
        generateIndividualReport.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                GloReportType.display("Report Type");
            }
        });

        Button sellTicket = new Button("Sell Ticket");
        sellTicket.setMinWidth(250);
        sellTicket.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                SellTicket.display("Ticket Selling");
            }
        });

        Button viewReports = new Button("View Reports");
        viewReports.setMinWidth(250);
        viewReports.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                ViewReports.display("Recorded Reports");
            }
        });

        Button setCurrExchangeRate = new Button("Set Currency Exchange Rate");
        setCurrExchangeRate.setMinWidth(250);
        setCurrExchangeRate.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                CurrencyExchange.display("Currency Exchange Rates");
            }
        });

        Button logOutButton_TA = new Button("Log-Out");
        logOutButton_TA.setOnAction(new EventHandler<ActionEvent>() {
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

        //---Layout---\\
        // Button Layout
        VBox center_layout_TA = new VBox(10);
        center_layout_TA.getChildren().addAll(generateIndividualReport, sellTicket, viewReports, setCurrExchangeRate);
        center_layout_TA.setAlignment(Pos.CENTER);

        HBox bottom_layout_TA = new HBox();
        bottom_layout_TA.getChildren().add(logOutButton_TA);
        bottom_layout_TA.setAlignment(Pos.BASELINE_RIGHT);

        VBox top_layout_TA = new VBox();
        top_layout_TA.getChildren().add(welcome_message_TA);
        top_layout_TA.setAlignment(Pos.CENTER);

        // Root Layout
        BorderPane TA_rootLayout = new BorderPane();
        TA_rootLayout.setPadding(new Insets(10,10,10,10));
        TA_rootLayout.setTop(top_layout_TA);
        TA_rootLayout.setCenter(center_layout_TA);
        TA_rootLayout.setBottom(bottom_layout_TA);

        // Create Scene
        TA_mainMenu = new Scene(TA_rootLayout, 850, 600);

        // Start-up
        window.setScene(login);
        window.setTitle("ATS System");
        window.show();
    }

    public void goToLogin()
    {
        window.setScene(login);
    }

}
