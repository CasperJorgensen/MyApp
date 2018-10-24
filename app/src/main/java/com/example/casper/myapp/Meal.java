package com.example.casper.myapp;

import java.util.List;

public class Meal {
    private List<String> meal;

    public Meal(List<String> meal) {
        this.meal = meal;
    }

    public List<String> getMeal() {
        return meal;
    }

    public void setMeal(List<String> meal) {
        this.meal = meal;
    }
}
