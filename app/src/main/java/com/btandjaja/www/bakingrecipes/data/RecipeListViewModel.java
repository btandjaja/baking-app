package com.btandjaja.www.bakingrecipes.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class RecipeListViewModel extends AndroidViewModel {

    private LiveData<List<RecipeEntry>> recipeEntries;
    private RecipeDatabase mDb;

    public RecipeListViewModel(@NonNull Application application) {
        super(application);
        mDb = RecipeDatabase.getsInstance(this.getApplication());
        recipeEntries = mDb.recipeDao().loadAllRecipes();
    }

    public LiveData<List<RecipeEntry>> getRecipeEntries() {
        return recipeEntries;
    }

    public LiveData<RecipeEntry> getRecipeEntry(String recipeName) {
        return mDb.recipeDao().loadRecipeByName(recipeName);
    }
    public void deleteAll() {
        mDb.recipeDao().deleteAll();
    }
}
