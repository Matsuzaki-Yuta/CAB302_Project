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
    public static final String TITLE = "StudySnap";
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

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

        //Database logic
        SqliteUserDAO userDAO = new SqliteUserDAO();

        //Populate the Users table with 3 mock users data (disabled by default). * Needs to comment after it's called once
        //userDAO.seedMockUsers();

        //Print users from DB
        for (User user : userDAO.getAllUsers()) {
            System.out.println(user.getUserId() + " | " + user.getUsername() + " | " + user.getEmail());
        }

        //Reset Users table (disabled by default *be careful to use*).
        //userDAO.resetUsersTable();
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