package SA_GUI;

import Database.DBConnectivity;
import TA_GUI.GenerateReport_TA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class ViewReports_SA {
    // Database
    private static DBConnectivity dbConnectivity = new DBConnectivity();
    private static Connection connection = dbConnectivity.getConnection();

    // Table
    private static TableView<Blank> table;
    private static ObservableList<Blank> reportSales = FXCollections.observableArrayList();

    // Amounts
    private static double totalAmount;
    private static double totalLocalTax;
    private static double totalOtherTax;
    private static double overallAmount;

    public static void display(String title)
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(980);
        window.setResizable(false);

        // Table
        TableColumn<Blank, Integer> id_column = new TableColumn<>("ID");
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));


        TableColumn<Blank, String> blankID_column = new TableColumn<>("Type");
        blankID_column.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Blank, String> amount_column = new TableColumn<>("Assigned To");
        amount_column.setCellValueFactory(new PropertyValueFactory<>("assignedTo"));
        amount_column.setPrefWidth(150);

        TableColumn<Blank, String> receivedDate_column = new TableColumn<>("Received Date");
        receivedDate_column.setCellValueFactory(new PropertyValueFactory<>("receivedDate"));
        receivedDate_column.setPrefWidth(150);

        TableColumn<Blank, String> assignedDate_column = new TableColumn<>("Assigned Date");
        assignedDate_column.setCellValueFactory(new PropertyValueFactory<>("assignedDate"));
        assignedDate_column.setPrefWidth(150);

        TableColumn<Blank, String> state_column = new TableColumn<>("State");
        state_column.setCellValueFactory(new PropertyValueFactory<>("state"));
        state_column.setPrefWidth(100);


        table = new TableView<>();
        table.setItems(getBlanks());
        table.getColumns().addAll(id_column, blankID_column, amount_column, receivedDate_column, assignedDate_column, state_column);

        // Labels
        Label page_info = new Label("Stock Turnover Report for period from " + GenerateStockTurnoverReport.getDatePicker1() + " to " + GenerateStockTurnoverReport.getDatePicker2());
        page_info.getStyleClass().add("label-title");




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
        top_layout.getChildren().add(page_info);

        VBox list_layout = new VBox();
        list_layout.setAlignment(Pos.CENTER);
        list_layout.getChildren().add(table);

        BorderPane center_layout = new BorderPane();
        center_layout.setCenter(list_layout);

        HBox bottom_layout = new HBox();
        bottom_layout.setPadding(new Insets(10,0,0,0));
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().add(close);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10,10,10,10));
        root_layout.setTop(top_layout);
        root_layout.setCenter(center_layout);
        root_layout.setBottom(bottom_layout);

        //Scene
        Scene scene = new Scene(root_layout);
        scene.getStylesheets().add("Stylesheet.css");
        window.setScene(scene);

        // Start window
        window.showAndWait();
    }

    // Gets the necessary details for blanks from the database
    private static ObservableList<Blank> getBlanks()
    {
        ResultSet resultSet = GenerateStockTurnoverReport.getCalculateReportResultSet();
        try {
            while (resultSet.next()) {
                reportSales.add(new Blank(resultSet.getString("ID"),
                        resultSet.getString("type"),
                        resultSet.getString("assignedTo"),
                        resultSet.getString("receivedDate"),
                        resultSet.getString("assignedDate"),
                        resultSet.getString("state")));
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return reportSales;
    }


}
