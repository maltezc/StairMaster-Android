package com.example.stairmaster.models;

import android.os.health.TimerStat;

import com.google.firebase.Timestamp;

import java.sql.Time;

public class Answer {

    private String answer; //TODO: Change answers to list
    private String comment; // TODO: Change comments to list
    private Timestamp timestamp;
    private int Score;
    private String author;

    public Answer(String answer, Timestamp timeStamp, int score, String author) {

        this.answer = answer;
        this.timestamp = timeStamp;
//        this.score; //TODO: figure out how to add score.
        this.author = author;


    }

    public Answer () {
        // public no-arg constructor needed
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
