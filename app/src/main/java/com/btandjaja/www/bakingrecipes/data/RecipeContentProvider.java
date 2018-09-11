package com.btandjaja.www.bakingrecipes.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.btandjaja.www.bakingrecipes.data.RecipeContract.RecipeEntry;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.Arrays;

public class RecipeContentProvider extends ContentProvider {
    // Define final integer constants for the directory of recipes and a single item.
    // It's convention to use 100, 200, 300, etc for directories,
    // and related ints (101, 102, ..) for items in that directory.
    public static final int RECIPE_LIST = 100;
    public static final int RECIPE_LIST_WITH_ID = 101;

    // CDeclare a static variable for the Uri matcher that you construct
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    // Define a static buildUriMatcher method that associates URI's with their int match

    /**
     * Initialize a new matcher object without any matches,
     * then use .addURI(String authority, String path, int match) to add matches
     */
    public static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        /*
          All paths added to the UriMatcher have a corresponding int.
          For each kind of uri you may want to access, add the corresponding match with addURI.
          The two calls below add matches for the task directory and a single item by ID.
         */
        uriMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.PATH_RECIPE_LIST, RECIPE_LIST);
        uriMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.PATH_RECIPE_LIST + "/*", RECIPE_LIST_WITH_ID);

        return uriMatcher;
    }

    private RecipeDbHelper mRecipeDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mRecipeDbHelper = new RecipeDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mRecipeDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor returnCursor;
        switch (match) {
            case RECIPE_LIST:
                returnCursor = db.query(RecipeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case RECIPE_LIST_WITH_ID:
                String id = uri.getLastPathSegment();
                String mSelection = RecipeContract.RecipeEntry.COLUMN_ARRAYLIST_INDEX + "=?";
                String[] mSelectionArg = new String[]{id};
                returnCursor = db.query(RecipeEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArg,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case RECIPE_LIST_WITH_ID:
                long id = db.insert(RecipeEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(uri, id);
                } else {
                    throw new SQLiteException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowDeleted;
        switch (match) {
            case RECIPE_LIST_WITH_ID:
                rowDeleted = db.delete(RecipeEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        if (rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mRecipeDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int recipeListUpdated;
        switch (match) {
            case RECIPE_LIST:
                recipeListUpdated = db.update(RecipeEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case RECIPE_LIST_WITH_ID:
                if (selection == null) selection = RecipeEntry._ID + "=?";
                else selection += " AND " + RecipeEntry._ID + "=?";
                String id = uri.getPathSegments().get(1);
                if (selectionArgs == null) selectionArgs = new String[] {id};
                else {
                    ArrayList<String> selectionArgsList = new ArrayList<>();
                    selectionArgsList.addAll(Arrays.asList(selectionArgs));
                    selectionArgsList.add(id);
                    selectionArgs = selectionArgsList.toArray(new String[selectionArgsList.size()]);
                }
                recipeListUpdated = db.update(RecipeEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (recipeListUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return 0;
    }
}
