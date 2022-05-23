package view.controllers.comicCopies;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.entities.ComicCopy;

import java.net.URL;
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
    private TextField txtName;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextArea txtArgument;

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

    }

    private void updateCopie(){

    }

    public boolean isLoaded(){
        return comicCopy != null;
    }

    public boolean isNeedUpdate(){
        return this.needUpdate;
    }

}
