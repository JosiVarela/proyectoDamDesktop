package view.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainFormController implements Initializable {
    private JButton swingButton;

    private ResourceBundle rb;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnCollection;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.rb = resources;

        swingButton = new JButton();
    }

    @FXML
    void btnHelpAction(ActionEvent event) {
        swingButton.doClick();
    }

    @FXML
    void btnCollectionAction(ActionEvent event) {
        Node node;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../forms/collection_presentation.fxml"), rb);

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
