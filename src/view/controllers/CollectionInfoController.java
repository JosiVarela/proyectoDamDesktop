package view.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TableView<ComicNumber> comicsTable;

    @FXML
    private TableColumn<ComicNumber, String> tbColIsbn;

    @FXML
    private TableColumn<ComicNumber, Integer> tbColNumber;

    @FXML
    private TableColumn<ComicNumber, Integer> tbColCopies;

    @FXML
    private TableColumn<ComicNumber, Date> tbColPurDate;

    @FXML
    private TableColumn<ComicNumber, String> tbColCover;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comicNumbersList = FXCollections.observableArrayList();

        this.tbColIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        this.tbColNumber.setCellValueFactory(new PropertyValueFactory<>("comicNumber"));

        this.tbColPurDate.setCellFactory(param -> {
            TableCell<ComicNumber, Date> cellDate = new TableCell<>(){
                private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);

                    if(empty){
                        setText(null);
                    }else{
                        this.setText(format.format(item));
                    }
                }
            };
            return cellDate;
        });

        this.tbColPurDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.tbColCover.setCellValueFactory(new PropertyValueFactory<>("cover"));
        this.tbColCopies.setCellValueFactory(new PropertyValueFactory<>("copies"));

        //for(int x = 0; x < 30; x++){
            comicNumbersList.add(new ComicNumber("XXXXXXXXXX", 1, Date.from(Instant.now()), "Blanda", 5));
            comicNumbersList.add(new ComicNumber("XXXXXXXXXX", 2, Date.from(Instant.now()), "Blanda", 5));
            comicNumbersList.add(new ComicNumber("XXXXXXXXXX", 3, Date.from(Instant.now()), "Blanda", 5));
            comicNumbersList.add(new ComicNumber("XXXXXXXXXX", 4, Date.from(Instant.now()), "Blanda", 5));
            comicNumbersList.add(new ComicNumber("XXXXXXXXXX", 5, Date.from(Instant.now()), "Blanda", 5));
        //}


        comicsTable.setItems(comicNumbersList);
    }
}
