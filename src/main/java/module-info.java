module com.example.cab302_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.app.studysnap to javafx.fxml;
    exports com.app.studysnap;
    exports com.app.studysnap.controllers;
    opens com.app.studysnap.controllers to javafx.fxml;
}