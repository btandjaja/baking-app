package com.btandjaja.www.bakingrecipes.data;

import java.util.ArrayList;

public class Recipe {
    private static String mRecipeName, mImagePath;
    private static ArrayList<String> mIngredients, mSteps;

    public Recipe(String recipeName, String imagePath) {
        mRecipeName = recipeName;
        mImagePath = imagePath;
        mIngredients = new ArrayList<String>();
        mSteps = new ArrayList<String>();
    }

    public Recipe(String recipeName, String imagePath, ArrayList<String> ingredients, ArrayList<String> steps) {
        mRecipeName = recipeName;
        mImagePath = imagePath;
        mIngredients = ingredients;
        mSteps = steps;
    }

    public void setRecipeName(String recipeName) { mRecipeName = recipeName; }
    public void setIngredients(String ingredient) { mIngredients.add(ingredient); }
    public void setStep(String step) { mSteps.add(step); }
    public void setImagePath(String imagePath) { mImagePath = imagePath; }

    public String getRecipeName() { return mRecipeName; }
    public String getIngredients() { return stringCombineHelper(mIngredients); }
    public String getImagePath() { return mImagePath; }
    public ArrayList<String> getIngredientsArrList() { return mIngredients; }
    public ArrayList<String> getSteps() { return mSteps; }

    private String stringCombineHelper(ArrayList<String> stringList) {
        // TODO need to combine the recipes into a step by step sentence
        return "testing";
    }

    public static Recipe copyRecipe(Recipe recipe) {
        return new Recipe(recipe.getRecipeName(), recipe.getImagePath(),
                recipe.getIngredientsArrList(), recipe.getSteps());
    }
}
