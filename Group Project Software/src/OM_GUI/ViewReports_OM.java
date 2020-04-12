package OM_GUI;

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
import sample.Sale;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewReports_OM
{
    // Database
    private static DBConnectivity dbConnectivity = new DBConnectivity();
    private static Connection connection = dbConnectivity.getConnection();

    // Table
    private static TableView<Sale> table;
    private static ObservableList<Sale> reportSales = FXCollections.observableArrayList();

    // Amounts
    private static double totalAmount;
    private static double totalLocalTax;
    private static double totalOtherTax;
    private static double overallAmount;

    private static double exchangeRate;

    public static void display(String title)
    {
        getExchangeRate();
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(980);
        window.setResizable(false);

        // Table
        TableColumn<Sale, Integer> id_column = new TableColumn<>("ID");
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Sale, String> blankID_column = new TableColumn<>("Blank ID");
        blankID_column.setCellValueFactory(new PropertyValueFactory<>("BlankID"));

        TableColumn<Sale, Double> amount_column = new TableColumn<>("Amount");
        amount_column.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amount_column.setPrefWidth(100);

        TableColumn<Sale, String> currency_column = new TableColumn<>("Currency");
        currency_column.setCellValueFactory(new PropertyValueFactory<>("currency"));
        currency_column.setPrefWidth(100);

        TableColumn<Sale, Double> localTax_column = new TableColumn<>("Local Tax");
        localTax_column.setCellValueFactory(new PropertyValueFactory<>("localTax"));
        localTax_column.setPrefWidth(100);

        TableColumn<Sale, Double> otherTax_column = new TableColumn<>("Other Tax");
        otherTax_column.setCellValueFactory(new PropertyValueFactory<>("otherTax"));
        otherTax_column.setPrefWidth(100);

        TableColumn<Sale, String> payMethod_column = new TableColumn<>("Pay method");
        payMethod_column.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        payMethod_column.setPrefWidth(100);

        TableColumn<Sale, Double> commissionRate_column = new TableColumn<>("Comm. Rate");
        commissionRate_column.setCellValueFactory(new PropertyValueFactory<>("commissionRate"));
        commissionRate_column.setPrefWidth(100);

        TableColumn<Sale, String> customer_column = new TableColumn<>("Customer");
        customer_column.setCellValueFactory(new PropertyValueFactory<>("customer"));
        customer_column.setPrefWidth(100);

        TableColumn<Sale, String> saleDate_column = new TableColumn<>("Date");
        saleDate_column.setCellValueFactory(new PropertyValueFactory<>("saleDate"));

        TableColumn<Sale, String> soldBy_column = new TableColumn<>("Sold By");
        soldBy_column.setCellValueFactory(new PropertyValueFactory<>("soldBy"));

        table = new TableView<>();
        table.setItems(getSales());
        table.getColumns().addAll(id_column, blankID_column, amount_column, currency_column, localTax_column, otherTax_column, payMethod_column,commissionRate_column, customer_column, saleDate_column, soldBy_column);

        // Labels
        Label page_info = new Label("Your Sales");
        page_info.getStyleClass().add("label-title");

        Label totalAmount_label = new Label("Total Amount: "+totalAmount+"");
        Label totalLocalTax_label = new Label("Total local Tax: "+totalLocalTax+"");
        Label totalOtherTax_label = new Label("Total other Tax: "+totalOtherTax+"");
        Label overallAmount_label = new Label("Overall Amount: "+overallAmount+"");
        overallAmount_label.getStyleClass().add("label-title");


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
                reportSales.clear();
                resetValues();
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

        VBox fields_layout = new VBox(10);
        fields_layout.setAlignment(Pos.CENTER_LEFT);
        fields_layout.getChildren().addAll(totalAmount_label, totalLocalTax_label, totalOtherTax_label, overallAmount_label);

        BorderPane center_layout = new BorderPane();
        center_layout.setCenter(list_layout);
        center_layout.setBottom(fields_layout);

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

    private static ObservableList<Sale> getSales()
    {
        double usdAmount;
        ResultSet resultSet = GenerateReport_OM.getCalculateReportResultSet();
        try
        {
            while (resultSet.next())
            {
                usdAmount = resultSet.getDouble("amount") * exchangeRate;
                reportSales.add(new Sale(resultSet.getInt("ID"),
                        resultSet.getString("BlankID"), usdAmount, "USD",
                        resultSet.getDouble("localTax"),
                        resultSet.getDouble("otherTax"),
                        resultSet.getString("paymentMethod"),
                        resultSet.getDouble("commissionRate"),
                        resultSet.getString("customer"),
                        resultSet.getString("saleDate"),
                        resultSet.getString("soldBy")));

                totalAmount = totalAmount + usdAmount;
                totalLocalTax = totalLocalTax + resultSet.getDouble("localTax");
                totalOtherTax = totalOtherTax + resultSet.getDouble("otherTax");
            }
            overallAmount = totalAmount + (totalLocalTax + totalOtherTax);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return reportSales;
    }

    private static void getExchangeRate()
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to get exchange rate
            String query = "SELECT * FROM currency_exchange";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next())
            {
                exchangeRate = resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void resetValues()
    {
        totalAmount = 0.0;
        totalLocalTax = 0.0;
        totalOtherTax = 0.0;
        overallAmount = 0.0;
    }
}
