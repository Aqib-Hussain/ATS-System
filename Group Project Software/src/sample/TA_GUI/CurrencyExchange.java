package sample.TA_GUI;

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
        // Text
        TextField text_currencyExchg = new TextField();
        text_currencyExchg.setMinSize(50,25);
        // Buttons
        Button button_setRates = new Button("Set");
        button_setRates.setMinSize(50,25);

        Button cancel = new Button("Cancel");
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
        button_layout.getChildren().addAll(text_currencyExchg, button_setRates);
        button_layout.setAlignment(Pos.CENTER);

        HBox bottom_layout = new HBox(50);
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().add(cancel);

        HBox top_layout = new HBox(50);
        top_layout.setAlignment(Pos.CENTER);
        top_layout.getChildren().add(label_task);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10,10,10,10));
        root_layout.setCenter(button_layout);
        root_layout.setBottom(bottom_layout);
        root_layout.setTop(top_layout);

        Scene scene = new Scene(root_layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
