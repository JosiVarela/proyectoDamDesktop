package view.controllers.comicNumbers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class NumbersInfoController implements Initializable {
    private ResourceBundle rb;

    @FXML
    private Label lblCover;

    @FXML
    private TextArea txtArgument;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblIsbn;

    @FXML
    private TableView<?> comicsTable;

    @FXML
    private Label lblCollection;

    @FXML
    private ImageView numberImage;

    public void innitData(){
        //TODO innit data

        //numberImage.setImage(new Image("/data/images/help.png"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
    }

}
