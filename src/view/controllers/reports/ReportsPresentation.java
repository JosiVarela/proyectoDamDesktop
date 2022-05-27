package view.controllers.reports;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsPresentation implements Initializable {
    private ResourceBundle rb;

    @FXML
    private BorderPane numberReportCol;

    @FXML
    private BorderPane colReportName;

    @FXML
    private TilePane cardsPane;

    @FXML
    private Label lblPanel;

    @FXML
    private BorderPane numberReport;

    @FXML
    private BorderPane colReport;

    @FXML
    private BorderPane numberReportName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;

        colReport.setOnMouseClicked(event -> colReport());

    }

    private void colReport(){

    }
}
