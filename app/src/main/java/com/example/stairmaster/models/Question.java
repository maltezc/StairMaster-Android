package com.example.stairmaster.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Question {

//    private String title;
    private String question;
    private String answer;
    private int priority;
//    private String timestamp;
    List<String> tags;
    private String documentID;
    private String author;


    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getDocumentID() {
        return documentID;
    }

    public Question() {
        // public no-arg constructor needed
    }

    public Question(String question, String answer, int priority, List<String> tags, String author){
        this.question = question;
        this.answer = answer;
        this.priority = priority;
        this.tags = tags;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    //    public Question(String title, String question, String answer, String timestamp) {
//        this.title = title;
//        this.question = question;
//        this.answer = answer;
//        this.timestamp = timestamp;
//    }

//    protected Question(Parcel in) {
//        title = in.readString();
//        question = in.readString();
//        answer = in.readString();
//        timestamp = in.readString();
//    }

//    public static final Creator<Question> CREATOR = new Creator<Question>() {
//        @Override
//        public Question createFromParcel(Parcel in) {
//            return new Question(in);
//        }
//
//        @Override
//        public Question[] newArray(int size) {
//            return new Question[size];
//        }
//    };

//    public String getTitle() {
//
//        return title;
//    }
//
//    public void setTitle(String title) {
//
//        this.title = title;
//    }

    public String getQuestion() {

        return question;
    }

    public void setQuestion(String question) {

        this.question = question;
    }
    public String getAnswer() {

        return answer;
    }

    public void setAnswer(String answer) {

        this.answer = answer;
    }

    public int getPriority() {

        return priority;
    }



    public List<String> getTags() {
        return tags;
    }

    //    public String getTimestamp() {
//
//        return timestamp;
//    }
//
//    public void setTimestamp(String timestamp) {
//
//        this.timestamp = timestamp;
//    }

//    @Override
//    public String toString() {
//        return "Question{" +
////                "title='" + title + '\'' +
//                ", question='" + question + '\'' +
//                ", answer='" + answer + '\'' +
////                ", timestamp='" + timestamp + '\'' +
//                '}';
//    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }

//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(title);
//        dest.writeString(question);
//        dest.writeString(answer);
//        dest.writeString(timestamp);
//    }
}
