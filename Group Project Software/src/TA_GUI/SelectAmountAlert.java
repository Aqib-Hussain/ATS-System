package TA_GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SelectAmountAlert
{
    public static void display()
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setTitle("Error");
        window.setMinWidth(300);
        window.setMinHeight(150);

        // Labels
        Label label = new Label("Please select an amount");

        // Buttons
        Button button = new Button("Close");
        button.getStyleClass().add("button-exit");
        button.setOnAction(e -> window.close());

        // Layout
        VBox center_layout = new VBox(20);
        center_layout.setPadding(new Insets(0,0,10,0));
        center_layout.getChildren().addAll(label, button);
        center_layout.setAlignment(Pos.CENTER);

        BorderPane root_layout = new BorderPane();
        root_layout.setCenter(center_layout);

        // Scene
        Scene scene = new Scene(root_layout);
        scene.getStylesheets().add("Stylesheet.css");
        window.setScene(scene);

        // Start window
        window.showAndWait();
    }
}
