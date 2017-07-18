package com.example.mahmoud.bloodyhelp.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

import com.example.mahmoud.bloodyhelp.widget.QuoteAppWidget;
import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.models.Quote;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateWidgetIntentService extends IntentService {

    public UpdateWidgetIntentService() {
        super("UpdateWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] widgetIDS = appWidgetManager.getAppWidgetIds(new ComponentName(this, QuoteAppWidget.class));
        QuoteAppWidget.updateAppWidget(getApplicationContext(), appWidgetManager, getQuote(), widgetIDS);
    }

    private Quote getQuote() {


        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(getApplicationContext().getString(R.string.url_qoutes))
                    .build();


            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String s = response.body().string();

                //  JSONArray jarr = new JSONArray(s);
                JSONObject jsonobj = new JSONObject(s);
                Quote quote = new Quote(jsonobj.getString("quote"), jsonobj.getString("author"), jsonobj.getString("cat"));
                return quote;

            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ;
            Log.d("myerror", "widget error");
            return null;


        }

    }


}
