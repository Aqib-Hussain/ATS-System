package sample;

import Database.DBConnectivity;
import TA_GUI.ViewBlankStock_TA;
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
import SA_GUI.ViewTravelAdvisors;
import SA_GUI.ViewBlankStock_SA;
import TA_GUI.CurrencyExchange;
import TA_GUI.SellTicket;
import TA_GUI.ViewReports;
import javafx.stage.WindowEvent;
import sample.Staff.SystemAdmin;
import sample.Staff.TravelAdvisor;
import BCrypt.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {
    // Database
    DBConnectivity dbConnectivity = new DBConnectivity();
    Connection connection = dbConnectivity.getConnection();

    SystemAdmin systemAdmin = new SystemAdmin();

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

    // User data

    String officeManagerType = "Manager";
    String systemAdminType = "Administrator";
    String travelAdviorType = "Travel Advisor";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        primaryStage.setResizable(false);
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                if (window.getScene() != login) {
                    ConfirmLogOut.display();
                    if (ConfirmLogOut.isIsLoogedOut()) {
                        window.setScene(login);
                        try {
                            // Connect to the Database
                            Statement statement = connection.createStatement();

                            // SQL query to set status of all staff to loggedOut
                            String updateStatus = "UPDATE staff SET status = 'loggedOut' WHERE status = 'loggedIn'";
                            statement.executeUpdate(updateStatus);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    window.close();
                }
            }
        });


        //--------------------------Log-in Menu------------------------------\\
        // Labels
        Label login_label = new Label("ATS-System Login");
        login_label.getStyleClass().add("label-login");
        login_label.setPadding(new Insets(30, 0, 0, 0));

        Label emailLabel = new Label("Name");
        emailLabel.setFont(Font.font(15));
        emailLabel.setPadding(new Insets(0, 0, -5, 0));

        Label passwordLabel = new Label("Password");
        passwordLabel.setFont(Font.font(15));
        passwordLabel.setPadding(new Insets(0, 0, -5, 0));

        Label OM_login = new Label("Office Manager \nName: Minnie Minx \nPassword: NotiGirl");
        OM_login.setFont(Font.font(16));

        Label SA_login = new Label("System Admin \nName: Arthur Daley \nPassword: LiessaLot");
        SA_login.setFont(Font.font(16));

        Label TA1_login = new Label("Travel Advisor 1 \nName: Penelope Pitstop \nPassword: PinkMobile");
        TA1_login.setFont(Font.font(16));

        Label TA2_login = new Label("Travel Advisor 2 \nName: Dennis Menace \nPassword: Gnasher");
        TA2_login.setFont(Font.font(16));

        // TextBoxes
        TextField nameText = new TextField();
        nameText.setFont(Font.font(16));
        nameText.setPromptText("Name");
        nameText.setMaxWidth(200);

        PasswordField passwordText = new PasswordField();
        passwordText.setFont(Font.font(16));
        passwordText.setPromptText("Password");
        passwordText.setMaxWidth(200);

        // Buttons
        Button loginButton = new Button("Log-In");
        loginButton.getStyleClass().add("button-login");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String hashed = BCrypt.hashpw(passwordText.getText(), BCrypt.gensalt(12));
                boolean loggedIn = false;

                try {
                    // Connect to the Database
                    Statement statement = connection.createStatement();

                    // SQL to set user status to loggedIn
                    String statusQuery = "UPDATE staff SET status = 'loggedIn' WHERE name = '" + nameText.getText() + "'";
                    statement.executeUpdate(statusQuery);

                    // SQL query to find matching email and password
                    String query = "SELECT name, password, StaffType FROM STAFF WHERE name ='" + nameText.getText() + "'";
                    ResultSet resultSet = statement.executeQuery(query);

                    // Execute while the data set is not empty
                    while (resultSet.next()) {
                        if (nameText.getText().equals(resultSet.getString("name")) &&
                                BCrypt.checkpw(passwordText.getText(), hashed) &&
                                passwordText.getText().equals(resultSet.getString("password"))) {
                            if (officeManagerType.equals(resultSet.getString("StaffType"))) {
                                window.setScene(OM_mainMenu);
                                loggedIn = true;
                            } else if (systemAdminType.equals(resultSet.getString("StaffType"))) {
                                window.setScene(SA_mainMenu);
                                loggedIn = true;
                            } else if (travelAdviorType.equals(resultSet.getString("StaffType"))) {
                                window.setScene(TA_mainMenu);
                                loggedIn = true;
                            }
                        }
                        nameText.clear();
                        passwordText.clear();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (!loggedIn) {
                    AlertBox.display("ALERT!", "Please provide correct details");
                    nameText.clear();
                    passwordText.clear();
                }
            }
        });

        Button logOutButton = new Button("Log-Out");
        logOutButton.getStyleClass().add("button-exit");
        logOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ConfirmLogOut.display();
                if (ConfirmLogOut.isIsLoogedOut()) {
                    window.setScene(login);
                    reSetStatus();
                }
            }
        });

        // Layout
        VBox top_layout = new VBox();
        top_layout.setPadding(new Insets(10, 0, 50, 0));
        top_layout.setAlignment(Pos.BASELINE_CENTER);
        top_layout.getChildren().add(login_label);

        HBox bottom_layout = new HBox(20);
        bottom_layout.setAlignment(Pos.CENTER);
        bottom_layout.getChildren().addAll(OM_login, SA_login, TA1_login, TA2_login);

        VBox center_layout = new VBox(15);
        center_layout.setPadding(new Insets(-100, 0, 0, 0));
        center_layout.setAlignment(Pos.CENTER);
        center_layout.getChildren().addAll(emailLabel, nameText, passwordLabel, passwordText, loginButton);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10, 10, 10, 10));
        root_layout.setTop(top_layout);
        root_layout.setBottom(bottom_layout);
        root_layout.setCenter(center_layout);

        // Create the Scene
        login = new Scene(root_layout, 850, 600);
        login.getStylesheets().add("Stylesheet.css");

        //******************************************//
        //*****        Office Manager GUI     ******//
        //******************************************//

        //----------------------------Office Manager Main menu-----------------------------\\
        // Labels
        Label welcome_message_OM = new Label("Hello Office Manager");
        welcome_message_OM.getStyleClass().add("label_title");
        welcome_message_OM.setPadding(new Insets(0, 0, 35, 0));

        // Buttons
        Button generateReport = new Button("Generate a Global Report");
        generateReport.setMinWidth(250);
        generateReport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                GloReportType.display("Report Type");
            }
        });
        Button viewCustomers = new Button("View Customers");
        viewCustomers.setMinWidth(250);
        viewCustomers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CustomerList.display("Customer List");
            }
        });
        Button viewBlankStock_OM = new Button("Blank Stock");
        viewBlankStock_OM.setMinWidth(250);
        viewBlankStock_OM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ViewBlankStock_OM.display("Blank Stock");
            }
        });
        Button setCommissionRates = new Button("Commission Rates");
        setCommissionRates.setMinWidth(250);
        setCommissionRates.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                CommissionRates.display("Commission Rate");
            }
        });
        Button viewRefundLog = new Button("Refund Log");
        viewRefundLog.setMinWidth(250);
        viewRefundLog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                RefundLog.display("Refund Log");
            }
        });

        Button logOutButton_OM = new Button("Log-Out");
        logOutButton_OM.getStyleClass().add("button-exit");
        logOutButton_OM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ConfirmLogOut.display();
                if (ConfirmLogOut.isIsLoogedOut()) {
                    window.setScene(login);
                    reSetStatus();
                }
            }
        });

        //---Layout---\\
        // Button Layout
        VBox button_layout_OM = new VBox(10);
        button_layout_OM.setAlignment(Pos.CENTER);
        button_layout_OM.getChildren().addAll(generateReport, viewCustomers, viewBlankStock_OM, setCommissionRates, viewRefundLog);

        HBox bottom_layout_OM = new HBox();
        bottom_layout_OM.getChildren().add(logOutButton_OM);
        bottom_layout_OM.setAlignment(Pos.BASELINE_RIGHT);

        VBox top_layout_OM = new VBox();

        top_layout_OM.setPadding(new Insets(10, 0, 50, 0));
        top_layout_OM.getChildren().add(welcome_message_OM);
        top_layout_OM.setAlignment(Pos.CENTER);
        // Root Layout
        BorderPane OM_rootLayout = new BorderPane();
        OM_rootLayout.setPadding(new Insets(10, 10, 10, 10));
        OM_rootLayout.setTop(top_layout_OM);
        OM_rootLayout.setCenter(button_layout_OM);
        OM_rootLayout.setBottom(bottom_layout_OM);

        // Create Scene
        OM_mainMenu = new Scene(OM_rootLayout, 850, 600);
        OM_mainMenu.getStylesheets().add("Stylesheet.css");

        //******************************************//
        //*****   System Administrator GUI    ******//
        //******************************************//
        //----------------------------System Admin Main menu-----------------------------\\
        // Label
        Label welcome_message_SA = new Label("Hello Administrator");
        welcome_message_SA.getStyleClass().add("label_title");
        welcome_message_SA.setPadding(new Insets(0, 0, 35, 0));
        welcome_message_SA.setFont(Font.font(20));

        // Buttons
        Button generateStockReport = new Button("Generate Stock Over Report");
        generateStockReport.setMinWidth(250);

        Button backUp = new Button("Back-Up System");
        backUp.setMinWidth(250);
        backUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SystemAdmin.SystemBackUp();
            }
        });

        Button restore = new Button("Restore System");
        restore.setMinWidth(250);
        restore.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SystemAdmin.SystemRestore();
            }
        });

        Button viewTravelAdvisors = new Button("View Travel Advisors");
        viewTravelAdvisors.setMinWidth(250);
        viewTravelAdvisors.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewTravelAdvisors.display("Travel Advisors");
            }
        });

        Button viewTicketTypes = new Button("View Ticket Types");
        viewTicketTypes.setMinWidth(250);
        viewTicketTypes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TicketTypes.display("Ticket Types");
            }
        });

        Button viewBlankStock_SA = new Button("View Blank Stock");
        viewBlankStock_SA.setMinWidth(250);
        viewBlankStock_SA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewBlankStock_SA.display("Blank Stock");
            }
        });

        Button logOutButton_SA = new Button("Log-Out");
        logOutButton_SA.getStyleClass().add("button-exit");
        logOutButton_SA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ConfirmLogOut.display();
                if (ConfirmLogOut.isIsLoogedOut()) {
                    window.setScene(login);
                    reSetStatus();
                }
            }
        });

        //---Layout---\\
        // Button Layout
        VBox button_layout_SA = new VBox(10);
        button_layout_SA.getChildren().addAll(generateStockReport, backUp, restore, viewTicketTypes, viewBlankStock_SA, viewTravelAdvisors);
        button_layout_SA.setAlignment(Pos.CENTER);

        HBox bottom_layout_SA = new HBox();
        bottom_layout_SA.getChildren().add(logOutButton_SA);
        bottom_layout_SA.setAlignment(Pos.BASELINE_RIGHT);

        VBox top_layout_SA = new VBox();
        top_layout_SA.setPadding(new Insets(10, 0, 50, 0));
        top_layout_SA.getChildren().add(welcome_message_SA);
        top_layout_SA.setAlignment(Pos.CENTER);

        // Root Layout
        BorderPane SA_rootLayout = new BorderPane();
        SA_rootLayout.setPadding(new Insets(10, 10, 10, 10));
        SA_rootLayout.setTop(top_layout_SA);
        SA_rootLayout.setCenter(button_layout_SA);
        SA_rootLayout.setBottom(bottom_layout_SA);

        // Create Scene
        SA_mainMenu = new Scene(SA_rootLayout, 850, 600);
        SA_mainMenu.getStylesheets().add("Stylesheet.css");

        //******************************************//
        //*****    Travel Advisor GUI GUI     ******//
        //******************************************//
        //----------------------------Travel Advisor Main menu-----------------------------\\
        // Label
        Label welcome_message_TA = new Label("Hello Travel Advisor");
        welcome_message_TA.getStyleClass().add("label_title");
        welcome_message_TA.setPadding(new Insets(0, 0, 35, 0));
        welcome_message_TA.setFont(Font.font(20));

        // Buttons
        Button generateIndividualReport = new Button("Generate Individual Report");
        generateIndividualReport.setMinWidth(250);
        generateIndividualReport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GloReportType.display("Report Type");
            }
        });

        Button sellTicket = new Button("Sell Ticket");
        sellTicket.setMinWidth(250);
        sellTicket.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SellTicket.display("Ticket Selling");
            }
        });

        Button viewBlankStock_TA = new Button("Your Blank Stock");
        viewBlankStock_TA.setMinWidth(250);
        viewBlankStock_TA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewBlankStock_TA.display("Your Blank Stock");
            }
        });

        Button viewReports = new Button("View Reports");
        viewReports.setMinWidth(250);
        viewReports.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewReports.display("Recorded Reports");
            }
        });

        Button setCurrExchangeRate = new Button("Set Currency Exchange Rate");
        setCurrExchangeRate.setMinWidth(250);
        setCurrExchangeRate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CurrencyExchange.display("Currency Exchange Rates");
            }
        });

        Button logOutButton_TA = new Button("Log-Out");
        logOutButton_TA.getStyleClass().add("button-exit");
        logOutButton_TA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ConfirmLogOut.display();
                if (ConfirmLogOut.isIsLoogedOut()) {
                    window.setScene(login);
                    reSetStatus();
                }
            }
        });

        //---Layout---\\
        // Button Layout
        VBox button_layout_TA = new VBox(10);
        button_layout_TA.getChildren().addAll(generateIndividualReport, sellTicket, viewBlankStock_TA, viewReports, setCurrExchangeRate);
        button_layout_TA.setAlignment(Pos.CENTER);

        HBox bottom_layout_TA = new HBox();
        bottom_layout_TA.getChildren().add(logOutButton_TA);
        bottom_layout_TA.setAlignment(Pos.BASELINE_RIGHT);

        VBox top_layout_TA = new VBox();
        top_layout_TA.setPadding(new Insets(10, 0, 50, 0));
        top_layout_TA.getChildren().add(welcome_message_TA);
        top_layout_TA.setAlignment(Pos.CENTER);

        // Root Layout
        BorderPane TA_rootLayout = new BorderPane();
        TA_rootLayout.setPadding(new Insets(10, 10, 10, 10));
        TA_rootLayout.setTop(top_layout_TA);
        TA_rootLayout.setCenter(button_layout_TA);
        TA_rootLayout.setBottom(bottom_layout_TA);

        // Create Scene
        TA_mainMenu = new Scene(TA_rootLayout, 850, 600);
        TA_mainMenu.getStylesheets().add("Stylesheet.css");

        // Start-up
//        window.setScene(login);
//        window.setTitle("ATS System");
//        window.show();

        ViewTravelAdvisors.display("test");

    }

    public void reSetStatus() {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to set status of all staff to loggedOut
            String updateStatus = "UPDATE staff SET status = 'loggedOut' WHERE status = 'loggedIn'";
            statement.executeUpdate(updateStatus);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


