package com.btandjaja.www.bakingrecipes.data;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private static String mRecipeName;
    private static ArrayList<String> mIngredients, mSteps;

    public void Recipe(String recipeName, List<String> ingredients) {
        mRecipeName = recipeName;
        mIngredients = new ArrayList<String>();
        mSteps = new ArrayList<String>();
    }

    public void setRecipeName(String recipeName) { mRecipeName = recipeName; }
    public void setIngredients(String ingredient) { mIngredients.add(ingredient); }
    public void setStep(String step) { mSteps.add(step); }

    public String getRecipeName() { return mRecipeName; }
    public String getIngredients() { return stringCombineHelper(mIngredients); }

    private String stringCombineHelper(ArrayList<String> stringList) {
        return "testing";
    }
}
