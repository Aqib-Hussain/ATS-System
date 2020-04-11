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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Blank;
import sample.Sale;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RefundSale
{
    // Database
    static DBConnectivity dbConnectivity = new DBConnectivity();
    static Connection connection = dbConnectivity.getConnection();

    // Table View
    static TableView<Sale> table;
    static ObservableList<Sale> sales = FXCollections.observableArrayList();

    public static void display()
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Sale Refunding");
        window.setHeight(650);
        window.setWidth(1200);
        window.setResizable(false);

        // Labels
        Label page_info = new Label("Sale Stock");
        page_info.getStyleClass().add("label-title");

        // Table
        TableColumn<Sale, String> blankID_column = new TableColumn<>("Blank ID");
        blankID_column.setMinWidth(100);
        blankID_column.setCellValueFactory(new PropertyValueFactory<>("BlankID"));

        TableColumn<Sale, Double> amount_column = new TableColumn<>("Amount GBP");
        amount_column.setMinWidth(120);
        amount_column.setCellValueFactory(new PropertyValueFactory<>("local_amount"));

        TableColumn<Sale, String> currency_column = new TableColumn<>("Currency used");
        currency_column.setMinWidth(120);
        currency_column.setCellValueFactory(new PropertyValueFactory<>("currency"));

        TableColumn<Sale, Double> tax_column = new TableColumn<>("Tax");
        tax_column.setMinWidth(80);
        tax_column.setCellValueFactory(new PropertyValueFactory<>("tax"));

        TableColumn<Sale, String> paymentMeth_column = new TableColumn<>("Payment Method");
        paymentMeth_column.setMinWidth(140);
        paymentMeth_column.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        TableColumn<Sale, String> customer_column = new TableColumn<>("Customer");
        customer_column.setMinWidth(140);
        customer_column.setCellValueFactory(new PropertyValueFactory<>("customer"));

        TableColumn<Sale, String> origin_column = new TableColumn<>("Origin");
        origin_column.setMinWidth(100);
        origin_column.setCellValueFactory(new PropertyValueFactory<>("origin"));

        TableColumn<Sale, String> destination_column = new TableColumn<>("Destination");
        destination_column.setMinWidth(120);
        destination_column.setCellValueFactory(new PropertyValueFactory<>("destination"));

        TableColumn<Sale, String> soldBy_column = new TableColumn<>("Sold By");
        soldBy_column.setMinWidth(140);
        soldBy_column.setCellValueFactory(new PropertyValueFactory<>("soldBy"));

        TableColumn<Sale, String> state_column = new TableColumn<>("State");
        state_column.setMinWidth(100);
        state_column.setCellValueFactory(new PropertyValueFactory<>("state"));


        table = new TableView<>();
        table.setItems(getSales());
        table.getColumns().addAll(blankID_column, amount_column, currency_column, tax_column, paymentMeth_column, customer_column, origin_column, destination_column, soldBy_column, state_column);

        // Buttons
        Button refund = new Button("Refund");
        refund.getStyleClass().add("button-login");
        refund.setMinWidth(100);
        refund.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!(table.getSelectionModel().isEmpty()))
                {
                    refundSale(table.getSelectionModel().getSelectedItem().getBlankID());
                    endRefund();
                    refreshTable();
                }
                else
                {
                    SelectASaleAlert.display();
                }
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
                endRefund();
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
        button_layout.getChildren().addAll(refund);
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

    public static ObservableList<Sale> getSales()
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to find matching travel advisors
            String query = "SELECT * FROM sales";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                sales.add(new Sale(resultSet.getString("BlankID"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("currency"),
                        resultSet.getDouble("localTax"),
                        resultSet.getDouble("otherTax"),
                        resultSet.getString("paymentMethod"),
                        resultSet.getString("customer"),
                        resultSet.getString("origin"),
                        resultSet.getString("destination"),
                        resultSet.getString("soldBy"),
                        resultSet.getString("state")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return sales;
    }

    public static void refundSale(String saleID)
    {
        try
        {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to update the sale to refunded
            String query = "UPDATE sales SET state = 'Refunded' WHERE BlankID = '"+saleID+"'";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void refreshTable()
    {
        sales.clear();
        getSales();
    }

    public static  void endRefund()
    {
        table.getSelectionModel().clearSelection();
    }
}
