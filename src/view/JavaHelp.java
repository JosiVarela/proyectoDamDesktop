package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.docgene.help.JavaHelpFactory;
import org.docgene.help.gui.jfx.JFXHelpContentViewer;
import org.xml.sax.SAXException;
import services.Resources;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class JavaHelp {

    public static void showHelp(Stage owner, ResourceBundle rb){
        String helpUrl;
        String title;
        File file;
        JavaHelpFactory factory;
        JFXHelpContentViewer viewer;
        Stage help;

        if(Locale.getDefault().equals(new Locale("es", "ES"))){
            helpUrl = "./help/help_es/articles.zip";
            title = "Ayuda";
        }else{
            helpUrl = "./help/help_gl/articles.zip";
            title = "Axuda";
        }

        file = new File(helpUrl);

        if(!file.exists()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(owner);
            alert.setHeaderText(null);
            alert.setTitle(rb.getString("error"));
            alert.setContentText(rb.getString("javahelp.noExiste"));
            alert.showAndWait();
            return;
        }


        try{
            factory = new JavaHelpFactory(file.toURI().toURL());
            factory.create();

            viewer = new JFXHelpContentViewer();
            factory.install(viewer);

            help = viewer.getHelpWindow(owner, title, 1000, 700);
            help.getIcons().add(Resources.APP_ICON);
            help.initModality(Modality.APPLICATION_MODAL);
            help.showAndWait();

        }catch (IOException | SAXException ex) {
        }

    }
}
