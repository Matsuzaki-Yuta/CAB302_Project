package com.app.studysnap.model;

import java.util.List;
import java.util.Map;

public class Quiz {
    private int quizId;
    private int createdBy;   // user_id from Users table
    private String title;
    private List<Question> questions; // questions for the quiz

    // Constructors
    public Quiz(int quizId, String title, int createdBy) {
        this.quizId = quizId;
        this.title = title;
        this.createdBy = createdBy;
    }

    public Quiz(String title, int createdBy) {
        this.title = title;
        this.createdBy = createdBy;
    }

    // Getters and setters
    public int getQuizId() { return quizId; }
    public void setQuizId(int quizId) { this.quizId = quizId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
}
