package view.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CollectionPaneController implements Initializable {

    private int id;
    private String name;

    @FXML
    private Label lblName;

    @FXML
    private BorderPane comicPane;


    public void innitData(int id, String name) {
        this.id = id;
        this.name = name;


        lblName.setText(this.name);
        lblName.setTooltip(new Tooltip(this.name));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comicPane.setOnMouseClicked(event -> mouseClickEvent());
    }

    private void mouseClickEvent(){
        System.out.println(this.name);
    }
}
