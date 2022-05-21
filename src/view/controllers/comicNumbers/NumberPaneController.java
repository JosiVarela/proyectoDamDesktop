package view.controllers.comicNumbers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.entities.Collection;
import model.entities.ComicNumber;
import services.Resources;
import view.controllers.collections.CollectionPresentationController;

import java.net.URL;
import java.util.ResourceBundle;

public class NumberPaneController implements Initializable {
    ResourceBundle rb;
    Stage owner;

    ComicNumber comicNumber;
    NumberPresentationController presentationController;

    public void innitData(ComicNumber comicNumber, NumberPresentationController presentationController) {
        this.comicNumber = comicNumber;
        this.presentationController = presentationController;

        lblName.setText(this.comicNumber.getName());
        lblName.setTooltip(new Tooltip(this.comicNumber.getName()));
    }

    @FXML
    private Label lblName;

    @FXML
    private BorderPane comicPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
        this.owner = Resources.getMainWindow();
        comicPane.setOnMouseClicked(event -> numberPaneClick());
    }

    private void numberPaneClick(){

    }
}
