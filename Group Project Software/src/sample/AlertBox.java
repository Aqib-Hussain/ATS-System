package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertBox
{
    public static void display(String title, String msg)
    {
        // Creating a new window
        Stage window = new Stage();
        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        // Labels
        Label label = new Label(msg);
        label.setFont(Font.font(15));

        // Buttons
        Button button = new Button("Close this window!");
        button.setOnAction(e -> window.close());

        // Layout
        VBox center_layout = new VBox(20);
        center_layout.setPadding(new Insets(0,0,15,0));
        center_layout.getChildren().addAll(label, button);
        center_layout.setAlignment(Pos.CENTER);

        BorderPane root_layout = new BorderPane();
        root_layout.setCenter(center_layout);

        // Scene
        Scene scene = new Scene(root_layout);
        window.setScene(scene);

        // Start window
        window.showAndWait();
    }
}
