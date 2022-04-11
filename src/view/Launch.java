package view;

import controller.ServerConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Launch extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        ServerConnection.startConnection("localhost", 8080);

        Locale.setDefault(new Locale("gl", "ES"));

        ResourceBundle resourceBundle = ResourceBundle.getBundle("data.language.language");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("forms/main_form.fxml"), resourceBundle);
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Hello!");
        stage.getIcons().add(new Image("/data/images/app_icon.png"));
        stage.setMinWidth(1000);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
