package com.btandjaja.www.bakingrecipes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.btandjaja.www.bakingrecipes.data.RecipeContract.RecipeEntry;

public class RecipeDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "listDb.db";
    private static final int VERSION = 1;

    RecipeDbHelper(Context context) { super(context, DATABASE_NAME, null, VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE "  + RecipeEntry.TABLE_NAME + " (" +
                RecipeEntry._ID                      + " INTEGER PRIMARY KEY, " +
                RecipeEntry.COLUMN_RECIPE_NAME       + " TEXT NOT NULL, " +
                RecipeEntry.COLUMN_ARRAYLIST_INDEX   + " INTEGER NOT NULL);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RecipeEntry.TABLE_NAME);
        onCreate(db);
    }
}
