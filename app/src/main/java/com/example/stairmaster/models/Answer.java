package com.example.stairmaster.models;

public class Answer {

    private String answer; //
    private String comment; // TODO: Change comments to list
    private String answerCreatedTimestamp;
    private int answerScore;
    private String author;
    private String answerFirebaseId;
    private String parentQuestionId;
    private String collectionType;


    public Answer(String answer, String answerCreatedTimestamp, String author, String parentQuestionId, int answerScore) {

        this.answer = answer;
        this.answerCreatedTimestamp = answerCreatedTimestamp;
        this.answerScore = answerScore;
        this.author = author;
        this.parentQuestionId = parentQuestionId;
        this.collectionType = collectionType;



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

    public String getAnswerCreatedTimestamp() {
        return answerCreatedTimestamp;
    }

    public void setAnswerCreatedTimestamp(String answerCreatedTimestamp) {
        this.answerCreatedTimestamp = answerCreatedTimestamp;
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
