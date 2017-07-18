package com.example.mahmoud.bloodyhelp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.models.Quote;
import com.example.mahmoud.bloodyhelp.services.UpdateWidgetIntentService;
import com.example.mahmoud.bloodyhelp.util.Utilities;

public class QuoteAppWidget extends AppWidgetProvider {

    private static final String ACTION_UPDATEWIDGET = "com.example.mahmoud.bloodyhelp.widget.QuoteAppWidget.UpdateWidget";

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, Quote qoute,
                                       int[] appWidgetId) {
        if (qoute != null) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quote_app_widget);
            views.setTextViewText(R.id.appwidget_text, "\" " + qoute.getQuote() + " \"");
            views.setTextViewText(R.id.tv_author, "~ " + qoute.getAuthor());
            Intent intent = new Intent(context, QuoteAppWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.appwidget_btn_next, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        if (Utilities.isConnected(context)) {

            Intent intent_auto = new Intent(context, UpdateWidgetIntentService.class);
            intent_auto.setAction(ACTION_UPDATEWIDGET);
            context.startService(intent_auto);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (Utilities.isConnected(context)) {
            Intent intent_auto = new Intent(context, UpdateWidgetIntentService.class);
            intent_auto.setAction(ACTION_UPDATEWIDGET);
            context.startService(intent_auto);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

