package com.btandjaja.www.bakingrecipes;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.btandjaja.www.bakingrecipes.data.RecipeDatabase;
import com.btandjaja.www.bakingrecipes.data.RecipeEntry;
import com.btandjaja.www.bakingrecipes.data.RecipeListViewModel;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {
    private static boolean mDatabase;
    // TODO remove
    private static final String TAG = BakingWidgetProvider.class.getSimpleName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        new GetDatabase(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, getIntent(context), 0);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        rv.setImageViewResource(R.id.iv_widget_baking_image, getImage(context));
        rv.setOnClickPendingIntent(R.id.iv_widget_baking_image, pendingIntent);
        //TODO remove
        Log.d("****widget", "database");
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private static Intent getIntent(Context context) {
        Intent intent = new Intent(context, CheckDatabase.class);
        return intent;
    }

    private static int getImage(Context context) {
        String resourceName = "launcher_icon";
        return context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
    }

    private static class GetDatabase extends AsyncTask<Void, Void, Void> {
        RecipeDatabase mDb;

        public GetDatabase(Context context) {
            mDb = RecipeDatabase.getsInstance(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<RecipeEntry> entries = mDb.recipeDao().loadAllRecipes().getValue();
            mDatabase = entries == null;
            return null;
        }
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