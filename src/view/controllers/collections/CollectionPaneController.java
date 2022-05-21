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

    private CollectionPresentationController presentationController;

    //<editor-fold desc="FXML vars Definition">
    @FXML
    private Label lblName;

    @FXML
    private BorderPane comicPane;
    //</editor-fold>


    public void innitData(Collection collection, CollectionPresentationController presentationController) {
        this.collection = collection;
        this.presentationController = presentationController;

        lblName.setText(this.collection.getTitle());
        lblName.setTooltip(new Tooltip(this.collection.getTitle()));
    }

    public void innitData(Collection collection) {
        this.collection = collection;

        lblName.setText(this.collection.getTitle());
        lblName.setTooltip(new Tooltip(this.collection.getTitle()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
        this.owner = Resources.getMainWindow();
        comicPane.setOnMouseClicked(event -> collectionPaneClick());
    }

    private void collectionPaneClick(){
        Object[] requestCol;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../forms/collections/collection_info.fxml"), rb);

        try{
            Parent root = fxmlLoader.load();

            CollectionInfoController controller = fxmlLoader.getController();

            requestCol = CollectionManagement.existsCollectionWithId(this.collection.getId());

            if(requestCol[0].equals("SQLE ERROR")){
                alerts(rb.getString("err.CargarInfoColeccion"));
                return;
            }

            if(!(boolean) requestCol[1]){
                alerts(rb.getString("collectionPaneController.noExisteColeccion"));
                presentationController.getCollections();
                return;
            }




            controller.innitData(this.presentationController);
            controller.loadCollection(this.collection.getId());

            if(!controller.isLoaded()){
                return;
            }

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
            alerts(rb.getString("err.noConexion"));
        } catch (IOException e) {
            alerts(rb.getString("err.cargarPantalla"));
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
}
