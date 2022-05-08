package view.controllers.collections;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import controller.CollectionManagement;
import model.entities.Collection;
import services.Resources;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;

import java.util.ResourceBundle;

public class CollectionPaneController implements Initializable {

    private ResourceBundle rb;
    private Collection collection;

    private Stage owner;

    //<editor-fold desc="FXML vars Definition">
    @FXML
    private Label lblName;

    @FXML
    private BorderPane comicPane;
    //</editor-fold>


    public void innitData(Collection collection) {
        this.collection = collection;


        lblName.setText(this.collection.getTitle());
        lblName.setTooltip(new Tooltip(this.collection.getTitle()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
        this.owner = Resources.getMainWindow();
        comicPane.setOnMouseClicked(event -> mouseClickEvent());
    }

    private void mouseClickEvent(){
        Object[] requestCol;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../forms/collections/collection_info.fxml"), rb);

        try{
            Parent root = fxmlLoader.load();

            CollectionInfoController controller = fxmlLoader.getController();

            requestCol = CollectionManagement.existsCollectionWithId(this.collection.getId());

            if(requestCol[0].equals("SQLE ERROR")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(this.owner);
                alert.setHeaderText(null);
                alert.setTitle(rb.getString("error"));
                alert.setContentText(rb.getString("err.CargarInfoColeccion"));
                alert.showAndWait();
                return;
            }

            if(!(boolean) requestCol[1]){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(this.owner);
                alert.setHeaderText(null);
                alert.setTitle(rb.getString("error"));
                alert.setContentText(rb.getString("collectionPaneController.noExisteColeccion"));
                alert.showAndWait();

                //TODO RECARGAR PÁGINA PRINCIPAL

                return;
            }


            requestCol = CollectionManagement.getCollectionInfoById(this.collection.getId());

            if(requestCol[0].equals("SQLE ERROR") || requestCol[1] == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(this.owner);
                alert.setHeaderText(null);
                alert.setTitle(rb.getString("error"));
                alert.setContentText(rb.getString("err.CargarInfoColeccion"));
                alert.showAndWait();
                return;
            }

            controller.setCollection((Collection) requestCol[1]);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.owner);
            stage.setScene(scene);
            stage.setHeight(700);
            stage.setWidth(600);
            stage.setMinHeight(700);
            stage.setMinWidth(600);
            stage.setTitle(rb.getString("collectionInfo.infoColeccion"));
            stage.getIcons().add(Resources.APP_ICON);
            stage.showAndWait();

            if(controller.isNeededUpdate()){
                innitData(controller.getCollection());
            }

        } catch (SocketException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.noConexion"));
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.cargarPantalla"));
            alert.showAndWait();
        } catch (ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.inesperado"));
            alert.showAndWait();
        }
    }
}
