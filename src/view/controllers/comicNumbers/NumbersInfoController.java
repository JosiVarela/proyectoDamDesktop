package view.controllers.comicNumbers;

import controller.CollectionManagement;
import controller.NumberManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.entities.ComicNumber;
import services.Resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NumbersInfoController implements Initializable {
    private ResourceBundle rb;

    private String isbn;

    private Stage owner;

    private String collectionName;

    private ComicNumber comicNumber;

    @FXML
    private Label lblCover;

    @FXML
    private TextArea txtArgument;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblIsbn;

    @FXML
    private TableView<?> comicsTable;

    @FXML
    private Label lblCollection;

    @FXML
    private ImageView numberImage;

    @FXML
    private Label lblNumber;
    @FXML
    private Button btnModify;

    public void innitData(String isbn){
        this.isbn = isbn;
        loadComicNumber();
    }

    public boolean isLoaded(){
        return comicNumber != null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
        this.owner = Resources.getMainWindow();
    }

    private void loadComicNumber(){
        Object[] response;
        ComicNumber comicNumber;

        try {
            response = NumberManagement.getComicNumber(isbn);

            if(response[0].equals("IOE")){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.initOwner(this.owner);
                errorAlert.setHeaderText(null);
                errorAlert.setTitle(rb.getString("error"));
                errorAlert.setContentText(rb.getString("numberInfo.errorCargaImagen"));
                errorAlert.showAndWait();
                return;
            } else if (response[0].equals("SQLE Error")) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.initOwner(this.owner);
                errorAlert.setHeaderText(null);
                errorAlert.setTitle(rb.getString("error"));
                errorAlert.setContentText(rb.getString("collectionInfoController.errorCargarNumero"));
                errorAlert.showAndWait();
                return;
            }

            comicNumber = (ComicNumber) response[1];

            if(comicNumber != null){

                response = CollectionManagement.getCollectionName(comicNumber.getColId());

                if(response[0].equals("SQLE Error")){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.initOwner(this.owner);
                    errorAlert.setHeaderText(null);
                    errorAlert.setTitle(rb.getString("error"));
                    errorAlert.setContentText(rb.getString("collectionInfoController.errorCargarNumero"));
                    errorAlert.showAndWait();
                    return;
                }

                collectionName = (String) response[1];

                this.comicNumber = comicNumber;

                setComicData();
            }else{
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.initOwner(this.owner);
                errorAlert.setHeaderText(null);
                errorAlert.setTitle(rb.getString("error"));
                errorAlert.setContentText(rb.getString("collectionInfoController.errorCargarNumero"));
                errorAlert.showAndWait();
            }
        } catch (SocketException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.noConexion"));
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("err.cargarPantalla"));
            alert.showAndWait();
        } catch (ClassNotFoundException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.initOwner(this.owner);
            errorAlert.setHeaderText(null);
            errorAlert.setTitle(rb.getString("error"));
            errorAlert.setContentText(rb.getString("err.inesperado"));
            errorAlert.showAndWait();
        }
    }

    private void setComicData(){
        lblTitle.setText(comicNumber.getName());
        lblCover.setText(rb.getString("collectionInfoCover." + comicNumber.getCover()));
        lblIsbn.setText(comicNumber.getIsbn());
        lblNumber.setText(String.valueOf(comicNumber.getComicNumber()));
        txtArgument.setText(comicNumber.getArgument());
        lblCollection.setText(collectionName);

        if(comicNumber.getImage() == null){
            numberImage.setImage(new Image("/data/images/noImage.png"));
        }else{
            numberImage.setImage(new Image(new ByteArrayInputStream(comicNumber.getImage())));
        }
    }
    private void loadComicCopies(){

    }

    @FXML
    void btnModifyAction(ActionEvent event) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.initOwner(this.owner);
        errorAlert.setHeaderText(null);
        errorAlert.setTitle(rb.getString("error"));
        errorAlert.setContentText(rb.getString("numberInfo.errorCargaImagen"));
        errorAlert.showAndWait();
    }

}
