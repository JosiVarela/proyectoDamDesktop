package view.controllers.collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Collection;
import model.entities.ComicNumber;

import javax.swing.text.DateFormatter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class CollectionInfoController implements Initializable {
    private ObservableList<ComicNumber> comicNumbersList;
    private Collection collection;

    //<editor-fold desc="FXML vars definition">
    @FXML
    private Button btnModifyCol;

    @FXML
    private Button btnDelCol;

    @FXML
    private Label lblArgument;

    @FXML
    private Label lblComicTitle;

    @FXML
    private Label lblNumbers;

    @FXML
    private Label lblFirstPublish;

    @FXML
    private TableView<ComicNumber> comicsTable;

    @FXML
    private TableColumn<ComicNumber, String> tbColIsbn;

    @FXML
    private TableColumn<ComicNumber, Integer> tbColNumber;

    @FXML
    private TableColumn<ComicNumber, Integer> tbColCopies;

    @FXML
    private TableColumn<ComicNumber, String> tbColCover;

    @FXML
    private TextArea txtArgument;
    //</editor-fold>

    public void setCollection(Collection collection){
        this.collection = collection;

        lblComicTitle.setText(collection.getTitle());
        lblFirstPublish.setText(lblFirstPublish.getText() + " " + collection.getPublishDate());
        lblNumbers.setText(collection.getComicQuantity() + " " + lblNumbers.getText());
        txtArgument.setText(collection.getArgument());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comicNumbersList = FXCollections.observableArrayList();

        this.tbColIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        this.tbColNumber.setCellValueFactory(new PropertyValueFactory<>("comicNumber"));
        this.tbColCover.setCellValueFactory(new PropertyValueFactory<>("cover"));
        this.tbColCopies.setCellValueFactory(new PropertyValueFactory<>("copies"));

        //for(int x = 0; x < 30; x++){
            comicNumbersList.add(new ComicNumber("XXXXXXXXXX", 1, "Blanda", 5));
            comicNumbersList.add(new ComicNumber("XXXXXXXXXX", 2, "Blanda", 5));
            comicNumbersList.add(new ComicNumber("XXXXXXXXXX", 3, "Blanda", 5));
            comicNumbersList.add(new ComicNumber("XXXXXXXXXX", 4, "Blanda", 5));
            comicNumbersList.add(new ComicNumber("XXXXXXXXXX", 5, "Blanda", 5));
        //}


        comicsTable.setItems(comicNumbersList);
    }
}