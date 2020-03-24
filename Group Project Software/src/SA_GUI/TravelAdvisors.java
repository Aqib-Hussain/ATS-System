package SA_GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TravelAdvisors
{
    // Layouts
    static BorderPane root_layout = new BorderPane();
    static BorderPane addTA_Root_Layout = new BorderPane();
    static BorderPane editTA_Root_Layout = new BorderPane();

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
        page_info.setFont(Font.font(20));

        // List
        ListView travelAdvisorList = new ListView();

        // Buttons
        Button addNewTA = new Button("Add Travel Advisor");
        addNewTA.setMinSize(100, 25);
        addNewTA.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(addTA_scene);
            }
        });

        Button removeTA = new Button("Remove Travel Advisor");
        removeTA.setMinSize(100, 25);

        Button editTA = new Button("Edit Travel Advisor");
        editTA.setMinSize(100,25);
        editTA.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(editTA_scene);
            }
        });

        Button close = new Button("Close");
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
        list_layout.getChildren().add(travelAdvisorList);
        list_layout.setPadding(new Insets(0,0,20,0));

        HBox button_layout = new HBox(30);
        button_layout.setAlignment(Pos.CENTER);
        button_layout.getChildren().addAll(addNewTA, removeTA, editTA);
        button_layout.setPadding(new Insets(0,0,20,0));

        BorderPane center_layout = new BorderPane();
        center_layout.setCenter(list_layout);
        center_layout.setBottom(button_layout);

        HBox bottom_layout = new HBox(50);
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().addAll(close);

        root_layout.setPadding(new Insets(10, 10, 10, 10));
        root_layout.setTop(top_layout);
        root_layout.setCenter(center_layout);
        root_layout.setBottom(bottom_layout);

        // Scene
        window.setScene(scene);

        // *************Add Travel Advisor Window************ \\
        // Label
        Label addTA_pageInfo = new Label("Enter details for a new Travel Advisor");
        addTA_pageInfo.setFont(Font.font(20));

        Label addTA_firstName = new Label("Firstname:");
        Label addTA_surname = new Label("Surname:");
        Label addTA_address = new Label("Address:");
        Label addTA_phoneNumber = new Label("Phone No:");

        // Buttons
        Button create = new Button("Create Travel Advisor");
        create.setMinSize(200,25);

        Button cancel = new Button("Cancel");
        cancel.setMinSize(75,25);
        cancel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(scene);
            }
        });

        //TextFields
        TextField addTA_firstname_text = new TextField();
        addTA_firstname_text.setPromptText("Firstname");
        addTA_firstname_text.setMaxWidth(300);

        TextField addTA_surname_text = new TextField();
        addTA_surname_text.setPromptText("Surname");
        addTA_surname_text.setMaxWidth(300);

        TextField addTA_address_text = new TextField();
        addTA_address_text.setPromptText("Address");
        addTA_address_text.setMaxWidth(300);

        TextField addTA_phoneNum_text = new TextField();
        addTA_phoneNum_text.setPromptText("Phone Number");
        addTA_phoneNum_text.setMaxWidth(300);

        //---Layout---\\
        // Button Layout
        VBox addTA_centre_layout = new VBox(10);
        addTA_centre_layout.getChildren().addAll(addTA_firstname_text, addTA_surname_text, addTA_address_text, addTA_phoneNum_text);
        addTA_centre_layout.setAlignment(Pos.CENTER);

        VBox addTA_left_layout = new VBox(21);
        addTA_left_layout.getChildren().addAll(addTA_firstName, addTA_surname, addTA_address, addTA_phoneNumber);
        addTA_left_layout.setAlignment(Pos.CENTER_LEFT);

        HBox addTA_bottom_layout = new HBox(313);
        addTA_bottom_layout.getChildren().addAll(create, cancel);
        addTA_bottom_layout.setAlignment(Pos.BASELINE_LEFT);

        VBox addTA_top_layout = new VBox();
        addTA_top_layout.getChildren().add(addTA_pageInfo);
        addTA_top_layout.setAlignment(Pos.TOP_CENTER);

        // Root Layout
        addTA_Root_Layout.setPadding(new Insets(10,10,10,10));
        addTA_Root_Layout.setTop(addTA_top_layout);
        addTA_Root_Layout.setCenter(addTA_centre_layout);
        addTA_Root_Layout.setBottom(addTA_bottom_layout);
        addTA_Root_Layout.setLeft(addTA_left_layout);

        // *************Edit Travel Advisor Window************ \\
        // Label
        Label editTA_pageInfo = new Label("Editing Details for ...");
        editTA_pageInfo.setFont(Font.font(20));

        Label editTA_firstName = new Label("Firstname:");
        Label editTA_surname = new Label("Surname:");
        Label editTA_address = new Label("Address:");
        Label editTA_phoneNumber = new Label("Phone No:");

        // Buttons
        Button editTA_save = new Button("Save");
        editTA_save.setMinSize(125,25);

        Button editTA_cancel = new Button("Cancel");
        editTA_cancel.setMinSize(75,25);
        editTA_cancel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.setScene(scene);
            }
        });

        //TextFields
        TextField editTA_firstname_text = new TextField();
        editTA_firstname_text.setPromptText("Firstname");
        editTA_firstname_text.setMaxWidth(300);

        TextField editTA_surname_text = new TextField();
        editTA_surname_text.setPromptText("Surname");
        editTA_surname_text.setMaxWidth(300);

        TextField editTA_address_text = new TextField();
        editTA_address_text.setPromptText("Address");
        editTA_address_text.setMaxWidth(300);

        TextField editTA_phoneNum_text = new TextField();
        editTA_phoneNum_text.setPromptText("Phone Number");
        editTA_phoneNum_text.setMaxWidth(300);

        //---Layout---\\
        // Button Layout
        VBox editTA_centre_layout = new VBox(10);
        editTA_centre_layout.getChildren().addAll(editTA_firstname_text, editTA_surname_text, editTA_address_text, editTA_phoneNum_text);
        editTA_centre_layout.setAlignment(Pos.CENTER);

        VBox editTA_left_layout = new VBox(21);
        editTA_left_layout.getChildren().addAll(editTA_firstName, editTA_surname, editTA_address, editTA_phoneNumber);
        editTA_left_layout.setAlignment(Pos.CENTER_LEFT);

        HBox editTA_bottom_layout = new HBox(388);
        editTA_bottom_layout.getChildren().addAll(editTA_save, editTA_cancel);
        editTA_bottom_layout.setAlignment(Pos.BASELINE_LEFT);

        VBox editTA_top_layout = new VBox();
        editTA_top_layout.getChildren().add(editTA_pageInfo);
        editTA_top_layout.setAlignment(Pos.TOP_CENTER);

        // Root Layout
        editTA_Root_Layout.setPadding(new Insets(10,10,10,10));
        editTA_Root_Layout.setTop(editTA_top_layout);
        editTA_Root_Layout.setCenter(editTA_centre_layout);
        editTA_Root_Layout.setBottom(editTA_bottom_layout);
        editTA_Root_Layout.setLeft(editTA_left_layout);

        // Start window
        window.showAndWait();
    }
}
