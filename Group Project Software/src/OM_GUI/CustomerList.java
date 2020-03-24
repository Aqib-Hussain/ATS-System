package OM_GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomerList
{
    public static void display(String title)
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setHeight(500);
        window.setWidth(625);
        window.setResizable(false);

        // Labels
        Label page_info = new Label("Customers");
        page_info.setFont(Font.font(20));

        // List
        ListView customerList = new ListView();

        // Buttons
        Button editStatus = new Button("Edit Status");
        editStatus.setMinSize(100,25);

        Button editDiscount = new Button("Edit Discount");
        editDiscount.setMinSize(100,25);

        Button close = new Button("Close");
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
        top_layout.setPadding(new Insets(10,0,10,0));
        top_layout.setAlignment(Pos.CENTER);
        top_layout.getChildren().add(page_info);

        VBox right_layout = new VBox(50);
        right_layout.setPadding(new Insets(10,0,10,0));
        right_layout.setAlignment(Pos.TOP_RIGHT);
        right_layout.getChildren().addAll(editDiscount, editStatus);

        VBox center_layout = new VBox();
        center_layout.setPadding(new Insets(10,10,10,0));
        center_layout.setAlignment(Pos.CENTER);
        center_layout.getChildren().add(customerList);

        HBox bottom_layout = new HBox();
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().addAll(close);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10,10,10,10));
        root_layout.setTop(top_layout);
        root_layout.setRight(right_layout);
        root_layout.setCenter(center_layout);
        root_layout.setBottom(bottom_layout);

        // Scene
        Scene scene = new Scene(root_layout);
        window.setScene(scene);

        // Start window
        window.showAndWait();

    }
}

