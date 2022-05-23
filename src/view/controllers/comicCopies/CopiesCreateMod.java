package view.controllers.comicCopies;

import controller.NumberCopiesManagement;
import controller.NumberManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.entities.ComicCopy;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CopiesCreateMod implements Initializable {
    private ResourceBundle rb;

    /**
     * If idOperation = 1 is insert mode and if idOperation = 0 is update mode
     */
    private int idOperation;
    private ComicCopy comicCopy;
    private boolean needUpdate;
    private Stage owner;
    private String isbn;

    private ObservableList<String> comboList = FXCollections.observableArrayList();

    @FXML
    private Button btnAccept;

    @FXML
    private ChoiceBox<String> cmbState;

    @FXML
    private Button btnCancel;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextArea txtObservations;

    @FXML
    private Button btnDel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
    }

    public void innitData(int idCopie, int idOperation, Stage owner){
        this.idOperation = idOperation;
        this.owner = owner;

        loadCopieData(idCopie);
    }

    public void innitData(String isbn, int idOperation, Stage owner){
        this.idOperation = idOperation;
        this.owner = owner;
        this.isbn = isbn;
        btnDel.setVisible(false);
        loadCombo();
        cmbState.setValue(rb.getString("copiesCreateMod.cmbNuevo"));
    }

    @FXML
    void btnAcceptAction(ActionEvent event) {
        if(this.idOperation == 1){
            insertCopie();
        }else{
            updateCopie();
        }
    }

    @FXML
    void btnCancelAction(ActionEvent event) {

    }

    @FXML
    void btnDelAction(ActionEvent event) {

    }

    private void loadCopieData(int idCopie){

    }

    private void loadCombo(){
        comboList.addAll(rb.getString("copiesCreateMod.cmbNuevo"), rb.getString("copiesCreateMod.cmbComoNuevo"),
                rb.getString("copiesCreateMod.aceptable"), rb.getString("copiesCreateMod.malo"));

        cmbState.setItems(comboList);
    }

    private void insertCopie(){
        LocalDate purchaseDate = txtDate.getValue();
        String state = cmbState.getValue();
        String observations = txtObservations.getText().trim();
        int stateInt;
        ComicCopy comicCopy;
        Object[] response;
        String secondResponse;

        if(purchaseDate == null){
            alerts(rb.getString("copiesCreateMod.fechaMsg"));
            return;
        }

        if(purchaseDate.isAfter(LocalDate.now())){
            alerts(rb.getString("copiesCreateMod.fechaPosterior"));
            return;
        }

        if(observations.length() > 300){
           alerts(rb.getString("copiesCreateMod.obsvMsg"));
           return;
        }

        if(state.equals(rb.getString("copiesCreateMod.cmbNuevo"))){
            stateInt = 0;
        } else if (state.equals(rb.getString("copiesCreateMod.cmbComoNuevo"))) {
            stateInt = 1;
        } else if (state.equals(rb.getString("copiesCreateMod.aceptable"))) {
            stateInt = 2;
        }else {
            stateInt = 3;
        }

        comicCopy = new ComicCopy(purchaseDate, stateInt, observations, this.isbn);

        try{
            response = NumberManagement.existsNumber(isbn);

            if(!response[0].equals("OK")){
                alerts(rb.getString("collectionInfoController.errorCargarNumero"));
                return;
            }

            if(!(boolean) response[1]){
                alerts(rb.getString("collectionInfoController.errorNoExisteNumero"));
                ((Stage)btnAccept.getScene().getWindow()).close();
                return;
            }

            secondResponse = NumberCopiesManagement.insertCopy(comicCopy);

            if(!secondResponse.equals("OK")){
                alerts(rb.getString("copiesCreateMod.errInsertar"));
                return;
            }

            this.needUpdate = true;
            ((Stage)btnAccept.getScene().getWindow()).close();

        }catch (SocketException e) {
            alerts(rb.getString("err.noConexion"));
        } catch (IOException e) {
            alerts(rb.getString("err.inesperado"));
        }
    }

    private void updateCopie(){
        LocalDate purchaseDate = txtDate.getValue();
        String state = cmbState.getValue();
        String observations = txtObservations.getText().trim();
        int stateInt;
        ComicCopy comicCopy;
        Object[] response;
        String secondResponse;

        if(purchaseDate == null){
            alerts(rb.getString("copiesCreateMod.fechaMsg"));
            return;
        }

        if(purchaseDate.isAfter(LocalDate.now())){
            alerts(rb.getString("copiesCreateMod.fechaPosterior"));
            return;
        }

        if(observations.length() > 300){
            alerts(rb.getString("copiesCreateMod.obsvMsg"));
            return;
        }

        if(state.equals(rb.getString("copiesCreateMod.cmbNuevo"))){
            stateInt = 0;
        } else if (state.equals(rb.getString("copiesCreateMod.cmbComoNuevo"))) {
            stateInt = 1;
        } else if (state.equals(rb.getString("copiesCreateMod.aceptable"))) {
            stateInt = 2;
        }else {
            stateInt = 3;
        }

        comicCopy = new ComicCopy(purchaseDate, stateInt, observations, this.isbn);

        try{
            response = NumberManagement.existsNumber(isbn);

            if(!response[0].equals("OK")){
                alerts(rb.getString("collectionInfoController.errorCargarNumero"));
                return;
            }

            if(!(boolean) response[1]){
                alerts(rb.getString("collectionInfoController.errorNoExisteNumero"));
                ((Stage)btnAccept.getScene().getWindow()).close();
                return;
            }

            response = NumberCopiesManagement.existsCopy(this.comicCopy.getIdCopy());

            if(!response[0].equals("OK")){
                alerts(rb.getString("copiesCreateMod.errCargaEjemplar"));
                return;
            }

            if(!(boolean) response[1]){
                alerts(rb.getString("copiesCreateMod.errNoExiste"));
                ((Stage)btnAccept.getScene().getWindow()).close();
                return;
            }

            secondResponse = NumberCopiesManagement.insertCopy(comicCopy);

            if(!secondResponse.equals("OK")){
                alerts(rb.getString("copiesCreateMod.errInsertar"));
                return;
            }

            this.needUpdate = true;
            ((Stage)btnAccept.getScene().getWindow()).close();

        }catch (SocketException e) {
            alerts(rb.getString("err.noConexion"));
        } catch (IOException e) {
            alerts(rb.getString("err.inesperado"));
        }
    }

    public boolean isLoaded(){
        return comicCopy != null;
    }

    public boolean isNeedUpdate(){
        return this.needUpdate;
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
