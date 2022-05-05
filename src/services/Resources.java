package services;


import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Resources {
    public static final String SETTINGS_FILE_URL = "./src/settings.properties";
    public static final Image APP_ICON = new Image("/data/images/app_icon.png");

    private static Stage mainWindow;

    public static void setMainWindow(Stage stage){
        mainWindow = stage;
    }

    public static Stage getMainWindow(){
        return mainWindow;
    }
}
