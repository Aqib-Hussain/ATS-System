package OM_GUI;

import Database.DBConnectivity;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Blank;
import sample.Staff.TravelAdvisor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewBlankStock_OM
{
    // Stage
    static Stage window = new Stage();

    // Layouts
    static BorderPane main_root_layout = new BorderPane();
    static BorderPane assign_root_layout = new BorderPane();


    // Scenes
    static Scene main_scene = new Scene(main_root_layout);
    static Scene assign_scene = new Scene(assign_root_layout);


    // Database
    static DBConnectivity dbConnectivity = new DBConnectivity();
    static Connection connection = dbConnectivity.getConnection();

    // Table View
    static TableView<Blank> blanksTable;
    static ObservableList<Blank> blanks = FXCollections.observableArrayList();

    static TableView<TravelAdvisor> travelAdvisorTable;
    static ObservableList<TravelAdvisor> travelAdvisors = FXCollections.observableArrayList();

    static String currentDate = "";

    public static void display()
    {
        // Window takes priority until taken care of
        window.setTitle("Blank Stock");
        window.setHeight(600);
        window.setWidth(780);
        window.setResizable(false);

        // Labels
        Label page_info = new Label("Blank Stock");
        page_info.getStyleClass().add("label-title");

        Label searchBlank_label = new Label("Search by blank:  ");

        // Table
        TableColumn<Blank, String> blankIDColumn = new TableColumn<>("Blank ID");
        blankIDColumn.setMinWidth(120);
        blankIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Blank, String> blankTypeColumn = new TableColumn<>("Blank Type");
        blankTypeColumn.setMinWidth(120);
        blankTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Blank, String> blankAssignedToColumn = new TableColumn<>("Assigned To");
        blankAssignedToColumn.setMinWidth(120);
        blankAssignedToColumn.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));

        TableColumn<Blank, String> blankReceivedDateColumn = new TableColumn<>("Received Date");
        blankReceivedDateColumn.setMinWidth(120);
        blankReceivedDateColumn.setCellValueFactory(new PropertyValueFactory<>("receivedDate"));

        TableColumn<Blank, String> blankAssignedDateColumn = new TableColumn<>("Assigned Date");
        blankAssignedDateColumn.setMinWidth(120);
        blankAssignedDateColumn.setCellValueFactory(new PropertyValueFactory<>("assignedDate"));

        TableColumn<Blank, String> blankStateColumn = new TableColumn<>("Blank State");
        blankStateColumn.setMinWidth(120);
        blankStateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

        blanksTable = new TableView<>();
        blanksTable.setItems(getBlanks());
        blanksTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        blanksTable.getColumns().addAll(blankIDColumn, blankTypeColumn, blankAssignedToColumn, blankReceivedDateColumn, blankAssignedDateColumn, blankStateColumn);

        // TextFields
        FilteredList<Blank> blankFilteredList = new FilteredList<>(blanks, b -> true);

        TextField selectBlank_textField = new TextField();
        selectBlank_textField.setPromptText("Search by blank...");
        selectBlank_textField.setMinWidth(100);
        // Creating a listener for a search function
        selectBlank_textField.setOnKeyReleased(e ->
        {
            selectBlank_textField.textProperty().addListener((observable, oldValue, newValue) ->
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
        sortedBlanks.comparatorProperty().bind(blanksTable.comparatorProperty());
        // Adding the sorted items to the table
        blanksTable.setItems(sortedBlanks);

        // Buttons
        Button re_assign = new Button("Re-Assign");
        re_assign.setMinWidth(100);
        re_assign.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                ReAssignBlanks.display();
            }
        });

        Button assign = new Button("Assign");
        assign.setMinWidth(100);
        assign.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!(blanksTable.getSelectionModel().isEmpty()))
                {
                    window.setScene(assign_scene);
                }
                else
                {
                    SelectABlankToContinue.display();
                }
            }
        });

        Button help = new Button("Help");
        help.setMinWidth(100);
        help.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                HelpAlert.display();
            }
        });

        Button close = new Button("Close");
        close.getStyleClass().add("button-exit");
        close.setMinSize(75,25);
        close.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                window.close();
                ReAssignBlanks.close();
            }
        });

        // Layout
        VBox top_layout = new VBox();
        top_layout.setAlignment(Pos.CENTER);
        top_layout.getChildren().add(page_info);
        top_layout.setPadding(new Insets(0, 0, 20, 0));

        VBox list_layout = new VBox(50);
        list_layout.setAlignment(Pos.CENTER);
        list_layout.getChildren().add(blanksTable);
        list_layout.setPadding(new Insets(0, 0, 20, 0));

        HBox searchBar_layout = new HBox(10);
        searchBar_layout.setAlignment(Pos.CENTER_RIGHT);
        searchBar_layout.getChildren().addAll(searchBlank_label, selectBlank_textField );
        searchBar_layout.setPadding(new Insets(0,0,5,0));

        HBox button_layout = new HBox(60);
        button_layout.setAlignment(Pos.CENTER);
        button_layout.getChildren().addAll(assign, re_assign, help);
        button_layout.setPadding(new Insets(0, 0, 10, 0));

        BorderPane center_layout = new BorderPane();
        center_layout.setTop(searchBar_layout);
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


        //************************* Assign Blanks *************************\\
        // Labels
        Label assign_page_info = new Label("Travel Advisors");
        assign_page_info.getStyleClass().add("label-title");

        Label search = new Label("Search by name:  ");

        // Table
        TableColumn<TravelAdvisor, String> iDColumn = new TableColumn<>("ID");
        iDColumn.setMinWidth(100);
        iDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<TravelAdvisor, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(225);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<TravelAdvisor, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(262);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        travelAdvisorTable = new TableView<>();
        travelAdvisorTable.setItems(getAdvisors());
        travelAdvisorTable.getColumns().addAll(iDColumn, nameColumn, emailColumn);

        // Button
        Button select = new Button("Confirm");
        select.setMinWidth(100);
        select.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!(travelAdvisorTable.getSelectionModel().isEmpty()))
                {
                    getCurrentDate();
                    for(Blank b : blanksTable.getSelectionModel().getSelectedItems())
                    {
                        assignBlanks(travelAdvisorTable.getSelectionModel().getSelectedItem().getName(), b.getId(), currentDate);
                    }
                    endAssign();
                    refreshBlankTable();
                    window.setScene(main_scene);
                }
                else
                {
                    SelectAdvisorAlert.display();
                }
            }
        });

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("button-exit");
        cancel.setMinWidth(75);
        cancel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                travelAdvisorTable.getSelectionModel().clearSelection();
                window.setScene(main_scene);
            }
        });

        // TextFields
        FilteredList<TravelAdvisor> advisorFilteredList = new FilteredList<>(travelAdvisors, b -> true);

        TextField selectTA_textField = new TextField();
        selectTA_textField.setPromptText("Search by name...");
        selectTA_textField.setMinWidth(100);
        // Creating a listener for a search function
        selectTA_textField.setOnKeyReleased(e ->
        {
            selectTA_textField.textProperty().addListener((observable, oldValue, newValue) ->
            {
                advisorFilteredList.setPredicate(advisor ->
                {
                    if(newValue == null || newValue.isEmpty())
                    {
                        return true;
                    }

                    if(advisor.getName().toLowerCase().contains(newValue.toLowerCase()))
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
        SortedList<TravelAdvisor> sortedAdvisors = new SortedList<>(advisorFilteredList);
        // Binding the sorted list comparator to the table view comparator
        sortedAdvisors.comparatorProperty().bind(travelAdvisorTable.comparatorProperty());
        // Adding the sorted items to the table
        travelAdvisorTable.setItems(sortedAdvisors);

        // Layout
        VBox assign_top_layout = new VBox();
        assign_top_layout.setAlignment(Pos.CENTER);
        assign_top_layout.getChildren().add(assign_page_info);
        assign_top_layout.setPadding(new Insets(0,0,20,0));

        VBox assign_list_layout = new VBox(50);
        assign_list_layout.setAlignment(Pos.CENTER);
        assign_list_layout.getChildren().add(travelAdvisorTable);
        assign_list_layout.setPadding(new Insets(0,0,20,0));

        HBox assign_searchBar_layout = new HBox(10);
        assign_searchBar_layout.setAlignment(Pos.CENTER_RIGHT);
        assign_searchBar_layout.getChildren().addAll(search, selectTA_textField);
        assign_searchBar_layout.setPadding(new Insets(0,0,5,0));

        HBox assign_button_layout = new HBox(30);
        assign_button_layout.setAlignment(Pos.CENTER);
        assign_button_layout.getChildren().add(select);
        assign_button_layout.setPadding(new Insets(0,0,20,0));

        BorderPane assign_center_layout = new BorderPane();
        assign_center_layout.setTop(assign_searchBar_layout);
        assign_center_layout.setCenter(assign_list_layout);
        assign_center_layout.setBottom(assign_button_layout);

        HBox assign_bottom_layout = new HBox(363);
        assign_bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        assign_bottom_layout.getChildren().addAll(cancel);

        assign_root_layout.setPadding(new Insets(10, 10, 10, 10));
        assign_root_layout.setTop(assign_top_layout);
        assign_root_layout.setCenter(assign_center_layout);
        assign_root_layout.setBottom(assign_bottom_layout);

        // Scene
        assign_scene.getStylesheets().add("Stylesheet.css");

        // Start window
        window.setScene(main_scene);
        window.show();
    }

    private static void assignBlanks(String name, String blankID, String currentDate)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to update assigned blanks

            String query = "UPDATE blank SET assignedTo = '"+name+"', assignedDate = '"+currentDate+"', state = 'Assigned' WHERE ID = '"+blankID+"'";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static ObservableList<TravelAdvisor> getAdvisors()
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to find matching travel advisors
            String query = "SELECT * FROM staff WHERE StaffType = 'Travel Advisor'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                travelAdvisors.add(new TravelAdvisor(resultSet.getString("name"),
                        resultSet.getString("ID"),
                        resultSet.getString("email"),
                        resultSet.getString("address")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return travelAdvisors;
    }

    private static void getCurrentDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        currentDate = dtf.format(now);
    }

    private static void refreshBlankTable()
    {
        blanks.clear();
        getBlanks();
    }

    private static void endAssign()
    {
        blanksTable.getSelectionModel().clearSelection();
        travelAdvisorTable.getSelectionModel().clearSelection();
    }

    private static ObservableList<Blank> getBlanks()
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to select all the blanks
            String query = "SELECT * FROM blank";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                blanks.add(new Blank(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return blanks;
    }
}
