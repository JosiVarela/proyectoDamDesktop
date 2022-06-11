package view.controllers.comicNumbers;

import controller.NumberManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Collection;
import model.entities.ComicNumber;
import services.Resources;
import view.controllers.collections.CollectionPresentationController;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

public class NumberPaneController implements Initializable {
    private ResourceBundle rb;
    private Stage owner;

    private ComicNumber comicNumber;
    NumberPresentationController presentationController;

    public void innitData(ComicNumber comicNumber, NumberPresentationController presentationController) {
        this.comicNumber = comicNumber;
        this.presentationController = presentationController;

        lblName.setText(this.comicNumber.getName());
        lblName.setTooltip(new Tooltip(this.comicNumber.getName()));
    }

    @FXML
    private Label lblName;

    @FXML
    private BorderPane comicPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
        this.owner = Resources.getMainWindow();
        comicPane.setOnMouseClicked(event -> numberPaneClick());
    }

    private void numberPaneClick(){
        Object[] response;
        try {
            response = NumberManagement.existsNumber(comicNumber.getIsbn());

            if(!response[0].equals("OK")){
                alerts(rb.getString("collectionInfoController.errorCargarNumero"));
                return;
            }

            if(!(boolean) response[1]){
                alerts(rb.getString("collectionInfoController.errorNoExisteNumero"));
                presentationController.getNumbers();
                return;
            }


        } catch (SocketException e) {
            alerts(rb.getString("err.noConexion"));
            return;
        } catch (IOException e) {
            alerts(rb.getString("err.inesperado"));
            return;
        }

        loadNumberScreen(comicNumber.getIsbn());
    }

    /**
     * This method show number info screen
     * @param isbn
     */
    private void loadNumberScreen(String isbn) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/comicNumbers/numbers_info.fxml"), rb);
        Parent root;

        try{
            root = fxmlLoader.load();

            NumbersInfoController infoController = fxmlLoader.getController();

            infoController.innitData(isbn);

            if(!infoController.isLoaded()){
                return;
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            infoController.setOwner(scene);

            stage.setScene(scene);
            stage.setWidth(635);
            stage.setHeight(845);
            stage.setMinWidth(635);
            stage.setMinHeight(845);
            stage.getIcons().add(Resources.APP_ICON);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.owner);
            stage.setTitle(rb.getString("numberPan.infoNumero"));
            stage.showAndWait();

            if(infoController.isNeedUpdate()){
                if(presentationController.isFiltered()){
                    presentationController.search();
                    return;
                }
                presentationController.getNumbers();
            }

        } catch (IOException e) {
            alerts(rb.getString("err.cargarPantalla"));
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
}
