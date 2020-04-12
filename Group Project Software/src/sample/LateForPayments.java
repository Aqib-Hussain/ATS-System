package sample;

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

public class LateForPayments
{
    public static void display(String name)
    {
        // Creating a new window
        Stage window = new Stage();
        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Late payments");
        window.sizeToScene();

        // Labels
        Label main = new Label("Late Payment Notification");
        main.setUnderline(true);
        main.getStyleClass().add("label-title");

        Label label = new Label(name);
        label.getStyleClass().add("label-name");

        // Buttons
        Button button = new Button("Ok");
        button.setOnAction(e -> window.close());

        // Layout
        HBox center_layout = new HBox();
        center_layout.setPadding(new Insets(20,0,0,0));
        center_layout.setAlignment(Pos.CENTER);
        center_layout.getChildren().add(label);

        VBox top_layout = new VBox();
        top_layout.setAlignment(Pos.CENTER);
        top_layout.getChildren().add(main);

        HBox bottom_layout = new HBox();
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().add(button);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10,10,10,10));
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
}
