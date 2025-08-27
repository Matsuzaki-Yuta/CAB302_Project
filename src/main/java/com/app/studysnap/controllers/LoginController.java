package com.app.studysnap.controllers;
import com.app.studysnap.Navigator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.app.studysnap.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Hyperlink toSignupLink;
    @FXML private Hyperlink forgotPassLink;

    @FXML
    private void handleLogin() throws IOException {

        // add real auth later
        String email = emailField.getText();
        String pass  = passwordField.getText();
        System.out.println("[LOGIN] " + email + " / " + pass);

        // Todo: Call Auth

        // On success, navigate to dashboard scene (add logic)
        Navigator.goTo(loginButton, "dashboard.fxml");
    }

    @FXML
    private void goToSignup() throws IOException {
        Navigator.goTo(toSignupLink, "signup.fxml");
    }

    @FXML
    private void goToPassReset() throws IOException {
        Navigator.goTo(forgotPassLink, "resetPassword.fxml");
    }
}