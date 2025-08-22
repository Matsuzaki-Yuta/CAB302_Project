package com.app.studysnap.controllers;

import com.app.studysnap.Main;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin() {

        // add real auth later
        String email = emailField.getText();
        String pass  = passwordField.getText();
        System.out.println("[LOGIN] " + email + " / " + pass);

        // On success, navigate to dashboard scene (add logic)
        Main.navigate("dashboard.fxml");
    }

    @FXML
    private void goToSignup() {
        Main.navigate("signup.fxml");
    }
}