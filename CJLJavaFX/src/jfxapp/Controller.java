package jfxapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.awt.Desktop;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Controller {

    @FXML
    private Label label;
    @FXML
    private Button button4;
    @FXML
    private GridPane gridPane;
    @FXML
    private WebView webView;

    public void initialize() {
        button4.setEffect(new DropShadow());
    }

    @FXML
    public void handleMouseEnter() {
        label.setScaleX(1.1);
        label.setScaleY(1.1);
    }

    @FXML
    public void handleMouseExit() {
        label.setScaleX(1.0);
        label.setScaleY(1.0);
    }

    @FXML
    public void handleClick() {
        FileChooser chooser = new FileChooser();
//        DirectoryChooser chooser = new DirectoryChooser();

        chooser.setTitle("Save Application File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text", "*.txt"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png", "*.gif"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );

        List<File> file = chooser.showOpenMultipleDialog(gridPane.getScene().getWindow());
//        File file = chooser.showSaveDialog(gridPane.getScene().getWindow());
//        File file = chooser.showDialog(gridPane.getScene().getWindow());

        if(file != null) {
            for(int i=0; i<file.size(); i++) {
                System.out.println(file.get(i));
            }
//            System.out.println(file.getPath());
        } else {
            System.out.println("Chooser was cancelled");
        }
    }

    @FXML
    public void handleLinkClick() {
        // Open a link in the default browser
//        try {
//            Desktop.getDesktop().browse(new URI("http://www.javafx.com"));
//        } catch (IOException | URISyntaxException e) {
//            e.printStackTrace();
//        }

        // Show a link in a web view (our window works like a browser)
        WebEngine engine = webView.getEngine();
        engine.load("http://www.javafx.com");
    }
}
