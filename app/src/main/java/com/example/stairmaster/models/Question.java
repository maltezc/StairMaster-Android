package com.example.stairmaster.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class Question {

//    private String title;
    private String question;
//    private String answer; //TODO: Change answers to list
    List<Answer> answers;
    private String comment; // TODO: Change comments to list
    private int priority;
    private String timestamp; //TODO: add timestamp for when question was created and then revised.
    List<String> tags;
    private String documentID;
    private String author;
    private String questionFirebaseId;


    public Question() {
        // public no-arg constructor needed
    }

    public Question(String question, int priority, List<String> tags, String author, String timestamp){
//    public Question(String question, List<Answer> answers, int priority, List<String> tags, String author){
        this.question = question;
        this.answers = answers;
        this.priority = priority;
        this.tags = tags;
        this.author = author;
        this.timestamp = timestamp;
//        this.questionFirebaseId = questionFirebaseId; //TODO: It would be nice to figure this out.
    }

    public String getQuestionFirebaseId() {
        return questionFirebaseId;
    }

    public void setQuestionFirebaseId(String questionFirebaseId) {
        this.questionFirebaseId = questionFirebaseId;
    }


    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getDocumentID() {
        return documentID;
    }

    public String getAuthor() { // this is what appears in firebase!!!
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public int getPriority() {

        return priority;
    }

    public List<String> getTags() {
        return tags;
    }

        public String getTimestamp() {

        return timestamp;
    }

    public void setTimestamp(String timestamp) {

        this.timestamp = timestamp;
    }

}
