package com.example.stairmaster.models;

import java.util.List;

public class Question {

//    private String title;
    private String question;
//    private String answer; //TODO: Change answers to list
    List<Answer> answers;
    private String comment; // TODO: Change comments to list
    private int questionPriority;
    private String questionTimestamp; //TODO: add questionTimestamp for when question was created and then revised.
    List<String> tags;
    private String questionDocumentId;
    private String questionAuthor;
    private String questionFirebaseId;


    public Question() {
        // public no-arg constructor needed
    }

    public Question(String question, int questionPriority, List<String> tags, String questionAuthor, String questionTimestamp){
//    public Question(String question, List<Answer> answers, int questionPriority, List<String> tags, String questionAuthor){
        this.question = question;
        this.answers = answers;
        this.questionPriority = questionPriority;
        this.tags = tags;
        this.questionAuthor = questionAuthor;
        this.questionTimestamp = questionTimestamp;
//        this.questionFirebaseId = questionFirebaseId; //TODO: It would be nice to figure this out.
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

    public int getQuestionPriority() {

        return questionPriority;
    }

    public List<String> getTags() {
        return tags;
    }

        public String getQuestionTimestamp() {

        return questionTimestamp;
    }

    public void setQuestionTimestamp(String questionTimestamp) {

        this.questionTimestamp = questionTimestamp;
    }

}
