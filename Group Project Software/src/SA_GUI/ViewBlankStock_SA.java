package SA_GUI;

import Database.DBConnectivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewBlankStock_SA
{
    // Database
    static DBConnectivity dbConnectivity = new DBConnectivity();
    static Connection connection = dbConnectivity.getConnection();

    // Table View
    static TableView<Blank> table;

    public static void display(String title)
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setHeight(500);
        window.setWidth(750);
        window.setResizable(false);

        // Labels
        Label page_info = new Label("Blank Stock");
        page_info.getStyleClass().add("label-title");

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

        // Buttons
        Button addBlank = new Button("Add Blanks");
        addBlank.setMinSize(140, 25);

        Button removeBlank = new Button("Remove Blanks");
        removeBlank.setMinSize(140, 25);

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

        HBox button_layout = new HBox(60);
        button_layout.setAlignment(Pos.CENTER);
        button_layout.getChildren().addAll(addBlank, removeBlank);
        button_layout.setPadding(new Insets(0, 0, 20, 0));

        BorderPane center_layout = new BorderPane();
        center_layout.setCenter(list_layout);
        center_layout.setBottom(button_layout);

        HBox bottom_layout = new HBox(50);
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().addAll(close);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10, 10, 10, 10));
        root_layout.setTop(top_layout);
        root_layout.setCenter(center_layout);
        root_layout.setBottom(bottom_layout);

        // Scene
        Scene scene = new Scene(root_layout);
        scene.getStylesheets().add("Stylesheet.css");
        window.setScene(scene);

        // Start window
        window.showAndWait();
    }

    public static ObservableList<Blank> getBlanks()
    {
        ObservableList<Blank> blanks = FXCollections.observableArrayList();
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
}
