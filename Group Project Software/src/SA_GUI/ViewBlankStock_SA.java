package SA_GUI;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Blank;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;

public class ViewBlankStock_SA
{
    // Stage
    private static Stage window = new Stage();

    // Layouts
    private static BorderPane main_root_layout = new BorderPane();

    // Scenes
    private static Scene main_scene = new Scene(main_root_layout);

    // Database
    private static DBConnectivity dbConnectivity = new DBConnectivity();
    private static Connection connection = dbConnectivity.getConnection();

    // Table View
    private static TableView<Blank> table;
    private static ObservableList<Blank> blanks = FXCollections.observableArrayList();

    // Blank
    private static Blank selectedBlank;

    // Date
    private static String saleDate = "";

    public static void display()
    {
        // Window takes priority until taken care of
        window.setTitle("Blank Stock");
        window.setHeight(500);
        window.setWidth(775);
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

        table = new TableView<>();
        table.setItems(getBlanks());
        table.getColumns().addAll(blankIDColumn, blankTypeColumn, blankAssignedToColumn, blankReceivedDateColumn, blankAssignedDateColumn, blankStateColumn);

        // TextField

        FilteredList<Blank> blankFilteredList = new FilteredList<>(blanks, b -> true);

        TextField searchBlank_textField = new TextField();
        searchBlank_textField.setPrefWidth(100);
        searchBlank_textField.setMinWidth(100);
        // Creating a listener for a search function
        searchBlank_textField.setOnKeyReleased(e ->
        {
            searchBlank_textField.textProperty().addListener((observable, oldValue, newValue) ->
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
        sortedBlanks.comparatorProperty().bind(table.comparatorProperty());
        // Adding the sorted items to the table
        table.setItems(sortedBlanks);

        // Buttons
        Button addBlank = new Button("Add Blanks");
        addBlank.setMinSize(140, 25);
        addBlank.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                displayAddBlank();
            }
        });

        Button removeBlank = new Button("Remove Blanks");
        removeBlank.setMinSize(140, 25);
        removeBlank.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!(table.getSelectionModel().isEmpty()))
                {
                    selectedBlank = table.getSelectionModel().getSelectedItem();
                    if (selectedBlank.getState().equals("Used") || selectedBlank.getState().equals("Assigned"))
                    {
                        CannotRemoveBlankAlert.display();
                        table.getSelectionModel().clearSelection();
                        refreshTable();
                    }
                    else
                    {
                        removeBlank(selectedBlank.getId());
                        refreshTable();
                        table.getSelectionModel().clearSelection();
                    }
                }
            }
        });

        Button close = new Button("Close");
        close.getStyleClass().add("button-exit");
        close.setMinSize(75, 25);
        close.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                window.close();
            }
        });

        // Layout
        VBox top_layout = new VBox();
        top_layout.setAlignment(Pos.CENTER);
        top_layout.getChildren().add(page_info);
        top_layout.setPadding(new Insets(0, 0, 20, 0));

        VBox list_layout = new VBox(50);
        list_layout.setAlignment(Pos.CENTER);
        list_layout.getChildren().add(table);
        list_layout.setPadding(new Insets(0, 0, 20, 0));

        HBox search_bar_layout = new HBox(10);
        search_bar_layout.setPadding(new Insets(0,0,10,0));
        search_bar_layout.setAlignment(Pos.CENTER_RIGHT);
        search_bar_layout.getChildren().addAll(searchBlank_label, searchBlank_textField);

        HBox button_layout = new HBox(60);
        button_layout.setAlignment(Pos.CENTER);
        button_layout.getChildren().addAll(addBlank, removeBlank);
        button_layout.setPadding(new Insets(0, 0, 20, 0));

        BorderPane center_layout = new BorderPane();
        center_layout.setTop(search_bar_layout);
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
        window.setScene(main_scene);

        // Start window
        window.showAndWait();
    }

    private static void displayAddBlank()
    {
        // Stage
        Stage window = new Stage();

        // Labels
        Label addBlank_page_info = new Label("Add Blanks");
        addBlank_page_info.getStyleClass().add("label-title");

        Label addBlank_from_label = new Label("From ");
        Label addBlank_to_label = new Label(" to ");

        // TextFields
        TextField addBlank_lowRange = new TextField();
        addBlank_lowRange.setPrefWidth(100);
        // Added a listener here to make sure the user doesn't enter more than 11 digits
        addBlank_lowRange.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if(addBlank_lowRange.getText().length()>11)
                {
                    String s = addBlank_lowRange.getText().substring(0,11);
                    addBlank_lowRange.setText(s);
                }
            }
        });

        TextField addBlank_highRange = new TextField();
        addBlank_highRange.setPrefWidth(100);
        // Added a listener here to make sure the user doesn't enter more than 11 digits
        addBlank_highRange.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if(addBlank_highRange.getText().length()>11)
                {
                    String s = addBlank_highRange.getText().substring(0,11);
                    addBlank_highRange.setText(s);
                }
            }
        });

        // Buttons
        Button AddBlanks = new Button("Add");
        AddBlanks.setPrefWidth(100);
        AddBlanks.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(addBlank_lowRange.getText().length()==11 && addBlank_highRange.getText().length()==11)
                {
                    getCurrentDate();
                    long lowValue = Long.parseLong(addBlank_lowRange.getText());
                    long highValue = Long.parseLong(addBlank_highRange.getText());
                    String type = addBlank_lowRange.getText().substring(0, 3);

                    for(long i = lowValue; i<=highValue; i++)
                    {
                        String id = Long.toString(i);
                        addBlanks(id, type, saleDate);
                    }
                    refreshTable();
                }
                else
                {
                    AddBlankErrorAlert.display();
                }
            }
        });

        Button addBlank_close = new Button("Cancel");
        addBlank_close.setPrefWidth(80);
        addBlank_close.getStyleClass().add("button-exit");
        addBlank_close.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.close();
                addBlank_lowRange.clear();
                addBlank_highRange.clear();
            }
        });

        // Layout
        HBox addBlank_top_layout = new HBox();
        addBlank_top_layout.setAlignment(Pos.CENTER);
        addBlank_top_layout.getChildren().add(addBlank_page_info);

        HBox addBlank_field_layout = new HBox(5);
        addBlank_field_layout.setPadding(new Insets(0,0,10,0));
        addBlank_field_layout.setAlignment(Pos.CENTER);
        addBlank_field_layout.getChildren().addAll(addBlank_from_label, addBlank_lowRange, addBlank_to_label, addBlank_highRange);

        HBox addBlank_button_layout = new HBox();
        addBlank_button_layout.setPadding(new Insets(0,0,10,0));
        addBlank_button_layout.setAlignment(Pos.CENTER);
        addBlank_button_layout.getChildren().add(AddBlanks);

        BorderPane addBlank_center_layout = new BorderPane();
        addBlank_center_layout.setCenter(addBlank_field_layout);
        addBlank_center_layout.setBottom(addBlank_button_layout);

        HBox addBlank_bottom_layout = new HBox();
        addBlank_bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        addBlank_bottom_layout.getChildren().add(addBlank_close);

        BorderPane addBlank_root_layout = new BorderPane();
        addBlank_root_layout.setPadding(new Insets(10,10,10,10));
        addBlank_root_layout.setTop(addBlank_top_layout);
        addBlank_root_layout.setCenter(addBlank_center_layout);
        addBlank_root_layout.setBottom(addBlank_bottom_layout);

        // Scene
        Scene addBlank_scene = new Scene(addBlank_root_layout);
        addBlank_scene.getStylesheets().add("Stylesheet.css");

        // Window
        window.setScene(addBlank_scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Blank Add");
        window.sizeToScene();
        window.setResizable(false);

        window.show();
    }

    private static ObservableList<Blank> getBlanks()
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to find matching travel advisors
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

    private static void addBlanks(String id, String type, String receivedDate)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to add blanks
            String query = "INSERT INTO blank (ID, type, receivedDate, state) VALUES ('"+id+"', '"+type+"', '"+receivedDate+"', 'Not Assigned')";
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

    private static void refreshTable()
    {
        blanks.clear();
        getBlanks();
    }

    private static void removeBlank(String id)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to find matching travel advisors
            String query = "DELETE FROM blank WHERE ID = '"+id+"'";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
