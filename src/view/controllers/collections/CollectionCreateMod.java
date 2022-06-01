package view.controllers.collections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import controller.CollectionManagement;
import model.entities.Collection;
import services.Resources;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CollectionCreateMod implements Initializable {
    private ResourceBundle rb;
    private Collection collection;
    private boolean neededUpdate;
    /**
     * If operationId = 0 this means that the collection will be updated and operationId = 1 this means that the
     * collection will be created
     */
    private int operationId;

    private Scene owner;

    //<editor-fold desc="FXML vars Definition">
    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtName;

    @FXML
    private Button btnAccept;

    @FXML
    private TextArea txtArgument;
    //</editor-fold>

    public void setOwner(Scene owner) {
        this.owner = owner;
    }

    public void modifyOption(Collection collection){
        this.operationId = 0;
        this.collection = collection;
        this.neededUpdate = false;

        txtName.setText(collection.getTitle());
        txtArgument.setText(collection.getArgument());

    }

    public void createOption(){
        this.operationId = 1;
        this.neededUpdate = false;

        this.collection = new Collection();
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
    }

    @FXML
    void btnAcceptAction(ActionEvent event) {
        String name = txtName.getText().trim();
        String argument = txtArgument.getText().trim();

        try{
            if(operationId == 0){
                updateCol(name, argument);
            }else{
                createCol(name, argument);
            }
        }catch (SocketException e) {
            alerts(rb.getString("err.noConexion"));
        }catch (IOException e) {
            alerts(rb.getString("err.inesperado"));
        }

    }

    @FXML
    void btnCancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void updateCol(String name, String argument) throws IOException {
        boolean existsCol;
        Object[] serverResponse;
        Collection auxCol;

        if(name.isEmpty() || argument.isEmpty()){
            alerts(rb.getString("errCollectionCreateMod.completarCampos"));
            return;
        }

        if(name.length() > 500){
            alerts(rb.getString("errCollectionCreateMod.coleccion500Car"));
            return;
        }

        auxCol = new Collection(collection.getId(), name, argument);

        serverResponse = CollectionManagement.existsCollectionWithName(auxCol.getId(), name);

        if (serverResponse[0].equals("SQLE Error")) {
            alerts(rb.getString("err.guardarDatos"));
            return;
        }

        existsCol = (boolean) serverResponse[1];

        if (existsCol) {
            alerts(rb.getString("errCollectionCreateMod.colMismoNombre"));
            return;
        }

        serverResponse[0] = CollectionManagement.updateCollection(auxCol);

        if(serverResponse[0].equals("SQLE Error")){
            alerts(rb.getString("errCollectionCreateMod.actualizarCol"));
            return;
        }

        collection = auxCol;

        this.neededUpdate = true;

        Stage stage = (Stage) btnAccept.getScene().getWindow();
        stage.close();


    }
    private void createCol(String name, String argument) throws IOException {
        boolean existsCol;
        Object[] serverResponse;

        if(name.isEmpty() || argument.isEmpty()){
            alerts(rb.getString("errCollectionCreateMod.completarCampos"));
            return;
        }

        if(name.length() > 500){
            alerts(rb.getString("errCollectionCreateMod.coleccion500Car"));
            return;
        }

        this.collection = new Collection(collection.getId(), name, LocalDate.now(), argument);

        serverResponse = CollectionManagement.existsCollectionWithName(name);

        if (serverResponse[0].equals("SQLE Error")) {
            alerts(rb.getString("err.guardarDatos"));
            return;
        }

        existsCol = (boolean) serverResponse[1];

        if (existsCol) {
            alerts(rb.getString("errCollectionCreateMod.colMismoNombre"));
            return;
        }

        serverResponse[0] = CollectionManagement.insertCollection(this.collection);

        if(serverResponse[0].equals("SQLE Error")){
            alerts(rb.getString("errCollectionCreateMod.crearCol"));
            return;
        }

        this.neededUpdate = true;

        Stage stage = (Stage) btnAccept.getScene().getWindow();
        stage.close();

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
