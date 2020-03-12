package sample.OM_GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
        // Buttons
        Button editStatus = new Button("Edit Status");
        editStatus.setMinSize(100,25);

        Button editDiscount = new Button("Edit Discount");
        editDiscount.setMinSize(100,25);
        // Layout
        BorderPane root_layout = new BorderPane();
        VBox button_layout = new VBox(50);
        button_layout.getChildren().addAll(editDiscount, editStatus);
        root_layout.setRight(button_layout);

        Scene scene = new Scene(root_layout);
        window.setScene(scene);
        window.showAndWait();

    }
}

