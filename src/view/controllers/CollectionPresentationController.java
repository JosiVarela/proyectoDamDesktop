package view.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import model.CollectionManagement;
import model.entities.Collection;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CollectionPresentationController implements Initializable {
    private ResourceBundle rb;

    @FXML
    private TilePane cardsPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;

        Object[] serverResponse = CollectionManagement.getCollections();
        List<Collection> collectionList = (List<Collection>) serverResponse[1];


        if(serverResponse[0] == "SQLE Error"){
            System.out.println("Error al obtener las colecciones");
            return;
        }

        FXMLLoader fxmlLoader;
        Node node;

        for(Collection col : collectionList){
           fxmlLoader = new FXMLLoader(getClass().getResource("../forms/collection_pane_form.fxml"));
            try {

                node = fxmlLoader.load();

                CollectionPaneController paneController = fxmlLoader.getController();

                paneController.innitData(1, col.getName());

                TilePane.setMargin(node, new Insets(5));

                cardsPane.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }


}
