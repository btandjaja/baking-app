package com.btandjaja.www.bakingrecipes;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, getIntent(context), 0);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        rv.setImageViewResource(R.id.iv_widget_baking_image, getImage(context));
        rv.setOnClickPendingIntent(R.id.iv_widget_baking_image, pendingIntent);
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