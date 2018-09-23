package com.btandjaja.www.bakingrecipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        final Intent intentNoData = new Intent(this, ListOfRecipesActivity.class);
        final Intent intentWithData = new Intent(this, DetailActivity.class);
        mViewModel.getRecipeEntries().observe(this, new Observer<List<RecipeEntry>>() {
            @Override
            public void onChanged(@Nullable List<RecipeEntry> recipeEntries) {
                if (recipeEntries.size() > 0)
                    startActivity(intentWithData);
                else startActivity(intentNoData);
            }
        });
        Bundle extras = new Bundle();
        extras.putBoolean(getResources().getString(R.string.click_from_widget), true);
        intentNoData.putExtras(extras);
        intentWithData.putExtras(extras);
    }
}
