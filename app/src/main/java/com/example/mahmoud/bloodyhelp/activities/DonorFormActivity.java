package com.example.mahmoud.bloodyhelp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.util.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DonorFormActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.input_name)
    EditText input_name;
    @BindView(R.id.input_email)
    EditText input_email;
    @BindView(R.id.input_desc)
    EditText inpute_desc;
    @BindView(R.id.input_mobile)
    EditText input_mobile;
    @BindView(R.id.spinner_blood)
    Spinner spinner_blood;
    @BindView(R.id.spinner_city)
    Spinner spinner_city;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    static Context mcontext;
    static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_form);
        ButterKnife.bind(this);
        mcontext = this;
        activity = this;
        setSupportActionBar(toolbar);
        tv_title.setTypeface(Utilities.getTypeface_toms(this));
        getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();

                    }
                }

        );

    }

    @OnClick(R.id.btn_submit)
    void onButtonSubmit_clicked() {
        Utilities.showProgressDialog(this);
        if (isDataValidated()) {
           // InsertNewDonorService.startActionFoo(this);
        }
        //    Toast.makeText(this, "submit button", Toast.LENGTH_LONG).show();

    }

    private boolean isDataValidated() {


        return true;
    }



    public static void showInsertingResults(boolean issucced) {
        Utilities.hideProgressDialog();
        if (issucced) {
            Toast.makeText(mcontext, "Inserted Successfully", Toast.LENGTH_LONG).show();
            activity.finish();
        } else {

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    mcontext
            );

            // set title
            alertDialogBuilder.setTitle("Error");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Process Inserted Successfully!")
                    .setCancelable(true)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();
        }

    }


//    private class LongOperation extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//
//
//            return "Executed";
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//        }
//    }
}



