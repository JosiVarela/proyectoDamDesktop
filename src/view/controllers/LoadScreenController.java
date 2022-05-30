package view.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadScreenController implements Initializable {
    @FXML
    private ProgressIndicator progress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void closeWindow(){
        ((Stage)progress.getScene().getWindow()).close();
    }
}
