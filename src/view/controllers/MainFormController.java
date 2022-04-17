package view.controllers;

import controller.Translatable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainFormController implements Initializable {
    private JButton swingButton;

    private FXMLLoader test;

    private ResourceBundle rb;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnCollection;

    @FXML
    private Button btnConfigMenu;

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

        test = fxmlLoader;

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

    @FXML
    void btnConfigMenuAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/app_config_window.fxml"));
        test = loader;

        try {
            Parent root = loader.load();

            Translatable tr = test.getController();

            tr.translate();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setMaxWidth(700);
            stage.setMaxHeight(900);
            stage.setTitle("Configuración");
            stage.getIcons().add(new Image("/data/images/app_icon.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
