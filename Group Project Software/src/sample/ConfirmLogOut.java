package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yes, no);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
