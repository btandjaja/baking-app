package com.btandjaja.www.bakingrecipes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
    String[] DEFAULT_RECIPES = new String[] {"Nutella Pie", "Brownies", "Yellow Cake", "Cheesecake"};
    String[] DEFAULT_RECIPES_IMG = new String[] {"nutella_pie", "brownies", "moist_yellow_cake", "cheese_cake"};

    public GridRemoteViewsFactory(Context applicationContext) { mContext = applicationContext; }

    @Override
    public void onCreate() { }

    @Override
    public void onDataSetChanged() { mRecipeEntries = mDb.recipeDao().loadAllRecipes(); }

    @Override
    public void onDestroy() { }

    @Override
    public int getCount() {
        return mRecipeEntries == null ? 0 : mRecipeEntries.getValue().size();
    }

    // same as onBindViewHolder
    @Override
    public RemoteViews getViewAt(int position) {
        List<RecipeEntry> recipeEntries= mDb.recipeDao().loadAllRecipes().getValue();
        RecipeEntry recipeEntry = recipeEntries.get(position);
        int imgResource = getImage(recipeEntry);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_widget);
        views.setImageViewResource(R.id.iv_widget_baking_image, imgResource);
        Bundle extras = new Bundle();
        extras.putInt(mContext.getString(R.string.POSITION), recipeEntry.getArrayListIndex());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.iv_widget_baking_image, fillInIntent);
        return null;
    }

    private int getImage(RecipeEntry recipeEntry) {
        String identifier = "";
        for (int i = 0; i < DEFAULT_RECIPES.length; i++) {
            if (recipeEntry.getRecipeName().equals(DEFAULT_RECIPES[i])) {
                identifier = DEFAULT_RECIPES_IMG[i];
                break;
            }
        }
        if (TextUtils.isEmpty(identifier)) {
            identifier = "no_image";
        }
        return mContext.getResources().getIdentifier(recipeEntry.getRecipeName(),
                "drawable", mContext.getPackageName());
    }

    @Override
    public RemoteViews getLoadingView() { return null; }

    // Treat all items in the GridView the same
    @Override
    public int getViewTypeCount() { return 1; }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public boolean hasStableIds() { return false; }
}
