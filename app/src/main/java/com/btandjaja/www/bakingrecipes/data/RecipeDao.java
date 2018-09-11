package com.btandjaja.www.bakingrecipes.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipelist ORDER BY arrayListIndex")
    LiveData<List<RecipeEntry>> loadAllRecipes();

    @Query("SELECT * FROM recipelist WHERE id = :id")
    LiveData<RecipeEntry> loadRecipeById(int id);

    @Insert
    void insertRecipe(RecipeEntry recipeEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(RecipeEntry recipeEntry);

    @Delete
    void deleteRecipe(RecipeEntry recipeEntry);
}
