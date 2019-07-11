package com.example.stairmaster.models;

import android.os.health.TimerStat;

import com.example.stairmaster.dummy.DummyContent;
import com.google.firebase.Timestamp;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Answer {

//    public static final List<Answer> ITEMS = new ArrayList<DummyContent.DummyItem>();
//    public static final List<DummyContent.DummyItem> ITEMS = new ArrayList<DummyContent.DummyItem>();


    private String answer; //TODO: Change answers to list
    private String comment; // TODO: Change comments to list
    private String timestamp;
    private int score;
    private String author;
    private String answerFirebaseId; // not sure if this is necessary;
    private String parentQuestionId;


    public Answer(String answer, String timeStamp, String author, String parentQuestionId, int score) {

        this.answer = answer;
        this.timestamp = timeStamp;
        this.score = score; //TODO: figure out how to add score.
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        score = score;
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
