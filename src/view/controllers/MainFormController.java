package view.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javax.help.HelpBroker;
import javax.help.HelpSetException;
import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

import javax.help.HelpSet;

public class MainFormController implements Initializable {
    private JButton swingButton;

    @FXML
    private Button btnHelp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        swingButton = new JButton();



    }

    @FXML
    void btnHelpAction(ActionEvent event) {
        swingButton.doClick();
    }


}
