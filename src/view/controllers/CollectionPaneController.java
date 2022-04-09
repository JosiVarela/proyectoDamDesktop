package view.controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CollectionPaneController implements Initializable {

    private String name;
    private String count;

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
