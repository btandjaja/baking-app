package com.btandjaja.www.bakingrecipes.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

public class AddRecipeViewModel extends ViewModel {
    private LiveData<RecipeEntry> recipe;

    public AddRecipeViewModel(RecipeDatabase db, String recipeName) {
        recipe = db.recipeDao().loadRecipeByName(recipeName);
    }
    public LiveData<RecipeEntry> getRecipe() {
        return recipe;
    }
}
