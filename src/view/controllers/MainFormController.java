package view.controllers;

import controller.AppConfigurations;
import controller.ServerConnection;
import controller.Translatable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.ServerConfig;


import javax.swing.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainFormController implements Initializable {
    private JButton swingButton;

    private FXMLLoader currentLoadedWindow = null;

    private ResourceBundle rb;

    private Stage thisStage;

    //<editor-fold desc="FXML vars Definition">
    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnCollection;

    @FXML
    private Button btnConfigMenu;

    @FXML
    private Button btnInicio;
    //</editor-fold>

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.rb = resources;

        swingButton = new JButton();

        loadHints();

    }

    @FXML
    void btnHelpAction(ActionEvent event) {
        swingButton.doClick();
    }

    @FXML
    void btnCollectionAction(ActionEvent event) {
        Node node;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../forms/collections/collection_presentation.fxml"), rb);

        currentLoadedWindow = fxmlLoader;

        if(ServerConnection.getConnection() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.noConexion"));
            alert.showAndWait();
            return;
        }

        try {
            node = fxmlLoader.load();

            AnchorPane.setTopAnchor(node, 0d);
            AnchorPane.setBottomAnchor(node, 0d);
            AnchorPane.setLeftAnchor(node, 0d);
            AnchorPane.setRightAnchor(node, 0d);

            mainPane.getChildren().add(node);
        } catch (SocketException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.noConexion"));
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.cargarPantalla"));
            alert.showAndWait();
        }


    }

    @FXML
    void btnConfigMenuAction(ActionEvent event) {
        Locale initLocale = Locale.getDefault();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("../forms/app_config_window.fxml"));
        loader.setResources(rb);

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setMaxWidth(700);
            stage.setMaxHeight(900);
            stage.setTitle(rb.getString("configuracion"));
            stage.getIcons().add(new Image("/data/images/app_icon.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.cargarPantalla"));
            alert.showAndWait();
            return;
        }

        if(!initLocale.equals(Locale.getDefault())){
            rb = ResourceBundle.getBundle("data.language.language");

            if(currentLoadedWindow != null){
                Translatable translatable = currentLoadedWindow.getController();

                translatable.translate(rb);
            }

            this.translate();
        }

    }



    private void translate() {
        //Controls
        btnInicio.setText(rb.getString("mainForm.btnInicio"));
        btnCollection.setText(rb.getString("mainForm.btnCol"));
        thisStage.setTitle(rb.getString("titulo.principal"));

        //Load hints
        loadHints();
    }

    private void loadHints(){
        btnHelp.setTooltip(new Tooltip(rb.getString("ayuda")));
        btnConfigMenu.setTooltip(new Tooltip(rb.getString("configuracion")));
    }

    public void setStaqe(Stage thisStage){
        this.thisStage = thisStage;
    }

    public void startConnection(){

        try {
            ServerConfig serverConfig = AppConfigurations.getServerConfig();
            ServerConnection.startConnection(serverConfig.getIp(), serverConfig.getPort());
        }catch (SocketException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.noConexion"));
            alert.showAndWait();
        }catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.inesperado"));
            alert.showAndWait();
        }
    }
}
