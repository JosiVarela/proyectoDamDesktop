package view.controllers.collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Collection;
import model.entities.ComicNumber;
import services.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CollectionInfoController implements Initializable {
    private ObservableList<ComicNumber> numberList;
    private Collection collection;
    private ResourceBundle rb;
    private boolean neededUpdate;
    private Stage owner;

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
    private TableColumn<ComicNumber, String> tbColName;

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
        if(!this.neededUpdate){
            populateNumberList();
        }
    }

    public boolean isNeededUpdate(){
        return this.neededUpdate;
    }

    public Collection getCollection(){
        return this.collection;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
        this.owner = Resources.getMainWindow();

        numberList = FXCollections.observableArrayList();

        this.tbColIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        this.tbColNumber.setCellValueFactory(new PropertyValueFactory<>("comicNumber"));
        this.tbColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.tbColCover.setCellValueFactory(new PropertyValueFactory<>("cover"));
        this.tbColCopies.setCellValueFactory(new PropertyValueFactory<>("copies"));

        comicsTable.setPlaceholder(new Label(""));
        neededUpdate = false;



        /*numberList.add(new ComicNumber("XXXXXXXXXX", 1, "Blanda", 5));
        numberList.add(new ComicNumber("XXXXXXXXXX", 2, "Blanda", 5));
        numberList.add(new ComicNumber("XXXXXXXXXX", 3, "Blanda", 5));
        numberList.add(new ComicNumber("XXXXXXXXXX", 4, "Blanda", 5));
        numberList.add(new ComicNumber("XXXXXXXXXX", 5, "Blanda", 5));*/




    }

    @FXML
    void btnModifyAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../forms/collections/collection_create_mod.fxml"), rb);

        Parent root;
        try{
            root = fxmlLoader.load();

            CollectionCreateMod collectionCreateMod = fxmlLoader.getController();

            collectionCreateMod.modifyOption(this.collection);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initOwner(this.owner);
            stage.setTitle(rb.getString("collectionCreateMod.modColeccion"));
            stage.setWidth(620);
            stage.setHeight(450);
            stage.setMinHeight(450);
            stage.setMinWidth(620);
            stage.getIcons().add(Resources.APP_ICON);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            if(collectionCreateMod.isNeededUpdate()){
                this.neededUpdate = true;
                setCollection(collectionCreateMod.getCollection());
            }

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.cargarPantalla"));
            alert.showAndWait();
        }
    }

    private void populateNumberList(){
        numberList.addAll(collection.getNumberList());
        comicsTable.setItems(numberList);
    }
}
