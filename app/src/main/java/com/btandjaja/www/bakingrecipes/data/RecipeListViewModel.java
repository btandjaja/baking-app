package com.btandjaja.www.bakingrecipes.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
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
        new DeleteAll(mDb).execute();
    }

    private static class DeleteAll extends AsyncTask<Void, Void, Void> {
        private RecipeDatabase mDb;
        public DeleteAll(RecipeDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDb.recipeDao().deleteAll();
            return null;
        }
    }
}
