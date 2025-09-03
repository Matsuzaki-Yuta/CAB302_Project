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
                    + "password TEXT,"
                    + "auth_provider TEXT NOT NULL DEFAULT 'LOCAL',"
                    + "google_sub TEXT UNIQUE"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /// This receives a User object, adds it to the database in the Users table and returns the ID
    @Override
    public int addUser(User user) {
        String provider = (user.getAuthProvider() == null || user.getAuthProvider().isBlank())
                ? "LOCAL" : user.getAuthProvider();

        String sql = "INSERT INTO Users(username, email, password, auth_provider, google_sub) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword()); // add encryption
            ps.setString(4, provider);
            ps.setString(5, user.getGoogleSub());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    /// addUser extension for Google OAuth signups
    @Override
    public int addGoogleUser(String username, String email, String googleSub) {
        return addUser(new User(username, email, "google_oauth", "GOOGLE", googleSub));
    }

    /// This updates the user
    @Override
    public void updateUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Users SET username=?, email=?, password=?, auth_provider=?, google_sub=? WHERE user_id=?"
            );
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail().toLowerCase());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getAuthProvider());
            statement.setString(5, user.getGoogleSub());
            statement.setInt(6, user.getUserId());
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
        List<User> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Users");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    //Add additional function as required
    @Override
    public User getUserById(int userId) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM Users WHERE user_id=?")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM Users WHERE username=?")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM Users WHERE email=?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public boolean emailExists(String email) { return getUserByEmail(email) != null; }

    @Override
    public boolean usernameExists(String username) { return getUserByUsername(username) != null; }

    @Override
    public User getUserByGoogleSub(String googleSub) {
        if (googleSub == null) return null;
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Users WHERE google_sub=?")) {
            ps.setString(1, googleSub);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    private User mapRow(ResultSet rs) throws Exception {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setAuthProvider(rs.getString("auth_provider"));
        u.setGoogleSub(rs.getString("google_sub"));
        return u;
    }

    // DEBUGGING
    // Add 3 user mock data for testing purposes *use the same email once, as it has a unique constraint
    public void seedMockUsers(){
        addUser(new User("test1", "email1@gmail.com", "password1", "LOCAL", null));
        addUser(new User("test2", "email2@gmail.com", "password2", "LOCAL", null));
        addUser(new User("test3", "email3@gmail.com", "password3", "LOCAL", null));
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



