package view.controllers;

import controller.Translatable;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AppConfigWindow implements Initializable, Translatable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void loadConfigurations(){

    }

    @Override
    public void translate() {
        System.out.println("hola mundo");
    }
}
