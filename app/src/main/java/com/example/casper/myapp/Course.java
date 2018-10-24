package com.example.casper.myapp;

import java.util.Date;
import java.util.List;

public class Course {
    private String author;
    private String courseName;
    private String meal;
    private int numberOfServings;
    private List<String> ingredients;
    private List<String> steps;
    private Date dateCreated;
    private String picturePath;

    public Course(String author, String courseName, String meal, int numberOfServings, List<String> ingredients, List<String> steps, Date dateCreated, String picturePath) {
        this.author = author;
        this.courseName = courseName;
        this.meal = meal;
        this.numberOfServings = numberOfServings;
        this.ingredients = ingredients;
        this.steps = steps;
        this.dateCreated = dateCreated;
        this.picturePath = picturePath;
    }

    public Course() {
        this.courseName = courseName;
        this.numberOfServings = numberOfServings;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public int getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(int numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
