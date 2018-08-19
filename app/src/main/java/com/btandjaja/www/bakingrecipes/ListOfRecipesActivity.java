package com.btandjaja.www.bakingrecipes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.btandjaja.www.bakingrecipes.utilities.NetworkUtils;
import com.btandjaja.www.bakingrecipes.utilities.RecipesUtils;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListOfRecipesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    @BindView(R.id.tv_error_main_activity) TextView mError;
    @BindView(R.id.pb_view) ProgressBar mIndicator;
    @BindView(R.id.rv_recipe_list) RecyclerView mRecyclerView;
    @BindView(R.id.iv_recipe_snapshot) ImageView mRecipeSnapShot;
    @BindView(R.id.tv_brief_recipe_info) TextView mRecipeInfo;

    private URL mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_recipes);
        ButterKnife.bind(this);
        setAdapter();
        setRecyclerView();
        getUrl();
        getSupportLoaderManager().initLoader(queryLoader(), null, this);
    }

    public void setAdapter() { }
    public void setRecyclerView() {

    }
    private void getUrl() { mUrl = NetworkUtils.buildUrl(this); }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(queryString(), mUrl.toString());
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
                mIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Nullable
            @Override
            public String loadInBackground() {
                return RecipesUtils.getRecipesList( args.getString(queryString()) );
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        mIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void showData() {
        mError.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void showError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.VISIBLE);
    }

    private int queryLoader() { return Integer.parseInt(getString(R.string.query_loader)); }
    private String queryString() { return getString(R.string.query); }
}
