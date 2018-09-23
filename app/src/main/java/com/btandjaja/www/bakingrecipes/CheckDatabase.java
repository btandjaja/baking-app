package com.btandjaja.www.bakingrecipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Delete;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.btandjaja.www.bakingrecipes.data.RecipeEntry;
import com.btandjaja.www.bakingrecipes.data.RecipeListViewModel;

import java.util.List;


public class CheckDatabase extends AppCompatActivity {

    private RecipeListViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_database);
        mViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        mViewModel.getRecipeEntries().observe(this, new Observer<List<RecipeEntry>>() {
            @Override
            public void onChanged(@Nullable List<RecipeEntry> recipeEntries) {
                Log.d("*****count", String.valueOf(mViewModel.count()));
            }
        });
        Log.d("*****count", String.valueOf(mViewModel.count()));
        Intent intent = mViewModel.count() <= 0 ? new Intent(this, ListOfRecipesActivity.class)
                : new Intent(this, DetailActivity.class);
        Bundle extras = new Bundle();
        extras.putBoolean(getResources().getString(R.string.click_from_widget), true);
        intent.putExtras(extras);

        startActivity(intent);
    }
}
