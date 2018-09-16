package com.btandjaja.www.bakingrecipes.data;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class AddRecipeViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final RecipeDatabase mDb;
    private final String mRecipeName;

    public AddRecipeViewModelFactory(RecipeDatabase db, String recipeName) {
        mDb = db; mRecipeName = recipeName;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddRecipeViewModel(mDb, mRecipeName);
    }
}
