package view.controllers.collections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private Stage owner;

    //<editor-fold desc="FXML vars Definition">
    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtName;

    @FXML
    private DatePicker txtDate;

    @FXML
    private Button btnAccept;

    @FXML
    private TextArea txtArgument;
    //</editor-fold>

    public void modifyOption(Collection collection){
        this.operationId = 0;
        this.collection = collection;
        this.neededUpdate = false;

        txtName.setText(collection.getTitle());
        txtDate.setValue(collection.getPublishDate());
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
        this.owner = Resources.getMainWindow();
    }

    @FXML
    void btnAcceptAction(ActionEvent event) {
        String name = txtName.getText().trim();
        LocalDate date = txtDate.getValue();
        String argument = txtArgument.getText().trim();

        try{
            if(operationId == 0){
                updateCol(name, date, argument);
            }else{
                createCol(name, date, argument);
            }
        }catch (SocketException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.noConexion"));
            alert.showAndWait();
        }catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.inesperado"));
            alert.showAndWait();
        }

    }

    @FXML
    void btnCancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void updateCol(String name, LocalDate date, String argument) throws IOException {
        boolean existsCol;
        Object[] serverResponse;
        Collection auxCol;

        if(name.isEmpty() || argument.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("errCollectionCreateMod.completarCampos"));
            alert.showAndWait();
            return;
        }

        if(collection.getTitle().equals(name) && collection.getPublishDate().equals(date) &&
                collection.getArgument().equals(argument)){
            Stage stage = (Stage) btnAccept.getScene().getWindow();
            stage.close();
            return;
        }

        if(name.length() > 500){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("errCollectionCreateMod.coleccion500Car"));
            alert.showAndWait();
            return;
        }

        auxCol = new Collection(collection.getId(), name, date, argument);

        serverResponse = CollectionManagement.existsCollectionWithName(auxCol.getId(), name);

        if (serverResponse[0].equals("SQLE Error")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.guardarDatos"));
            alert.showAndWait();
            return;
        }

        existsCol = (boolean) serverResponse[1];

        if (existsCol) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("errCollectionCreateMod.colMismoNombre"));
            alert.showAndWait();
            return;
        }

        serverResponse[0] = CollectionManagement.updateCollection(auxCol);

        if(serverResponse[0].equals("SQLE Error")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("errCollectionCreateMod.actualizarCol"));
            alert.showAndWait();
            return;
        }

        collection = auxCol;

        this.neededUpdate = true;

        Stage stage = (Stage) btnAccept.getScene().getWindow();
        stage.close();


    }
    private void createCol(String name, LocalDate date, String argument) throws IOException {
        boolean existsCol;
        Object[] serverResponse;

        if(name.isEmpty() || argument.isEmpty() || date == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("errCollectionCreateMod.completarCampos"));
            alert.showAndWait();
            return;
        }

        if(name.length() > 500){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("errCollectionCreateMod.coleccion500Car"));
            alert.showAndWait();
            return;
        }

        this.collection = new Collection(collection.getId(), name, date, argument);

        serverResponse = CollectionManagement.existsCollectionWithName(name);

        if (serverResponse[0].equals("SQLE Error")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.guardarDatos"));
            alert.showAndWait();
            return;
        }

        existsCol = (boolean) serverResponse[1];

        if (existsCol) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("errCollectionCreateMod.colMismoNombre"));
            alert.showAndWait();
            return;
        }

        serverResponse[0] = CollectionManagement.insertCollection(this.collection);

        if(serverResponse[0].equals("SQLE Error")){
            System.out.println("Error al insertar la colección");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("errCollectionCreateMod.crearCol"));
            alert.showAndWait();
            return;
        }

        this.neededUpdate = true;

        Stage stage = (Stage) btnAccept.getScene().getWindow();
        stage.close();

    }
}
