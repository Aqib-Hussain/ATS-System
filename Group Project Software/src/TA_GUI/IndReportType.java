package TA_GUI;

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

public class IndReportType
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
        Label selectAReport = new Label("Please Select a Report Type");
        selectAReport.setFont(Font.font(14));
        selectAReport.setPadding(new Insets(10,0,10,0));

        // Buttons
        Button interlineReportButton = new Button("Generate Interline Report");
        interlineReportButton.setMaxWidth(175);

        Button domesticReportButton = new Button("Generate Domestic Report");
        domesticReportButton.setMaxWidth(175);

        Button close = new Button("Close");
        close.setMinSize(75,25);
        close.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.close();
            }
        });

        // Layout
        VBox button_layout = new VBox(10);
        button_layout.setAlignment(Pos.CENTER);
        button_layout.setSpacing(10);
        button_layout.getChildren().addAll(interlineReportButton, domesticReportButton);

        HBox bottom_layout = new HBox();
        bottom_layout.setAlignment(Pos.BASELINE_CENTER);
        bottom_layout.getChildren().add(close);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10,10,10,10));
        root_layout.setCenter(button_layout);
        root_layout.setBottom(bottom_layout);
        root_layout.setTop(selectAReport);

        // Scene
        Scene scene = new Scene(root_layout);
        window.setScene(scene);

        // Start window
        window.showAndWait();

    }
}
