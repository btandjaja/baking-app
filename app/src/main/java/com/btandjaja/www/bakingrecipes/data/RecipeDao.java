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
    @Query("SELECT * FROM recipelist")
    LiveData<List<RecipeEntry>> loadAllRecipes();

    @Query("SELECT * FROM recipelist WHERE mRecipeName = :recipeName")
    LiveData<RecipeEntry> loadRecipeByName(String recipeName);

    // TODO think about it: pick specific step, do I need this?
    @Query("SELECT * FROM recipelist WHERE mStepNum = :stepNum")
    LiveData<RecipeEntry> loadRecipeByArrListIndex(int stepNum);

    @Query("DELETE FROM recipelist")
    void deleteAll();

    @Insert
    void insertRecipe(RecipeEntry recipeEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(RecipeEntry recipeEntry);

    @Delete
    void deleteRecipe(RecipeEntry recipeEntry);
}
