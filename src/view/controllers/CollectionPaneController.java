package view.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CollectionManagement;
import model.entities.Collection;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class CollectionPaneController implements Initializable {

    private ResourceBundle resourceBundle;
    private Collection collection;

    @FXML
    private Label lblName;

    @FXML
    private BorderPane comicPane;


    public void innitData(Collection collection) {
        this.collection = collection;




        lblName.setText(this.collection.getTitle());
        lblName.setTooltip(new Tooltip(this.collection.getTitle()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
        comicPane.setOnMouseClicked(event -> mouseClickEvent());
    }

    private void mouseClickEvent(){
        Object[] requestCol;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../forms/collection_info.fxml"), resourceBundle);

        try{
            Parent root = fxmlLoader.load();

            CollectionInfoController controller = fxmlLoader.getController();

            requestCol = CollectionManagement.getCollectionInfoById(this.collection.getId());

            if(requestCol[0].equals("SQLE ERROR") || requestCol[1] == null){
                //TODO SHOW MESSAGE ERROR
                return;
            }

            controller.setCollection((Collection) requestCol[1]);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(this.collection.getTitle());
        System.out.println(this.collection.getComicQuantity());

    }
}
