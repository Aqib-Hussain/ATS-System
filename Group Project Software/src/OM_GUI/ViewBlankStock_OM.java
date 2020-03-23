package OM_GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewBlankStock_OM
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
        // List
        ListView blankStockList = new ListView();

        // Buttons
        Button re_assign = new Button("Re-Assign");
        re_assign.setMinSize(75, 25);

        Button assign = new Button("Assign");
        assign.setMinSize(75,25);

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
        VBox button_layout = new VBox(50);
        button_layout.getChildren().addAll(re_assign, assign);

        HBox bottom_layout = new HBox(50);
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().addAll(close);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10,10,10,10));
        root_layout.setRight(button_layout);
        root_layout.setLeft(blankStockList);
        root_layout.setBottom(bottom_layout);

        Scene scene = new Scene(root_layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
