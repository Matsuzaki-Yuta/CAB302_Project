package com.app.studysnap.controllers;
import com.app.studysnap.Navigator;
import com.app.studysnap.auth.AuthService;
import com.app.studysnap.auth.Session;
import com.app.studysnap.model.SqliteUserDAO;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Hyperlink toSignupLink;
    @FXML private Hyperlink forgotPassLink;

    private final AuthService auth = new AuthService(new SqliteUserDAO());

    @FXML
    private void handleLogin() {
        try {
            var user = auth.loginWithEmail(emailField.getText(), passwordField.getText());
            Session.setCurrentUser(user);
            Navigator.goTo(loginButton, "dashboard.fxml");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        } catch (Exception ex) {
            showError("Unexpected error. Please try again.");
            ex.printStackTrace();
        }
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
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