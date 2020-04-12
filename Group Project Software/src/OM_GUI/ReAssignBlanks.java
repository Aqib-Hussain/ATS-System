package OM_GUI;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Blank;
import sample.Staff.TravelAdvisor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReAssignBlanks
{
    // Database
    static DBConnectivity dbConnectivity = new DBConnectivity();
    static Connection connection = dbConnectivity.getConnection();

    //--ReAssign_TableView
    static TableView<Blank> reAssignBlankTable;
    static ObservableList<Blank> reAssignblanks = FXCollections.observableArrayList();

    static TableView<TravelAdvisor> reAssignAdvisorsTable;
    static ObservableList<TravelAdvisor> reAssignAdvisors = FXCollections.observableArrayList();

    // Window
    static Stage window = new Stage();

    // Layout
    static BorderPane reAssign_root_layout = new BorderPane();

    // Scenes
    static Scene reAssign_scene = new Scene(reAssign_root_layout);

    public static void display()
    {
        // Window takes priority until taken care of
        window.setTitle("Blank Stock");
        window.sizeToScene();
        window.setX(300);
        window.setResizable(false);

        // Labels
        Label ReAssign_page_info = new Label("Re-Assign Blanks");
        ReAssign_page_info.getStyleClass().add("label-title");

        Label ReAssign_search = new Label("Search by name:  ");
        Label ReAssign_searchBlank = new Label("Search by blank:  ");

        // Table
        TableColumn<Blank, String> ReAssign_blankIDColumn = new TableColumn<>("Blank ID");
        ReAssign_blankIDColumn.setMinWidth(120);
        ReAssign_blankIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Blank, String> ReAssign_blankTypeColumn = new TableColumn<>("Blank Type");
        ReAssign_blankTypeColumn.setMinWidth(120);
        ReAssign_blankTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Blank, String> ReAssign_blankAssignedToColumn = new TableColumn<>("Assigned To");
        ReAssign_blankAssignedToColumn.setMinWidth(120);
        ReAssign_blankAssignedToColumn.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));

        TableColumn<Blank, String> ReAssign_blankReceivedDateColumn = new TableColumn<>("Received Date");
        ReAssign_blankReceivedDateColumn.setMinWidth(120);
        ReAssign_blankReceivedDateColumn.setCellValueFactory(new PropertyValueFactory<>("receivedDate"));

        TableColumn<Blank, String> ReAssign_blankAssignedDateColumn = new TableColumn<>("Assigned Date");
        ReAssign_blankAssignedDateColumn.setMinWidth(120);
        ReAssign_blankAssignedDateColumn.setCellValueFactory(new PropertyValueFactory<>("assignedDate"));

        TableColumn<Blank, String> ReAssign_blankStateColumn = new TableColumn<>("Blank State");
        ReAssign_blankStateColumn.setMinWidth(120);
        ReAssign_blankStateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

        reAssignBlankTable = new TableView<>();
        reAssignBlankTable.setItems(getBlanks());
        reAssignBlankTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        reAssignBlankTable.getColumns().addAll(ReAssign_blankIDColumn, ReAssign_blankTypeColumn, ReAssign_blankAssignedToColumn, ReAssign_blankReceivedDateColumn, ReAssign_blankAssignedDateColumn, ReAssign_blankStateColumn);

        TableColumn<TravelAdvisor, String> ReAssignAdvisor_iDColumn = new TableColumn<>("ID");
        ReAssignAdvisor_iDColumn.setMinWidth(100);
        ReAssignAdvisor_iDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<TravelAdvisor, String> ReAssignAdvisor_nameColumn = new TableColumn<>("Name");
        ReAssignAdvisor_nameColumn.setMinWidth(225);
        ReAssignAdvisor_nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<TravelAdvisor, String> ReAssignAdvisor_emailColumn = new TableColumn<>("Email");
        ReAssignAdvisor_emailColumn.setMinWidth(262);
        ReAssignAdvisor_emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        reAssignAdvisorsTable = new TableView<>();
        reAssignAdvisorsTable.setItems(getAdvisors());
        reAssignAdvisorsTable.getColumns().addAll(ReAssignAdvisor_iDColumn, ReAssignAdvisor_nameColumn, ReAssignAdvisor_emailColumn);

        // Button
        Button ReAssign = new Button("Confirm");
        ReAssign.setMinWidth(100);
        ReAssign.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!(reAssignBlankTable.getSelectionModel().isEmpty()) && !(reAssignAdvisorsTable.getSelectionModel().isEmpty()))
                {
                    for(Blank b : reAssignBlankTable.getSelectionModel().getSelectedItems())
                    {
                        reAssign(b.getId(), reAssignAdvisorsTable.getSelectionModel().getSelectedItem().getName());
                    }
                    close();
                    clearTables();
                }
                else
                {
                    SelectAdvisorAlert.display();
                }
            }
        });

        Button ReAssign_cancel = new Button("Cancel");
        ReAssign_cancel.getStyleClass().add("button-exit");
        ReAssign_cancel.setMinWidth(75);
        ReAssign_cancel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                close();
                clearTables();
            }
        });

        // TextFields
        FilteredList<TravelAdvisor> ReAssign_advisorFilteredList = new FilteredList<>(reAssignAdvisors, b -> true);
        TextField ReAssign_selectTA_textField = new TextField();
        ReAssign_selectTA_textField.setPromptText("Search by name...");
        ReAssign_selectTA_textField.setMinWidth(100);
        // Creating a listener for a search function
        ReAssign_selectTA_textField.setOnKeyReleased(e -> {
            ReAssign_selectTA_textField.textProperty().addListener((observable, oldValue, newValue) -> {
                ReAssign_advisorFilteredList.setPredicate(advisor -> {
                    if (newValue == null || newValue.isEmpty())
                    {
                        return true;
                    }
                    if (advisor.getName().toLowerCase().contains(newValue.toLowerCase()))
                    {
                        return true;
                    } else
                    {
                        return false;
                    }
                });
            });
        });
        // Creating a sorted list for the new items
        SortedList<TravelAdvisor> ReAssigned_sortedAdvisors = new SortedList<>(ReAssign_advisorFilteredList);
        // Binding the sorted list comparator to the table view comparator
        ReAssigned_sortedAdvisors.comparatorProperty().bind(reAssignAdvisorsTable.comparatorProperty());
        // Adding the sorted items to the table
        reAssignAdvisorsTable.setItems(ReAssigned_sortedAdvisors);

        FilteredList<Blank> ReAssign_blankFilteredList = new FilteredList<>(reAssignblanks, b -> true);
        TextField ReAssign_selectBlank_textField = new TextField();
        ReAssign_selectBlank_textField.setPromptText("Search by blank...");
        ReAssign_selectBlank_textField.setMinWidth(100);
        // Creating a listener for a search function
        ReAssign_selectBlank_textField.setOnKeyReleased(e -> {
            ReAssign_selectBlank_textField.textProperty().addListener((observable, oldValue, newValue) -> {
                ReAssign_blankFilteredList.setPredicate(blank -> {
                    if (newValue == null || newValue.isEmpty())
                    {
                        return true;
                    }

                    if (blank.getId().toLowerCase().contains(newValue.toLowerCase()))
                    {
                        return true;
                    } else
                    {
                        return false;
                    }
                });
            });
        });

        // Creating a sorted list for the new items
        SortedList<Blank> ReAssigned_sortedBlanks = new SortedList<>(ReAssign_blankFilteredList);
        // Binding the sorted list comparator to the table view comparator
        ReAssigned_sortedBlanks.comparatorProperty().bind(reAssignBlankTable.comparatorProperty());
        // Adding the sorted items to the table
        reAssignBlankTable.setItems(ReAssigned_sortedBlanks);

        // Layout
        VBox ReAssign_top_layout = new VBox();
        ReAssign_top_layout.setAlignment(Pos.CENTER);
        ReAssign_top_layout.getChildren().add(ReAssign_page_info);
        ReAssign_top_layout.setPadding(new Insets(0, 0, 20, 0));

        HBox ReAssign_searchBlank_layout = new HBox(10);
        ReAssign_searchBlank_layout.setAlignment(Pos.CENTER_RIGHT);
        ReAssign_searchBlank_layout.getChildren().addAll(ReAssign_searchBlank, ReAssign_selectBlank_textField);

        VBox blank_list = new VBox(10);
        blank_list.setPadding(new Insets(0, 20, 0, 0));
        blank_list.setAlignment(Pos.CENTER_RIGHT);
        blank_list.getChildren().addAll(ReAssign_searchBlank_layout, reAssignBlankTable);

        HBox ReAssign_searchAdvisor_layout = new HBox(10);
        ReAssign_searchAdvisor_layout.setAlignment(Pos.CENTER_RIGHT);
        ReAssign_searchAdvisor_layout.getChildren().addAll(ReAssign_search, ReAssign_selectTA_textField);

        VBox TA_list = new VBox(10);
        TA_list.setAlignment(Pos.CENTER_RIGHT);
        TA_list.getChildren().addAll(ReAssign_searchAdvisor_layout, reAssignAdvisorsTable);

        HBox button_layout = new HBox();
        button_layout.setPadding(new Insets(10,0,0,0));
        button_layout.setAlignment(Pos.CENTER);
        button_layout.getChildren().add(ReAssign);

        BorderPane ReAssign_center_layout = new BorderPane();
        ReAssign_center_layout.setPadding(new Insets(0, 0, 10, 0));
        ReAssign_center_layout.setLeft(blank_list);
        ReAssign_center_layout.setRight(TA_list);
        ReAssign_center_layout.setBottom(button_layout);

        HBox ReAssign_bottom_layout = new HBox();
        ReAssign_bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        ReAssign_bottom_layout.getChildren().add(ReAssign_cancel);

        reAssign_root_layout.setPadding(new Insets(10, 10, 10, 10));
        reAssign_root_layout.setTop(ReAssign_top_layout);
        reAssign_root_layout.setCenter(ReAssign_center_layout);
        reAssign_root_layout.setBottom(ReAssign_bottom_layout);

        // Scene
        reAssign_scene.getStylesheets().add("Stylesheet.css");

        // Start window
        window.setScene(reAssign_scene);
        window.show();
    }

    public static void close()
    {
        window.close();
        reAssignAdvisors.clear();
        reAssignblanks.clear();
    }

    private static void clearTables()
    {
        reAssignBlankTable.getSelectionModel().clearSelection();
        reAssignAdvisorsTable.getSelectionModel().clearSelection();
    }

    private static void reAssign(String blankID, String advisorName)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to update assigned blanks

            String query = "UPDATE blank SET assignedTo = '"+advisorName+"' WHERE ID = '"+blankID+"'";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static ObservableList<Blank> getBlanks()
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to select all the blanks
            String query = "SELECT * FROM blank WHERE state = 'Assigned'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                reAssignblanks.add(new Blank(resultSet.getString(1),
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
        return reAssignblanks;
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
                reAssignAdvisors.add(new TravelAdvisor(resultSet.getString("name"),
                        resultSet.getString("ID"),
                        resultSet.getString("email"),
                        resultSet.getString("address")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return reAssignAdvisors;
    }
}
