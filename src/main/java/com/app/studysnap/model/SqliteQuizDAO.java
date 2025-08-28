package com.app.studysnap.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SqliteQuizDAO implements IQuizDAO
{
    private final Connection connection;

    public SqliteQuizDAO(){
        connection = SqliteConnection.getInstance();
    }

    @Override
    public void addQuiz(Quiz quiz) {
    }

    @Override
    public Quiz getQuizById(int quizId) {
        return null;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return List.of();
    }

    @Override
    public List<Quiz> getQuizzesByUser(int userId) {
        return List.of();
    }

    @Override
    public void updateQuiz(Quiz quiz) {

    }

    @Override
    public void deleteQuiz(int quizId) {

    }
}
