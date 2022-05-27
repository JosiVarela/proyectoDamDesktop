package view.controllers.reports;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsPresentation implements Initializable {
    private ResourceBundle rb;

    @FXML
    private Label lblPanel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;


    }
}
