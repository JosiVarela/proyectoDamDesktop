package view.controllers.collections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CollectionManagement;
import model.entities.Collection;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
    }

    @FXML
    void btnAcceptAction(ActionEvent event) {
        String name = txtName.getText().trim();
        LocalDate date = txtDate.getValue();
        String argument = txtArgument.getText().trim();


        if(operationId == 0){
            updateCol(name, date, argument);
        }else{
            createCol(name, date, argument);
        }
    }

    private void updateCol(String name, LocalDate date, String argument){
        boolean existsCol;
        Object[] serverResponse;
        Collection auxCol;

        if(name.isEmpty() || argument.isEmpty()){
            System.out.println("Debe completar todos los campos");
            return;
        }

        if(collection.getTitle().equals(name) && collection.getPublishDate().equals(date) &&
                collection.getArgument().equals(argument)){
            Stage stage = (Stage) btnAccept.getScene().getWindow();
            stage.close();
            return;
        }

        if(name.length() > 500){
            System.out.println("El nombre de la colección no puede superara los 500 caracteres");
            return;
        }

        auxCol = new Collection(collection.getId(), name, date, argument);

        try {


            serverResponse = CollectionManagement.existsCollectionWithName(auxCol.getId(), name);

            if (serverResponse[0].equals("SQLE Error")) {
                System.out.println("Se produjo un error al guardar los datos");
                return;
            }

            existsCol = (boolean) serverResponse[1];

            if (existsCol) {
                System.out.println("Ya existe una colección con el mismo nombre");
                return;
            }

            serverResponse[0] = CollectionManagement.updateCollection(auxCol);

            if(serverResponse[0].equals("SQLE Error")){
                System.out.println("Error al actualizar la colección");
                return;
            }

            collection = auxCol;

            this.neededUpdate = true;

            Stage stage = (Stage) btnAccept.getScene().getWindow();
            stage.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void createCol(String name, LocalDate date, String argument){
        boolean existsCol;
        Object[] serverResponse;

        if(name.isEmpty() || argument.isEmpty() || date == null){
            System.out.println("Debe completar todos los campos");
            return;
        }

        if(name.length() > 500){
            System.out.println("El nombre de la colección no puede superara los 500 caracteres");
            return;
        }

        this.collection = new Collection(collection.getId(), name, date, argument);

        try {


            serverResponse = CollectionManagement.existsCollectionWithName(name);

            if (serverResponse[0].equals("SQLE Error")) {
                System.out.println("Se produjo un error al guardar los datos");
                return;
            }

            existsCol = (boolean) serverResponse[1];

            if (existsCol) {
                System.out.println("Ya existe una colección con el mismo nombre");
                return;
            }

            serverResponse[0] = CollectionManagement.insertCollection(this.collection);

            if(serverResponse[0].equals("SQLE Error")){
                System.out.println("Error al insertar la colección");
                return;
            }

            this.neededUpdate = true;

            Stage stage = (Stage) btnAccept.getScene().getWindow();
            stage.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
