package com.example.casper.myapp;

import java.util.Date;

public class User {
    private String uid;
    private String email;
    private int NoOfRecipies;
    private String username;
    private int Followers;
    private int Follows;
    private Date dateCreated;

    public User() {}

    public User(String uid, String email, int noOfRecipies, String username, int followers, int follows, Date dateCreated) {
        this.uid = uid;
        this.email = email;
        NoOfRecipies = noOfRecipies;
        this.username = username;
        Followers = followers;
        Follows = follows;
        this.dateCreated = dateCreated;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNoOfRecipies() {
        return NoOfRecipies;
    }

    public void setNoOfRecipies(int noOfRecipies) {
        NoOfRecipies = noOfRecipies;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFollowers() {
        return Followers;
    }

    public void setFollowers(int followers) {
        Followers = followers;
    }

    public int getFollows() {
        return Follows;
    }

    public void setFollows(int follows) {
        Follows = follows;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
