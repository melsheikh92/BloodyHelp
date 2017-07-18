package com.example.mahmoud.bloodyhelp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.activities.DonorFormActivity;
import com.example.mahmoud.bloodyhelp.models.Donor;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InsertNewDonorService extends IntentService {

    static Donor donor;

    public InsertNewDonorService() {
        super("InsertNewDonorService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("myinsert", "Service Started!");

        if (intent != null) {
            donor = intent.getParcelableExtra(DonorFormActivity.KEY_INSERT_DONOR);
        }
        try {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(getApplicationContext().getString(R.string.url_root) + "InsertUser/" + donor.getName() + "/" + donor.getPhone() + "/" + donor.getTypeId() + "/" + donor.getCityId() + "/" + donor.getEmail() + "/" + donor.getDescription())
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String s = response.body().string();
                if (s.contains("true")) {
                    DonorFormActivity.showInsertingResults(true);
                    //  Toast.makeText(this, "Inserted successfully", Toast.LENGTH_SHORT).show();

                } else {
                    DonorFormActivity.showInsertingResults(false);
                    Log.d("myinsert", "Service else!");


                }

            } else {
                DonorFormActivity.showInsertingResults(false);
                Log.d("myinsert", "Service else else!");

            }

        } catch (Exception exep) {
            Log.d("myinsert", "Service exception!");

            DonorFormActivity.showInsertingResults(false);
            FirebaseCrash.log(exep.toString());

        }
    }
}