package com.example.stairmaster.models;

import android.net.Uri;

import java.util.List;

import androidx.databinding.BaseObservable;

public class User extends BaseObservable {


    public int id;
    public String firstName;
    public String lastName;
    public String userName;
    public String email;
    public List<Question> questions;
    public Uri userImageURI;


    public User() {
        // no arg constructor needed
    }

    public User(String firstName, String lastName, String userName, String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.questions = questions;
        this.userImageURI = userImageURI;

    }


    public Uri getUserImageURI() {
        return userImageURI;
    }

    public void setUserImageURI(Uri userImageURI) {
        this.userImageURI = userImageURI;
    }


    public String getUserName(String userName) { return userName;}


    public void setUserName(String userName) { this.userName = userName; }

    public String getFirstName(String firstName) {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName(String lastName) {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail(String email) {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

//    public Uri getUserImageURI() {
//        return userImageURI;
//    }

//    public void setUserImageURI(Uri userImageURI) {
//        this.userImageURI = userImageURI;
//    }
}



