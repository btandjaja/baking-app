package com.btandjaja.www.bakingrecipes.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class RecipeListViewModel extends AndroidViewModel {

    private LiveData<List<RecipeEntry>> recipeEntries;

    public RecipeListViewModel(@NonNull Application application) {
        super(application);
        RecipeDatabase db = RecipeDatabase.getsInstance(this.getApplication());
        recipeEntries = db.recipeDao().loadAllRecipes();
    }

    public LiveData<List<RecipeEntry>> getRecipeEntries() {
        return recipeEntries;
    }
}
