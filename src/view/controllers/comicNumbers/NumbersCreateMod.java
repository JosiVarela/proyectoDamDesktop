package view.controllers.comicNumbers;

import controller.CollectionManagement;
import controller.NumberManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.entities.ComicNumber;
import services.Resources;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumbersCreateMod implements Initializable {

    private byte[] byteImage;
    ObservableList<String> cmbValues = FXCollections.observableArrayList();
    ResourceBundle rb;
    /**
     * If operationMode = 1 is modification and if operationMode = 0 is insertion
     */
    private int operationMode;

    private int colId;

    private Stage owner;

    private boolean needUpdate;

    private ComicNumber comicNumber;
    @FXML
    private Button btnAddImage;
    @FXML
    private TextField txtName;
    @FXML
    private ImageView comicImage;

    @FXML
    private ChoiceBox<String> cmbCover;

    @FXML
    private TextField txtNumber;

    @FXML
    private TextArea txtArgument;

    @FXML
    private TextField txtIsbn;
    @FXML
    private Button btnAccept;
    @FXML
    private Button btnDelImage;

    public boolean isLoaded(){
        return comicNumber != null;
    }

    public boolean isNeedUpdate(){
        return this.needUpdate;
    }

    public void innitData(int operationMode, int colId, Stage owner){
        this.operationMode = operationMode;
        this.owner = owner;
        this.colId = colId;

        loadDefaultData();
    }

    public void innitData(int operationMode, String isbn, Stage owner){
        this.operationMode = operationMode;
        this.owner = owner;

        loadNumberData(isbn);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
        cmbValues.addAll(rb.getString("collectionInfoCover.soft"), rb.getString("collectionInfoCover.hard"));
        cmbCover.setItems(cmbValues);
    }

    @FXML
    void btnAddImageAction(ActionEvent event) {
        File file;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("*.png, *.jpg", "*.png", "*.jpg")
        );
        file = fileChooser.showOpenDialog(Resources.getMainWindow());

        if(file == null){
            return;
        }

        try(
                FileInputStream inputStream = new FileInputStream(file);
                ){

            comicImage.setImage(new Image(file.getAbsolutePath(), comicImage.getFitWidth(), comicImage.getFitHeight(),
                    false, true));



            this.byteImage = inputStream.readAllBytes();

            btnDelImage.setDisable(false);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAcceptAction(ActionEvent event) {
        if(this.operationMode == 0){
            insertNumber();
        }else{
            updateNumber();
        }
    }

    @FXML
    void btnDelImageAction(ActionEvent event) {
        byteImage = null;
        comicImage.setImage(new Image("/data/images/noImage.png", comicImage.getFitWidth(), comicImage.getFitHeight(),
                false, true));
        btnDelImage.setDisable(true);
    }

    /**
     * This method loads the default data when the form is started in insert mode
     */
    private void loadDefaultData(){
        comicImage.setImage(new Image("/data/images/noImage.png", comicImage.getFitWidth(), comicImage.getFitHeight(),
                false, true));

        cmbCover.setValue(rb.getString("collectionInfoCover.soft"));

        btnDelImage.setDisable(true);
    }

    /**
     * This method loads
     */
    private void loadNumberData(String isbn){
        Object[] response;
        ComicNumber comicNumber;

        try {
            response = NumberManagement.getComicNumber(isbn);

            if(response[0].equals("IOE")){
                alerts(rb.getString("numberInfo.errorCargaImagen"));
                return;
            } else if (response[0].equals("SQLE Error")) {
                alerts(rb.getString("collectionInfoController.errorCargarNumero"));
                return;
            }

            comicNumber = (ComicNumber) response[1];

            if(comicNumber != null){

                response = CollectionManagement.getCollectionName(comicNumber.getColId());

                if(response[0].equals("SQLE Error")){
                    alerts(rb.getString("collectionInfoController.errorCargarNumero"));
                    return;
                }

                this.comicNumber = comicNumber;

                txtName.setText(comicNumber.getName());
                txtNumber.setText(String.valueOf(comicNumber.getComicNumber()));
                txtIsbn.setText(comicNumber.getIsbn());
                txtIsbn.setDisable(true);
                cmbCover.setValue(rb.getString("collectionInfoCover." + comicNumber.getCover()));
                txtArgument.setText(comicNumber.getArgument());
                byteImage = comicNumber.getImage();

                if(byteImage != null){
                    comicImage.setImage(new Image(new ByteArrayInputStream(byteImage), comicImage.getFitWidth(), comicImage.getFitHeight(),
                            false, true));
                }else{
                    comicImage.setImage(new Image("/data/images/noImage.png", comicImage.getFitWidth(), comicImage.getFitHeight(),
                            false, true));

                    btnDelImage.setDisable(true);
                }


            }else{
                alerts(rb.getString("collectionInfoController.errorCargarNumero"));
            }
        } catch (SocketException e) {
            alerts(rb.getString("err.noConexion"));
        } catch (IOException e) {
            alerts(rb.getString("err.cargarPantalla"));
        } catch (ClassNotFoundException e) {
            alerts(rb.getString("err.inesperado"));
        }
    }

    /**
     * This method inserts the number in the DB
     */
    private void insertNumber(){
        String name = txtName.getText().trim();
        String numberNum = txtNumber.getText().trim();
        String isbn = txtIsbn.getText().trim();
        String argument = txtArgument.getText().trim();
        int numberNumInt;
        String cover;
        Object[] response;
        ComicNumber comicNumber;
        String isbn10 = "^\\d{10}";
        String isbn13 = "^\\d{13}";

        Pattern isbn10Pattern = Pattern.compile(isbn10);
        Pattern isbn13Pattern = Pattern.compile(isbn13);

        if(cmbCover.getValue().equals(rb.getString("collectionInfoCover.soft"))){
            cover = "soft";
        }else{
            cover = "hard";
        }

        if(name.isEmpty() || numberNum.isEmpty() || isbn.isEmpty() || argument.isEmpty()){
            alerts(rb.getString("numberCreateMod.completarCampos"));
            return;
        }

        if(!isbn10Pattern.matcher(isbn).matches() || !isbn13Pattern.matcher(isbn).matches()){
            alerts(rb.getString("numberCreateMod.isbnFormato"));
            return;
        }

        try {

            numberNumInt = Integer.parseInt(numberNum);

            //Query if isbn exists
            response = NumberManagement.existsNumber(isbn);

            if(response[0].equals("SQLE Error")){
                alerts("numbersCreateMod.errConsultarDatos");
                return;
            }

            if((boolean)response[1]){
                alerts(rb.getString("numbersCreateMod.errExisteIsbn"));
                return;
            }

            comicNumber = new ComicNumber(isbn, numberNumInt, cover, this.colId, name, argument);

            if(this.byteImage != null){
                comicNumber.setImage(this.byteImage);
            }

            //Insert number into DB
            response[0] = NumberManagement.insertComicNumber(comicNumber);

            if(response[0].equals("SQLE Error")){
                alerts(rb.getString("numbersCreateMod.errInsertarNumero"));
                return;
            }
            System.out.println("Insertado");
            //Update numbers table


        }catch (NumberFormatException e) {
            alerts(rb.getString("numbersCreateMod.numeroNoValido"));
        } catch (SocketException e) {
            alerts(rb.getString("err.noConexion"));
        } catch (IOException e) {
            alerts("err.inesperado");
        }
    }

    /**
     * This method updates the number
     */
    private void updateNumber(){
        String name = txtName.getText().trim();
        String numberNum = txtNumber.getText().trim();
        String argument = txtArgument.getText().trim();
        int numberNumInt;
        String cover;
        String response;

        if(cmbCover.getValue().equals(rb.getString("collectionInfoCover.soft"))){
            cover = "soft";
        }else{
            cover = "hard";
        }

        if(name.isEmpty() || numberNum.isEmpty() || argument.isEmpty()){
            alerts(rb.getString("numberCreateMod.completarCampos"));
            return;
        }

        try {

            numberNumInt = Integer.parseInt(numberNum);


            this.comicNumber.setComicNumber(numberNumInt);
            this.comicNumber.setCover(cover);
            this.comicNumber.setName(name);
            this.comicNumber.setArgument(argument);

            if(this.byteImage != this.comicNumber.getImage()){
                this.comicNumber.setImage(this.byteImage);
            }

            //update number into DB
            response = NumberManagement.updateComicNumber(comicNumber);

            if(response.equals("SQLE Error")){
                alerts(rb.getString("numbersCreateMod.errInsertarNumero"));
                return;
            }

            //Update numbers table
            needUpdate = true;

            ((Stage)btnAccept.getScene().getWindow()).close();

        }catch (NumberFormatException e) {
            alerts(rb.getString("numbersCreateMod.numeroNoValido"));
        } catch (SocketException e) {
            alerts(rb.getString("err.noConexion"));
        } catch (IOException e) {
            alerts("err.inesperado");
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
