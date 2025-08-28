package com.app.studysnap;

import com.app.studysnap.model.SqliteUserDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.app.studysnap.model.User;

import java.io.IOException;

public class Main extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("signup.fxml"));
        scene = new Scene(fxmlLoader.load());

        stage.setTitle("StudySnap");
        stage.setScene(scene);

        // fill the screen
        stage = (Stage) scene.getWindow();
        stage.setMaximized(true);
        stage.show();

        ///Run mock data after GUI starts
        SqliteUserDAO userDAO = new SqliteUserDAO();
        //userDAO.seedMockUsers(); this used once for adding mock user data to Users table, but if run this more than once, spits out error due to the email unique constraint
        for (User user : userDAO.getAllUsers()) {
            System.out.println(user.getUserId() + " | " + user.getUsername() + " | " + user.getEmail());
        }

        ///reset Users table
        userDAO.resetUsersTable();
    }

    /** Simple screen navigation helper. */
    public static void navigate(String fxmlFile) {
        try {
            Parent newRoot = FXMLLoader.load(
                    Main.class.getResource("/com/app/studysnap/" + fxmlFile)
            );
            scene.setRoot(newRoot);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}