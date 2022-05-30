package view.controllers.reports;

import com.github.gbfragoso.JasperViewerFX;
import controller.ReportsManagement;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperPrint;
import view.controllers.LoadScreenController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ColNameReport implements Initializable {
    private ResourceBundle rb;
    private Stage owner;

    @FXML
    private Button btnAccept;

    @FXML
    private TextField txtColName;

    @FXML
    private Button btnCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
    }

    @FXML
    void btnAcceptAction(ActionEvent event) {
        loadReport();
    }

    @FXML
    void txtColNameAction(ActionEvent event) {
        loadReport();
    }

    @FXML
    void btnCancelAction(ActionEvent event) {
        ((Stage)btnCancel.getScene().getWindow()).close();
    }

    private void loadReport(){
        String name = txtColName.getText().trim();

        if(name.isEmpty()){
            System.out.println("debe introducir un nombre de colección");
            return;
        }

        ((Stage)btnAccept.getScene().getWindow()).close();

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

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);

        Thread th = new Thread(() -> {
            Object[] response;

            try{
                response = ReportsManagement.getCollectionReportByName(name);

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
                    JasperViewerFX jasperViewer = new JasperViewerFX();
                    jasperViewer.initModality(Modality.APPLICATION_MODAL);
                    jasperViewer.initOwner(owner);
                    jasperViewer.viewReport(rb.getString("reports.informeColecciones"), (JasperPrint) response[1]);
                    loadScreenController.closeWindow();
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

    private void alerts(String alertMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(this.owner);
        alert.setHeaderText(null);
        alert.setTitle(rb.getString("error"));
        alert.setContentText(alertMsg);
        alert.showAndWait();
    }
}
