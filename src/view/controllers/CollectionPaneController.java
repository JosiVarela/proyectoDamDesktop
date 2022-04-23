package view.controllers;

import javafx.event.ActionEvent;
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
import model.entities.Collection;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class CollectionPaneController implements Initializable {

    private int id;
    private String name;

    @FXML
    private Label lblName;

    @FXML
    private BorderPane comicPane;


    public void innitData(Collection collection) {
        this.id = collection.getId();
        this.name = collection.getName();




        lblName.setText(this.name);
        lblName.setTooltip(new Tooltip(this.name));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comicPane.setOnMouseClicked(event -> mouseClickEvent());
    }

    private void mouseClickEvent(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../forms/collection_info.fxml"));

        try{
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(this.name);


    }
}
