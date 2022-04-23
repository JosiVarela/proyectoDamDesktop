package view.controllers;

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
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CollectionPresentationController implements Initializable, Translatable {
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

        Object[] serverResponse = new Object[0];
        try {
            serverResponse = CollectionManagement.getCollections();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Parece que no hay conexión con el servidor");
            alert.showAndWait();
        } catch (ClassNotFoundException e) {
        }

        List<Collection> collectionList = (List<Collection>) serverResponse[1];


        if(serverResponse[0] == "SQLE Error"){
            System.out.println("Error al obtener las colecciones");
            return;
        }

        FXMLLoader fxmlLoader;
        Node node;

        for(Collection col : collectionList){
           fxmlLoader = new FXMLLoader(getClass().getResource("../forms/collection_pane_form.fxml"));
            try {

                node = fxmlLoader.load();

                CollectionPaneController paneController = fxmlLoader.getController();

                paneController.innitData(col);

                TilePane.setMargin(node, new Insets(5));

                cardsPane.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
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
    }

    private void loadHints(){
        btnBuscar.setTooltip(new Tooltip(rb.getString("buscar")));
        btnAnhadir.setTooltip(new Tooltip(rb.getString("collectionPresentation.anhadir")));
    }
}
