package SA_GUI;

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

public class ViewBlankStock_SA
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
        Label page_info = new Label("Blank Stock");
        page_info.getStyleClass().add("label-title");

        // List
        ListView blankStock = new ListView();

        // Buttons
        Button addBlank = new Button("Add Blanks");
        addBlank.setMinSize(140, 25);

        Button removeBlank = new Button("Remove Blanks");
        removeBlank.setMinSize(140, 25);

        Button close = new Button("Close");
        close.getStyleClass().add("button-exit");
        close.setMinSize(75, 25);
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
        top_layout.getChildren().add(page_info);
        top_layout.setPadding(new Insets(0, 0, 20, 0));

        VBox list_layout = new VBox(50);
        list_layout.setAlignment(Pos.CENTER);
        list_layout.getChildren().add(blankStock);
        list_layout.setPadding(new Insets(0, 0, 20, 0));

        HBox button_layout = new HBox(60);
        button_layout.setAlignment(Pos.CENTER);
        button_layout.getChildren().addAll(addBlank, removeBlank);
        button_layout.setPadding(new Insets(0, 0, 20, 0));

        BorderPane center_layout = new BorderPane();
        center_layout.setCenter(list_layout);
        center_layout.setBottom(button_layout);

        HBox bottom_layout = new HBox(50);
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().addAll(close);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10, 10, 10, 10));
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
