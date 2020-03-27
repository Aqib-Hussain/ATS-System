package TA_GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CurrencyExchange
{
    public static void display(String title)
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(200);
        window.setMinHeight(250);
        window.setResizable(false);

        // Labels
        Label label_task = new Label("Please enter a value");
        label_task.getStyleClass().add("label-title");

        // Text
        TextField text_currencyExchange = new TextField();
        text_currencyExchange.setFont(Font.font(16));
        text_currencyExchange.setMaxSize(100,25);

        // Buttons
        Button button_setRates = new Button("Set");
        button_setRates.setMinSize(50,25);

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("button-exit");
        cancel.setMinSize(75,25);
        cancel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                window.close();
            }
        });

        // Layout
        VBox button_layout = new VBox(10);
        button_layout.setAlignment(Pos.CENTER);
        button_layout.getChildren().addAll(text_currencyExchange, button_setRates);

        HBox bottom_layout = new HBox();
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().add(cancel);

        HBox top_layout = new HBox();
        top_layout.setAlignment(Pos.CENTER);
        top_layout.getChildren().add(label_task);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10,10,10,10));
        root_layout.setCenter(button_layout);
        root_layout.setBottom(bottom_layout);
        root_layout.setTop(top_layout);

        // Scene
        Scene scene = new Scene(root_layout);
        scene.getStylesheets().add("Stylesheet.css");
        window.setScene(scene);

        // Start window
        window.showAndWait();
    }
}
