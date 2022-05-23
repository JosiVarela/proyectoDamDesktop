package view.controllers.comicNumbers;

import controller.CollectionManagement;
import controller.NumberManagement;
import controller.Translatable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import model.entities.Collection;
import model.entities.ComicNumber;
import services.Resources;
import view.controllers.collections.CollectionPaneController;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NumberPresentationController implements Initializable, Translatable {
    private ResourceBundle rb;
    private Stage owner;

    private final List<ComicNumber> numberList = new ArrayList<>();

    @FXML
    private Button btnSearch;

    @FXML
    private TilePane cardsPane;

    @FXML
    private Button btnAdd;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label lblPanel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
        this.owner = Resources.getMainWindow();

        getNumbers();
    }

    @FXML
    void txtSearchAction(ActionEvent event) {

    }

    @FXML
    void btnSearchAction(ActionEvent event) {

    }

    @FXML
    void btnAddAction(ActionEvent event) {

    }

    private void loadHints(){
        btnSearch.setTooltip(new Tooltip(rb.getString("buscar")));
        btnAdd.setTooltip(new Tooltip(rb.getString("collectionPresentation.anhadir")));
    }

    public void getNumbers(){

        Object[] serverResponse;
        try {
            serverResponse = NumberManagement.getComicNumbers();
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

        numberList.removeAll(numberList);

        numberList.addAll((List<ComicNumber>) serverResponse[1]);

        loadHints();

        loadNumbers();
    }

    private void loadNumbers(){
        FXMLLoader fxmlLoader;
        Node node;

        cardsPane.getChildren().remove(0, cardsPane.getChildren().size());

        for(ComicNumber number : numberList){

            fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/comicNumbers/number_pane.fxml"), rb);

            try {

                node = fxmlLoader.load();

                NumberPaneController paneController = fxmlLoader.getController();

                paneController.innitData(number, this);

                TilePane.setMargin(node, new Insets(5));

                cardsPane.getChildren().add(node);
            } catch (IOException e) {
                alerts(rb.getString("err.ObtenerColecciones"));
                return;
            }
        }
    }

    private void alerts(String alertMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(this.owner);
        alert.setHeaderText(null);
        alert.setTitle(rb.getString("error"));
        alert.setContentText(alertMsg);
        alert.showAndWait();
    }

    @Override
    public void translate(ResourceBundle resources) {
        this.rb = resources;

        //Controls
        lblPanel.setText("");
        lblPanel.setText(rb.getString("numberPresentation.numeros"));
        //Load Hints
        loadHints();
        //Translate sub-forms
        loadNumbers();
    }
}
