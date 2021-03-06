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
import javafx.stage.StageStyle;
import model.entities.ServerConfig;
import org.docgene.help.JavaHelpFactory;
import services.Resources;
import view.JavaHelp;
import view.controllers.collections.CollectionPresentationController;

import javax.swing.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainFormController implements Initializable {
    private JButton swingButton;

    private FXMLLoader currentLoadedWindow = null;

    private ResourceBundle rb;

    private Stage thisStage;

    private Button activeButton;

    private JavaHelp javaHelp;

    //<editor-fold desc="FXML vars Definition">
    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button btnReports;

    @FXML
    private Button btnMainPage;

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnCollection;

    @FXML
    private Button btnConfigMenu;
    @FXML
    private Button btnNumber;
    //</editor-fold>

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.rb = resources;

        swingButton = new JButton();

        loadHints();

        loadMainPage();
    }

    @FXML
    void btnHelpAction(ActionEvent event) {
        //swingButton.doClick();
        //javaHelp.start();
        JavaHelp.showHelp(thisStage, rb);
    }

    @FXML
    void btnCollectionAction(ActionEvent event) {
        Parent node;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/collections/collection_presentation.fxml"), rb);

        if(currentLoadedWindow != null && currentLoadedWindow.equals(fxmlLoader)){
            return;
        }

        currentLoadedWindow = fxmlLoader;

        if(ServerConnection.getConnection() == null){
            alerts(rb.getString("err.noConexion"));
            return;
        }

        try {

            node = fxmlLoader.load();

            CollectionPresentationController controller = fxmlLoader.getController();

            AnchorPane.setTopAnchor(node, 0d);
            AnchorPane.setBottomAnchor(node, 0d);
            AnchorPane.setLeftAnchor(node, 0d);
            AnchorPane.setRightAnchor(node, 0d);
            mainPane.getChildren().remove(0, mainPane.getChildren().size());
            mainPane.getChildren().add(node);

            controller.setOwner(node.getScene());
            controller.innitData();

            resetMenuButton();
            activeButton = btnCollection;
            activeButton.setStyle("-fx-background-color: #2d873f;");

        } catch (SocketException e){
            alerts(rb.getString("err.noConexion"));
        } catch (IOException e) {
            alerts(rb.getString("err.cargarPantalla"));
        }


    }

    @FXML
    void btnConfigMenuAction(ActionEvent event) {
        Locale initLocale = Locale.getDefault();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/forms/app_config_window.fxml"));
        loader.setResources(rb);

        try {
            Parent root = loader.load();

            AppConfigWindow configWindow = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setMaxWidth(1000);
            stage.setMaxHeight(700);
            stage.setWidth(620);
            stage.setHeight(440);
            stage.setMinWidth(620);
            stage.setMinHeight(440);
            stage.initOwner(this.thisStage);
            stage.setTitle(rb.getString("configuracion"));
            stage.getIcons().add(new Image("/data/images/app_icon.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();

            if(configWindow.isNewConnection()){
                loadMainPage();
            }

        }catch (IOException e) {
            alerts(rb.getString("err.cargarPantalla"));
            return;
        }

        if(!initLocale.equals(Locale.getDefault())){
            rb = ResourceBundle.getBundle("data.language.language");

            if(currentLoadedWindow != null){
                Translatable translatable = currentLoadedWindow.getController();
                if(translatable != null){
                    translatable.translate(rb);
                }
            }

            this.translate();
        }

    }
    @FXML
    void btnNumberAction(ActionEvent event) {
        Node node;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/comicNumbers/number_presentation.fxml"), rb);

        if(currentLoadedWindow != null && currentLoadedWindow.equals(fxmlLoader)){
            return;
        }

        currentLoadedWindow = fxmlLoader;

        if(ServerConnection.getConnection() == null){
            alerts(rb.getString("err.noConexion"));
            return;
        }

        try {

            node = fxmlLoader.load();

            AnchorPane.setTopAnchor(node, 0d);
            AnchorPane.setBottomAnchor(node, 0d);
            AnchorPane.setLeftAnchor(node, 0d);
            AnchorPane.setRightAnchor(node, 0d);
            mainPane.getChildren().remove(0, mainPane.getChildren().size());
            mainPane.getChildren().add(node);

            resetMenuButton();
            activeButton = btnNumber;
            activeButton.setStyle("-fx-background-color: #2d873f;");

        } catch (SocketException e){
            alerts(rb.getString("err.noConexion"));
        } catch (IOException e) {
            alerts(rb.getString("err.cargarPantalla"));
        }
    }

    @FXML
    void btnMainPageAction(ActionEvent event) {
        loadMainPage();
    }

    @FXML
    void btnReportsAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/reports/reports_presentation.fxml"), rb);
        Parent node;

        if(currentLoadedWindow != null && currentLoadedWindow.equals(fxmlLoader)){
            return;
        }

        currentLoadedWindow = fxmlLoader;

        if(ServerConnection.getConnection() == null){
            alerts(rb.getString("err.noConexion"));
            return;
        }

        try {

            node = fxmlLoader.load();

            AnchorPane.setTopAnchor(node, 0d);
            AnchorPane.setBottomAnchor(node, 0d);
            AnchorPane.setLeftAnchor(node, 0d);
            AnchorPane.setRightAnchor(node, 0d);
            mainPane.getChildren().remove(0, mainPane.getChildren().size());
            mainPane.getChildren().add(node);

            resetMenuButton();
            activeButton = btnReports;
            activeButton.setStyle("-fx-background-color: #2d873f;");



        }  catch (IOException e) {
            alerts(rb.getString("err.cargarPantalla"));
        }

    }

    private void loadMainPage(){
        Node node;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forms/welcome_screen.fxml"), rb);

        if(currentLoadedWindow != null && currentLoadedWindow.equals(fxmlLoader)){
            return;
        }

        currentLoadedWindow = fxmlLoader;

        try {

            node = fxmlLoader.load();

            AnchorPane.setTopAnchor(node, 0d);
            AnchorPane.setBottomAnchor(node, 0d);
            AnchorPane.setLeftAnchor(node, 0d);
            AnchorPane.setRightAnchor(node, 0d);
            mainPane.getChildren().remove(0, mainPane.getChildren().size());
            mainPane.getChildren().add(node);

            resetMenuButton();
            activeButton = btnMainPage;
            activeButton.setStyle("-fx-background-color: #2d873f;");

        }catch (IOException e) {
            e.printStackTrace();
            alerts(rb.getString("err.cargarPantalla"));
        }
    }

    private void translate() {
        //Controls
        btnMainPage.setText(rb.getString("mainForm.btnInicio"));
        btnCollection.setText(rb.getString("mainForm.btnCol"));
        //thisStage.setTitle(rb.getString("titulo.principal"));

        //Load hints
        loadHints();
    }

    private void loadHints(){
        btnHelp.setTooltip(new Tooltip(rb.getString("ayuda")));
        btnConfigMenu.setTooltip(new Tooltip(rb.getString("configuracion")));
    }

    private void resetMenuButton(){
        if(activeButton != null){
            activeButton.setStyle("" +
                    ".buttons_style{\n" +
                    "    -fx-background-color: #59c229;\n" +
                    "    -fx-background-radius: 50;\n" +
                    "    -fx-font-weight: bold;\n" +
                    "    -fx-font-size: 15;\n" +
                    "    -fx-pref-height: 60;\n" +
                    "    -fx-border-radius: 50;\n" +
                    "}\n" +
                    "\n" +
                    ".buttons_style:hover{\n" +
                    "    -fx-background-color: #2d873f;\n" +
                    "}");
        }
    }

    public void setStage(Stage thisStage){
        this.thisStage = thisStage;
        Resources.setMainWindow(this.thisStage);
    }

    public void startConnection(){

        try {
            ServerConfig serverConfig = AppConfigurations.getServerConfig();
            ServerConnection.startConnection(serverConfig.getIp(), serverConfig.getPort());
        }catch (SocketException e) {
            alerts(rb.getString("err.noConexion"));
        }catch (IOException e) {
            alerts(rb.getString("err.inesperado"));
        }
    }

    private void alerts(String alertMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(this.thisStage);
        alert.setHeaderText(null);
        alert.setTitle(rb.getString("error"));
        alert.setContentText(alertMsg);
        alert.showAndWait();
    }
}
