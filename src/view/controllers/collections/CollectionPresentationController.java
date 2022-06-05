package view.controllers.collections;

import controller.Translatable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import controller.CollectionManagement;
import model.entities.Collection;
import services.Resources;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CollectionPresentationController implements Initializable, Translatable {

    private final List<Collection> collectionList = new ArrayList<>();
    private ResourceBundle rb;

    private Scene owner;

    private Stage thisStage;

    //<editor-fold desc="FXML vars Definition">
    @FXML
    private TilePane cardsPane;

    @FXML
    private Button btnAdd;

    @FXML
    private Label lblPanel;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSearch;
    //</editor-fold>


    public void setOwner(Scene scene){
        this.owner = scene;
    }

    public void innitData(){
        getCollections();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
        //this.owner = Resources.getMainWindow();

    }

    @FXML
    void btnSearchAction (ActionEvent event) {
        search();
    }
    @FXML
    void txtSearchAction(ActionEvent event) {
        search();
    }

    @Override
    public void translate(ResourceBundle resources) {
        this.rb = resources;

        //Controls
        lblPanel.setText("");
        lblPanel.setText(rb.getString("mainForm.btnCol"));
        //Load Hints
        loadHints();
        //Translate sub-forms
        loadCollections();
    }
    @FXML
    void btnAddAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/collections/collection_create_mod.fxml"), rb);

        Parent root;

        try{
            root = fxmlLoader.load();

            CollectionCreateMod controller = fxmlLoader.getController();
            controller.createOption();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            controller.setOwner(scene);

            stage.setScene(scene);
            stage.initOwner(this.owner.getWindow());
            stage.setWidth(620);
            stage.setHeight(450);
            stage.setMinHeight(450);
            stage.setMinWidth(620);
            stage.setTitle(rb.getString("collectionInfo.altaColeccion"));
            stage.getIcons().add(Resources.APP_ICON);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            if(controller.isNeededUpdate()){
                getCollections();
            }

        } catch (IOException e) {
            alerts(rb.getString("err.cargarPantalla"));
        }
    }

    private void loadCollections(){
        FXMLLoader fxmlLoader;
        Parent node;

        cardsPane.getChildren().remove(0, cardsPane.getChildren().size());

        for(Collection col : collectionList){

            fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/collections/collection_pane_form.fxml"), rb);

            try {

                node = fxmlLoader.load();

                CollectionPaneController paneController = fxmlLoader.getController();

                paneController.setOwner(owner);

                paneController.innitData(col, this);

                TilePane.setMargin(node, new Insets(5));

                cardsPane.getChildren().add(node);


            } catch (IOException e) {
                alerts(rb.getString("err.ObtenerColecciones"));
                return;
            }
        }
    }

    private void loadHints(){
        btnSearch.setTooltip(new Tooltip(rb.getString("buscar")));
        btnAdd.setTooltip(new Tooltip(rb.getString("collectionPresentation.anhadir")));
    }

    public void getCollections(){

        loadHints();

        Object[] serverResponse;
        try {
            serverResponse = CollectionManagement.getCollections();
        } catch (SocketException e){
            alerts(rb.getString("err.noConexion"));
            return;
        } catch (IOException e) {
            alerts(rb.getString("err.ObtenerColecciones"));
            return;
        } catch (ClassNotFoundException e) {
            alerts(rb.getString("err.inesperado"));
            return;
        }

        if(serverResponse[0].equals("SQLE Error")){
            alerts(rb.getString("err.ObtenerColecciones"));
            return;
        }

        collectionList.removeAll(collectionList);

        collectionList.addAll((List<Collection>) serverResponse[1]);

        loadCollections();
    }

    private void search(){
        String colName = txtSearch.getText().trim();
        Object[] serverResponse;

        if(colName.isEmpty()){
            getCollections();
            return;
        }

        try {
            serverResponse = CollectionManagement.getCollectionsByName(colName);
        } catch (SocketException e) {
            alerts(rb.getString("err.noConexion"));
            return;
        }catch (IOException e) {
            alerts(rb.getString("err.ObtenerColecciones"));
            return;

        } catch (ClassNotFoundException e) {
            return;
        }

        if(serverResponse[0].equals("SQLE Error")){
            alerts(rb.getString("err.ObtenerColecciones"));
            return;
        }

        collectionList.removeAll(collectionList);

        collectionList.addAll((List<Collection>) serverResponse[1]);

        loadCollections();
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
