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

import static com.btandjaja.www.bakingrecipes.data.BakingContract.AUTHORITY;
import static com.btandjaja.www.bakingrecipes.data.BakingContract.BakingEntry.COLUMN_ARRAYLIST_INDEX;
import static com.btandjaja.www.bakingrecipes.data.BakingContract.BakingEntry.TABLE_NAME;
import static com.btandjaja.www.bakingrecipes.data.BakingContract.PATH_RECIPE;

public class BakingContentProvider extends ContentProvider {
    public static final int RECIPES = 100;
    public static final int RECIPE_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private BakingDbHelper mBakingDbHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        /* directory */
        uriMatcher.addURI(AUTHORITY, PATH_RECIPE, RECIPES);
        /* single item */
        uriMatcher.addURI(AUTHORITY, PATH_RECIPE + "/#", RECIPE_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mBakingDbHelper = new BakingDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mBakingDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor returnCursor;
        switch(match) {
            case RECIPES:
                returnCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case RECIPE_WITH_ID:
                String id = uri.getLastPathSegment();
                String mSelection = COLUMN_ARRAYLIST_INDEX + "=?";
                String [] mSelectionArgs = new String [] {id};
                returnCursor = db.query(TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) { return null; }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mBakingDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch(match) {
            case RECIPE_WITH_ID:
                long id = db.insert(TABLE_NAME, null, values);
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
        final SQLiteDatabase db = mBakingDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowDeleted;
        switch(match) {
            case RECIPE_WITH_ID:
                rowDeleted = db.delete(TABLE_NAME, selection, selectionArgs);
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
        final SQLiteDatabase db = mBakingDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        int recipeUpdate;
        switch(match) {
            case RECIPE_WITH_ID:
                recipeUpdate = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver of a change and return the number of items updated
        if(recipeUpdate!=0) {
            // A place (or more) was updated, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return recipeUpdate;
    }
}
