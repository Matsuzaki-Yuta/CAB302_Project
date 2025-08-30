package com.app.studysnap.auth;

import com.app.studysnap.model.*;

public class AuthService {
    private final IUserDAO users;

    public AuthService(IUserDAO users) { this.users = users; }

    public User register(String username, String email, String rawPassword) throws IllegalArgumentException {
        if (isBlank(username) || isBlank(email) || isBlank(rawPassword))
            throw new IllegalArgumentException("All fields are required.");
        if (users.usernameExists(username))
            throw new IllegalArgumentException("Username already in use.");
        if (users.emailExists(email))
            throw new IllegalArgumentException("Email already in use.");

        // For now: store as-is. Later: hash here before saving.
        User u = new User();
        u.setUsername(username.trim());
        u.setEmail(email.trim().toLowerCase());
        u.setPassword(rawPassword);

        int id = users.addUser(u);
        u.setUserId(id);
        return u;
    }

    public User loginWithEmail(String email, String rawPassword) throws IllegalArgumentException {
        if (isBlank(email) || isBlank(rawPassword))
            throw new IllegalArgumentException("Email and password are required.");
        User u = users.getUserByEmail(email.trim().toLowerCase());
        if (u == null || !rawPassword.equals(u.getPassword()))
            throw new IllegalArgumentException("Invalid email or password.");
        return u;
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
