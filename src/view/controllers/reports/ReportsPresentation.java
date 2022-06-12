package view.controllers.reports;

import com.github.gbfragoso.JasperViewerFX;
import controller.ReportsManagement;
import controller.Translatable;
import javafx.application.Platform;
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
import services.Resources;
import view.controllers.LoadScreenController;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportsPresentation implements Initializable, Translatable {
    private ResourceBundle rb;

    private Stage owner;

    @FXML
    private Label lblNumberReportName;

    @FXML
    private Label lblCopiesReportFiltered;
    @FXML
    private BorderPane copiesReportFiltered;

    @FXML
    private BorderPane colReportName;

    @FXML
    private BorderPane copiesReport;

    @FXML
    private Label lblCopiesReport;

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

        colReport.setOnMouseClicked(event -> colReportAction());
        colReportName.setOnMouseClicked(event -> colNameReportAction());
        numberReportName.setOnMouseClicked(event -> numberReportNameAction());
        numberReport.setOnMouseClicked(event -> numbersReportAction());
        copiesReport.setOnMouseClicked(event -> copiesReportAction());
        copiesReportFiltered.setOnMouseClicked(event -> copiesReportFilteredAction());
    }

    private void copiesReportFilteredAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/reports/copies_report_filtered.fxml"), rb);
        Parent root;

        try{
            root = fxmlLoader.load();

            CopiesReportFiltered copiesReportFiltered = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            copiesReportFiltered.setOwner(scene);

            stage.setMinWidth(620);
            stage.setMinHeight(440);
            stage.setWidth(620);
            stage.setHeight(440);
            stage.setMaxHeight(440);
            stage.setMaxWidth(1200);
            stage.setTitle(rb.getString("reports.informeEjemplaresFiltrado"));
            stage.initStyle(StageStyle.UTILITY);
            stage.getIcons().add(Resources.APP_ICON);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.owner);
            stage.showAndWait();


        } catch (IOException e) {
            alerts(rb.getString("err.inesperado"));
        }
    }

    private void copiesReportAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/load_screen.fxml"));
        Parent root;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            alerts(rb.getString("err.inesperado"));
            return;
        }

        LoadScreenController loadScreenController = fxmlLoader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.initOwner(this.owner);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);

        Thread th = new Thread(() -> {
            Object[] response;

            try{
                response = ReportsManagement.getCopiesReport();

                switch ((String)response[0]){
                    case "SQLE Error" -> {
                        Platform.runLater(()->{
                            loadScreenController.closeWindow();
                            alerts(rb.getString("reports.errSql"));
                        });
                        return;
                    }
                    case "JRE" -> {
                        Platform.runLater(()->{
                            loadScreenController.closeWindow();
                            alerts(rb.getString("reports.errJasper"));
                        });

                        return;
                    }
                }
                Platform.runLater(() -> {
                    JasperPrint jasperPrint;
                    jasperPrint = (JasperPrint) response[1];

                    if(jasperPrint.getPages().size() <= 0){
                        loadScreenController.closeWindow();
                        alerts(rb.getString("reports.noRegistros"));
                        return;
                    }

                    JasperViewerFX jasperViewer = new JasperViewerFX();
                    jasperViewer.initModality(Modality.APPLICATION_MODAL);
                    jasperViewer.initOwner(owner);
                    jasperViewer.viewReport(rb.getString("reports.informeEjemplares"), jasperPrint);
                    loadScreenController.closeWindow();
                });


            } catch (SocketException e) {
                Platform.runLater(()->{
                    loadScreenController.closeWindow();
                    alerts(rb.getString("err.noConexion"));
                });
            } catch (IOException e) {
                Platform.runLater(()->{
                    loadScreenController.closeWindow();
                    alerts(rb.getString("err.inesperado"));
                });
            } catch (ClassNotFoundException e) {
            }
        });

        th.start();

        stage.show();
    }

    private void numberReportNameAction() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/reports/numbers_report.fxml"), rb);
        Parent root;

        try{
            root = fxmlLoader.load();

            NumberNameReport numberNameReport = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            numberNameReport.setOwner(scene);

            stage.setMinWidth(620);
            stage.setMinHeight(440);
            stage.setWidth(620);
            stage.setHeight(440);
            stage.setMaxHeight(440);
            stage.setMaxWidth(1200);
            stage.setTitle(rb.getString("reports.informeNumerosNombre"));
            stage.initStyle(StageStyle.UTILITY);
            stage.getIcons().add(Resources.APP_ICON);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.owner);
            stage.showAndWait();


        } catch (IOException e) {
            alerts(rb.getString("err.inesperado"));
        }
    }

    private void colReportAction(){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/load_screen.fxml"));
        Parent root;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            alerts(rb.getString("err.inesperado"));
            return;
        }

        LoadScreenController loadScreenController = fxmlLoader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.initOwner(this.owner);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);

        Thread th = new Thread(() -> {
            Object[] response;

            try{
                response = ReportsManagement.getCollectionReport();

                switch ((String)response[0]){
                    case "SQLE Error" -> {
                        Platform.runLater(()->{
                            loadScreenController.closeWindow();
                            alerts(rb.getString("reports.errSql"));
                        });
                        return;
                    }
                    case "JRE" -> {
                        Platform.runLater(()->{
                            loadScreenController.closeWindow();
                            alerts(rb.getString("reports.errJasper"));
                        });

                        return;
                    }
                }
                Platform.runLater(() -> {
                    JasperPrint jasperPrint;
                    jasperPrint = (JasperPrint) response[1];

                    if(jasperPrint.getPages().size() <= 0){
                        loadScreenController.closeWindow();
                        alerts(rb.getString("reports.noRegistros"));
                        return;
                    }

                    JasperViewerFX jasperViewer = new JasperViewerFX();
                    jasperViewer.initModality(Modality.APPLICATION_MODAL);
                    jasperViewer.initOwner(owner);
                    jasperViewer.viewReport(rb.getString("reports.informeColecciones"), jasperPrint);
                    loadScreenController.closeWindow();
                });


            } catch (SocketException e) {
                Platform.runLater(()->{
                    loadScreenController.closeWindow();
                    alerts(rb.getString("err.noConexion"));
                });
            } catch (IOException e) {
                Platform.runLater(()->{
                    loadScreenController.closeWindow();
                    alerts(rb.getString("err.inesperado"));
                });
            } catch (ClassNotFoundException e) {
            }
        });

        th.start();

        stage.show();

    }

    private void numbersReportAction(){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/load_screen.fxml"));
        Parent root;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            alerts(rb.getString("err.inesperado"));
            return;
        }

        LoadScreenController loadScreenController = fxmlLoader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.initOwner(this.owner);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);

        Thread th = new Thread(() -> {
            Object[] response;

            try{
                response = ReportsManagement.getNumbersReport();

                switch ((String)response[0]){
                    case "SQLE Error" -> {
                        Platform.runLater(()->{
                            loadScreenController.closeWindow();
                            alerts(rb.getString("reports.errSql"));
                        });
                        return;
                    }
                    case "JRE" -> {
                        Platform.runLater(()->{
                            loadScreenController.closeWindow();
                            alerts(rb.getString("reports.errJasper"));
                        });

                        return;
                    }
                }
                Platform.runLater(() -> {
                    JasperPrint jasperPrint;
                    jasperPrint = (JasperPrint) response[1];

                    if(jasperPrint.getPages().size() <= 0){
                        loadScreenController.closeWindow();
                        alerts(rb.getString("reports.noRegistros"));
                        return;
                    }

                    JasperViewerFX jasperViewer = new JasperViewerFX();
                    jasperViewer.initModality(Modality.APPLICATION_MODAL);
                    jasperViewer.initOwner(owner);
                    jasperViewer.viewReport(rb.getString("reports.informeNumeros"), jasperPrint);
                    loadScreenController.closeWindow();
                });


            } catch (SocketException e) {
                Platform.runLater(()->{
                    loadScreenController.closeWindow();
                    alerts(rb.getString("err.noConexion"));
                });
            } catch (IOException e) {
                Platform.runLater(()->{
                    loadScreenController.closeWindow();
                    alerts(rb.getString("err.inesperado"));
                });
            } catch (ClassNotFoundException e) {
            }
        });

        th.start();

        stage.show();

    }

    private void colNameReportAction(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/reports/col_name_report.fxml"), rb);
        Parent root;

        try{
            root = fxmlLoader.load();

            ColNameReport colNameReport = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            colNameReport.setOwner(scene);

            stage.setScene(scene);
            stage.getIcons().add(Resources.APP_ICON);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.owner);
            stage.setMinWidth(620);
            stage.setMinHeight(170);
            stage.setWidth(620);
            stage.setHeight(170);
            stage.setMaxHeight(170);
            stage.setMaxWidth(1200);
            stage.setTitle(rb.getString("reports.informeColeccionesNombre"));
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();


        } catch (IOException e) {
            alerts(rb.getString("err.inesperado"));
        }
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
        lblNumberReportName.setText(resources.getString("reports.informeNumerosNombre"));
        lblCopiesReport.setText(resources.getString("reports.informeEjemplares"));
        lblCopiesReportFiltered.setText(resources.getString("reports.informeEjemplaresFiltrado"));
        this.rb = resources;
    }
}
