package com.example.stairmaster.models;

import java.util.List;

public class Question {

//    private String title;
    private String question;
    List<Answer> answers;
    private String comment; // TODO: Change comments to list
    private int questionScore;
    private String questionTimestamp; //TODO: add questionTimestamp for when question was created and then revised.
    List<String> questionTags;
    private String questionDocumentId;
    private String questionAuthor;
    private String questionFirebaseId;
    private String collectionType;


    public Question() {
        // public no-arg constructor needed
    }

    public Question(String question, int questionScore, List<String> questionTags, String questionAuthor, String questionTimestamp){
//    public Question(String question, List<Answer> answers, int questionScore, List<String> questionTags, String questionAuthor){
        this.question = question;
        this.answers = answers;
        this.questionScore = questionScore;
        this.questionTags = questionTags;
        this.questionAuthor = questionAuthor;
        this.questionTimestamp = questionTimestamp;
        this.collectionType = collectionType;
    }

    public String getQuestionFirebaseId() {
        return questionFirebaseId;
    }

    public void setQuestionFirebaseId(String questionFirebaseId) {
        this.questionFirebaseId = questionFirebaseId;
    }


    public void setQuestionDocumentId(String questionDocumentId) {
        this.questionDocumentId = questionDocumentId;
    }

    public String getQuestionDocumentId() {
        return questionDocumentId;
    }

    public String getQuestionAuthor() { // this is what appears in firebase!!!
        return questionAuthor;
    }

    public void setQuestionAuthor(String questionAuthor) {
        this.questionAuthor = questionAuthor;
    }


    public String getQuestion() {

        return question;
    }

    public void setQuestion(String question) {

        this.question = question;
    }
    public List<Answer> getAnswers() {

        return answers;
    }

    public void setAnswer(String answer) {

        this.answers = answers;
    }

    public int getQuestionScore() {

        return questionScore;
    }

    public List<String> getQuestionTags() {
        return questionTags;
    }

        public String getQuestionTimestamp() {

        return questionTimestamp;
    }

    public void setQuestionTimestamp(String questionTimestamp) {

        this.questionTimestamp = questionTimestamp;
    }

    public String getCollectionType() {
        return collectionType;
    }
}
