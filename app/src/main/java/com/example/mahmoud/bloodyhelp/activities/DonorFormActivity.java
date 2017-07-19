package com.example.mahmoud.bloodyhelp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoud.bloodyhelp.R;
import com.example.mahmoud.bloodyhelp.models.Donor;
import com.example.mahmoud.bloodyhelp.services.InsertNewDonorService;
import com.example.mahmoud.bloodyhelp.util.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DonorFormActivity extends AppCompatActivity {

    public static final String KEY_INSERT_DONOR = "com.example.mahmoud.bloodyhelp.KEY_INSERT_DONOR";
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
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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

        input_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (input_email.getText().length() == 0) {
                    input_email.setError("Cannot be empty");

                } else {
                    if (input_email.getText().toString().trim().matches(emailPattern)) {
                    } else {

                        input_email.setError("you input not seems to be an email");

                    }

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inpute_desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inpute_desc.getText().toString().trim().length() == 0) {
                    inpute_desc.setError("cannot be empty");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (input_mobile.getText().toString().trim().length() == 0) {
                    input_mobile.setError("Cannot be empty");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (input_name.getText().toString().trim().length() == 0) {
                    input_name.setError("cannot be empty");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.btn_submit)
    void onButtonSubmit_clicked() {
        Utilities.showProgressDialog(this);
        if (isDataValidated()) {
            Intent i = new Intent(this, InsertNewDonorService.class);
            Donor donor = new Donor();
            donor.setName(input_name.getText().toString());
            donor.setCityId(spinner_city.getSelectedItemPosition()+1);
            donor.setPhone(input_mobile.getText().toString());
            donor.setEmail(input_email.getText().toString());
            donor.setTypeId(spinner_blood.getSelectedItemPosition()+1);
            donor.setDescription(inpute_desc.getText().toString());
            i.putExtra(KEY_INSERT_DONOR, donor);
            startService(i);
        }else {
            Utilities.hideProgressDialog();

        }
    }

    private boolean isDataValidated() {
        boolean flag = true;
        if (input_email.getText().toString().trim().length() == 0) {
            flag = false;
            input_email.setError(mcontext.getString(R.string.empty));
        } else {
            if (input_email.getText().toString().trim().matches(emailPattern)) {
            } else {
                input_email.setError(mcontext.getString(R.string.not_email));
                flag = false;

            }
        }
        if (input_name.getText().toString().trim().length() == 0) {
            input_name.setError(mcontext.getString(R.string.empty));
            flag = false;
        }
        if (input_mobile.getText().toString().trim().length() == 0) {
            input_mobile.setError(mcontext.getString(R.string.empty));
            flag = false;
        }
        if (inpute_desc.getText().toString().trim().length() == 0) {
            inpute_desc.setError(mcontext.getString(R.string.empty));
            flag = false;
        }
        return flag;
    }


    public static void showInsertingResults(boolean issucced) {
        Utilities.hideProgressDialog();
        if (issucced) {
            Toast.makeText(mcontext, mcontext.getString(R.string.inserted_successfully), Toast.LENGTH_LONG).show();
            activity.finish();
        } else {

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    mcontext
            );

            // set title
            alertDialogBuilder.setTitle(mcontext.getString(R.string.error));

            // set dialog message
            alertDialogBuilder
                    .setMessage(mcontext.getString(R.string.process_failed))
                    .setCancelable(true)
                    .setPositiveButton(mcontext.getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }


}



