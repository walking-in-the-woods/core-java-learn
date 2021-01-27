module CJLJavaFX {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;
    requires jlfgr;
    requires java.desktop;

    opens sample;
    opens layouts;
    opens events;
    opens todolist;
    opens jfxapp;
    opens scenebuilder;
    opens mycontacts;
    opens mycontacts.datamodel;
}