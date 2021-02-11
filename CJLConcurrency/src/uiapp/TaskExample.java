package uiapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TaskExample extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("uiapp.fxml"));
        primaryStage.setTitle("Task Example");
        primaryStage.setScene(new Scene(root, 300, 550));
        primaryStage.show();
    }
}
