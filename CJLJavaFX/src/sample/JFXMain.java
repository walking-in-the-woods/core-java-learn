package sample;

/*
JavaFX was designed with the MVC, or Model-View-Controller, pattern in mind.
This pattern keeps the code that handles an application's data separate from the UI code.
Because of this, we wouldn't mix the code that deals with the UI and the code that manipulates
the application data in the same class. The controller is sort of the middleman between the UI and the data.
*/

/*
Model - application's data model
View - FXML
Controller - the code that determines with happens when a user interacts with the UI
*/

/*
APPLICATION class is the entry point for the application. It manages the lifecycle of a JavaFX application.
INIT METHODS: start() and stop(). We run the application.launch() form the main method.
This method launches the JavaFX application and doesn't return until the application has exited.
When the JavaFX application is launched the init methods runs first. The start method runs right after the init
method. We have to override this class because it's an abstract method. We create the UI in the start method.
When the application finishes, because a user closes the applications main window, the stop method runs
just like the init method. This method is empty in the application class so unless we override it, it won't
do anything.
STAGE: is a top-level JavaFX container that extends the window class (essentially it's a main window).
The JavaFX runtime constructs the initial stage and passes it into the start method. We can't create other stages.
*/

/*
FXMLLoader loads the UI from the FXML file. FXML is a flavor of XML.
In this particular application all we have is a grid pane. The FX colon "controller" attribute tells the runtime
which class is the controller for this window.
*/

/*
When we load the ethics of an FXML file all of the UI objects defined in the file are constructed.
Then we set a scene. Each stage requires a scene and backing each scene is a scene graph, a tree which each node
corresponds to a UI control or an area of the scene, for example a rectangle.
When we load the FXML we assign it to a variable of type parent with the name root. Nodes that descend from parent can
have children in the scene graph. The stage cast provides all the window decorations such as close, resize
and minimize button.
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class JFXMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

//        GridPane root = new GridPane();
//        root.setAlignment(Pos.CENTER);
//        root.setVgap(10);
//        root.setHgap(10);
//
//        Label greeting = new Label("Welcome to JavaFX!");
//        greeting.setTextFill(Color.GREEN);
//        greeting.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
//
//        root.getChildren().add(greeting);

        primaryStage.setTitle("Hello JavaFX!");
        primaryStage.setScene(new Scene(root, 700, 275));
        primaryStage.show();
    }
}
