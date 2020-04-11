package OM_GUI;

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
import sample.Customer;
import sample.Refund;
import sample.Sale;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RefundLog
{
    // Database
    static DBConnectivity dbConnectivity = new DBConnectivity();
    static Connection connection = dbConnectivity.getConnection();

    // Table View
    static TableView<Sale> table;

    public static void display(String title)
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(500);
        window.setMinHeight(425);
        window.setResizable(false);

        // Labels
        Label refund_label = new Label("Refund Log");
        refund_label.getStyleClass().add("label-title");

        // Table
        TableColumn<Sale, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(100);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("refundDate"));

        TableColumn<Sale, String> blankIDColumn = new TableColumn<>("Blank ID");
        blankIDColumn.setMinWidth(100);
        blankIDColumn.setCellValueFactory(new PropertyValueFactory<>("BlankID"));

        TableColumn<Sale, String> amountColumn = new TableColumn<>("Amount");
        amountColumn.setMinWidth(100);
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("refundAmount"));

        table = new TableView<>();
        table.setItems(getRefunds());
        table.getColumns().addAll(dateColumn, blankIDColumn, amountColumn);

        // Buttons
        Button close = new Button("Close");
        close.getStyleClass().add("button-exit");
        close.setMinSize(75,25);
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
        top_layout.setPadding(new Insets(10,0,10,0));
        top_layout.getChildren().add(refund_label);

        VBox centre_layout = new VBox();
        centre_layout.setAlignment(Pos.CENTER);
        centre_layout.setPadding(new Insets(0,0,10,0));
        centre_layout.getChildren().add(table);

        HBox bottom_layout = new HBox();
        bottom_layout.setPadding(new Insets(10,0,0,0));
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().add(close);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10,10,10,10));
        root_layout.setTop(top_layout);
        root_layout.setCenter(centre_layout);
        root_layout.setBottom(bottom_layout);

        //Scene
        Scene scene = new Scene(root_layout);
        scene.getStylesheets().add("Stylesheet.css");
        window.setScene(scene);

        // Start window
        window.showAndWait();
    }

    public static ObservableList<Sale> getRefunds()
    {
        ObservableList<Sale> refunds = FXCollections.observableArrayList();

        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to find matching email and password
            String query = "SELECT refundDate, BlankID, refundAmount FROM sales WHERE state = 'Refunded'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                refunds.add(new Sale(resultSet.getString("refundDate"),
                        resultSet.getString("BlankID"),
                        resultSet.getDouble("refundAmount")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return refunds;
    }
}
