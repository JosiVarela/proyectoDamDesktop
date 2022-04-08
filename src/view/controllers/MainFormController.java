package view.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.CollectionManagement;
import model.entities.Collection;


import javax.swing.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class MainFormController implements Initializable {
    private JButton swingButton;

    @FXML
    private Button btnHelp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        swingButton = new JButton();

        Object[] serverResponse = CollectionManagement.getCollections();
        List<Collection> collectionList = (List<Collection>) serverResponse[1];


        if(serverResponse[0] == "ok"){
            for(Collection col : collectionList){
                System.out.println(col.getName());
            }
        }

    }

    @FXML
    void btnHelpAction(ActionEvent event) {
        swingButton.doClick();
    }


}
