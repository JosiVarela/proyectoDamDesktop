package view.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import model.entities.Collection;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CollectionPresentationController implements Initializable {
    @FXML
    private TilePane cardsPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader fxmlLoader;
        Node node;

        for(int x = 0; x<5; x++){
           fxmlLoader = new FXMLLoader(getClass().getResource("../forms/collection_pane_form.fxml"));
            try {
                node = fxmlLoader.load();

                CollectionPaneController paneController = fxmlLoader.getController();

                TilePane.setMargin(node, new Insets(5));

                cardsPane.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }


}
