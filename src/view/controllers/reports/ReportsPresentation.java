package view.controllers.reports;

import controller.Translatable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsPresentation implements Initializable, Translatable {
    private ResourceBundle rb;

    @FXML
    private Label lblNumberReportName;

    @FXML
    private BorderPane numberReportCol;

    @FXML
    private Label lblNumberReportCol;

    @FXML
    private BorderPane colReportName;

    @FXML
    private TilePane cardsPane;

    @FXML
    private Label lblColReport;

    @FXML
    private Label lblNumberReport;

    @FXML
    private Label lblColReportName;

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

    @Override
    public void translate(ResourceBundle resources) {
        lblColReport.setText(resources.getString("reports.informeColecciones"));
        lblColReportName.setText(resources.getString("reports.informeColeccionesNombre"));
        lblNumberReport.setText(resources.getString("reports.informeNumeros"));
        lblNumberReportCol.setText(resources.getString("reports.informeNumerosColec"));
        lblNumberReportName.setText(resources.getString("reports.informeNumerosNombre"));
    }
}
