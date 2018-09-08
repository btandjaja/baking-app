package com.btandjaja.www.bakingrecipes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BakingDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recipe_list.db";
    private static final int DATABASE_VERSION = 1;

    public BakingDbHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + BakingContract.BakingEntry.TABLE_NAME + " (" +
                BakingContract.BakingEntry._ID + " INTEGER PRIMARY KEY, " +
                BakingContract.BakingEntry.COLUMN_RECIPE_NAME + " TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BakingContract.BakingEntry.TABLE_NAME);
        onCreate(db);
    }
}
