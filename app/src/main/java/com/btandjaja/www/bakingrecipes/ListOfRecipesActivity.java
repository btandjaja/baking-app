package com.btandjaja.www.bakingrecipes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.btandjaja.www.bakingrecipes.data.BakingContract;
import com.btandjaja.www.bakingrecipes.data.Recipe;
import com.btandjaja.www.bakingrecipes.data.RecipesAdapter;
import com.btandjaja.www.bakingrecipes.utilities.NetworkUtils;
import com.btandjaja.www.bakingrecipes.utilities.RecipesUtils;
import com.btandjaja.www.bakingrecipes.data.BakingContract.BakingEntry;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListOfRecipesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>,RecipesAdapter.RecipeAdapterOnClickHandler {
    @BindView(R.id.tv_error_main_activity) TextView mError;
    @BindView(R.id.pb_view) ProgressBar mIndicator;
    @BindView(R.id.rv_recipe_list) RecyclerView mRecyclerView;

    private URL mUrl;
    private RecipesAdapter mRecipeAdapter;
    private ArrayList<Recipe> mRecipesList;
    public static boolean mTabletMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_recipes);
        Intent intentParent = getParentActivityIntent();
        if (intentParent == null) {
            ButterKnife.bind(this);
            if (savedInstanceState == null) {
                mTabletMode = findViewById(R.id.rl_tablet_mode) != null;
                initializeValriable();
                createAdapter();
                setRecyclerView();
                loadRecipeData();
                getSupportLoaderManager().initLoader(queryLoader(), null, this);
            }
        } else {

        }
    }

    private void initializeValriable() {
        mRecipesList = new ArrayList<>();
    }
    private void createAdapter() { mRecipeAdapter = new RecipesAdapter(this); }

    private void setRecyclerView() {
        // Default gridView count
        int spanCount = Integer.valueOf(getString(R.string.default_gridView_1));
        if (mTabletMode) spanCount = Integer.valueOf(getString(R.string.gridView_3));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        mRecyclerView.setAdapter(mRecipeAdapter);
    }

    private void loadRecipeData() {
        showData();
        restartLoader();
    }

    private void restartLoader() {
        mUrl = NetworkUtils.buildUrl(this);
        Bundle bundle = new Bundle();
        bundle.putString(queryString(), mUrl.toString());
        LoaderManager loaderManager = getSupportLoaderManager();
        if(loaderManager.getLoader(queryLoader()) == null) {
            loaderManager.initLoader(queryLoader(), bundle, this);
        } else {
            loaderManager.restartLoader(queryLoader(), bundle, this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(queryString(), mUrl.toString());
        outState.putParcelableArrayList(Recipe.RECIPE, mRecipesList);
    }

    /*
     * Get data with network
     */
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable final Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if ( args == null ) return;
                showIndicator();
                forceLoad();
            }

            @Nullable
            @Override
            public String loadInBackground() {
                return RecipesUtils.getRecipesListJsonString( args.getString(queryString()) );
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String jsonString) {
        hideIndicator();
        if (jsonString == null || TextUtils.isEmpty(jsonString)) {
            showError();
            return;
        }
        if (mRecipesList.size() == 0) RecipesUtils.getRecipesList(this, jsonString, mRecipesList);
        // Add recipe to database if it's not already on database
        addRecipeToDb();
        setAdapter();
        showData();
    }

    /**
     * Needed, but not used.
     * @param loader
     */
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    /**
     * Add recipe to database if it's not in database
     */
    private void addRecipeToDb() {
        for (int i = 0; i < mRecipesList.size(); i++) {
            Recipe recipe = mRecipesList.get(i);
            String selection = BakingEntry.COLUMN_ARRAYLIST_INDEX + "=?";
            String[] selectionArgs = new String[]{String.valueOf(i)};

            Cursor cursor = getContentResolver().query(BakingEntry.CONTENT_URI,
                    null,
                    selection,
                    selectionArgs,
                    null);
            // item to be add/update to db
            ContentValues cv = createContentValue(recipe.getRecipeName(), i);
            // Uri of contentResolver
            Uri uri = BakingEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(i)).build();
            if (cursor == null) {
                getContentResolver().insert(uri, cv);
            } else {
                getContentResolver().update(uri, cv, selection, selectionArgs);
            }
        }

        //TODO check table, remove
        checkDB();
    }

    //TODO remove
    private void checkDB() {
        Cursor cursor = getContentResolver().query(BakingContract.BakingEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        int count = cursor.getCount();
        while(cursor.moveToNext()) {
            int columnName = cursor.getColumnIndex(BakingEntry.COLUMN_RECIPE_NAME);
            String recipeName = cursor.getColumnName(columnName);
            int columnIndex = cursor.getColumnIndex(BakingEntry.COLUMN_ARRAYLIST_INDEX);
            String arrListIndex = cursor.getColumnName(columnIndex);
            Toast.makeText(this, recipeName + " " + arrListIndex, Toast.LENGTH_LONG).show();
        }
        cursor.close();
    }

    private ContentValues createContentValue(String recipeName, int index) {
        ContentValues cv = new ContentValues();
        cv.put(BakingEntry.COLUMN_RECIPE_NAME, recipeName);
        cv.put(BakingEntry.COLUMN_ARRAYLIST_INDEX, index);
        return cv;
    }

    private void setAdapter() {
        mRecipeAdapter.setRecipeList(this, mRecipesList);
        mRecyclerView.setAdapter(mRecipeAdapter);
    }

    private void showData() {
        mError.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.VISIBLE);
    }

    private void showIndicator() {
        mIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    private void hideIndicator() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mIndicator.setVisibility(View.INVISIBLE);
    }

    private int queryLoader() { return Integer.parseInt(getString(R.string.query_loader)); }
    private String queryString() { return getString(R.string.query); }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Recipe.RECIPE, recipe);
        startActivity(intent);
    }
}
