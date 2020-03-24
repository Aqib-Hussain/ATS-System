package OM_GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RefundLog
{
    public static void display(String title)
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(500);
        window.setMinHeight(425);
        window.setResizable(false);

        // Labels
        Label refund_label = new Label("Refund Log");
        refund_label.setFont(Font.font(20));

        // List
        ListView refundList = new ListView();

        // Buttons
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
        VBox top_layout = new VBox();
        top_layout.setAlignment(Pos.CENTER);
        top_layout.setPadding(new Insets(10,0,10,0));
        top_layout.getChildren().add(refund_label);

        VBox centre_layout = new VBox();
        centre_layout.setAlignment(Pos.CENTER);
        centre_layout.setPadding(new Insets(0,0,10,0));
        centre_layout.getChildren().add(refundList);

        HBox bottom_layout = new HBox();
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().add(close);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10,10,10,10));
        root_layout.setTop(top_layout);
        root_layout.setCenter(centre_layout);
        root_layout.setBottom(bottom_layout);

        //Scene
        Scene scene = new Scene(root_layout);
        window.setScene(scene);

        // Start window
        window.showAndWait();
    }
}
