package com.example.hoh.model;

public class Favorite {

    private Integer recipeId;

    private Integer userId;

    public Favorite(Integer recipeId, Integer userId) {
        this.recipeId = recipeId;
        this.userId = userId;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
