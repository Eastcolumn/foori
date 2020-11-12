package com.example.realkepstone.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FoodAfter implements Serializable {

    @SerializedName("food_korName")
    @Expose
    private List<String> foodKorName  = null;
    @SerializedName("food_engName")
    @Expose
    private List<String> foodEngName = null;
    @SerializedName("food_description")
    @Expose
    private List<String> foodDescription = null;
    @SerializedName("food_ingredients")
    @Expose
    private List<List<String>> foodIngredients = null;
    @SerializedName("food_allergy")
    @Expose
    private List<List<String>> foodAllergy = null;

    public List<String> getFoodKorName() {
        return foodKorName;
    }

    public void setFoodKorName(List<String> foodKorName) {
        this.foodKorName = foodKorName;
    }

    public List<String> getFoodEngName() {
        return foodEngName;
    }

    public void setFoodEngName(List<String> foodEngName) {
        this.foodEngName = foodEngName;
    }

    public List<String> getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(List<String> foodDescription) {
        this.foodDescription = foodDescription;
    }

    public List<List<String>> getFoodIngredients() {
        return foodIngredients;
    }

    public void setFoodIngredients(List<List<String>> foodIngredients) {
        this.foodIngredients = foodIngredients;
    }

    public List<List<String>> getFoodAllergy() {
        return foodAllergy;
    }

    public void setFoodAllergy(List<List<String>> foodAllergy) {
        this.foodAllergy = foodAllergy;
    }

}