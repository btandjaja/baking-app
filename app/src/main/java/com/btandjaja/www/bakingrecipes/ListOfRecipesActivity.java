package com.btandjaja.www.bakingrecipes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Intent;
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
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.btandjaja.www.bakingrecipes.data.AppExecutors;
import com.btandjaja.www.bakingrecipes.data.Recipe;
import com.btandjaja.www.bakingrecipes.data.RecipeDatabase;
import com.btandjaja.www.bakingrecipes.data.RecipeEntry;
import com.btandjaja.www.bakingrecipes.data.RecipeListViewModel;
import com.btandjaja.www.bakingrecipes.data.RecipesAdapter;
import com.btandjaja.www.bakingrecipes.utilities.NetworkUtils;
import com.btandjaja.www.bakingrecipes.utilities.RecipesUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListOfRecipesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, RecipesAdapter.RecipeAdapterOnClickHandler {
    @BindView(R.id.tv_error_main_activity)
    TextView mError;
    @BindView(R.id.pb_view)
    ProgressBar mIndicator;
    @BindView(R.id.rv_recipe_list)
    RecyclerView mRecyclerView;

    private URL mUrl;
    private RecipesAdapter mRecipeAdapter;
    private ArrayList<Recipe> mRecipesList;
    public static boolean mTabletMode;

    // database variable
    private RecipeDatabase mDb;
    private RecipeListViewModel mRecipeListViewModel;

    //TODO remove
    private String TAG = "*******";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_recipes);
        ButterKnife.bind(this);
        // connect to the database
        mDb = RecipeDatabase.getsInstance(getApplicationContext());
        insertRecipesToEmptyDatabase();
        if (savedInstanceState == null) {
            mTabletMode = findViewById(R.id.rl_tablet_mode) != null;
            initializeValriable();
            createAdapter();
            setRecyclerView();
            loadRecipeData();
            getSupportLoaderManager().initLoader(queryLoader(), null, this);
        }
    }

    private void insertRecipesToEmptyDatabase() {
        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        mRecipeListViewModel.getRecipeEntries().observe(this, new Observer<List<RecipeEntry>>() {
            @Override
            public void onChanged(@Nullable List<RecipeEntry> recipeEntries) {
                for (RecipeEntry entry : recipeEntries) {
                    Log.d(TAG, entry.getRecipeName() + ": " + String.valueOf(entry.getArrayListIndex()));
                }
            }
        });
        mRecipeListViewModel.deleteAll();
    }

    private void initializeValriable() {
        mRecipesList = new ArrayList<>();
    }

    private void createAdapter() {
        mRecipeAdapter = new RecipesAdapter(this);
    }

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
        if (loaderManager.getLoader(queryLoader()) == null) {
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
                if (args == null) return;
                showIndicator();
                forceLoad();
            }

            @Nullable
            @Override
            public String loadInBackground() {
                return RecipesUtils.getRecipesListJsonString(args.getString(queryString()));
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
        // to prevent from stacking the same recipes when maneuvering between activies
        if (mRecipesList.size() == 0) RecipesUtils.getRecipesList(this, jsonString, mRecipesList);
        // Add recipe to database if it's not already on database
        addRecipeToDb();
        setAdapter();
        showData();
    }

    /**
     * Needed, but not used.
     *
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
            final Recipe recipe = mRecipesList.get(i);
            final RecipeEntry newEntry = new RecipeEntry(recipe.getRecipeName(), i);
            Log.d(TAG, newEntry.getRecipeName() + String.valueOf(newEntry.getArrayListIndex()));
            mRecipeListViewModel.insertRecipe(newEntry);
//            AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                @Override
//                public void run() {
//                        mDb.recipeDao().insertRecipe(newEntry);
//                }
//            });
        }
        // TODO remove
        checkDb();
    }

    // TODO remove
    private void checkDb() {
//        LiveData<List<RecipeEntry>> entries = mDb.recipeDao().loadAllRecipes();
//        List<RecipeEntry> recipeEntries = entries.getValue();
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                List<RecipeEntry> data = mDb.recipeDao().loadAllRecipes().getValue();
//                showInsideDb(data);
//            }
//        });
    }

    // TODO remove
    private void showInsideDb(List<RecipeEntry> entries) {
        for(RecipeEntry entry : entries) {
            Log.d(TAG, String.valueOf(entry.getId()) + ": " + entry.getRecipeName() + ": " + String.valueOf(entry.getArrayListIndex()) );
        }
    }

    private ContentValues createContentValue(String recipeName, int index) {

        return null;
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

    private int queryLoader() {
        return Integer.parseInt(getString(R.string.query_loader));
    }

    private String queryString() {
        return getString(R.string.query);
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Recipe.RECIPE, recipe);
        startActivity(intent);
    }
}
