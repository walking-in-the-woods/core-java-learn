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

/*
And one last note here. If we want to pass parameters to the task that the service runs, we have to expose those
parameters as properties of the service sub class.
*/

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;

public class Controller {

    @FXML
    private ListView listView;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label progressLabel;
    @FXML
    private Service<ObservableList<String>> service;

    public void initialize() {
        service = new EmployeeService();
        progressBar.progressProperty().bind(service.progressProperty());
        progressLabel.textProperty().bind(service.messageProperty());
        listView.itemsProperty().bind(service.valueProperty());
        progressBar.visibleProperty().bind(service.runningProperty());
        progressLabel.visibleProperty().bind(service.runningProperty());
    }

    @FXML
    public void buttonPressed() {
        if(service.getState() == Service.State.SUCCEEDED) {
            service.reset();
            service.start();
        } else if(service.getState() == Service.State.READY) {
            service.start();
        }
    }
}
