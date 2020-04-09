package OM_GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.security.auth.login.AccountLockedException;

public class HelpAlert
{
    public static void display()
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setTitle("Help");
        window.setMinWidth(400);
        window.setMinHeight(300);

        // Labels
        Label tip_label = new Label("Tip");
        tip_label.getStyleClass().add("label-title");

        Label shift = new Label("You can use SHIFT to select multiple connected rows");
        Label ctrl = new Label ("You can use CTRL to select multiple distinct rows  ");

        // Buttons
        Button button = new Button("Close");
        button.getStyleClass().add("button-exit");
        button.setOnAction(e -> window.close());

        // Layout
        VBox center_layout = new VBox(20);
        center_layout.setPadding(new Insets(0,0,10,0));
        center_layout.getChildren().addAll(tip_label,shift,ctrl);
        center_layout.setAlignment(Pos.CENTER);

        HBox bottom_layout = new HBox();
        bottom_layout.setAlignment(Pos.CENTER);
        bottom_layout.getChildren().add(button);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10,10,10,10));
        root_layout.setCenter(center_layout);
        root_layout.setBottom(bottom_layout);

        // Scene
        Scene scene = new Scene(root_layout);
        scene.getStylesheets().add("Stylesheet.css");
        window.setScene(scene);

        // Start window
        window.showAndWait();
    }
}
