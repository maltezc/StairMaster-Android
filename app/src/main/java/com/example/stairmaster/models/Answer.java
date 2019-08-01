package com.example.stairmaster.models;

public class Answer {

    private String answer; //
    private String comment; // TODO: Change comments to list
    private String timestamp;
    private int answerScore;
    private String author;
    private String answerFirebaseId;
    private String parentQuestionId;


    public Answer(String answer, String timeStamp, String author, String parentQuestionId, int answerScore) {

        this.answer = answer;
        this.timestamp = timeStamp;
        this.answerScore = answerScore;
        this.author = author;
        this.parentQuestionId = parentQuestionId;


    }

    public Answer () {
        // public no-arg constructor needed
    }

    public String getAnswerFirebaseId() {
        return answerFirebaseId;
    }

    public void setAnswerFirebaseId(String answerFirebaseId) {
        this.answerFirebaseId = answerFirebaseId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(int answerScore) {
        answerScore = answerScore;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getParentQuestionId() {
        return parentQuestionId;
    }

    public void setParentQuestionId(String parentQuestionId) {
        this.parentQuestionId = parentQuestionId;
    }
}
