package com.app.studysnap.controllers;

import com.app.studysnap.Main;
import com.app.studysnap.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;

import java.util.Objects;

public class DashboardController {

    @FXML private BorderPane root;          // root of dashboard.fxml
    @FXML private StackPane contentArea;    // where we inject pages

    @FXML
    private void initialize() {
        safeLoadCenter("home.fxml");
    }

    @FXML
    private void handleNav(ActionEvent event) {
        Object src = event.getSource();
        if (src instanceof Button btn) {
            Object ud = btn.getUserData();
            if (ud != null) {
                safeLoadCenter(ud.toString());
            }
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            Navigator.goTo((Node) event.getSource(), "login.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void safeLoadCenter(String fxmlName) {
        try {
            Node view = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxmlName)));
            contentArea.getChildren().setAll(view);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
