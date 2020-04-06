package TA_GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CancelPaymentAlert
{
    static boolean answer;

    public static boolean display()
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setTitle("Confirm cancel Payment");
        window.setMinWidth(250);
        window.setMinHeight(150);

        // Labels
        Label label = new Label("Are you sure you want to cancel payment?");

        // Buttons
        Button yes = new Button("Yes");
        yes.getStyleClass().add("button-exit");
        yes.setMinWidth(75);
        yes.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                answer = true;
                window.close();
            }
        });

        Button no = new Button("No");
        no.setMinWidth(75);
        no.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                answer = false;
                window.close();
            }
        });

        // Layout
        VBox top_layout = new VBox();
        top_layout.setPadding(new Insets(0,0,10,0));
        top_layout.setAlignment(Pos.CENTER);
        top_layout.getChildren().add(label);

        VBox center_layout = new VBox(15);
        center_layout.getChildren().addAll(yes, no);
        center_layout.setAlignment(Pos.CENTER);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10,10,15,10));
        root_layout.setTop(top_layout);
        root_layout.setCenter(center_layout);

        // Scene
        Scene scene = new Scene(root_layout);
        scene.getStylesheets().add("Stylesheet.css");
        window.setScene(scene);

        // Start window
        window.showAndWait();

        return answer;
    }
}
