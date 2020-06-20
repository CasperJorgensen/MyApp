package com.example.casper.myapp;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Recipe {
    private String author;
    private String title;
    private String meal;
    private int numberOfServings;
//    private List<String> ingredients;
    private List<String> steps;
    private Date dateCreated;
    private Date dateEdited;
    private int rating;
    private String picturePath;
    private Map<String, List<String>> ingredients;

    public Recipe(String author, String courseName, String meal, int numberOfServings, List<String> steps, Date dateCreated, String picturePath, Map<String, List<String>> ingredients) {
        this.author = author;
        this.title = courseName;
        this.meal = meal;
        this.numberOfServings = numberOfServings;
//        this.ingredients = ingredients;
        this.steps = steps;
        this.dateCreated = dateCreated;
        this.picturePath = picturePath;
        this.ingredients = ingredients;
    }

    public Recipe() {
        this.title = title;
        this.numberOfServings = numberOfServings;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe(String userName, String toString, String toString1, int parseInt, List<String> steps, Date date, Map<String, List<String>> ingredientsList) {

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
