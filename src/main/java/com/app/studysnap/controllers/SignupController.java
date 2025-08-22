package com.app.studysnap.controllers;

import com.app.studysnap.Main;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    @FXML
    private void handleSignup() {

        // add validation & persistence later
        String name  = nameField.getText();
        String email = emailField.getText();
        String pass1 = passwordField.getText();
        String pass2 = confirmPasswordField.getText();

        System.out.println("[SIGNUP] " + name + " / " + email + " / " + pass1 + " / " + pass2);

        // If ok, navigate to Log in or straight to app (add logic)
        Main.navigate("login.fxml");
    }

    @FXML
    private void goToLogin() {
        Main.navigate("login.fxml");
    }
}
