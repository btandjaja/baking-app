package com.btandjaja.www.bakingrecipes;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.btandjaja.www.bakingrecipes.data.AppExecutors;
import com.btandjaja.www.bakingrecipes.data.RecipeDatabase;
import com.btandjaja.www.bakingrecipes.data.RecipeEntry;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

//    private static RecipeDatabase mDb;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
//        mDb = RecipeDatabase.getsInstance(context);
//        // Retrieve from db
//        final LiveData<List<RecipeEntry>> recipeEntryList = mDb.recipeDao().loadAllRecipes();
//        // TODO put it into the gridview result
//        recipeEntryList.observe(, new Observer<List<RecipeEntry>>() {
//            @Override
//            public void onChanged(@Nullable List<RecipeEntry> recipeEntries) {
//
//            }
//        });

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews rv;
        if (width < 300) {
//            rv =
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

        Intent intent = new Intent(context, ListOfRecipesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.iv_widget_baking_image, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

