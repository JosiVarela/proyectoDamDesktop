package view.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.CollectionManagement;
import model.entities.Collection;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class MainFormController implements Initializable {
    private JButton swingButton;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnCollection;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        swingButton = new JButton();

        Object[] serverResponse = CollectionManagement.getCollections();
        List<Collection> collectionList = (List<Collection>) serverResponse[1];


        if(serverResponse[0] == "OK"){
            for(Collection col : collectionList){
                System.out.println(col.getName());
            }
        }else if(serverResponse[0] == "SQLE Error"){
            System.out.println("Error al obtener las colecciones");
        }

    }

    @FXML
    void btnHelpAction(ActionEvent event) {
        swingButton.doClick();
    }

    @FXML
    void btnCollectionAction(ActionEvent event) {
        Node node;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../forms/collection_presentation.fxml"));

        try {
            node = fxmlLoader.load();

            AnchorPane.setTopAnchor(node, 0d);
            AnchorPane.setBottomAnchor(node, 0d);
            AnchorPane.setLeftAnchor(node, 0d);
            AnchorPane.setRightAnchor(node, 0d);

            mainPane.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
