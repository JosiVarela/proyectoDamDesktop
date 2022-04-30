package view.controllers.collections;

import controller.Translatable;
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
import java.util.List;
import java.util.ResourceBundle;

public class CollectionPresentationController implements Initializable, Translatable {

    List<Collection> collectionList;

    private ResourceBundle rb;

    @FXML
    private TilePane cardsPane;

    @FXML
    private Button btnAnhadir;

    @FXML
    private Label lblPanel;

    @FXML
    private TextField txtBusqueda;

    @FXML
    private Button btnBuscar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
        loadHints();

        Object[] serverResponse;
        try {
            serverResponse = CollectionManagement.getCollections();
        } catch (SocketException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Parece que no hay conexión con el servidor");
            alert.showAndWait();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            return;
        }

        collectionList = (List<Collection>) serverResponse[1];


        if(serverResponse[0].equals("SQLE Error")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.ObtenerColecciones"));
            alert.showAndWait();
            return;
        }

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

    private void loadHints(){
        btnBuscar.setTooltip(new Tooltip(rb.getString("buscar")));
        btnAnhadir.setTooltip(new Tooltip(rb.getString("collectionPresentation.anhadir")));
    }
}
