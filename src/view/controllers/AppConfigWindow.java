package view.controllers;

import controller.AppConfigurations;
import controller.Translatable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class AppConfigWindow implements Initializable {
    @FXML
    private Button btnClose;

    @FXML
    private Label txtLangSelec;

    @FXML
    private ToggleGroup rdGroupLanguage;

    @FXML
    private RadioButton rdLangGal;

    @FXML
    private RadioButton rdLangEsp;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(AppConfigurations.getTranslations().equals("es")){
            rdLangEsp.setSelected(true);
        }else{
            rdLangGal.setSelected(true);
        }

    }

    private void loadConfigurations(){

    }

    @FXML
    void rdLangGalAction(ActionEvent event) {
        txtLangSelec.setText("Galego");
        try {
            AppConfigurations.storeTranslations("gl");
            Locale.setDefault(new Locale("gl", "ES"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void rdLangEspAction(ActionEvent event) {
        txtLangSelec.setText("Castellano");
        try {
            AppConfigurations.storeTranslations("es");
            Locale.setDefault(new Locale("es", "ES"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
