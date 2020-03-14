package sample.OM_GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class GloReportType
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
        selectAReport.setPadding(new Insets(0,0,13,8));
        // Buttons
        Button interlineReportButton = new Button("Generate Interline Report");
        interlineReportButton.setMaxWidth(175);

        Button domesticReportButton = new Button("Generate Domestic Report");
        domesticReportButton.setMaxWidth(175);

        Button close = new Button("Close");
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

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(11,11,11,11));
        root_layout.setCenter(button_layout);
        root_layout.setBottom(close);
        root_layout.setTop(selectAReport);

        Scene scene = new Scene(root_layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
