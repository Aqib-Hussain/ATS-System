package SA_GUI;

import Database.DBConnectivity;
import javafx.scene.layout.GridPane;
import sample.Staff.TravelAdvisor;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class ViewTravelAdvisors
{
    // Database
    static DBConnectivity dbConnectivity = new DBConnectivity();
    static Connection connection = dbConnectivity.getConnection();

    // Table View
    static TableView<TravelAdvisor> table;
    static ObservableList<TravelAdvisor> advisors = FXCollections.observableArrayList();

    static TravelAdvisor travelAdvisorEdit = new TravelAdvisor();

    // Layouts
    static BorderPane root_layout = new BorderPane();
    static BorderPane addTA_Root_Layout = new BorderPane();
    static BorderPane editTA_Root_Layout = new BorderPane();

    // Label
    static Label editTA_pageInfo = new Label();

    // TextFields
    static TextField editTA_name_text = new TextField();
    static TextField editTA_email_text = new TextField();
    static TextField editTA_address_text = new TextField();

    // Scenes
    static Scene scene = new Scene(root_layout);
    static Scene addTA_scene = new Scene(addTA_Root_Layout);
    static Scene editTA_scene = new Scene(editTA_Root_Layout);

    public static void display(String title) {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setHeight(500);
        window.setWidth(625);
        window.setResizable(false);

        // Labels
        Label page_info = new Label("Travel Advisors");
        page_info.getStyleClass().add("label-title");

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

        table = new TableView<>();
        table.setItems(getAdvisors());
        table.getColumns().addAll(iDColumn, nameColumn, emailColumn);

        // Buttons
        Button addNewTA = new Button("Add Travel Advisor");
        addNewTA.setMinWidth(140);
        addNewTA.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(addTA_scene);
            }
        });

        Button refreshTable_button = new Button("Refresh Table");
        refreshTable_button.getStyleClass().add("button-login");
        refreshTable_button.setMinWidth(150);
        refreshTable_button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                refreshTable();
            }
        });

        Button removeTA = new Button("Remove Travel Advisor");
        removeTA.setMinWidth(140);
        removeTA.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!(table.getSelectionModel().isEmpty()))
                {
                    TravelAdvisor selectedTA_Remove = table.getSelectionModel().getSelectedItem();

                    boolean answer = RemoveTAConfirm.display(selectedTA_Remove.getName());

                    if (answer)
                    {
                        table.getItems().remove(selectedTA_Remove);
                        table.getSelectionModel().clearSelection();
                        removeAdvisor(selectedTA_Remove.getId());
                    } else
                    {
                        table.getSelectionModel().clearSelection();
                    }
                }
                else
                {
                    SelectTAToRemoveAlert.display();
                }
            }
        });

        Button editTA = new Button("Edit Travel Advisor");
        editTA.setMinWidth(140);
        editTA.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(!(table.getSelectionModel().isEmpty()))
                {
                    travelAdvisorEdit = table.getSelectionModel().getSelectedItem();
                    window.setScene(editTA_scene);
                    editTA_pageInfo.setText("Editing details for "+travelAdvisorEdit.getName()+"");
                    editTA_name_text.setText(travelAdvisorEdit.getName());
                    editTA_email_text.setText(travelAdvisorEdit.getEmail());
                    editTA_address_text.setText(travelAdvisorEdit.getAddress());
                    table.getSelectionModel().clearSelection();
                }
                else
                {
                    SelectTAToEdit.display();
                    table.getSelectionModel().clearSelection();
                }
            }
        });

        Button close = new Button("Close");
        close.getStyleClass().add("button-exit");
        close.setMinSize(75,25);
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.close();
            }
        });

        // Layout
        VBox top_layout = new VBox();
        top_layout.setAlignment(Pos.CENTER);
        top_layout.getChildren().add(page_info);
        top_layout.setPadding(new Insets(0,0,20,0));

        VBox list_layout = new VBox(50);
        list_layout.setAlignment(Pos.CENTER);
        list_layout.getChildren().add(table);
        list_layout.setPadding(new Insets(0,0,20,0));

        HBox button_layout = new HBox(30);
        button_layout.setAlignment(Pos.CENTER);
        button_layout.getChildren().addAll(addNewTA, removeTA, editTA);
        button_layout.setPadding(new Insets(0,0,20,0));

        BorderPane center_layout = new BorderPane();
        center_layout.setCenter(list_layout);
        center_layout.setBottom(button_layout);

        HBox bottom_layout = new HBox(363);
        bottom_layout.setAlignment(Pos.CENTER);
        bottom_layout.getChildren().addAll(refreshTable_button, close);

        root_layout.setPadding(new Insets(10, 10, 10, 10));
        root_layout.setTop(top_layout);
        root_layout.setCenter(center_layout);
        root_layout.setBottom(bottom_layout);

        // Scene
        scene.getStylesheets().add("Stylesheet.css");
        window.setScene(scene);

        // *************Add Travel Advisor Window************ \\
        // Label
        Label addTA_pageInfo = new Label("Enter details for a new Travel Advisor");
        addTA_pageInfo.getStyleClass().add("label-title");

        Label addTA_name = new Label("Name:");
        Label addTA_email = new Label("Email:");
        Label addTA_address = new Label("Address: ");
        Label addTA_password = new Label("Password:");

        //TextFields
        TextField addTA_name_text = new TextField();
        addTA_name_text.setPromptText("Name");
        addTA_name_text.setMinWidth(300);

        TextField addTA_password_text = new TextField();
        addTA_password_text.setPromptText("Email");
        addTA_password_text.setMinWidth(300);

        TextField addTA_address_text = new TextField();
        addTA_address_text.setPromptText("Email");
        addTA_address_text.setMinWidth(300);

        TextField addTA_email_text = new TextField();
        addTA_email_text.setPromptText("Password");
        addTA_email_text.setMinWidth(300);

        // Buttons
        Button create = new Button("Create Travel Advisor");
        create.setMinWidth(200);
        create.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                addAdvisor(addTA_name_text.getText(), addTA_password_text.getText(), addTA_address_text.getText() ,addTA_email_text.getText());
                window.setScene(scene);
                refreshTable();
                addTA_name_text.clear();
                addTA_email_text.clear();
                addTA_password_text.clear();
                addTA_address_text.clear();
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
                window.setScene(scene);
            }
        });

        //---Layout---\\
        // Button Layout
        GridPane addTA_centre_layout = new GridPane();
        addTA_centre_layout.setAlignment(Pos.CENTER);
        addTA_centre_layout.setHgap(15);
        addTA_centre_layout.setVgap(12);
        GridPane.setConstraints(addTA_name, 0, 0);
        GridPane.setConstraints(addTA_name_text, 1, 0);
        GridPane.setConstraints(addTA_email, 0, 1);
        GridPane.setConstraints(addTA_email_text, 1, 1);
        GridPane.setConstraints(addTA_password, 0, 2);
        GridPane.setConstraints(addTA_password_text, 1, 2);
        GridPane.setConstraints(addTA_address, 0, 3);
        GridPane.setConstraints(addTA_address_text, 1, 3);
        addTA_centre_layout.getChildren().addAll(addTA_name, addTA_name_text, addTA_email, addTA_email_text, addTA_password, addTA_password_text, addTA_address, addTA_address_text);

        HBox addTA_bottom_layout = new HBox(313);
        addTA_bottom_layout.getChildren().addAll(create, cancel);
        addTA_bottom_layout.setAlignment(Pos.CENTER);

        VBox addTA_top_layout = new VBox();
        addTA_top_layout.getChildren().add(addTA_pageInfo);
        addTA_top_layout.setAlignment(Pos.TOP_CENTER);

        // Root Layout
        addTA_Root_Layout.setPadding(new Insets(10,10,10,10));
        addTA_Root_Layout.setTop(addTA_top_layout);
        addTA_Root_Layout.setCenter(addTA_centre_layout);
        addTA_Root_Layout.setBottom(addTA_bottom_layout);

        // Scene
        addTA_scene.getStylesheets().add("Stylesheet.css");

        // *************Edit Travel Advisor Window************ \\
        // Label
        editTA_pageInfo.getStyleClass().add("label-title");

        Label editTA_name = new Label("Name:");
        Label editTA_email = new Label("Email:");
        Label editTA_address = new Label("Address:");

        //TextFields

        editTA_name_text.setMinWidth(300);

        editTA_email_text.setMinWidth(300);

        editTA_address_text.setMinWidth(300);

        // Buttons
        Button editTA_save = new Button("Save");
        editTA_save.getStyleClass().add("button-login");
        editTA_save.setMinWidth(125);
        editTA_save.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                editAdvisor(editTA_name_text.getText(), editTA_email_text.getText(), editTA_address_text.getText(), travelAdvisorEdit.getId());
                editTA_name_text.clear();
                editTA_email_text.clear();
                editTA_address_text.clear();
                refreshTable();
                window.setScene(scene);
            }
        });

        Button editTA_cancel = new Button("Cancel");
        editTA_cancel.getStyleClass().add("button-exit");
        editTA_cancel.setMinWidth(75);
        editTA_cancel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(scene);
                editTA_name_text.clear();
                editTA_email_text.clear();
                editTA_address_text.clear();
            }
        });

        //---Layout---\\
        // Button Layout
        GridPane editTA_centre_layout = new GridPane();
        editTA_centre_layout.setAlignment(Pos.CENTER);
        editTA_centre_layout.setHgap(15);
        editTA_centre_layout.setVgap(12);
        GridPane.setConstraints(editTA_name, 0, 0);
        GridPane.setConstraints(editTA_name_text, 1, 0);
        GridPane.setConstraints(editTA_email, 0, 1);
        GridPane.setConstraints(editTA_email_text, 1, 1);
        GridPane.setConstraints(editTA_address, 0, 2);
        GridPane.setConstraints(editTA_address_text, 1, 2);
        editTA_centre_layout.getChildren().addAll(editTA_name, editTA_name_text, editTA_email, editTA_email_text, editTA_address, editTA_address_text);

        HBox editTA_bottom_layout = new HBox(388);
        editTA_bottom_layout.getChildren().addAll(editTA_save, editTA_cancel);
        editTA_bottom_layout.setAlignment(Pos.CENTER);

        VBox editTA_top_layout = new VBox();
        editTA_top_layout.getChildren().add(editTA_pageInfo);
        editTA_top_layout.setAlignment(Pos.TOP_CENTER);

        // Root Layout
        editTA_Root_Layout.setPadding(new Insets(10,10,10,10));
        editTA_Root_Layout.setTop(editTA_top_layout);
        editTA_Root_Layout.setCenter(editTA_centre_layout);
        editTA_Root_Layout.setBottom(editTA_bottom_layout);

        // Scene
        editTA_scene.getStylesheets().add("Stylesheet.css");

        // Start window
        window.showAndWait();
    }

    public static ObservableList<TravelAdvisor> getAdvisors()
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();
            // SQL query to find matching travel advisors
            String query = "SELECT * FROM staff WHERE StaffType = 'Travel Advisor'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
            {
                advisors.add(new TravelAdvisor(resultSet.getString("name"),
                                               resultSet.getString("ID"),
                                               resultSet.getString("email"),
                                               resultSet.getString("address")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return advisors;
    }

    public static void removeAdvisor(String id)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to find matching travel advisors
            String query = "DELETE FROM staff WHERE ID = '"+id+"'";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void editAdvisor(String name, String email, String address, String ID)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to edit advisors data
            String query = "UPDATE staff SET name = '"+name+"', address = '"+address+"', email = '"+email+"' WHERE ID = '"+ID+"'";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void refreshTable()
    {
        advisors.clear();
        getAdvisors();
    }

    public static void addAdvisor(String name, String password, String address, String email)
    {
        try {
            // Connect to the Database
            Statement statement = connection.createStatement();

            // SQL query to add travel advisor
            String query = "INSERT INTO staff (ID, name, StaffType, password, address, email, status) VALUES ('1', '"+ name +"', 'Travel Advisor', '"+password+"', '"+address+"','"+email+"', 'loggedOut')";
            statement.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
