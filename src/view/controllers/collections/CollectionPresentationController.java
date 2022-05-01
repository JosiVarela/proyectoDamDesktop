package view.controllers.collections;

import controller.Translatable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import model.CollectionManagement;
import model.entities.Collection;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CollectionPresentationController implements Initializable, Translatable {

    private final List<Collection> collectionList = new ArrayList<>();
    private ResourceBundle rb;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;

        loadDefaultCollections();
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
        lblPanel.setText(rb.getString("mainForm.btnCol"));
        //Load Hints
        loadHints();
        //Translate sub-forms
        loadCollections();
    }

    private void loadCollections(){
        FXMLLoader fxmlLoader;
        Node node;

        cardsPane.getChildren().remove(0, cardsPane.getChildren().size());

        for(Collection col : collectionList){

            fxmlLoader = new FXMLLoader(getClass().getResource("../../forms/collections/collection_pane_form.fxml"), rb);

            try {

                node = fxmlLoader.load();

                CollectionPaneController paneController = fxmlLoader.getController();

                paneController.innitData(col);

                TilePane.setMargin(node, new Insets(5));

                cardsPane.getChildren().add(node);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle(rb.getString("error"));
                alert.setContentText(rb.getString("err.ObtenerColecciones"));
                alert.showAndWait();
                return;
            }
        }
    }

    private void loadHints(){
        btnSearch.setTooltip(new Tooltip(rb.getString("buscar")));
        btnAdd.setTooltip(new Tooltip(rb.getString("collectionPresentation.anhadir")));
    }

    private void loadDefaultCollections(){

        loadHints();

        Object[] serverResponse;
        try {
            serverResponse = CollectionManagement.getCollections();
        } catch (SocketException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.noConexion"));
            alert.showAndWait();
            return;
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.ObtenerColecciones"));
            alert.showAndWait();
            return;
        } catch (ClassNotFoundException e) {
            return;
        }

        if(serverResponse[0].equals("SQLE Error")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.ObtenerColecciones"));
            alert.showAndWait();
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
            loadDefaultCollections();
            return;
        }

        try {
            serverResponse = CollectionManagement.getCollectionsByName(colName);
        } catch (SocketException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.noConexion"));
            alert.showAndWait();
            return;
        }catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.ObtenerColecciones"));
            alert.showAndWait();
            return;

        } catch (ClassNotFoundException e) {
            return;
        }

        if(serverResponse[0].equals("SQLE Error")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.ObtenerColecciones"));
            alert.showAndWait();
            return;
        }

        collectionList.removeAll(collectionList);

        collectionList.addAll((List<Collection>) serverResponse[1]);

        loadCollections();
    }
}
