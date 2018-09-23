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

    @Query("DELETE FROM recipelist")
    void deleteStepOne();

    @Query("SELECT COUNT(*) FROM recipelist")
    int loadCount();

    @Delete
    void deleteAll(RecipeEntry... recipeEntries);

    @Insert
    void insertRecipe(RecipeEntry recipeEntry);
}
