package view;

import controller.AppConfigurations;
import controller.ServerConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import services.Resources;
import view.controllers.MainFormController;

import java.util.Locale;
import java.util.ResourceBundle;

public class Launch extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MainFormController mainFormController;
        FXMLLoader fxmlLoader;

        AppConfigurations.generateConfig();

        AppConfigurations.switchLanguage(AppConfigurations.getTranslations());

        ResourceBundle resourceBundle = ResourceBundle.getBundle("data.language.language");

        fxmlLoader = new FXMLLoader(getClass().getResource("forms/main_form.fxml"), resourceBundle);
        Parent root = fxmlLoader.load();
        mainFormController = fxmlLoader.getController();

        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Hello!");
        stage.getIcons().add(Resources.APP_ICON);
        stage.setMinWidth(1000);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();

        mainFormController.startConnection();
    }

    public static void main(String[] args) {
        launch();
    }
}
