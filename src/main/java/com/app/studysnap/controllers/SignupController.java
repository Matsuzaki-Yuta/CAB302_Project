package com.app.studysnap.controllers;

import com.app.studysnap.Main;
import com.app.studysnap.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button googleSignButton;
    @FXML private Button signButton;
    @FXML private Hyperlink toLoginLink;

    @FXML
    private void handleSignup() throws IOException {

        // add validation & persistence later
        String name  = nameField.getText();
        String email = emailField.getText();
        String pass1 = passwordField.getText();
        String pass2 = confirmPasswordField.getText();

        System.out.println("[SIGNUP] " + name + " / " + email + " / " + pass1 + " / " + pass2);

        // Todo: call auth

        // If ok, navigate to Log in or straight to app (add logic)
        Navigator.goTo(signButton, "dashboard.fxml");
    }

    @FXML
    private void handleGoogleSignup() throws IOException {
        // Todo: feature research (Google Signup)
    }

    @FXML
    private void goToLogin() throws IOException {
        Navigator.goTo(toLoginLink, "login.fxml");
    }
}
