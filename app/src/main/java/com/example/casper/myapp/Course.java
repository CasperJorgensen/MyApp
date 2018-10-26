package com.example.casper.myapp;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Course {
    private String author;
    private String courseName;
    private String meal;
    private int numberOfServings;
//    private List<String> ingredients;
    private List<String> steps;
    private Date dateCreated;
    private String picturePath;
    private Map<String, List<String>> ingredients;

    public Course(String author, String courseName, String meal, int numberOfServings, List<String> steps, Date dateCreated, String picturePath, Map<String, List<String>> ingredients) {
        this.author = author;
        this.courseName = courseName;
        this.meal = meal;
        this.numberOfServings = numberOfServings;
//        this.ingredients = ingredients;
        this.steps = steps;
        this.dateCreated = dateCreated;
        this.picturePath = picturePath;
        this.ingredients = ingredients;
    }

    public Course() {
        this.courseName = courseName;
        this.numberOfServings = numberOfServings;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Map<String, List<String>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, List<String>> ingredients) {
        this.ingredients = ingredients;
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

//    public List<String> getTestIngredientList() {
//        return ingredients;
//    }
//
//    public void setTestIngredientList(List<String> ingredients) {
//        this.ingredients = ingredients;
//    }

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
