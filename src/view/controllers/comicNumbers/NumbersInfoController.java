package view.controllers.comicNumbers;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.ComicCopy;
import model.entities.ComicNumber;
import services.Resources;
import view.controllers.comicCopies.CopiesCreateMod;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class NumbersInfoController implements Initializable {
    private ResourceBundle rb;

    private String isbn;

    private Stage owner;

    private String collectionName;

    private ComicNumber comicNumber;

    private boolean needUpdate;

    private ObservableList<ComicCopy> comicCopies;
    @FXML
    private Button btnAddCopy;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<ComicNumber, LocalDate> tbColPurchase;

    @FXML
    private TextArea txtArgument;

    @FXML
    private Button btnModify;

    @FXML
    private TableView<ComicCopy> copiesTable;

    @FXML
    private Label lblNumber;

    @FXML
    private Label lblCover;

    @FXML
    private TableColumn<ComicNumber, Integer> tbColState;

    @FXML
    private TableColumn<ComicCopy, String> tbColObservations;

    @FXML
    private ImageView numberImage;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblIsbn;

    @FXML
    private Button btnDel;

    @FXML
    private Label lblCollection;

    public void innitData(String isbn){
        this.isbn = isbn;
        loadComicNumber();
    }

    public boolean isLoaded(){
        return comicNumber != null;
    }

    public boolean isNeedUpdate(){
        return needUpdate;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
        this.owner = Resources.getMainWindow();

        comicCopies = FXCollections.observableArrayList();

        this.tbColPurchase.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        this.tbColState.setCellValueFactory(new PropertyValueFactory<>("state"));
        this.tbColObservations.setCellValueFactory(new PropertyValueFactory<>("observations"));

        this.tbColPurchase.setCellFactory(param -> {
            TableCell<ComicNumber, LocalDate> cellDate = new TableCell<>(){
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if(empty){
                        setText(null);
                    }else{
                        this.setText(item.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    }
                }
            };
            return cellDate;
        });

        this.tbColState.setCellFactory(param -> {
            TableCell<ComicNumber, Integer> cellCover = new TableCell<>(){
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);


                    if(!empty){
                        switch (item){
                            case 0 -> this.setText(rb.getString("copiesCreateMod.cmbNuevo"));
                            case 1 -> this.setText(rb.getString("copiesCreateMod.cmbComoNuevo"));
                            case 2 -> this.setText(rb.getString("copiesCreateMod.aceptable"));
                            case 3 -> this.setText(rb.getString("copiesCreateMod.malo"));
                        }
                    }

                }
            };
            return cellCover;
        });

        //createRowClickListener();

        copiesTable.setItems(comicCopies);

        copiesTable.setPlaceholder(new Label(""));
    }

    @FXML
    void btnModifyAction(ActionEvent event) {
        String isbn = comicNumber.getIsbn();
        Object[] response;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../forms/comicNumbers/numbers_create_mod.fxml"), rb);
        Parent root;

        try {
            response = NumberManagement.existsNumber(isbn);

            if(!response[0].equals("OK")){
                alerts(rb.getString("collectionInfoController.errorCargarNumero"));
                return;
            }

            if(!(boolean) response[1]){
                alerts(rb.getString("collectionInfoController.errorNoExisteNumero"));

                this.needUpdate = true;
                ((Stage)btnModify.getScene().getWindow()).close();

                return;
            }


            root = fxmlLoader.load();

            NumbersCreateMod numbersCreateMod = fxmlLoader.getController();

            numbersCreateMod.innitData(1, isbn, this.owner);

            if(!numbersCreateMod.isLoaded()) return;

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initOwner(this.owner);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            if(numbersCreateMod.isNeedUpdate()){
                loadComicNumber();
                needUpdate = true;
            }

        } catch (SocketException e) {
            alerts(rb.getString("err.noConexion"));
        } catch (IOException e) {
            alerts(rb.getString("err.inesperado"));
        }
    }

    @FXML
    void btnDelAction(ActionEvent event) {
        String isbn = comicNumber.getIsbn();
        Object[] response;
        String deleteResponse;

        ButtonType btnAccept = new ButtonType(rb.getString("aceptar"), ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancel = new ButtonType(rb.getString("cancelar"), ButtonBar.ButtonData.CANCEL_CLOSE);



        Alert question = new Alert(Alert.AlertType.CONFIRMATION);
        question.initOwner(this.owner);
        question.setHeaderText(null);
        question.setTitle(rb.getString("eliminar"));
        question.setContentText(rb.getString("numberInfo.seguroEliminar"));
        question.getButtonTypes().clear();
        question.getButtonTypes().addAll(btnAccept, btnCancel);
        ((Button)question.getDialogPane().lookupButton(btnAccept)).setDefaultButton(false);
        ((Button)question.getDialogPane().lookupButton(btnCancel)).setDefaultButton(true);

        Optional<ButtonType> result = question.showAndWait();

        if(result.isPresent() && result.get() == btnAccept){
            try {
                response = NumberManagement.existsNumber(isbn);

                if(!response[0].equals("OK")){
                    alerts(rb.getString("collectionInfoController.errorCargarNumero"));
                    return;
                }

                if(!(boolean) response[1]){
                    alerts(rb.getString("collectionInfoController.errorNoExisteNumero"));

                    this.needUpdate = true;
                    ((Stage)btnModify.getScene().getWindow()).close();

                    return;
                }

                deleteResponse = NumberManagement.deleteComicNumber(isbn);

                if(deleteResponse.equals("SQLE Error")){
                    alerts(rb.getString("numberInfo.errorEliminar"));
                    return;
                }

                needUpdate = true;
                ((Stage)btnDel.getScene().getWindow()).close();

            } catch (SocketException e) {
                alerts(rb.getString("err.noConexion"));
            } catch (IOException e) {
                alerts(rb.getString("err.inesperado"));
            }
        }
    }

    @FXML
    void btnSearchAction(ActionEvent event) {

    }

    @FXML
    void btnAddCopyAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/comicCopies/copies_create_mod.fxml"), rb);
        Parent root;

        try{
            //TODO CHECK IF NUMBER EXISTS

            root = fxmlLoader.load();

            CopiesCreateMod copiesCreateMod = fxmlLoader.getController();

            copiesCreateMod.innitData(comicNumber.getIsbn(), 1, this.owner);

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

    /**
     * This method obtains the comic to display
     */
    private void loadComicNumber(){
        Object[] response;
        ComicNumber comicNumber;

        try {
            response = NumberManagement.getComicNumber(isbn);

            if(response[0].equals("IOE")){
                alerts(rb.getString("numberInfo.errorCargaImagen"));
                return;
            } else if (response[0].equals("SQLE Error")) {
                alerts(rb.getString("collectionInfoController.errorCargarNumero"));
                return;
            }

            comicNumber = (ComicNumber) response[1];

            if(comicNumber != null){

                response = CollectionManagement.getCollectionName(comicNumber.getColId());

                if(response[0].equals("SQLE Error")){
                    alerts(rb.getString("collectionInfoController.errorCargarNumero"));
                    return;
                }

                collectionName = (String) response[1];

                this.comicNumber = comicNumber;

                setComicData();

                populateCopiesTable();
            }else{
                alerts(rb.getString("collectionInfoController.errorCargarNumero"));
            }
        } catch (SocketException e) {
            alerts(rb.getString("err.noConexion"));
        } catch (IOException e) {
            alerts(rb.getString("err.cargarPantalla"));
        } catch (ClassNotFoundException e) {
            alerts(rb.getString("err.inesperado"));
        }
    }

    /**
     * This method establish the comic data in the screen
     */
    private void setComicData(){
        lblTitle.setText(comicNumber.getName());
        lblCover.setText(rb.getString("collectionInfoCover." + comicNumber.getCover()));
        lblIsbn.setText(comicNumber.getIsbn());
        lblNumber.setText(String.valueOf(comicNumber.getComicNumber()));
        txtArgument.setText(comicNumber.getArgument());
        lblCollection.setText(collectionName);

        if(comicNumber.getImage() == null){
            numberImage.setImage(new Image("/data/images/noImage.png", numberImage.getFitWidth(), numberImage.getFitHeight(),
                    true, true));
        }else{

            numberImage.setImage(new Image(new ByteArrayInputStream(comicNumber.getImage()),numberImage.getFitWidth(),
                    numberImage.getFitHeight(),true, true));
        }
    }

    private void populateCopiesTable(){



        comicCopies.remove(0, comicCopies.size());
        comicCopies.addAll(comicNumber.getComicCopyList());
    }

    /**
     * This method load the comic copies in the table
     */
    private void loadComicCopies(){

    }

    private void alerts(String alertMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(this.owner);
        alert.setHeaderText(null);
        alert.setTitle(rb.getString("error"));
        alert.setContentText(alertMsg);
        alert.showAndWait();
    }

}
