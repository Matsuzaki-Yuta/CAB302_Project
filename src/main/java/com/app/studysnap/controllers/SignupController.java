package com.app.studysnap.controllers;

import com.app.studysnap.Navigator;
import com.app.studysnap.auth.AuthService;
import com.app.studysnap.model.SqliteUserDAO;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class SignupController {
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button googleSignButton;
    @FXML private Button signButton;
    @FXML private Hyperlink toLoginLink;

    private final AuthService auth = new AuthService(new SqliteUserDAO());

    @FXML
    private void handleSignup() {
        try {
            String username = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (!password.equals(confirmPassword)) {
                showError("Passwords do not match.");
                return; // stop signup
            }

            auth.register(username, email, password);
            new Alert(Alert.AlertType.INFORMATION, "Account created! Please log in.", ButtonType.OK).showAndWait();
            Navigator.goTo(signButton, "login.fxml");

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
    private void handleGoogleSignup() throws IOException {
        // Todo: feature research (Google Signup)
    }

    @FXML
    private void goToLogin() throws IOException {
        Navigator.goTo(toLoginLink, "login.fxml");
    }
}
