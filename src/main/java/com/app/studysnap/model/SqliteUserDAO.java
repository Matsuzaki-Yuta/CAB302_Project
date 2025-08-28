package com.app.studysnap.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqliteUserDAO implements IUserDAO {
    private Connection connection;

    public SqliteUserDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    // This creates a table named Users if the table doesn't already exist
    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Users ("
                    + "user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "username TEXT NOT NULL,"
                    + "email TEXT NOT NULL UNIQUE,"
                    + "password TEXT NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /// This receives a User object, adds it to the database in the Users table
    @Override
    public void addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Users (username, email, password) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setUserId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /// This updates the user
    @Override
    public void updateUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Users SET username = ?, email = ?, password = ? WHERE user_id = ?"
            );
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getUserId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /// This deletes the user with the specified userId from the database
    @Override
    public void deleteUser(int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM Users WHERE user_id = ?"
            );
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /// This returns the list of all the users in the database
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int user_id = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                User user = new User();
                user.setUserId(user_id);
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);

                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    //Add additional function as required
    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    // Add 3 user mock data for testing purposes *use the same email once, as it has a unique constraint
    public void seedMockUsers(){
        addUser(new User("test1", "email1@gmail.com", "password1"));
        addUser(new User("test2", "email2@gmail.com", "password2"));
        addUser(new User("test3", "email3@gmail.com", "password3"));
    }

    //Reset the Users table by deleting all the data in the table and reset the autoincrement at the same time
    public void resetUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Users");
            statement.executeUpdate("DELETE FROM sqlite_sequence WHERE name='Users'"); //reset autoincrement
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



