package com.app.studysnap.model;

import java.util.List;
public interface IQuizDAO {
    void addQuiz(Quiz quiz);
    Quiz getQuizById(int quizId);
    List<Quiz> getAllQuizzes();
    List<Quiz> getQuizzesByUser(int userId);
    void updateQuiz(Quiz quiz);
    void deleteQuiz(int quizId);
}
