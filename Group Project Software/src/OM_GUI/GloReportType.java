package OM_GUI;

import TA_GUI.ViewReports_TA;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import TA_GUI.GenerateReport_TA;

public class GloReportType
{
    public static void display(String title)
    {
        // Creating a new window
        Stage window = new Stage();

        // Window takes priority until taken care of
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(250);
        window.setResizable(false);

        // Labels
        Label selectAReport = new Label("Please Select a Report Type");
        selectAReport.setPadding(new Insets(10,0,10,0));

        // Buttons
        Button interlineReportButton = new Button("Generate Interline Report");
        interlineReportButton.setMaxWidth(250);
        interlineReportButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent) {
                GenerateReport_TA.display(title);
                ViewReports_TA.display(title);
            }
        });

        Button domesticReportButton = new Button("Generate Domestic Report");
        domesticReportButton.setMaxWidth(250);
        domesticReportButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent) {
                GenerateReport_TA.display(title);
                ViewReports_TA.display(title);
            }
        });

        Button close = new Button("Close");
        close.getStyleClass().add("button-exit");
        close.setMinSize(75,25);
        close.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.close();
            }
        });

        // Layout
        VBox top_layout = new VBox();
        top_layout.setAlignment(Pos.CENTER);
        top_layout.setPadding(new Insets(0,0,10,0));
        top_layout.getChildren().add(selectAReport);

        VBox center_layout = new VBox(15);
        center_layout.setAlignment(Pos.CENTER);
        center_layout.setSpacing(10);
        center_layout.getChildren().addAll(interlineReportButton, domesticReportButton);

        HBox bottom_layout = new HBox();
        bottom_layout.setPadding(new Insets(10,0,10,0));
        bottom_layout.setAlignment(Pos.BASELINE_RIGHT);
        bottom_layout.getChildren().add(close);

        BorderPane root_layout = new BorderPane();
        root_layout.setPadding(new Insets(10,10,10,10));
        root_layout.setCenter(center_layout);
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
