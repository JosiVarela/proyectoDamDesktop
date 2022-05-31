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
import java.util.Optional;
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
        loadCombo();
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
            updateCopy();
        }
    }

    @FXML
    void btnCancelAction(ActionEvent event) {
        ((Stage)btnDel.getScene().getWindow()).close();
    }

    @FXML
    void btnDelAction(ActionEvent event) {
        String response;

        ButtonType btnAccept = new ButtonType(rb.getString("aceptar"), ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancel = new ButtonType(rb.getString("cancelar"), ButtonBar.ButtonData.CANCEL_CLOSE);



        Alert question = new Alert(Alert.AlertType.CONFIRMATION);
        question.initOwner(this.owner);
        question.setHeaderText(null);
        question.setTitle(rb.getString("eliminar"));
        question.setContentText(rb.getString("copiesCreateMod.seguroEliminar"));
        question.getButtonTypes().clear();
        question.getButtonTypes().addAll(btnAccept, btnCancel);
        ((Button)question.getDialogPane().lookupButton(btnAccept)).setDefaultButton(false);
        ((Button)question.getDialogPane().lookupButton(btnCancel)).setDefaultButton(true);

        Optional<ButtonType> result = question.showAndWait();

        if(result.isPresent() && result.get() == btnAccept){
            try{
                response = NumberCopiesManagement.deleteCopy(this.comicCopy.getIdCopy());

                if(!response.equals("OK")){
                    alerts(rb.getString("copiesCreateMod.errEliminar"));
                    return;
                }

                this.needUpdate = true;
                ((Stage)btnDel.getScene().getWindow()).close();

            }catch (SocketException e) {
                alerts(rb.getString("err.noConexion"));
            } catch (IOException e) {
                alerts(rb.getString("err.inesperado"));
            }
        }

    }

    private void loadCopieData(int idCopy){
        Object[] response;
        try{
            response = NumberCopiesManagement.getComicCopy(idCopy);

            if(response[0].equals("SQLE Error")){
                alerts("copiesCreateMod.errCargaEjemplar");
                return;
            }

            this.comicCopy = (ComicCopy) response[1];

            loadFieldsData();

        }catch (SocketException e) {
            alerts(rb.getString("err.noConexion"));
        } catch (IOException e) {
            alerts(rb.getString("err.cargarPantalla"));
        } catch (ClassNotFoundException e) {
            alerts(rb.getString("err.inesperado"));
        }
    }

    private void loadFieldsData() {
        cmbState.setValue(getState(this.comicCopy.getState()));
        txtDate.setValue(this.comicCopy.getPurchaseDate());
        txtObservations.setText(this.comicCopy.getObservations());
    }

    private void loadCombo(){
        System.out.println("Cargo combo");
        comboList.addAll(rb.getString("copiesCreateMod.cmbNuevo"), rb.getString("copiesCreateMod.cmbComoNuevo"),
                rb.getString("copiesCreateMod.aceptable"), rb.getString("copiesCreateMod.malo"));

        System.out.println("Longitud combo" + comboList.size());


        cmbState.setItems(comboList);
    }

    private void insertCopie(){
        LocalDate purchaseDate = txtDate.getValue();
        String state = cmbState.getValue();
        String observations = (txtObservations.getText() == null ? "" : txtObservations.getText()).trim();
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

        stateInt = getState(state);

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

    private void updateCopy(){
        LocalDate purchaseDate = txtDate.getValue();
        String state = cmbState.getValue();
        String observations = (txtObservations.getText() == null ? "" : txtObservations.getText()).trim();
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

        stateInt = getState(state);

        comicCopy = new ComicCopy(this.comicCopy.getIdCopy(), purchaseDate, stateInt, observations, this.comicCopy.getIsbn());

        try{
            response = NumberManagement.existsNumber(comicCopy.getIsbn());

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

            secondResponse = NumberCopiesManagement.updateCopy(comicCopy);

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

    private int getState(String state){
        int stateInt;
        if(state.equals(rb.getString("copiesCreateMod.cmbNuevo"))){
            stateInt = 0;
        } else if (state.equals(rb.getString("copiesCreateMod.cmbComoNuevo"))) {
            stateInt = 1;
        } else if (state.equals(rb.getString("copiesCreateMod.aceptable"))) {
            stateInt = 2;
        }else {
            stateInt = 3;
        }

        return stateInt;
    }
    private String getState(int intState){
        String state;
        switch (intState){
            case 0 -> state = rb.getString("copiesCreateMod.cmbNuevo");
            case 1 -> state = rb.getString("copiesCreateMod.cmbComoNuevo");
            case 2 -> state = rb.getString("copiesCreateMod.aceptable");
            //Case 3
            default -> state = rb.getString("copiesCreateMod.malo");
        }

        return state;
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
