package com.example.casper.myapp;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Course {
    private String author;
    private String courseName;
    private String meal;
    private int numberOfServings;
    private List<String> ingredients;
    private List<String> steps;
    private Date dateCreated;
    private Map<String, String> testMap;

    public Course(String author, String courseName, String meal, int numberOfServings, List<String> ingredients, List<String> steps, Date dateCreated, Map<String, String> testMap) {
        this.author = author;
        this.courseName = courseName;
        this.meal = meal;
        this.numberOfServings = numberOfServings;
        this.ingredients = ingredients;
        this.steps = steps;
        this.dateCreated = dateCreated;
        this.testMap = testMap;
    }

    public Course() {
        this.courseName = courseName;
        this.numberOfServings = numberOfServings;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Map<String, String> getTestMap() {
        return testMap;
    }

    public void setTestMap(Map<String, String> testMap) {
        this.testMap = testMap;
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
