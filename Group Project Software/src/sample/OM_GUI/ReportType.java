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


public class ReportType {
    public static void display(String title, String msg)
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
        // Buttons
        Button interlineReportButton = new Button("Generate Interline Report");
        interlineReportButton.setMaxWidth(175);
        Button domesticReportButton = new Button("Generate Domestic Report");
        domesticReportButton.setMaxWidth(175);
        Button cancel = new Button("Cancel");
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.close();
            }
        });
        // Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(selectAReport, interlineReportButton, domesticReportButton, cancel);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
