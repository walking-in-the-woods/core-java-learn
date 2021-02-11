package uiapp;

/*
While we can use platform.runLater to update the UI, it's not good practice to tie a task to the UI in this way.
If we have a change, the UI would also have to change the task. In general, the UI code and the code that processes
data should be kept separately. So, there is a better way to update the ListView, and this is the recommended way
to do it. Instead of using platform.runLater we can use what's called "Data Binding".
So, the task class is a property called value which is the value returned by the task. In our example it would be
the observable list of strings. The list view control has a property called "items" and that contains the items that
the list view is displaying. What we're going to do is bind the items property in the tasks value property.
Whatever the value property changes, the items property will update accordingly. We no longer have to explicitly
update the list view by calling platform.runLater.
*/

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class Controller {

    private Task<ObservableList<String>> task;
    @FXML
    private ListView listView;

    public void initialize() {
        task = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws Exception {
                Thread.sleep(1000);

                ObservableList<String> employees =  FXCollections.observableArrayList(
                        "Tim Buchalka",
                        "Bill Rogers",
                        "Jack Jill",
                        "Joan Andrews",
                        "Mary Johnson",
                        "Bob McDonald");

                // not a good practice example
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        listView.setItems(employees);
//                    }
//                });

                return employees;
            }
        };

        // and this line we've added as an example of a better practice
        listView.itemsProperty().bind(task.valueProperty());
    }

    @FXML
    public void buttonPressed() {
        new Thread(task).start();
    }
}
