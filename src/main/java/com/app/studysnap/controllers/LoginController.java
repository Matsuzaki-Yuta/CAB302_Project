package com.app.studysnap.controllers;
import com.app.studysnap.Navigator;
import com.app.studysnap.auth.AuthService;
import com.app.studysnap.auth.GoogleAuthService;
import com.app.studysnap.auth.Session;
import com.app.studysnap.model.SqliteUserDAO;

import com.app.studysnap.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML public Button googleLoginButton;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Hyperlink toSignupLink;
    @FXML private Hyperlink forgotPassLink;

    private final AuthService auth = new AuthService(new SqliteUserDAO());

    @FXML
    private void handleLogin() throws IOException {
        try {
            var user = auth.loginWithEmail(emailField.getText(), passwordField.getText());
            Session.setCurrentUser(user);
            Navigator.goTo(loginButton, "dashboard.fxml");
        } catch (IllegalArgumentException ex) {
            String message = ex.getMessage();
            showError(message);
            if (Objects.equals(message, "This account uses Google Sign-In. Use 'Sign in with Google'.")) {
                handleGoogleLogin();
            }
        } catch (Exception ex) {
            showError("Unexpected error. Please try again.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleGoogleLogin() throws IOException {
        try {
            GoogleAuthService googleAuth = new GoogleAuthService();
            var userInfo = googleAuth.login();
            User u = auth.loginWithGoogle(userInfo.getId(), userInfo.getEmail(), userInfo.getName());
            Session.setCurrentUser(u);
            new Alert(Alert.AlertType.INFORMATION, "Welcome, " + u.getUsername(), ButtonType.OK).showAndWait();
            Navigator.goTo(googleLoginButton, "dashboard.fxml");
        } catch (IllegalArgumentException ex) {
            String message = ex.getMessage();
            showError(message);
            if (Objects.equals(message, "An account with this email uses a password. Use email login.")) {
                Navigator.goTo(googleLoginButton, "login.fxml");
            }
        } catch (Exception e) {
            showError("Google login failed: " + e.getMessage());
            e.printStackTrace();
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