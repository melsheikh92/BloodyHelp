package com.example.mahmoud.bloodyhelp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.activities.DonorFormActivity;
import com.example.mahmoud.bloodyhelp.models.Donor;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InsertNewDonorService extends IntentService {

    static Donor donor;

    public InsertNewDonorService() {
        super("InsertNewDonorService");
    }


    // TODO: Customize helper method
    public static void startAction(Context context, Donor mdonor) {
        donor = mdonor;
        Intent intent = new Intent(context, InsertNewDonorService.class);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
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

                } else {
                    DonorFormActivity.showInsertingResults(false);


                }

            } else {
                DonorFormActivity.showInsertingResults(false);

            }

        } catch (Exception exep) {
            DonorFormActivity.showInsertingResults(false);


        }
    }
}