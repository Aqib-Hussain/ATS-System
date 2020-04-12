package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConfirmLogOut
{
    static boolean isLoogedOut = false;

    public static boolean isIsLoogedOut()
    {
        return isLoogedOut;
    }

    public static void setIsLoogedOut(boolean isLoogedOut)
    {
        ConfirmLogOut.isLoogedOut = isLoogedOut;
    }

    public static void display()
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setTitle("Confirm Log-out");
        window.setMinWidth(250);
        window.setMinHeight(150);

        // Labels
        Label label = new Label("Are you sure you want to Log-Out?");

        // Buttons
        Button yes = new Button("Yes");
        yes.getStyleClass().add("button-exit");
        yes.setMinWidth(75);
        yes.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.close();
                setIsLoogedOut(true);
            }
        });

        Button no = new Button("No");
        no.setMinWidth(75);
        no.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                window.close();
                setIsLoogedOut(false);
            }
        });

        // Layout
        VBox top_layout = new VBox();
        top_layout.setPadding(new Insets(0,0,10,0));
        top_layout.setAlignment(Pos.CENTER);
        top_layout.getChildren().add(label);

        HBox center_layout = new HBox(15);
        center_layout.getChildren().addAll(yes, no);
        center_layout.setAlignment(Pos.CENTER);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10,10,10,10));
        root_layout.setTop(top_layout);
        root_layout.setCenter(center_layout);

        // Scene
        Scene scene = new Scene(root_layout);
        scene.getStylesheets().add("Stylesheet.css");
        window.setScene(scene);

        // Start window
        window.showAndWait();
    }
}
