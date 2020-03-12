package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
        // Buttons
        Button button = new Button("Close this window!");
        button.setOnAction(e -> window.close());

        // Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
