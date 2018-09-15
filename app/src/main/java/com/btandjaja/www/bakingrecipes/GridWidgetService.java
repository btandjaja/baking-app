package com.btandjaja.www.bakingrecipes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.btandjaja.www.bakingrecipes.data.RecipeDatabase;
import com.btandjaja.www.bakingrecipes.data.RecipeEntry;
import com.btandjaja.www.bakingrecipes.data.RecipeListViewModel;

import java.util.List;

public class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}
// same as adapter
class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    // database variable
    RecipeDatabase mDb;
    LiveData<List<RecipeEntry>> mRecipeEntries;

    public GridRemoteViewsFactory(Context applicationContext) { mContext = applicationContext; }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        // TODO retrieve data from db
        mRecipeEntries = mDb.recipeDao().loadAllRecipes();

    }

    @Override
    public void onDestroy() { }

    @Override
    public int getCount() {
        return mRecipeEntries == null ? 0 : mRecipeEntries.getValue().size();
    }

    // same as onBindViewHolder
    @Override
    public RemoteViews getViewAt(int position) {
        LiveData<RecipeEntry> recipeEntry = mDb.recipeDao().loadRecipeById(position);
        // TODO confused
//        RecipeListViewModel vm = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        return null;
    }
/**************/
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
