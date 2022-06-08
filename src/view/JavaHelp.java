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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;

public class JavaHelp {
    private JavaHelpFactory factory = null;
    private JFXHelpContentViewer viewer = null;
    private Button helpButton;
    private Stage stage;

    private Stage root;

    public static void showHelp(Stage owner){
        String helpUrl;
        String title;
        File file;
        JavaHelpFactory factory;
        JFXHelpContentViewer viewer;

        if(Locale.getDefault().equals(new Locale("es", "ES"))){
            helpUrl = "./help/help_es/articles.zip";
            title = "Ayuda";
        }else{
            helpUrl = "./help/help_gl/articles.zip";
            title = "Axuda";
        }

        file = new File(helpUrl);

        if(!file.exists()){
            System.out.println("Error cargar ayuda");
            return;
        }

        try{
            factory = new JavaHelpFactory(file.toURI().toURL());
            factory.create();

            viewer = new JFXHelpContentViewer();
            factory.install(viewer);

            viewer.getHelpWindow(owner, title, 1000, 700).showAndWait();

        }catch (IOException | SAXException ex) {
        }

    }



    public JavaHelp(Button helpButton, Stage stage) {
        this.helpButton = helpButton;
        this.stage = stage;

        /*createFactory();

        try {
            //stage.initModality(Modality.APPLICATION_MODAL);
            viewer = new JFXHelpContentViewer();
            factory.install(viewer);
            viewer.getHelpWindow(stage, "Help Content", 600, 700);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        root = generateHelp();
        root.initModality(Modality.APPLICATION_MODAL);
    }

    /*public void start(Stage stage){
        createFactory();

        BorderPane root = createLayout(stage);

        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("DocGenerator Help Tutorial");
        stage.setScene(scene);
        stage.show();
    }*/

    private Stage generateHelp(){
        System.out.println("Antes de crear URL");
        URL url = this.getClass().getResource("articles.zip");

        File file = new File("./help/help_gl/articles.zip");


        System.out.println("");
        /*try {
            url = ClassLoader.getSystemResource("/data/help/helpes/articles.zip").toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }*/

        //System.out.println("url : " + url);

        try {
            // create the Help factory

            //System.out.println("URL: " + file.toURI().toURL());

            factory = new JavaHelpFactory(file.toURI().toURL());
            factory.create();

            // create the viewer component
            viewer = new JFXHelpContentViewer();
            factory.install(viewer);


            // do whatever you want with the Node
        } catch (IOException | SAXException ex) {
            ex.printStackTrace();
        }
        return viewer.getHelpWindow(stage, "Help Content", 600, 700);
    }

    public void start(){
        //viewer.showHelpDialog(helpButton.getGraphic());
        root.showAndWait();
    }

    private void createFactory() {
        System.out.println("Antes de crear URL");
        URL url = this.getClass().getResource("/data/help/helpes/articles.zip");
        System.out.println("url : " + url);

        System.out.println("Antes de crear factory");

        factory = new JavaHelpFactory(url);
        try {
            factory.create();
        } catch (IOException | SAXException ex) {
            ex.printStackTrace();
        }
    }

    private BorderPane createLayout(Stage stage) {
        BorderPane root = new BorderPane();
        VBox top = new VBox();
        root.setTop(top);

        try {
            viewer = new JFXHelpContentViewer();
            factory.install(viewer);
            viewer.getHelpWindow(stage, "Help Content", 600, 700);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        createContent(root);
        createMenu(top);

        return root;
    }

    private void createMenu(VBox top) {
        MenuBar menuBar = new MenuBar();
        top.getChildren().add(menuBar);
        Menu menu = new Menu("Help");
        menuBar.getMenus().add(menu);
        MenuItem item = new MenuItem("Help Content");
        menu.getItems().add(item);
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                viewer.showHelpDialog(menu.getGraphic());
            }
        });
    }

    private void createContent(BorderPane root) {
        TextArea area = new TextArea();
        area.setStyle("-fx-background-color: null;");
        Insets insets = new Insets(10, 10, 10, 10);
        area.setPadding(insets);
        area.setText("my area content");
        root.setCenter(area);
    }
}
