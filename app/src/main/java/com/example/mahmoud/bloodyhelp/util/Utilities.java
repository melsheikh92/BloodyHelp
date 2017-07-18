package com.example.mahmoud.bloodyhelp.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.example.mahmoud.bloodyhelp.R;

/**
 * Created by Mahmoud on 26/06/2017.
 */

public class Utilities {

    static ProgressDialog progress;

    public static boolean isConnected(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    public static boolean get_settings_notif_pref(Context context) {


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        return preferences.getBoolean(context.getString(R.string.key_settings_notif), true);

    }


    public static void showProgressDialog(Context context) {

        progress = new ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();
        progress.dismiss();
        progress.show();
    }


    public static void hideProgressDialog() {
        progress.dismiss();
    }

    public static Typeface getTypeface_toms(Context context) {

        return Typeface.createFromAsset(context.getAssets(), "fonts/toms_handwritten.ttf");

    }
}
