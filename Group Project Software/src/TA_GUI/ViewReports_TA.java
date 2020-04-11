package TA_GUI;

import Database.DBConnectivity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Sale;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewReports_TA
{
    // Database
    private static DBConnectivity dbConnectivity = new DBConnectivity();
    private static Connection connection = dbConnectivity.getConnection();

    // Table
    private static TableView<Sale> table;
    private static ObservableList<Sale> reportSales = FXCollections.observableArrayList();

    public static void display(String title)
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(600);
        window.setMinHeight(425);
        window.setResizable(false);

        // Labels
        Label refund_label = new Label("Reports");
        refund_label.getStyleClass().add("label-title");
        refund_label.setFont(Font.font(20));

        // Table


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

        HBox bottom_layout = new HBox();
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

    private static ObservableList<Sale> getSales()
    {
        ResultSet resultSet = GenerateReport_TA.getCalculateReportResultSet();
        try
        {
            while (resultSet.next())
            {
                reportSales.add(new Sale());
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return reportSales;
    }
}
