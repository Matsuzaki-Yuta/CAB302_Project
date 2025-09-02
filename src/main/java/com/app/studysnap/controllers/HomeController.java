package com.app.studysnap.controllers;

import com.app.studysnap.auth.Session;
import com.app.studysnap.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private void initialize() {
        User user = Session.getCurrentUser();
        if (user != null) {
            welcomeLabel.setText("Welcome, " + user.getUsername() + "!");
        } else {
            welcomeLabel.setText("Welcome, guest!");
        }
    }
}
