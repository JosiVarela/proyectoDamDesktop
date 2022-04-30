package view.controllers;

import controller.AppConfigurations;
import controller.ServerConnection;
import controller.Translatable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import model.entities.ServerConfig;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class AppConfigWindow implements Initializable {
    ResourceBundle rb;

    //<editor-fold desc="FXML vars Definition">
    @FXML
    private Button btnConnect;
    @FXML
    private TextField txtPort;
    @FXML
    private TextField txtHost;
    @FXML
    private Button btnClose;

    @FXML
    private Label txtLangSelec;

    @FXML
    private ToggleGroup rdGroupLanguage;

    @FXML
    private RadioButton rdLangGal;

    @FXML
    private RadioButton rdLangEsp;
    //</editor-fold>


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.rb = resources;

        if(AppConfigurations.getTranslations().equals("es")){
            rdLangEsp.setSelected(true);
        }else{
            rdLangGal.setSelected(true);
        }

        txtHost.setText(AppConfigurations.getServerConfig().getIp());
        txtPort.setText(String.valueOf(AppConfigurations.getServerConfig().getPort()));
    }

    @FXML
    void rdLangGalAction(ActionEvent event) {
        txtLangSelec.setText("Galego");
        try {
            AppConfigurations.storeTranslations("gl");
            Locale.setDefault(new Locale("gl", "ES"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void rdLangEspAction(ActionEvent event) {
        txtLangSelec.setText("Castellano");
        try {
            AppConfigurations.storeTranslations("es");
            Locale.setDefault(new Locale("es", "ES"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnConnectAction(ActionEvent event) {
        String host = txtHost.getText().trim();
        String portStr = txtPort.getText().trim();
        int port;

        if(host.isBlank() || portStr.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.completarDatos"));
            alert.showAndWait();
            return;
        }

        if(host.equals(AppConfigurations.getServerConfig().getIp()) &&
                portStr.equals(String.valueOf(AppConfigurations.getServerConfig().getPort())) &&
                        isConnected()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("appConfigErr.yaConectado"));
            alert.showAndWait();

            return;
        }

        try {
            port = Integer.parseInt(portStr);

            ServerConnection.startConnection(host, port);

            AppConfigurations.storeServerConfig(new ServerConfig(host, port));

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("appConfigErr.puertoNoValido"));
            alert.showAndWait();
            return;
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("appConfigErr.noConServidor"));
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(rb.getString("mensaje"));
        alert.setContentText(rb.getString("appConfig.conexionOk"));
        alert.showAndWait();
    }

    @FXML
    void btnReconnectAction(ActionEvent event) {

    }

    private static boolean isConnected(){
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        boolean haveConnexion = true;

        if(ServerConnection.getConnection() != null){

            try {
                dataOutputStream = new DataOutputStream(ServerConnection.getConnection().getOutputStream());
                dataOutputStream.writeUTF("ping");

                dataInputStream = new DataInputStream(ServerConnection.getConnection().getInputStream());
                dataInputStream.readUTF();
            } catch (IOException e) {
                haveConnexion = false;
            }
        }else{
            haveConnexion = false;
        }

        return haveConnexion;
    }

}