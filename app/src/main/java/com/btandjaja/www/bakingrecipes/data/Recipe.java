package com.btandjaja.www.bakingrecipes.data;

import android.text.TextUtils;

import java.util.ArrayList;

public class Recipe {
    private int mStep, mServings;
    private String mRecipeName, mImagePath;
    private ArrayList<String> mIngredients, mShortDescription, mDescription, mVideoUrl, mThumbnailUrl;

    public Recipe() {
        mRecipeName = null;
        constructorHelper();
    }

    public Recipe(String recipeName) {
        mRecipeName = recipeName;
        constructorHelper();
    }

    /* setter */
    public void setServings(int serving) { mServings = serving; }
    public void setRecipeName(String recipeName) { mRecipeName = recipeName; }
    public void setImagePath(String imagePath) { mImagePath = checkValidString(imagePath); }
    public void setIngredient(String ingredient) { mIngredients.add(ingredient); }
    public void setIngredients(ArrayList<String> ingredients) { mIngredients = ingredients; }
    public void setShortDescription(String shortDescription) { mShortDescription.add(shortDescription); }
    public void setDescription(String description) { mDescription.add(description); }
    public void setVideoUrl(String videoUrl) { mVideoUrl.add(checkValidString(videoUrl)); }
    public void setThumbnailUrl(String thumbnailUrl) { mThumbnailUrl.add(checkValidString(thumbnailUrl)); }

    /* getter */
    public int getServings() { return mServings; }
    public String getRecipeName() { return mRecipeName; }
    public String getImagePath() { return mImagePath; }
    public String getIngredientsString() { return stringCombineHelper(mIngredients); }
    public ArrayList<String> getIngredientsArrList() { return mIngredients; }
    public String getShortDescription() { return mShortDescription.get(mStep); }
    public String getDescription() { return mDescription.get(mStep); }
    public String getVideoUrl() { return mVideoUrl.get(mStep); }
    public String getThumbnailUrl() { return mThumbnailUrl.get(mStep); }

    /* helper methods */
    public static Recipe copyRecipe(Recipe recipe) {
        Recipe recipeCopy = new Recipe();
        recipeCopy.setServings(recipe.getServings());
        recipeCopy.setRecipeName(recipe.getRecipeName());
        recipeCopy.setImagePath(recipe.getImagePath());
        recipeCopy.setIngredients(recipe.getIngredientsArrList());
        recipeCopy.setShortDescription(recipe.getShortDescription());
        recipeCopy.setDescription(recipe.getDescription());
        recipeCopy.setVideoUrl(recipe.getVideoUrl());
        recipeCopy.setThumbnailUrl(recipe.getThumbnailUrl());
        return recipeCopy;
    }

    public void nextStep() { mStep++; }

    private String stringCombineHelper(ArrayList<String> stringList) {
        String result = "";
        for (int i = 0; i < stringList.size(); i++) {
            result += stringList.get(i);
            if ( i < stringList.size() - 1 ) {
                result += "\n";
            }
        }
        return result;
    }

    private String checkValidString(String validString) {
        if (validString == null || TextUtils.isEmpty(validString)) return null;
        return validString;
    }

    private void constructorHelper() {
        mStep = 0; mServings= 0;
        mImagePath = null;
        mIngredients = new ArrayList<>();
        mShortDescription = new ArrayList<>();
        mDescription = new ArrayList<>();
        mVideoUrl = new ArrayList<>();
        mThumbnailUrl = new ArrayList<>();
    }
}
