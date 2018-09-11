package com.btandjaja.www.bakingrecipes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.btandjaja.www.bakingrecipes.data.BakingContract.BakingEntry;
public class BakingDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recipe.db";
    private static final int VERSION = 6;

    public BakingDbHelper(Context context) { super(context, DATABASE_NAME, null, VERSION);  }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + BakingEntry.TABLE_NAME + " (" +
                BakingEntry._ID                     + " INTEGER PRIMARY KEY, " +
                BakingEntry.COLUMN_RECIPE_NAME      + " TEXT NOT NULL, " +
                BakingEntry.COLUMN_ARRAYLIST_INDEX  + " INTEGER NOT NULL);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BakingEntry.TABLE_NAME);
        onCreate(db);
    }
}
