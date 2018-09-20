package com.btandjaja.www.bakingrecipes.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {RecipeEntry.class}, version = 3, exportSchema = false )
public abstract class RecipeDatabase extends RoomDatabase {
    private static final String LOG_TAG = RecipeDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "recipeList";
    private static RecipeDatabase sInstance;

    public static RecipeDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        RecipeDatabase.class,
                        RecipeDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract RecipeDao recipeDao();
}
