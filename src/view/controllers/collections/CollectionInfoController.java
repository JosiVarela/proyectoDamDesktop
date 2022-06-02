package view.controllers.collections;

import controller.CollectionManagement;
import controller.NumberManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Collection;
import model.entities.ComicNumber;
import services.Resources;
import view.controllers.comicNumbers.NumbersCreateMod;
import view.controllers.comicNumbers.NumbersInfoController;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CollectionInfoController implements Initializable {
    private ObservableList<ComicNumber> numberList;
    private Collection collection;
    private ResourceBundle rb;
    private boolean neededUpdate;
    private Scene owner;
    private CollectionPresentationController presentationController;

    //<editor-fold desc="FXML vars definition">
    @FXML
    private Button btnModifyCol;
    @FXML
    private Button btnAddNumber;
    @FXML
    private Button btnDelCol;
    @FXML
    private Button btnSearch;
    @FXML
    private Label lblArgument;

    @FXML
    private Label lblComicTitle;

    @FXML
    private Label lblNumbers;

    @FXML
    private Label lblCreationDate;

    @FXML
    private TextField txtSearch;

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

    public void setScene(Scene scene){
        this.owner = scene;
    }

    public void loadCollection(int idCol){
        Object[] requestCol;

        try{

            requestCol = CollectionManagement.getCollectionInfoById(idCol);

            if(requestCol[0].equals("SQLE ERROR") || requestCol[1] == null){
                alerts(rb.getString("err.CargarInfoColeccion"));
                return;
            }

            this.collection = (Collection) requestCol[1];

            loadCollectionData();

        } catch (SocketException e){
            alerts(rb.getString("err.noConexion"));
        } catch (IOException e) {
            alerts(rb.getString("err.cargarPantalla"));
        } catch (ClassNotFoundException e) {
            alerts(rb.getString("err.inesperado"));
        }
    }

    private void loadCollectionData(){
        lblComicTitle.setText(collection.getTitle());
        lblCreationDate.setText(collection.getCreationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        lblNumbers.setText(String.valueOf(collection.getComicQuantity()));
        txtArgument.setText(collection.getArgument());
        if(!this.neededUpdate){
            populateNumberList();
        }
    }

    public boolean isLoaded(){
        return this.collection != null;
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
        //this.owner = Resources.getMainWindow();

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/collections/collection_create_mod.fxml"), rb);

        Parent root;

        Object[] requestCol;
        try{

            requestCol = CollectionManagement.existsCollectionWithId(this.collection.getId());

            if(requestCol[0].equals("SQLE ERROR")){
                alerts(rb.getString("err.CargarInfoColeccion"));
                return;
            }

            if(!(boolean) requestCol[1]){
                alerts(rb.getString("collectionPaneController.noExisteColeccion"));

                closeAndReload();
                return;
            }

            root = fxmlLoader.load();

            CollectionCreateMod collectionCreateMod = fxmlLoader.getController();

            collectionCreateMod.modifyOption(this.collection);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            collectionCreateMod.setOwner(scene);
            stage.setScene(scene);
            stage.initOwner(this.owner.getWindow());
            stage.setTitle(rb.getString("collectionCreateMod.modColeccion"));
            stage.setWidth(635);
            stage.setHeight(845);
            stage.setMinWidth(635);
            stage.setMinHeight(845);
            stage.getIcons().add(Resources.APP_ICON);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            if(collectionCreateMod.isNeededUpdate()){
                this.neededUpdate = true;
                //setCollection(collectionCreateMod.getCollection());
                loadCollection(this.getCollection().getId());
            }

        } catch (IOException e) {
            alerts(rb.getString("err.cargarPantalla"));
        }
    }

    @FXML
    void btnDelColAction(ActionEvent event) {
        String response;
        Object[] exitsCol;

        try {
            exitsCol = CollectionManagement.existsCollectionWithId(this.collection.getId());

            if(exitsCol[0].equals("SQLE Error")){
                alerts(rb.getString("errCollectionInfoController.delColeccion"));
                return;
            }

            if(!(boolean) exitsCol[1]){
                alerts(rb.getString("collectionPaneController.noExisteColeccion"));
                closeAndReload();

                return;
            }

        } catch (IOException e) {
            alerts(rb.getString("err.noConexion"));
            return;
        }

        ButtonType btnAccept = new ButtonType(rb.getString("aceptar"), ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancel = new ButtonType(rb.getString("cancelar"), ButtonBar.ButtonData.CANCEL_CLOSE);



        Alert question = new Alert(Alert.AlertType.CONFIRMATION);
        question.initOwner(this.owner.getWindow());
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
                        alerts(rb.getString("errCollectionInfoController.eliminarComics"));
                    }
                    case "SQLE Error" -> {
                        alerts(rb.getString("errCollectionInfoController.delColeccion"));
                    }
                }

            } catch (IOException e) {
                alerts(rb.getString("err.noConexion"));
            }
        }
    }
    @FXML
    void btnAddNumberAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/comicNumbers/numbers_create_mod.fxml"),
                rb);
        Parent root;

        try{
            root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            NumbersCreateMod numbersCreateMod = fxmlLoader.getController();
            numbersCreateMod.innitData(0, collection.getId(), (Stage) this.owner.getWindow());


            stage.setScene(scene);
            stage.initOwner(this.owner.getWindow());

            numbersCreateMod.setOwner(scene);

            stage.setTitle(rb.getString("collectionInfo.altaNumero"));
            stage.setWidth(635);
            stage.setHeight(845);
            stage.setMinWidth(635);
            stage.setMinHeight(845);
            stage.getIcons().add(Resources.APP_ICON);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            if(numbersCreateMod.isNeedUpdate()){
                numberList.remove(0, numberList.size());
                loadCollection(this.collection.getId());
            }

        } catch (IOException e) {
            alerts(rb.getString("err.cargarPantalla"));
        }
    }

    @FXML
    void txtSearchAction(ActionEvent event) {
        searchNumber();
    }

    @FXML
    void btnSearchAction(ActionEvent event) {
        searchNumber();
    }

    private void closeAndReload(){
        presentationController.getCollections();
        ((Stage)btnModifyCol.getScene().getWindow()).close();
    }

    private void populateNumberList(){
        numberList.remove(0, numberList.size());
        numberList.addAll(collection.getNumberList());
        comicsTable.setItems(numberList);
    }

    private void populateNumberList(List<ComicNumber> searchList){
        numberList.remove(0, numberList.size());
        numberList.addAll(searchList);
        comicsTable.setItems(numberList);
    }

    private void searchNumber(){
        String search = txtSearch.getText().trim();
        Object[] response;

        if(search.isEmpty()){
            loadCollection(this.collection.getId());
            return;
        }

        try{
            response = NumberManagement.getNumbersByNameCol(search, this.collection.getId());

            if(!response[0].equals("OK")){
                alerts(rb.getString("collectionInfoController.errBuscarNumero"));
                return;
            }

            populateNumberList((List<ComicNumber>) response[1]);

        } catch (SocketException e) {
            alerts(rb.getString("err.noConexion"));
        } catch (IOException | ClassNotFoundException e) {
            alerts(rb.getString("err.inesperado"));
        }
    }

    /**
     * This method creates a listener for double click event on table row
     */
    private void createRowClickListener(){

        comicsTable.setRowFactory( tv -> {
            TableRow<ComicNumber> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Object[] response;

                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    ComicNumber comic = row.getItem();

                    try {
                        response = NumberManagement.existsNumber(comic.getIsbn());

                        if(!response[0].equals("OK")){
                            alerts(rb.getString("collectionInfoController.errorCargarNumero"));
                            return;
                        }

                        if(!(boolean) response[1]){
                            alerts(rb.getString("collectionInfoController.errorNoExisteNumero"));

                            numberList.remove(0, numberList.size());
                            loadCollection(this.collection.getId());
                            return;
                        }


                    } catch (SocketException e) {
                        alerts(rb.getString("err.noConexion"));
                        return;
                    } catch (IOException e) {
                        alerts(rb.getString("err.inesperado"));
                        return;
                    }

                    loadNumberScreen(comic.getIsbn());
                }
            });
            return row ;
        });
    }


    /**
     * This method loads number screen
     * @param isbn The number isbn code
     */
    private void loadNumberScreen(String isbn) {
        //TODO SET SCENE
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/comicNumbers/numbers_info.fxml"), rb);
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

            infoController.setOwner(scene);

            stage.setScene(scene);
            stage.setWidth(635);
            stage.setHeight(845);
            stage.setMinWidth(635);
            stage.setMinHeight(845);
            stage.getIcons().add(Resources.APP_ICON);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.owner.getWindow());
            stage.setTitle(rb.getString("numberInfo.titulo"));
            stage.showAndWait();

            if(infoController.isNeedUpdate()){
                numberList.remove(0, numberList.size());
                loadCollection(this.collection.getId());
            }

        } catch (IOException e) {
            alerts(rb.getString("err.cargarPantalla"));
        }
    }

    private void alerts(String alertMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(this.owner.getWindow());
        alert.setHeaderText(null);
        alert.setTitle(rb.getString("error"));
        alert.setContentText(alertMsg);
        alert.showAndWait();
    }
}
