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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.CommissionRate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommissionRates
{
    // Database
    static private DBConnectivity dbConnectivity = new DBConnectivity();
    static private Connection connection = dbConnectivity.getConnection();

    // Table View
    static private ObservableList<CommissionRate> commissionRates = FXCollections.observableArrayList();

    // Layout
    static private BorderPane root_layout = new BorderPane();
    static private BorderPane addRate_root_layout = new BorderPane();

    // Scene
    static private Scene scene = new Scene(root_layout);
    static private Scene addRate_scene = new Scene(addRate_root_layout);

    // Window
    static private Stage window = new Stage();
    static private Stage addRate_window = new Stage();

    public static void display()
    {
        // Window
        window.setTitle("Commission Rates");
        window.sizeToScene();
        window.setResizable(false);

        // Labels
        Label page_info = new Label("Commission Rates");
        page_info.getStyleClass().add("label-title");

        // Text
        TextField text_commissionRate = new TextField();
        text_commissionRate.setMaxSize(200,25);
        text_commissionRate.setFont(Font.font(16));

        // Table
        TableColumn<CommissionRate, Integer> IDColumn = new TableColumn<>("ID");
        IDColumn.setMinWidth(100);
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<CommissionRate, String> blankTypeColumn = new TableColumn<>("Blank Type");
        blankTypeColumn.setMinWidth(120);
        blankTypeColumn.setCellValueFactory(new PropertyValueFactory<>("blankType"));

        TableColumn<CommissionRate, Double> commissionRateColumn = new TableColumn<>("Rate");
        commissionRateColumn.setMinWidth(100);
        commissionRateColumn.setCellValueFactory(new PropertyValueFactory<>("commissionRate"));

        TableView<CommissionRate> table = new TableView<>();
        table.setItems(getCommissionRates());
        table.setEditable(true);
        table.getColumns().addAll(IDColumn, blankTypeColumn, commissionRateColumn);

        // Buttons
        Button addRate_button = new Button("Add");
        addRate_button.setPrefWidth(100);
        addRate_button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                display_addRate();
            }
        });

        Button removeRate_button = new Button("Remove");
        removeRate_button.setPrefWidth(100);
        removeRate_button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                removeRate(table.getSelectionModel().getSelectedItem().getId());
                table.getSelectionModel().clearSelection();
                refreshTable();
            }
        });

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("button-exit");
        cancel.setMinSize(75,25);
        cancel.setOnAction(new EventHandler<ActionEvent>()
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

        VBox button_layout = new VBox(10);
        button_layout.setPadding(new Insets(0,0,0,10));
        button_layout.setAlignment(Pos.TOP_CENTER);
        button_layout.getChildren().addAll(addRate_button, removeRate_button);

        HBox bottom_layout = new HBox();
        bottom_layout.setPadding(new Insets(10,0,0,0));
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().add(cancel);

        root_layout.setPadding(new Insets(10,10,10,10));
        root_layout.setTop(top_layout);
        root_layout.setCenter(table);
        root_layout.setRight(button_layout);
        root_layout.setBottom(bottom_layout);

        // Scene
        scene.getStylesheets().add("Stylesheet.css");
        window.setScene(scene);

        // Start window
        window.show();
    }

    private static void display_addRate()
    {
        // Labels
        Label addRate_pageInfo = new Label("Enter new Rate");
        addRate_pageInfo.getStyleClass().add("label-title");

        Label blankType_label = new Label("Blank Type ");
        Label rate_label = new Label("Commission Rate ");

        // ChoiceBox
        ChoiceBox<String> blankType_choiceBox = new ChoiceBox<>();
        blankType_choiceBox.getItems().addAll("444", "420", "201", "101");
        blankType_choiceBox.setPrefWidth(70);

        // TextField
        TextField commRate_textField = new TextField();
        commRate_textField.setPrefWidth(75);
        commRate_textField.setPromptText("e.g 0.09");

        // Buttons
        Button addRate_add_button = new Button("Add");
        addRate_add_button.setPrefWidth(75);
        addRate_add_button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                addRate(blankType_choiceBox.getSelectionModel().getSelectedItem(), Double.parseDouble(commRate_textField.getText()));
                commRate_textField.clear();
                refreshTable();
                addRate_window.close();
            }
        });

        Button addRate_close_button = new Button("Cancel");
        addRate_close_button.setPrefWidth(100);
        addRate_close_button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                addRate_window.close();
                commRate_textField.clear();
            }
        });

        // Layout
        HBox addRate_top_layout = new HBox();
        addRate_top_layout.setAlignment(Pos.CENTER);
        addRate_top_layout.getChildren().add(addRate_pageInfo);

        GridPane addRate_fields_layout = new GridPane();
        addRate_fields_layout.setAlignment(Pos.CENTER);
        addRate_fields_layout.setHgap(15);
        addRate_fields_layout.setVgap(12);
        // Row 1
        GridPane.setConstraints(blankType_label, 0, 0);
        GridPane.setConstraints(blankType_choiceBox, 1, 0);
        // Row 2
        GridPane.setConstraints(rate_label, 0, 1);
        GridPane.setConstraints(commRate_textField, 1, 1);
        addRate_fields_layout.getChildren().addAll(blankType_label, blankType_choiceBox, rate_label, commRate_textField);

        HBox addRate_button_layout = new HBox();
        addRate_button_layout.setAlignment(Pos.CENTER);
        addRate_button_layout.setPadding(new Insets(10,0,10,0));
        addRate_button_layout.getChildren().add(addRate_add_button);

        BorderPane addRate_center_layout = new BorderPane();
        addRate_center_layout.setCenter(addRate_fields_layout);
        addRate_center_layout.setBottom(addRate_button_layout);

        HBox addRate_bottom_layout = new HBox();
        addRate_bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        addRate_bottom_layout.getChildren().add(addRate_close_button);

        addRate_root_layout.setPadding(new Insets(10,10,10,10));
        addRate_root_layout.setTop(addRate_top_layout);
        addRate_root_layout.setCenter(addRate_center_layout);
        addRate_root_layout.setBottom(addRate_bottom_layout);

        // Scene
        addRate_window.setScene(addRate_scene);
        addRate_scene.getStylesheets().add("Stylesheet.css");

        // Window
        addRate_window.setTitle("Add Commission Rate");
        addRate_window.sizeToScene();
        addRate_window.setResizable(false);
        addRate_window.show();
    }

    private static void addRate(String blankType, double rate)
    {
        try
        {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to insert a new commission Rate
            String query = "INSERT INTO commissionrate (BlankType, CommissionRate) VALUES ('"+blankType+"', '"+rate+"')";
            statement.executeUpdate(query);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static void removeRate(int id)
    {
        try
        {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to delete the selected rate
            String query = "delete from commissionrate where ID = '"+id+"'";
            statement.executeUpdate(query);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static ObservableList<CommissionRate> getCommissionRates()
    {
        try
        {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to select the commission rates
            String query = "SELECT * FROM commissionrate ORDER BY BlankType DESC";
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next())
            {
                commissionRates.add(new CommissionRate(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3)));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return commissionRates;
    }

    private static void refreshTable()
    {
        commissionRates.clear();
        getCommissionRates();
    }
}
