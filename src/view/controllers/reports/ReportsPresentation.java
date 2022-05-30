package view.controllers.reports;

import com.github.gbfragoso.JasperViewerFX;
import controller.ReportsManagement;
import controller.Translatable;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import services.Resources;
import view.controllers.LoadScreenController;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

public class ReportsPresentation implements Initializable, Translatable {
    private ResourceBundle rb;

    private Stage owner;

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
        this.owner = Resources.getMainWindow();

        colReport.setOnMouseClicked(event -> colReport());

    }

    private void colReport(){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/load_screen.fxml"));
        Parent root;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LoadScreenController loadScreenController = fxmlLoader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.UNDECORATED);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);

        Thread th = new Thread(() -> {
            Object[] response;

            try{
                response = ReportsManagement.getCollectionReport();

                switch ((String)response[0]){
                    case "SQLE Error" -> {
                        Platform.runLater(loadScreenController::closeWindow);

                        alerts(rb.getString("reports.errSql"));
                        return;
                    }
                    case "JRE" -> {
                        Platform.runLater(loadScreenController::closeWindow);
                        alerts(rb.getString("reports.errJasper"));
                        return;
                    }
                }
                Platform.runLater(() -> {
                    JasperViewerFX jasperViewer = new JasperViewerFX();
                    jasperViewer.initModality(Modality.APPLICATION_MODAL);
                    jasperViewer.initOwner(owner);
                    jasperViewer.viewReport(rb.getString("reports.informeColecciones"), (JasperPrint) response[1]);
                    loadScreenController.closeWindow();
                });


            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        th.start();

        stage.show();
    }

    private void alerts(String alertMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(this.owner);
        alert.setHeaderText(null);
        alert.setTitle(rb.getString("error"));
        alert.setContentText(alertMsg);
        alert.showAndWait();
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
