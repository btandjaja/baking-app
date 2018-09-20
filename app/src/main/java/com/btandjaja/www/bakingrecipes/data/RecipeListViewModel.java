package com.btandjaja.www.bakingrecipes.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

public class RecipeListViewModel extends AndroidViewModel {

    private LiveData<List<RecipeEntry>> recipeEntries;
    private RecipeDatabase mDb;
    private static final int QUERY_INSERT = 1, QUERY_DELETE = 2;

    public RecipeListViewModel(@NonNull Application application) {
        super(application);
        mDb = RecipeDatabase.getsInstance(this.getApplication());
        recipeEntries = mDb.recipeDao().loadAllRecipes();
    }

    public LiveData<List<RecipeEntry>> getRecipeEntries() {
        return recipeEntries;
    }

    public void deleteAll() {
        new DatabaseAccess(mDb, QUERY_DELETE).execute();
    }

    public void insertRecipe(RecipeEntry recipeEntry) { new DatabaseAccess(mDb, QUERY_INSERT).execute(recipeEntry); }

    private static class DatabaseAccess extends AsyncTask<RecipeEntry, Void, Void> {
        private RecipeDatabase mDb;
        private int QUERY_TYPE;
        public DatabaseAccess(RecipeDatabase db, int type) {
            mDb = db;
            QUERY_TYPE = type;
        }

        @Override
        protected Void doInBackground(RecipeEntry... recipeEntries) {
            switch(QUERY_TYPE) {
                case QUERY_DELETE:
                    mDb.recipeDao().deleteAll();
                    break;
                case QUERY_INSERT:
                    mDb.recipeDao().insertRecipe(recipeEntries[0]);
                    break;
            }
            return null;
        }
    }

//    private static class InsertEntry extends AsyncTask<RecipeEntry, Void, Void> {
//        private RecipeDatabase mDb;
//        public InsertEntry(RecipeDatabase db) {
//            mDb = db;
//        }
//
//        @Override
//        protected Void doInBackground(RecipeEntry... recipeEntries) {
//            mDb.recipeDao().insertRecipe(recipeEntries[0]);
//            return null;
//        }
//    }
}
