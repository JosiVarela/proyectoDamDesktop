package view.controllers.collections;

import controller.CollectionManagement;
import controller.NumberManagement;
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
import view.controllers.comicNumbers.NumbersInfoController;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CollectionInfoController implements Initializable {
    private ObservableList<ComicNumber> numberList;
    private Collection collection;
    private ResourceBundle rb;
    private boolean neededUpdate;
    private Stage owner;
    private CollectionPresentationController presentationController;

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

    public void innitData(CollectionPresentationController presentationController){
        this.presentationController = presentationController;
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

        this.tbColCover.setCellFactory(param -> {
            TableCell<ComicNumber, String> cellCover = new TableCell<>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    item = "collectionInfoCover." + item;

                    if(empty || !rb.containsKey(item)){
                        setText(null);
                    }else{
                        this.setText(rb.getString(item));
                    }
                }
            };
            return cellCover;
        });

        createRowClickListener();

        comicsTable.setPlaceholder(new Label(""));
        neededUpdate = false;

    }

    @FXML
    void btnModifyAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../forms/collections/collection_create_mod.fxml"), rb);

        Parent root;

        Object[] requestCol;
        try{

            requestCol = CollectionManagement.existsCollectionWithId(this.collection.getId());

            if(requestCol[0].equals("SQLE ERROR")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(this.owner);
                alert.setHeaderText(null);
                alert.setTitle(rb.getString("error"));
                alert.setContentText(rb.getString("err.CargarInfoColeccion"));
                alert.showAndWait();
                return;
            }

            if(!(boolean) requestCol[1]){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(this.owner);
                alert.setHeaderText(null);
                alert.setTitle(rb.getString("error"));
                alert.setContentText(rb.getString("collectionPaneController.noExisteColeccion"));
                alert.showAndWait();

                closeAndReload();

                return;
            }

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

    @FXML
    void btnDelColAction(ActionEvent event) {
        String response;
        Object[] exitsCol;

        try {
            exitsCol = CollectionManagement.existsCollectionWithId(this.collection.getId());

            if(exitsCol[0].equals("SQLE Error")){
                Alert sqlAlert = new Alert(Alert.AlertType.ERROR);
                sqlAlert.initOwner(this.owner);
                sqlAlert.setHeaderText(null);
                sqlAlert.setTitle(rb.getString("error"));
                sqlAlert.setContentText(rb.getString("errCollectionInfoController.delColeccion"));
                sqlAlert.showAndWait();
                return;
            }

            if(!(boolean) exitsCol[1]){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(this.owner);
                alert.setHeaderText(null);
                alert.setTitle(rb.getString("error"));
                alert.setContentText(rb.getString("collectionPaneController.noExisteColeccion"));
                alert.showAndWait();

                closeAndReload();

                return;
            }

        } catch (IOException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.initOwner(this.owner);
            errorAlert.setHeaderText(null);
            errorAlert.setTitle(rb.getString("error"));
            errorAlert.setContentText(rb.getString("err.noConexion"));
            errorAlert.showAndWait();
        }

        ButtonType btnAccept = new ButtonType(rb.getString("aceptar"), ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancel = new ButtonType(rb.getString("cancelar"), ButtonBar.ButtonData.CANCEL_CLOSE);



        Alert question = new Alert(Alert.AlertType.CONFIRMATION);
        question.initOwner(this.owner);
        question.setHeaderText(null);
        question.setTitle(rb.getString("eliminar"));
        question.setContentText(rb.getString("collectionInfoController.seguroEliminar"));
        question.getButtonTypes().clear();
        question.getButtonTypes().addAll(btnAccept, btnCancel);
        ((Button)question.getDialogPane().lookupButton(btnAccept)).setDefaultButton(false);
        ((Button)question.getDialogPane().lookupButton(btnCancel)).setDefaultButton(true);

        Optional<ButtonType> result = question.showAndWait();

        if(result.isPresent() && result.get() == btnAccept){
            try {

                response = CollectionManagement.deleteCollection(this.collection.getId());

                switch (response) {
                    case "OK" -> closeAndReload();
                    case "SQLE Foreing" -> {
                        Alert sqlAlert = new Alert(Alert.AlertType.ERROR);
                        sqlAlert.initOwner(this.owner);
                        sqlAlert.setHeaderText(null);
                        sqlAlert.setTitle(rb.getString("error"));
                        sqlAlert.setContentText(rb.getString("errCollectionInfoController.eliminarComics"));
                        sqlAlert.showAndWait();
                    }
                    case "SQLE Error" -> {
                        Alert sqlAlert = new Alert(Alert.AlertType.ERROR);
                        sqlAlert.initOwner(this.owner);
                        sqlAlert.setHeaderText(null);
                        sqlAlert.setTitle(rb.getString("error"));
                        sqlAlert.setContentText(rb.getString("errCollectionInfoController.delColeccion"));
                        sqlAlert.showAndWait();
                    }
                }

            } catch (IOException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.initOwner(this.owner);
                errorAlert.setHeaderText(null);
                errorAlert.setTitle(rb.getString("error"));
                errorAlert.setContentText(rb.getString("err.noConexion"));
                errorAlert.showAndWait();
            }
        }
    }

    private void closeAndReload(){
        presentationController.getCollections();
        ((Stage)btnModifyCol.getScene().getWindow()).close();
    }

    private void populateNumberList(){
        numberList.removeAll();
        numberList.addAll(collection.getNumberList());
        comicsTable.setItems(numberList);
    }

    /**
     * This method creates a listener for double click event on table row
     */
    private void createRowClickListener(){

        System.out.println("Creo Listener");
        comicsTable.setRowFactory( tv -> {
            TableRow<ComicNumber> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Object response[];

                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    ComicNumber comic = row.getItem();

                    try {
                        response = NumberManagement.existsNumber(comic.getIsbn());

                        if(!response[0].equals("OK")){
                            Alert sqlAlert = new Alert(Alert.AlertType.ERROR);
                            sqlAlert.initOwner(this.owner);
                            sqlAlert.setHeaderText(null);
                            sqlAlert.setTitle(rb.getString("error"));
                            sqlAlert.setContentText(rb.getString("collectionInfoController.errorCargarNumero"));
                            sqlAlert.showAndWait();
                            return;
                        }

                        if(!(boolean) response[1]){
                            Alert sqlAlert = new Alert(Alert.AlertType.ERROR);
                            sqlAlert.initOwner(this.owner);
                            sqlAlert.setHeaderText(null);
                            sqlAlert.setTitle(rb.getString("error"));
                            sqlAlert.setContentText(rb.getString("collectionInfoController.errorNoExisteNumero"));
                            sqlAlert.showAndWait();
                            //TODO Reload table
                            populateNumberList();
                            return;
                        }


                    } catch (SocketException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.initOwner(this.owner);
                        errorAlert.setHeaderText(null);
                        errorAlert.setTitle(rb.getString("error"));
                        errorAlert.setContentText(rb.getString("err.noConexion"));
                        errorAlert.showAndWait();
                        return;
                    } catch (IOException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.initOwner(this.owner);
                        errorAlert.setHeaderText(null);
                        errorAlert.setTitle(rb.getString("error"));
                        errorAlert.setContentText(rb.getString("err.inesperado"));
                        errorAlert.showAndWait();
                        return;
                    }

                    loadNumberScreen(comic.getIsbn());

                    System.out.println(comic.getName());
                }
            });
            return row ;
        });
    }



    private void loadNumberScreen(String isbn) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../forms/comicNumbers/numbers_info.fxml"), rb);
        Parent root;

        try{
            root = fxmlLoader.load();

            NumbersInfoController infoController = fxmlLoader.getController();

            infoController.innitData(isbn);

            if(!infoController.isLoaded()){
                return;
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.owner);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void reloadNumberTable(){

    }
}
