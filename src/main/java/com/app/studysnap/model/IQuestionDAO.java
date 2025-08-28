package com.app.studysnap.model;

import java.util.List;

public interface IQuestionDAO {
    void addQuestion(Question question);
    Question getQuestionById(int questionId);
    List<Question> getQuestionsForQuiz(int quizId);
    void updateQuestion(Question question);
    void deleteQuestion(int questionId);
}